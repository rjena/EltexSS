package ru.eltex.app.java.lab5;

import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ManagerOrderFile extends AManageOrder {
    private String pathname = "/home/jen/EltexSS/src/ru/eltex/app/java/lab5/files/bin.dat";
    private File file;

    public ManagerOrderFile() {
        file = new File(pathname);
    }

    @Override
    public Order readById(UUID id) {
        if (file.exists())
            try (FileInputStream fis = new FileInputStream(file)) {
                if (fis.available() > 0) {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Order order;
                    while (fis.available() > 0) {
                        order = (Order) ois.readObject();
                        if (order.getId().equals(id)) return order;
                    }
                }
            } catch (IOException | ClassNotFoundException e) { e.getMessage(); }
        return null;
    }

    @Override
    public void saveById(Order order) {
        /**
         * есть заказ с указанным id -> удалить и добавить заказ
         * нет заказа с указанным id -> добавить заказ
         */
        try {
            ArrayList<Order> orders = new ArrayList<>();
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                if (fis.available() > 0) {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    while (fis.available() > 0) {
                        Order o = (Order) ois.readObject();
                        if (!o.getId().equals(order.getId())) orders.add(o);
                    }
                }
            } else file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            for (Order o : orders) oos.writeObject(o);
            oos.writeObject(order);
        } catch (IOException | ClassNotFoundException e) { e.getMessage(); }
    }

    @Override
    public Orders<Order> readAll() {
        if (file.exists()) {
            Orders<Order> orders = new Orders<>();
            try (FileInputStream fis = new FileInputStream(file)) {
                if (fis.available() > 0) {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    while (fis.available() > 0) orders.add((Order) ois.readObject());
                }
            } catch (IOException | ClassNotFoundException e) { e.getMessage(); }
            return orders;
        }
        return null;
    }

    @Override
    public void saveAll(Orders<Order> orders) {
        try {
            Set<UUID> ids = new HashSet<>();
            for (Order o : orders.getOrders()) ids.add(o.getId());
            ArrayList<Order> fileOrders = new ArrayList<>();
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                if (fis.available() > 0) {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    while (fis.available() > 0) {
                        Order o = (Order) ois.readObject();
                        if (!ids.contains(o.getId())) orders.add(o);
                    }
                    while (fis.available() > 0) fileOrders.add((Order) ois.readObject());
                }
            } else file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            fileOrders.addAll(orders.getOrders());
            for (Order o : fileOrders) oos.writeObject(o);
        } catch (IOException | ClassNotFoundException e) { e.getMessage(); }
    }
}