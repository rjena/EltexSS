package ru.eltex.app.java.lab4;

import ru.eltex.app.java.lab2.OrderStatusEnum;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

import java.util.ArrayList;

public class ACheckProcessed extends ACheck {

    public ACheckProcessed(Orders<Order> orders) {
        super(orders, OrderStatusEnum.Processed.name());
    }

    /**
     * Если у заказа статус "обработан", заказ удаляется из списка.
     */
    @Override
    public void run() {
        while (true) {
            if (!fStop) {
                ArrayList<Order> ords = new ArrayList<>(orders.getOrders());
                for (Order o : ords)
                    if (o.getStatus().equals(OrderStatusEnum.Processed.name()))
                        orders.delete(o);
                System.out.println("Processed orders checked: " + ords.size() + " -> " + orders.getOrders().size());
            } else return;
            try {
                Thread.sleep(SLEEPTIME + SLEEPTIME / 2);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}