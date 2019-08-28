package ru.eltex.app.lab8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab1.Phone;
import ru.eltex.app.lab1.Smartphone;
import ru.eltex.app.lab1.Tablet;
import ru.eltex.app.lab2.Credentials;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;
import ru.eltex.app.lab3.ShoppingCart;
import ru.eltex.app.lab8.repositories.CredentialsRepo;
import ru.eltex.app.lab8.repositories.ElectronicRepo;
import ru.eltex.app.lab8.repositories.OrderRepo;
import ru.eltex.app.lab8.repositories.ShoppingCartRepo;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

@Service
public class ServiceDB {
    @Autowired private ElectronicRepo electronicRepo;
    @Autowired private ShoppingCartRepo shoppingCartRepo;
    @Autowired private OrderRepo orderRepo;
    @Autowired private CredentialsRepo credentialsRepo;

    public Order readById(String id) {
        try { return orderRepo.findById(UUID.fromString(id)).get(); }
        catch (Exception e) { return null; }
    }

    public Orders<Order> readAll() {
        return new Orders<>(new ArrayList<>(orderRepo.findAll()));
    }

    public String delById(String id) {
        try { UUID uuid = UUID.fromString(id); } catch (IllegalArgumentException e) { return "3"; }
        Order o = readById(id);
        if (o != null) {
            orderRepo.delete(o);
            return "0";
        } else return "1";
    }

    public String addToCardAndGetOrderId(String uuid) {
        try { if (Integer.parseInt(uuid) < 1) return "incorrect"; }
        catch (NumberFormatException | NullPointerException e) { return "incorrect"; }
        int id = Integer.parseInt(uuid);
        ShoppingCart cart;
        try { cart = shoppingCartRepo.findById(id).get(); }
        catch (NoSuchElementException ex) { cart = new ShoppingCart<Electronic>(id); }
        Electronic e;
        int t = new Random().nextInt(3);
        if (t == 1) e = new Phone();
        else if (t == 2) e = new Smartphone();
        else e = new Tablet();
        e.create();
        try {
            e.setCart(cart);
            cart.add(e);
            shoppingCartRepo.saveAndFlush(cart);
            electronicRepo.saveAndFlush(e);
            return e.getID().toString();
        } catch (Exception ex) { ex.getMessage(); }
        return null;
    }

    public String addOrders() {
        int count = 0;
        while (count < 3) {
            Credentials cred = new Credentials("Surname" + ++count, "Name" + count, "Patronym" + count,
                    "email" + count + "@gmail.com");
            ShoppingCart<Electronic> cart = new ShoppingCart<>(count);
            Electronic[] electronics = new Electronic[new Random().nextInt(3) + 1];
            for (int j = 0; j < electronics.length; j++) {
                Electronic e;
                int t = new Random().nextInt(3);
                if (t == 1) e = new Phone();
                else if (t == 2) e = new Smartphone();
                else e = new Tablet();
                e.create();
                e.setCart(cart);
                electronics[j] = e;
            }
            Order order = new Order(cred, cart);
            credentialsRepo.saveAndFlush(cred);
            shoppingCartRepo.saveAndFlush(cart);
            for (int j = 0; j < electronics.length; j++)
                electronicRepo.saveAndFlush(electronics[j]);
            orderRepo.saveAndFlush(order);
        }
        return null;
    }
}
