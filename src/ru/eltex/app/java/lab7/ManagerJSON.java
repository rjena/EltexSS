package ru.eltex.app.java.lab7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab1.Phone;
import ru.eltex.app.java.lab1.Smartphone;
import ru.eltex.app.java.lab1.Tablet;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;
import ru.eltex.app.java.lab3.ShoppingCart;
import ru.eltex.app.java.lab5.ElectronicAdapter;
import ru.eltex.app.java.lab5.ManagerOrderJSON;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

class ManagerJSON {
    private String pathname = "/src/ru/eltex/app/java/lab7/files/";
    private File file;

    ManagerJSON() {
        file = new File(new File("").getAbsolutePath() + pathname + "json.txt");
    }

    Order readById(String id) {
        try { UUID uuid = UUID.fromString(id); } catch (IllegalArgumentException e) { return null; }
        return new ManagerOrderJSON(new File("").getAbsolutePath() + pathname + "json.txt")
                .readById(UUID.fromString(id));
    }

    Orders<Order> readAll() {
        return new ManagerOrderJSON(new File("").getAbsolutePath() + pathname + "json.txt")
                .readAll();
    }

    String delById(String id) {
        try { UUID uuid = UUID.fromString(id); } catch (IllegalArgumentException e) { return "3"; }
        boolean exist = false;
        ArrayList<Order> orders = new ArrayList<>();
        if (file.exists())
            if (file.length() != 0)
                try (JsonReader jr = new JsonReader(new FileReader(file))) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Electronic.class, new ElectronicAdapter()).create();
                    jr.setLenient(true);
                    Order o;
                    while (jr.peek() != JsonToken.END_DOCUMENT)
                        try {
                            o = gson.fromJson(jr, Order.class);
                            if (!id.equals(o.getId().toString())) orders.add(o); else exist = true;
                        } catch (JsonIOException | JsonSyntaxException e) { return "2"; }
                } catch (IOException e) { return "2"; }
        if (exist)
            try (FileWriter fw = new FileWriter(file)) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Electronic.class, new ElectronicAdapter())
                        .setPrettyPrinting().create();
                for (Order o : orders) fw.write(gson.toJson(o) + "\n");
                return "0";
            } catch (IOException e) { return "2"; }
        else return "1";
    }

    String addToCardAndGetOrderId(String uuid) {
        try { if (Integer.parseInt(uuid) < 1) return "incorrect"; }
        catch (NumberFormatException | NullPointerException e) { return "incorrect"; }
        int id = Integer.parseInt(uuid);
        File cartFile = new File(new File("").getAbsolutePath() + pathname + "carts.txt");
        ArrayList<ShoppingCart<Electronic>> carts = new ArrayList<>();
        ShoppingCart<Electronic> cart = null;
        if (cartFile.exists()) {
            if (cartFile.length() != 0)
                try (JsonReader jr = new JsonReader(new FileReader(cartFile))) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Electronic.class, new ElectronicAdapter()).create();
                    jr.setLenient(true);
                    ShoppingCart<Electronic> sc;
                    while (jr.peek() != JsonToken.END_DOCUMENT)
                        try {
                            sc = gson.fromJson(jr, ShoppingCart.class);
                            if (id != sc.getId()) carts.add(sc); else cart = sc;
                        } catch (JsonIOException | JsonSyntaxException e) { e.getMessage(); }
                } catch (IOException e) { e.getMessage(); }
        } else try { cartFile.createNewFile(); } catch (IOException e) { e.getMessage(); }
        try (FileWriter fw = new FileWriter(cartFile)) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Electronic.class, new ElectronicAdapter())
                    .setPrettyPrinting().create();
            for (ShoppingCart<Electronic> sc : carts) fw.write(gson.toJson(sc) + "\n");
            Electronic e;
            switch (new Random().nextInt(3)) {
                case 0: e = new Phone();
                case 1: e = new Smartphone();
                default: e = new Tablet();
            }
            e.create();
            if (cart == null) cart = new ShoppingCart<Electronic>(id);
            cart.add(e);
            fw.write(gson.toJson(cart) + "\n");
            return e.getID().toString();
        } catch (IOException e) { e.getMessage(); }
        return null;
    }
}
