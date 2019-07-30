package ru.eltex.app.java.lab5;

import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ManagerOrderJSON extends AManageOrder {
    private String pathname = "/home/jen/EltexSS/src/ru/eltex/app/java/lab5/files/json.txt";
    private File file;

    public ManagerOrderJSON() {
        file = new File(pathname);
    }

    @Override
    public Order readById(UUID id) {
        return null;
    }

    @Override
    public void saveById(Order order) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * есть заказ с указанным id -> удалить и добавить заказ
         * нет заказа с указанным id -> добавить заказ
         */
    }

    @Override
    public Orders<Order> readAll() {
        return null;
    }

    @Override
    public void saveAll(Orders<Order> orders) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
