package ru.eltex.app.lab4;

import ru.eltex.app.lab2.OrderStatusEnum;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;

public class CheckPending extends ACheck {

    public CheckPending(Orders<Order> orders) {
        super(orders, OrderStatusEnum.Pending.name());
    }

    /**
     * Если у заказа статус "в ожидании", то меняется статус на «обработан».
     */
    @Override
    public void run() {
        while (!fStop) {
            for (Order o : orders.getOrders())
                if (o.getStatus().equals(OrderStatusEnum.Pending.name()))
                    o.setStatus(OrderStatusEnum.Processed.name());
            System.out.println("Pending orders checked: " + orders.getOrders().size());
            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
