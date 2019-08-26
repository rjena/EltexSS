package ru.eltex.app.lab4;

import ru.eltex.app.lab2.OrderStatusEnum;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class CheckProcessed extends ACheck {

    public CheckProcessed(Orders<Order> orders) {
        super(orders, OrderStatusEnum.Processed.name());
    }

    /**
     * Если у заказа статус "обработан", заказ удаляется из списка.
     */
    @Override
    public void run() {
        while (!fStop) {
            ArrayList<Order> ords = new ArrayList<>(orders.getOrders());
            for (Order o : ords)
                if (o.getStatus().equals(OrderStatusEnum.Processed.name())) {
                    if (o.getIp() != null) {
                        /** При изменении на статус «обработан» клиенту по UDP высылается оповещение */
                        try (DatagramSocket socket = new DatagramSocket()) {
                            byte[] buf = "Processed".getBytes();
                            socket.send(new DatagramPacket(buf, buf.length, o.getIp(), 8087));
                        } catch (IOException e) { e.getMessage(); }
                    }
                    synchronized (orders) {
                        orders.delete(o);
                    }
                }
            synchronized (orders) {
                System.out.println("Processed orders checked: " + ords.size() + " -> " + orders.getOrders().size());
            }
            try { Thread.sleep(SLEEPTIME); } catch (InterruptedException e) { break; }
        }
    }
}
