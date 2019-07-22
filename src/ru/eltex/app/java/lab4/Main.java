package ru.eltex.app.java.lab4;

public class Main {
    public static void main(String[] args) {
        OrdersGenerator orderGen1 = new OrdersGenerator(1400);
        OrdersGenerator orderGen2 = new OrdersGenerator(3200);
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
