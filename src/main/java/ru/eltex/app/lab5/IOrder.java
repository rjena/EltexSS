package ru.eltex.app.lab5;

import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;

import java.util.UUID;

public interface IOrder {
    Order readById(UUID id);

    void saveById(Order order);

    Orders<Order> readAll();

    void saveAll(Orders<Order> orders);
}
