package ru.eltex.app.java.lab3;

import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab2.Credentials;
import ru.eltex.app.java.lab2.OrderStatusEnum;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Order implements Serializable {
    private UUID id;
    private String status;
    private Date creationTime; // устанавливается в момент оформления покупки
    private int waitingTime; // через cколько секунд заказ должен исчезнуть (должен быть обработан)
    private Credentials credentials;
    private ShoppingCart<Electronic> shoppingCart;
    private InetAddress ip;

    public Order(Credentials credentials, ShoppingCart<Electronic> shoppingCart) {
        id = UUID.randomUUID();
        status = OrderStatusEnum.Pending.name();
        creationTime = new Date();
        waitingTime = new Random().nextInt(50) + 10; // from 10 to 60 sec
        this.credentials = credentials;
        this.shoppingCart = shoppingCart;
        ip = null;
    }

    public Order(Credentials credentials, ShoppingCart<Electronic> shoppingCart, InetAddress ip) {
        id = UUID.randomUUID();
        status = OrderStatusEnum.Pending.name();
        creationTime = new Date();
        waitingTime = new Random().nextInt(50) + 10; // from 10 to 60 sec
        this.credentials = credentials;
        this.shoppingCart = shoppingCart;
        this.ip = ip;
    }

    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public ShoppingCart<Electronic> getShoppingCart() {
        return shoppingCart;
    }

    public InetAddress getIp() {
        return ip;
    }

    @Override
    public String toString() {
        String cart = "";
        for (int i = 0; i < shoppingCart.getCartItems().size(); i++) {
            cart += "    Cart item №" + (i + 1);
            cart += "\n" + shoppingCart.getCartItems().get(i).toString();
        }
        return "Order (ID: " + id + "):" +
                "\n  Creation time: " + creationTime + ", Status: " + status +
                "\n  User (ID: " + credentials.getId() + ", email: " + credentials.getEmail() + ") " +
                credentials.getSurname() + " " + credentials.getName() + " " + credentials.getPatronym() +
                "\n  IP: " + ip + "\n  Shopping cart:" + cart + "\n";
    }
}
