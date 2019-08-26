package ru.eltex.app.lab5;

import ru.eltex.app.lab2.Credentials;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;
import ru.eltex.app.lab4.OrdersGenerator;

public class Main {
    public static void main(String[] args) {
        OrdersGenerator orderGen1 = new OrdersGenerator(1000,
                new Credentials("Surname", "Name", "Patronym", "email@gmail.com"));
        try {
            Thread.sleep(5000);
            orderGen1.setStop();
        } catch (InterruptedException e) { orderGen1.setStop(); }
        Orders<Order> orders = orderGen1.getOrders();

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
                "                  JSON FILE\n==============================================\n");
        ManagerOrderJSON moj = new ManagerOrderJSON();
        moj.saveAll(orders);
        System.out.println("All orders were saved\n");
        orders = moj.readAll();
        System.out.println("All orders were read");
        if (orders != null && orders.getOrders().size() > 0) {
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
            System.out.println("\n==============================================\n");
            Order order = moj.readById(orders.getOrders().get(0).getId());
            System.out.println("Order " + order.getId() + " was read");
            order.setStatus("Processed");
            System.out.println("\nStatus of Order " + order.getId() + " was changed\n");
            moj.saveById(order);
            System.out.println("Order " + order.getId() + " was saved\n");
            orders = moj.readAll();
            System.out.println("All orders were read");
            for (Order o : orders.getOrders()) System.out.println(o.getId() + " " + o.getStatus());
        }
    }
}
