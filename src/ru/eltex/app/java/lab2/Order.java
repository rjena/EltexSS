package ru.eltex.app.java.lab2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Order {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private String status;
    private Date creationTime; // устанавливается в момент оформления покупки
    private int waitingTime; // через cколько секунд заказ должен исчезнуть (должен быть обработан)
    private Credentials credentials;
    private ShoppingCart shoppingCart;

    public Order(Credentials credentials, ShoppingCart shoppingCart) {
        status = OrderStatusEnum.Pending.name();
        creationTime = new Date();
        waitingTime = new Random().nextInt(240) + 60; // from 1 to 5 min
        this.credentials = credentials;
        this.shoppingCart = shoppingCart;
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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
