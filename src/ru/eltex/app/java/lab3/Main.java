package ru.eltex.app.java.lab3;

import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab1.Phone;
import ru.eltex.app.java.lab1.Smartphone;
import ru.eltex.app.java.lab1.Tablet;
import ru.eltex.app.java.lab2.Credentials;

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

        try {
            Orders<Order> orders = new Orders<>();
            for (int i = 0; i < new Random().nextInt(4) + 3; i++) {
                ShoppingCart<Electronic> shoppingCart = new ShoppingCart<>();
                Credentials cred = new Credentials("Surname" + (i + 1), "Name" + (i + 1),
                        "Patronym" + (i + 1), "Email" + (i + 1) + "@gmail.com");
                System.out.println("\n===================================================");
                System.out.println("---------------------------------------------------");
                System.out.println("===================================================");
                System.out.printf("\nUser %s created\n", i + 1);
                cred.show();
                for (int j = 0; j < new Random().nextInt(4) + 3; j++)
                    shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
                shoppingCart.delete(shoppingCart.getCartItems().get(new Random().nextInt(shoppingCart.getCartItems().size())));
                shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
                shoppingCart.show();
                orders.add(new Order(cred, shoppingCart));
                System.out.printf("Shopping cart for User %s added\n", i + 1);
            }
            orders.delete(orders.getOrders().get(0));
            System.out.println("Order №1 deleted");

            System.out.println("\nTrying to find new random UUID in cart: " + orders.getOrders().get(0).getShoppingCart()
                    .containsID(UUID.randomUUID()));
            System.out.println("Trying to find not random UUID in cart: " + orders.getOrders().get(0).getShoppingCart()
                    .containsID(((Electronic) orders.getOrders().get(0).getShoppingCart().getCartItems().getFirst()).getID()));
        } catch (Exception e) {
            System.out.println("Hmmm... This part should work correctly...");
        }
        System.out.println("\n\n\n======================================================================================");
        System.out.println("======================================================================================");
        System.out.println("======================================================================================");
        System.out.println("======================================================================================");
        System.out.println("======================================================================================");
        try {
            System.out.println("\n\n");
            Orders<ShoppingCart> orders = new Orders<>();
            for (int i = 0; i < new Random().nextInt(4) + 3; i++) {
                Credentials cred = new Credentials("Surname" + (i + 1), "Name" + (i + 1),
                        "Patronym" + (i + 1), "Email" + (i + 1) + "@gmail.com");
                System.out.printf("\nUser %s created\n", i + 1);
                //cred.show();
                //ShoppingCart<Credentials> shoppingCart = new ShoppingCart<>();
                System.out.println("\nwhen trying ShoppingCart<Credentials> shoppingCart = new ShoppingCart<>();\n" +
                        "error appeared, it is said that it should extend Electronic");
                ShoppingCart<Electronic> shoppingCart = new ShoppingCart<>();
                for (int j = 0; j < new Random().nextInt(4) + 3; j++)
                    shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
                shoppingCart.delete(shoppingCart.getCartItems().get(new Random().nextInt(shoppingCart.getCartItems().size())));
                shoppingCart.add(electronics[new Random().nextInt(electronics.length)]);
                //shoppingCart.show();
                orders.add(shoppingCart);
                System.out.printf("\nShopping cart for User %s added\n", i + 1);
                System.out.println("\nHmmm... This part shouldn't work correctly");
            }
            orders.delete(orders.getOrders().get(0));

            /*System.out.println("\nTrying to find new random UUID in cart: " + orders.getOrders().get(0).getShoppingCart()
                    .containsID(UUID.randomUUID()));
            System.out.println("Trying to find not random UUID in cart: " + orders.getOrders().get(0).getShoppingCart()
                    .containsID(((Electronic) orders.getOrders().get(0).getShoppingCart().getCartItems().getFirst()).getID()));*/
        } catch (Exception e) {
            System.out.println("\nCorrect error. It was planned");
            System.out.println(e.getMessage());
        }
    }
}
