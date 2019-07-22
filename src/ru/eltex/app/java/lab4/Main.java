package ru.eltex.app.java.lab4;

public class Main {
    public static void main(String[] args) {
        OrdersGenerator orderGen1 = new OrdersGenerator(1400);
        OrdersGenerator orderGen2 = new OrdersGenerator(3200);
        ACheckPending pendChecker = new ACheckPending(orderGen1.getOrders());
        ACheckProcessed procChecker = new ACheckProcessed(orderGen1.getOrders());

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
