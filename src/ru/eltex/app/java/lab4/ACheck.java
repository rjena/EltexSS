package ru.eltex.app.java.lab4;

import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

public abstract class ACheck implements Runnable {
    Orders<Order> orders;
    Thread thread;
    final int SLEEPTIME = 5000;
    boolean fStop;

    ACheck(Orders<Order> orders, String status) {
        this.orders = orders;
        thread = new Thread(this, status);
        System.out.println("New thread: " + thread);
        thread.start();
    }

    public void setStop() {
        fStop = true;
    }
}
