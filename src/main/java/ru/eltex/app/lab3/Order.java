package ru.eltex.app.lab3;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab2.Credentials;
import ru.eltex.app.lab2.OrderStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Column(name = "order_id")
    @Type(type = "uuid-char")
    //@GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(min = 7, max = 9)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time")
    private Date creationTime; // устанавливается в момент оформления покупки

    @Column(name = "waiting_time")
    private int waitingTime; // через cколько секунд заказ должен исчезнуть (должен быть обработан)

    @ManyToOne(targetEntity = Credentials.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cred_id", nullable = false)
    private Credentials credentials;

    @ManyToOne(targetEntity = ShoppingCart.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private ShoppingCart<Electronic> cart;

    @Column(name = "client_ip")
    private InetAddress ip;

    public Order() {
        id = UUID.randomUUID();
        status = OrderStatusEnum.Pending.name();
        creationTime = new Date();
        waitingTime = new Random().nextInt(50) + 10; // from 10 to 60 sec
        credentials = null;
        cart = null;
        ip = null;
    }

    public Order(Credentials credentials, ShoppingCart<Electronic> shoppingCart) {
        id = UUID.randomUUID();
        status = OrderStatusEnum.Pending.name();
        creationTime = new Date();
        waitingTime = new Random().nextInt(50) + 10; // from 10 to 60 sec
        this.credentials = credentials;
        this.cart = shoppingCart;
        ip = null;
    }

    public Order(Credentials credentials, ShoppingCart<Electronic> shoppingCart, InetAddress ip) {
        id = UUID.randomUUID();
        status = OrderStatusEnum.Pending.name();
        creationTime = new Date();
        waitingTime = new Random().nextInt(50) + 10; // from 10 to 60 sec
        this.credentials = credentials;
        this.cart = shoppingCart;
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

    public ShoppingCart<Electronic> getCart() {
        return cart;
    }

    public InetAddress getIp() {
        return ip;
    }

    @Override
    public String toString() {
        String cart = "";
        for (int i = 0; i < this.cart.getCartItems().size(); i++) {
            cart += "    Cart item №" + (i + 1);
            cart += "\n" + this.cart.getCartItems().get(i).toString();
        }
        return "Order (ID: " + id + "):" +
                "\n  Creation time: " + creationTime + ", Status: " + status +
                "\n  User (ID: " + credentials.getId() + ", email: " + credentials.getEmail() + ") " +
                credentials.getSurname() + " " + credentials.getName() + " " + credentials.getPatronym() +
                "\n  IP: " + ip + "\n  Shopping cart:" + cart + "\n";
    }
}
