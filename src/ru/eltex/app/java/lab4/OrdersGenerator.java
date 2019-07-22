package ru.eltex.app.java.lab4;

import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab1.Phone;
import ru.eltex.app.java.lab1.Smartphone;
import ru.eltex.app.java.lab1.Tablet;
import ru.eltex.app.java.lab2.Credentials;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;
import ru.eltex.app.java.lab3.ShoppingCart;

import java.util.Random;

public class OrdersGenerator implements Runnable {
    private static Orders<Order> orders = new Orders<>();
    private Thread thread;
    private int sleepTime;
    private static boolean fStop;
    private static Electronic[] electronics;
    private static int credN = 1;

    OrdersGenerator(int sleep) {
        int N = 20;
        if (electronics == null) {
            electronics = new Electronic[3 * N];
            for (int i = 0; i < N; i++) {
                (electronics[i] = new Phone()).create();
                (electronics[i + N] = new Smartphone()).create();
                (electronics[i + 2 * N] = new Tablet()).create();
            }
        }

        sleepTime = sleep;
        thread = new Thread(this, "Sleep" + sleep);
        System.out.println("New thread: " + thread);
        thread.start();
    }

    @Override
    public void run() {
        while (!fStop) {
            /** создание пользователя */
            Credentials cred = new Credentials("Surname" + credN++, "Name" + credN++,
                    "Patronym" + credN++, "Email" + credN++ + "@gmail.com");
            /** создание корзины */
            ShoppingCart<Electronic> shoppingCart = new ShoppingCart<>();
            for (int j = 0; j < new Random().nextInt(3) + 1; j++)
                shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
            /** совершение покупки */
            orders.offer(shoppingCart, cred);
            System.out.println("New order generated " + sleepTime + " (count = " + orders.getOrders().size() + ")");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Orders<Order> getOrders() {
        return orders;
    }

    public void setStop() {
        fStop = true;
    }
}
