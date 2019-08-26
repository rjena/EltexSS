package ru.eltex.app.lab8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab1.Phone;
import ru.eltex.app.lab1.Smartphone;
import ru.eltex.app.lab1.Tablet;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;
import ru.eltex.app.lab3.ShoppingCart;
import ru.eltex.app.lab8.repository.ElectronicRepository;
import ru.eltex.app.lab8.repository.OrderRepository;
import ru.eltex.app.lab8.repository.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

@Service
public class ServiceDB {
    @Autowired private ElectronicRepository electronicRepository;
    @Autowired private ShoppingCartRepository shoppingCartRepository;
    @Autowired private OrderRepository orderRepository;

    public Order readById(String id) {
        try { return orderRepository.findById(UUID.fromString(id)).get(); }
        catch (Exception e) { return null; }
    }

    public Orders<Order> readAll() {
        return new Orders<>(new ArrayList<>(orderRepository.findAll()));
    }

    public String delById(String id) {
        try { UUID uuid = UUID.fromString(id); } catch (IllegalArgumentException e) { return "3"; }
        Order o = readById(id);
        if (o != null) {
            orderRepository.delete(o);
            return "0";
        } else return "1";
    }

    public String addToCardAndGetOrderId(String uuid) {
        try { if (Integer.parseInt(uuid) < 1) return "incorrect"; }
        catch (NumberFormatException | NullPointerException e) { return "incorrect"; }
        int id = Integer.parseInt(uuid);
        ShoppingCart cart;
        try { cart = shoppingCartRepository.findById(id).get(); }
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
            shoppingCartRepository.saveAndFlush(cart);
            electronicRepository.saveAndFlush(e);
            return e.getID().toString();
        } catch (Exception ex) { ex.getMessage(); }
        return null;
    }
}
