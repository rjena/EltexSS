package ru.eltex.app.java.lab3;

import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab2.Credentials;
import ru.eltex.app.java.lab2.OrderStatusEnum;

import java.io.Serializable;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * класс-коллекция для объединения списка заказов
 */
public class Orders<O> implements Serializable {
    private ArrayList<O> orders;
    private HashMap<Date, O> ordersByCreationTime;

    public Orders() {
        orders = new ArrayList<>();
        ordersByCreationTime = new HashMap<>();
    }

    public Orders(ArrayList<O> orders) {
        this.orders = orders;
    }

    public void add(O o) {
        orders.add(o);
        ordersByCreationTime.put(((Order) o).getCreationTime(), o);
    }

    public void delete(O o) {
        orders.remove(o);
        ordersByCreationTime.remove(((Order) o).getCreationTime(), o);
    }

    public ArrayList<O> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<O> orders) {
        this.orders = orders;
    }

    public HashMap<Date, O> getOrdersByCreationTime() {
        return ordersByCreationTime;
    }

    public void offer(ShoppingCart<Electronic> cart, Credentials creds) {
        /**
         * «корзина» с товарами объединяется в объект «заказ» вместе с данными пользователя
         * и добавляется в класс-коллекцию «заказы»
         */
        Order order = new Order(creds, cart);
        orders.add((O) order);
        ordersByCreationTime.put(order.getCreationTime(), (O) order);
    }

    public void offer(ShoppingCart<Electronic> cart, Credentials creds, InetAddress ip) {
        /**
         * «корзина» с товарами объединяется в объект «заказ» вместе с данными пользователя
         * и добавляется в класс-коллекцию «заказы»
         * (ip пользователя добавлен)
         */
        Order order = new Order(creds, cart, ip);
        orders.add((O) order);
        ordersByCreationTime.put(order.getCreationTime(), (O) order);
    }

    public void checkTime() throws ParseException {
        /**
         * проверка заказов - обход коллекции и удаление всех объектов
         */
        Calendar c = Calendar.getInstance();
        for (O oo : orders) {
            Order o = (Order) oo;
            c.setTime(o.getCreationTime());
            c.add(Calendar.SECOND, o.getWaitingTime());
            if (new Date().compareTo(c.getTime()) > 0 && o.getStatus().equals(OrderStatusEnum.Pending.name())) {
                o.setStatus(OrderStatusEnum.Processed.name());
                orders.remove(o);
            }
        }
    }
}
