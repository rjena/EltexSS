package ru.eltex.app.lab3;

import org.hibernate.annotations.Type;
import ru.eltex.app.lab1.Electronic;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * класс-коллекция для сгенерированных объектов
 */
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart<T extends Electronic> implements Serializable {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //@Type(type = "ru.eltex.app.lab1.Electronic")
    @OneToMany(mappedBy = "cart", targetEntity = Electronic.class, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<T> shoppingCart;

    @Transient
    private Set<String> ids;

    //@Type(type = "ru.eltex.app.lab3.Order")
    @OneToMany(mappedBy = "cart", targetEntity = Order.class, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Order> orders;

    public ShoppingCart() {
        shoppingCart = new LinkedList<>();
        ids = new HashSet<>();
        orders = new ArrayList<>();
    }

    public ShoppingCart(int id) {
        this.id = id;
        shoppingCart = new LinkedList<>();
        ids = new HashSet<>();
        orders = new ArrayList<>();
    }

    public ShoppingCart(LinkedList<T> electronics) {
        this.shoppingCart = electronics;
        ids = new HashSet<>();
        for (T e : electronics) ids.add(e.getID().toString());
    }

    /**
     * добавление объекта в «корзину»
     */
    public void add(T e) {
        shoppingCart.add(e);
        ids.add(e.getID().toString());
    }

    /**
     * удаление объекта из «корзины»
     */
    public void delete(T e) {
        shoppingCart.remove(e);
        ids.remove(e.getID().toString());
    }

    public int getId() {
        return id;
    }

    public List<T> getCartItems() {
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

    public Set<String> getIds() {
        return ids;
    }

    public boolean containsID(UUID id) {
        return ids.contains(id.toString());
    }
}