package ru.eltex.app.java.lab3;

import ru.eltex.app.java.lab1.Electronic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

/**
 * класс-коллекция для сгенерированных объектов
 */
public class ShoppingCart<T extends Electronic> {

    private LinkedList<T> shoppingCart;
    private HashSet<UUID> ids;

    public ShoppingCart() {
        shoppingCart = new LinkedList<>();
        ids = new HashSet<>();
    }

    public ShoppingCart(LinkedList<T> electronics) {
        this.shoppingCart = electronics;
        ids = new HashSet<>();
        for (T e : electronics)
            ids.add(e.getID());
    }

    /**
     * добавление объекта в «корзину»
     */
    public void add(T e) {
        shoppingCart.add(e);
        ids.add(e.getID());
    }

    /**
     * удаление объекта из «корзины»
     */
    public void delete(T e) {
        shoppingCart.remove(e);
        ids.remove(e.getID());
    }

    public LinkedList<T> getCartItems() {
        return shoppingCart;
    }

    public void setShoppingCart(LinkedList<T> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * поиск объекта в коллекции «корзина» по идентификатору.
     */
    public T findByID(UUID id) {
        if (containsID(id))
            for (T e : shoppingCart)
                if (e.getID() == id)
                    return e;
        return null;
    }

    /**
     * «показать все объекты».
     */
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