package ru.eltex.app.java.lab5;

import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

import java.util.UUID;

public interface IOrder {
    Order readById(UUID id);

    void saveById(Order order);

    Orders<Order> readAll();

    void saveAll(Orders<Order> orders);
}
