package ru.eltex.app.java.lab5;

import ru.eltex.app.java.lab2.Credentials;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;
import ru.eltex.app.java.lab3.ShoppingCart;

public class Main {
    public static void main(String[] args) {
        ManagerOrderFile mof = new ManagerOrderFile();
        Orders<Order> orders = new Orders<>();
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        mof.saveAll(orders);
        orders = mof.readAll();
        if (orders != null && orders.getOrders().size() > 0) {
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
            System.out.println("\n===============================================\n");
            Order order = mof.readById(orders.getOrders().get(0).getId());
            order.setStatus("Processed");
            mof.saveById(order);
            orders = mof.readAll();
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
        }
    }
}
