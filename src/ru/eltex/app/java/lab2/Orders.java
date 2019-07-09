package ru.eltex.app.java.lab2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Orders {
    // класс-коллекция для объединения списка заказов

    private ArrayList<Order> orders;
    private HashMap<Date, Order> ordersByCreationTime;

    public Orders() {
        orders = new ArrayList<>();
        ordersByCreationTime = new HashMap<>();
    }

    public Orders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public HashMap<Date, Order> getOrdersByCreationTime() {
        return ordersByCreationTime;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void offer(ShoppingCart cart, Credentials creds) {
        /* «корзина» с товарами объединяется в объект «заказ» вместе с данными пользователя
        и добавляется в класс-коллекцию «заказы» */
        Order order = new Order(creds, cart);
        orders.add(order);
        ordersByCreationTime.put(order.getCreationTime(), order);
    }

    public void checkTime() throws ParseException {
        // проверка заказов - обход коллекции и удаление всех объектов
        Calendar c = Calendar.getInstance();
        for (Order o : orders) {
            c.setTime(o.getCreationTime());
            c.add(Calendar.SECOND, o.getWaitingTime());
            if (new Date().compareTo(c.getTime()) > 0 && o.getStatus().equals(OrderStatusEnum.Pending.name())) {
                o.setStatus(OrderStatusEnum.Processed.name());
                orders.remove(o);
            }
        }
    }
}
