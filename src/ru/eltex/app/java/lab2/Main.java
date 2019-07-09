package ru.eltex.app.java.lab2;

import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab1.Phone;
import ru.eltex.app.java.lab1.Smartphone;
import ru.eltex.app.java.lab1.Tablet;

import java.util.Random;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // создание товаров
        int pN = 10;
        int sN = 10;
        int tN = 10;
        Electronic[] electronics = new Electronic[pN + sN + tN];
        for (int i = 0; i < pN; i++)
            electronics[i] = new Phone();
        for (int i = pN; i < pN + sN; i++)
            electronics[i] = new Smartphone();
        for (int i = pN + sN; i < pN + sN + tN; i++)
            electronics[i] = new Tablet();

        for (Electronic el : electronics)
            el.create();

        System.out.printf("\n\n%s objects created.\n", pN + sN + tN);

        Orders orders = new Orders();
        for (int i = 0; i < new Random().nextInt(4) + 3; i++) {
            ShoppingCart shoppingCart = new ShoppingCart();
            Credentials cred = new Credentials("Surname" + (i + 1), "Name" + (i + 1),
                    "Patronym" + (i + 1), "Email" + (i + 1) + "@gmail.com");
            System.out.printf("\nUser %s created\n", i + 1);
            for (int j = 0; j < new Random().nextInt(4) + 3; j++)
                shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
            shoppingCart.delete(shoppingCart.getShoppingCart().get(new Random().nextInt(shoppingCart.getShoppingCart().size())));
            orders.offer(shoppingCart, cred);
            shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
            orders.offer(shoppingCart, cred);
            System.out.printf("Shopping cart for User %s ordered\n", i + 1);
        }

        System.out.println("\n" + orders.getOrders().get(0).getShoppingCart().containsID(UUID.randomUUID()));
    }
}
