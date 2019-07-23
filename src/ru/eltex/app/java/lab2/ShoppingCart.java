package ru.eltex.app.java.lab2;

import ru.eltex.app.java.lab1.Electronic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

public class ShoppingCart {
    // класс-коллекция для сгенерированных объектов.
    private LinkedList<Electronic> shoppingCart;
    private HashSet<UUID> ids;

    public ShoppingCart() {
        shoppingCart = new LinkedList<>();
        ids = new HashSet<>();
    }

    public ShoppingCart(LinkedList<Electronic> electronics) {
        this.shoppingCart = electronics;
        ids = new HashSet<>();
        for (Electronic e : electronics)
            ids.add(e.getID());
    }

    // При операции add происходит добавление объекта в «корзину».
    public void add(Electronic e) {
        shoppingCart.add(e);
        ids.add(e.getID());
    }

    // При операции delеte происходит удаление объекта из «корзины».
    public void delete(Electronic e) {
        shoppingCart.remove(e);
        ids.remove(e.getID());
    }

    public LinkedList<Electronic> getCartItems() {
        return shoppingCart;
    }

    public void setShoppingCart(LinkedList<Electronic> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    // поиск объекта в коллекции «корзина» по идентификатору.
    public Electronic findByID(UUID id) {
        if (containsID(id))
            for (Electronic e : shoppingCart)
                if (e.getID() == id)
                    return e;
        return null;
    }

    // «показать все объекты».
    public void show() {
        for (int i = 0; i < shoppingCart.size(); i++) {
            System.out.println("==========================================\n\t\tCartItem №" + (i + 1));
            shoppingCart.get(i).read();
        }
    }

    public HashSet<UUID> getIds() {
        return ids;
    }

    public boolean containsID(UUID id) {
        return ids.contains(id);
    }
}