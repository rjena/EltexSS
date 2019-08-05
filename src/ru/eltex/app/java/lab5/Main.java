package ru.eltex.app.java.lab5;

import ru.eltex.app.java.lab2.Credentials;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;
import ru.eltex.app.java.lab3.ShoppingCart;

public class Main {
    public static void main(String[] args) {
        Orders<Order> orders = new Orders<>();
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));
        orders.add(new Order(new Credentials(), new ShoppingCart<>()));

        System.out.println("==============================================\n" +
                "                 BINARY FILE\n==============================================");
        ManagerOrderFile mof = new ManagerOrderFile();
        mof.saveAll(orders);
        System.out.println("\nAll orders were saved");
        orders = mof.readAll();
        System.out.println("\nAll orders were read");
        if (orders != null && orders.getOrders().size() > 0) {
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
            System.out.println("\n==============================================");
            Order order = mof.readById(orders.getOrders().get(0).getId());
            System.out.println("\nOrder " + order.getId() + " was read");
            order.setStatus("Processed");
            System.out.println("\nStatus of Order " + order.getId() + " was changed");
            mof.saveById(order);
            System.out.println("\nOrder " + order.getId() + " was saved");
            orders = mof.readAll();
            System.out.println("\nAll orders were read");
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
        }

        System.out.println("\n\n\n==============================================\n" +
                "                  JSON FILE\n==============================================");
        ManagerOrderJSON moj = new ManagerOrderJSON();
        moj.saveAll(orders);
        System.out.println("\nAll orders were saved");
        orders = moj.readAll();
        System.out.println("\nAll orders were read");
        if (orders != null && orders.getOrders().size() > 0) {
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
            System.out.println("\n==============================================");
            Order order = moj.readById(orders.getOrders().get(0).getId());
            System.out.println("\nOrder " + order.getId() + " was read");
            order.setStatus("Processed");
            System.out.println("\nStatus of Order " + order.getId() + " was changed");
            moj.saveById(order);
            System.out.println("\nOrder " + order.getId() + " was saved");
            orders = moj.readAll();
            System.out.println("\nAll orders were read");
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
        }
    }
}
