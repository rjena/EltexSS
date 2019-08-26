package ru.eltex.app.lab5;

import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class ManagerOrderFile extends AManageOrder {
    private String pathname = new File("").getAbsolutePath() + "/src/main/java/ru/eltex/app/lab5/files/bin.dat";

    public ManagerOrderFile() {
        file = new File(pathname);
    }

    @Override
    public Order readById(UUID id) {
        if (file.exists())
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                Order order;
                while (fis.available() > 0)
                    try {
                        order = (Order) ois.readObject();
                        if (order.getId().equals(id)) return order;
                    } catch (ClassNotFoundException e) { e.getMessage(); }
            } catch (IOException e) { e.getMessage(); }
        return null;
    }

    @Override
    public void saveById(Order order) {
        /**
         * есть заказ с указанным id -> удалить и добавить заказ
         * нет заказа с указанным id -> добавить заказ
         */
        ArrayList<Order> orders = new ArrayList<>();
        if (file.exists())
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                while (fis.available() > 0)
                    try {
                        Order o = (Order) ois.readObject();
                        if (!o.getId().equals(order.getId())) orders.add(o);
                    } catch (ClassNotFoundException e) { e.getMessage(); }
            } catch (IOException e) { e.getMessage(); }
        else try { file.createNewFile(); } catch (IOException e) { e.getMessage(); }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Order o : orders) oos.writeObject(o);
            oos.writeObject(order);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public Orders<Order> readAll() {
        if (file.exists()) {
            Orders<Order> orders = null;
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                orders = new Orders<>();
                while (fis.available() > 0)
                    try {
                        orders.add((Order) ois.readObject());
                    } catch (ClassNotFoundException e) { e.getMessage(); }
            } catch (IOException e) { e.getMessage(); }
            return orders;
        }
        return null;
    }

    @Override
    public void saveAll(Orders<Order> orders) {
        HashSet<UUID> ids = new HashSet<>();
        for (Order o : orders.getOrders()) ids.add(o.getId());
        ArrayList<Order> fileOrders = new ArrayList<>();
        if (file.exists())
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                Order o;
                while (fis.available() > 0)
                    try {
                        o = (Order) ois.readObject();
                        if (!ids.contains(o.getId())) fileOrders.add(o);
                    } catch (ClassNotFoundException e) { e.getMessage(); }
            } catch (IOException e) { e.getMessage(); }
        else try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            fileOrders.addAll(orders.getOrders());
            for (Order o : fileOrders) oos.writeObject(o);
        } catch (IOException e) { e.getMessage(); }
    }
}