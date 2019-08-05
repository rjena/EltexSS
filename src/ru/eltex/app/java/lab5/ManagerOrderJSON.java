package ru.eltex.app.java.lab5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ManagerOrderJSON extends AManageOrder {
    private String pathname = "/home/jen/EltexSS/src/ru/eltex/app/java/lab5/files/json.txt";
    private File file;

    public ManagerOrderJSON() {
        file = new File(pathname);
    }

    @Override
    public Order readById(UUID id) {
        if (file.exists() && file.length() != 0) {
            JsonReader jr = null;
            try {
                Gson gson = new GsonBuilder().create();
                jr = new JsonReader(new FileReader(file));
                jr.setLenient(true);
                Order order;
                while (jr.peek() != JsonToken.END_DOCUMENT) {
                    order = gson.fromJson(jr, Order.class);
                    if (order.getId().equals(id)) return order;
                }
            } catch (IOException e) { e.printStackTrace();
            } finally { if (jr != null) try { jr.close(); } catch (IOException e) { e.printStackTrace(); } }
        }
        return null;
    }

    @Override
    public void saveById(Order order) {
        /**
         * есть заказ с указанным id -> удалить и добавить заказ
         * нет заказа с указанным id -> добавить заказ
         */
        try {
            ArrayList<Order> orders = new ArrayList<>();
            if (file.exists()) {
                if (file.length() != 0) {
                    JsonReader jr = null;
                    try {
                        Gson gson = new GsonBuilder().create();
                        jr = new JsonReader(new FileReader(file));
                        jr.setLenient(true);
                        Order o;
                        while (jr.peek() != JsonToken.END_DOCUMENT) {
                            o = gson.fromJson(jr, Order.class);
                            if (!o.getId().equals(order.getId())) orders.add(o);
                        }
                    } finally { if (jr != null) try { jr.close(); } catch (IOException e) { e.printStackTrace(); } }
                }
            } else file.createNewFile();
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                for (Order o : orders) fw.write(gson.toJson(o) + "\n");
                fw.write(gson.toJson(order) + "\n");
            } finally { if (fw != null) try { fw.close(); } catch (IOException e) { e.getMessage(); } }
        } catch (IOException e) { e.getMessage(); }
    }

    @Override
    public Orders<Order> readAll() {
        return null;
    }

    @Override
    public void saveAll(Orders<Order> orders) {

    }

}
