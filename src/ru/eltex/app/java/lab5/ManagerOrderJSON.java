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
import java.util.HashSet;
import java.util.UUID;

public class ManagerOrderJSON extends AManageOrder {
    private String pathname = new File("").getAbsolutePath() + "/src/ru/eltex/app/java/lab5/files/json.txt";
    private File file;

    public ManagerOrderJSON() {
        file = new File(pathname);
    }

    @Override
    public Order readById(UUID id) {
        if (file.exists() && file.length() != 0) {
            try (JsonReader jr = new JsonReader(new FileReader(file))) {
                Gson gson = new GsonBuilder().create();
                Order order;
                while (jr.peek() != JsonToken.END_DOCUMENT)
                    try {
                        order = gson.fromJson(jr, Order.class);
                        if (order.getId().equals(id)) return order;
                    } catch (ClassCastException e) { e.getMessage(); }
            } catch (IOException e) { e.getMessage(); }
        }
        return null;
    }

    @Override
    public void saveById(Order order) {
        /**
         * есть заказ с указанным id -> удалить и добавить заказ
         * нет заказа с указанным id -> добавить заказ
         */
        ArrayList<Order> orders = new ArrayList<>();
        if (file.exists()) {
            if (file.length() != 0)
                try (JsonReader jr = new JsonReader(new FileReader(file))) {
                    Gson gson = new GsonBuilder().create();
                    jr.setLenient(true);
                    Order o;
                    while (jr.peek() != JsonToken.END_DOCUMENT)
                        try {
                            o = gson.fromJson(jr, Order.class);
                            if (!o.getId().equals(order.getId())) orders.add(o);
                        } catch (ClassCastException e) { e.getMessage(); }
                } catch (IOException e) { e.getMessage(); }
        } else try { file.createNewFile(); } catch (IOException e) { e.getMessage(); }
        try (FileWriter fw = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (Order o : orders) fw.write(gson.toJson(o) + "\n");
            fw.write(gson.toJson(order) + "\n");
        } catch (IOException e) { e.getMessage(); }
    }

    @Override
    public Orders<Order> readAll() {
        if (file.exists() && file.length() != 0) {
            Orders<Order> orders = null;
            try (JsonReader jr = new JsonReader(new FileReader(file))) {
                orders = new Orders<>();
                Gson gson = new GsonBuilder().create();
                jr.setLenient(true);
                while (jr.peek() != JsonToken.END_DOCUMENT)
                    try {
                        orders.add(gson.fromJson(jr, Order.class));
                    } catch (ClassCastException e) { e.getMessage(); }
            } catch (IOException e) { e.getMessage(); }
            return orders;
        }
        return null;
    }

    @Override
    public void saveAll(Orders<Order> orders) {
        HashSet<UUID> ids = new HashSet<>();
        for (Order o : orders.getOrders()) ids.add(o.getId());
        ArrayList<Order> fileOrders = new ArrayList<>();
        if (file.exists()) {
            if (file.length() != 0)
                try (JsonReader jr = new JsonReader(new FileReader(file))) {
                    Gson gson = new GsonBuilder().create();
                    jr.setLenient(true);
                    Order o;
                    while (jr.peek() != JsonToken.END_DOCUMENT)
                        try {
                            o = gson.fromJson(jr, Order.class);
                            if (!ids.contains(o.getId())) fileOrders.add(o);
                        } catch (ClassCastException e) { e.getMessage(); }
                } catch (IOException e) { e.getMessage(); }
        } else try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        fileOrders.addAll(orders.getOrders());
        try (FileWriter fw = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (Order o : fileOrders) fw.write(gson.toJson(o) + "\n");
        } catch (IOException e) { e.getMessage(); }
    }
}