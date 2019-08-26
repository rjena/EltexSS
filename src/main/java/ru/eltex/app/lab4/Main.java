package ru.eltex.app.lab4;

import ru.eltex.app.lab2.Credentials;

public class Main {
    public static void main(String[] args) {
        OrdersGenerator orderGen1 = new OrdersGenerator(1400,
                new Credentials("Surname1", "Name1", "Patronym1", "email1@gmail.com"));
        OrdersGenerator orderGen2 = new OrdersGenerator(3200,
                new Credentials("Surname2", "Name2", "Patronym2", "email2@gmail.com"));
        CheckPending pendChecker = new CheckPending(orderGen1.getOrders());
        CheckProcessed procChecker = new CheckProcessed(orderGen1.getOrders());

        try {
            Thread.sleep(30000);
            pendChecker.setStop();
            procChecker.setStop();
            orderGen1.setStop();
        } catch (InterruptedException e) {
            pendChecker.setStop();
            procChecker.setStop();
            orderGen1.setStop();
            //orderGen2.setStop();
        }
    }
}
