package ru.eltex.app.lab4;

import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;

public abstract class ACheck implements Runnable {
    final int SLEEPTIME = 5000;
    Orders<Order> orders;
    Thread thread;
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
