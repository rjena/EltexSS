package ru.eltex.app.lab1;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.eltex.app.lab1.Enums.*;
import ru.eltex.app.lab3.ShoppingCart;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

@Entity
@Table(name = "electronics")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "electronic_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Electronic implements ICrudAction, Serializable {
    static int count = 0;

    @Type(type = "ru.eltex.app.lab3.ShoppingCart")
    @ManyToOne(targetEntity = ShoppingCart.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    @Id
    @Column(name = "el_id")
    @Type(type = "uuid-char")
    //@GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(max = 30)
    private String name;

    @DecimalMin("0.0")
    private double price;

    @Size(max = 30)
    private String firm;

    @Size(max = 30)
    private String model;

    @Size(max = 30)
    @Column(name = "operating_system")
    private String os;

    Electronic() {}

    static protected double doubleOrIntegerInputWithMessage(String msg, boolean integer) {
        double d = 2;
        if (integer)
            System.out.printf(msg + "[default: %s]: ", (int) d);
        else
            System.out.printf(msg + "[default: %s]: ", d);
        String line = new Scanner(System.in).nextLine();
        return line.matches("\\d+[.\\d]?") ? Double.valueOf(line) : d;
    }

    static protected String stringInputWithMessage(String msg) {
        String s = "";
        switch (msg.substring(6)) {
            case "name":
                s = NameEnum.values()[new Random().nextInt(NameEnum.values().length)].toString();
                break;
            case "firm":
                s = FirmEnum.values()[new Random().nextInt(FirmEnum.values().length)].toString();
                break;
            case "model":
                s = ModelEnum.values()[new Random().nextInt(ModelEnum.values().length)].toString();
                break;
            case "OS":
                s = OSEnum.values()[new Random().nextInt(OSEnum.values().length)].toString();
                break;
            case "video processor":
                s = VideoProcessorEnum.values()
                        [new Random().nextInt(VideoProcessorEnum.values().length)].toString();
                break;
            case "screen resolution":
                s = ScreenResolutionEnum.values()
                        [new Random().nextInt(ScreenResolutionEnum.values().length)].toString();
                break;
        }
        System.out.printf(msg + " [default: %s]: ", s);
        String line = new Scanner(System.in).nextLine();
        return line.equals("") ? s : line;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public int getCount() {
        return count;
    }

    public UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOS() {
        return os;
    }

    public void setOS(String os) {
        this.os = os;
    }

    @Override
    public void create() {
        id = UUID.randomUUID();
        name = NameEnum.values()[new Random().nextInt(NameEnum.values().length)].toString();
        price = new Random().nextDouble();
        firm = FirmEnum.values()[new Random().nextInt(FirmEnum.values().length)].toString();
        model = ModelEnum.values()[new Random().nextInt(ModelEnum.values().length)].toString();
        os = OSEnum.values()[new Random().nextInt(OSEnum.values().length)].toString();
        count++;
    }

    @Override
    public void read() {
        System.out.println("==========================================");
        System.out.println("ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Firm: " + firm);
        System.out.println("Model: " + model);
        System.out.println("OS: " + os);
    }

    @Override
    public void update() {
        name = stringInputWithMessage("Input name");
        price = doubleOrIntegerInputWithMessage("Input price", false);
        firm = stringInputWithMessage("Input firm");
        model = stringInputWithMessage("Input model");
        os = stringInputWithMessage("Input OS");
    }

    @Override
    public void delete() {
        id = null;
        name = null;
        price = 0;
        firm = null;
        model = null;
        os = null;
        count--;
    }

    @Override
    public String toString() {
        return "      " + getClass() + " (ID: " + id + ")" +
                "\n        Price = " + price +
                "\n        Name: " + name + ", Model: " + model +
                "\n        Firm: " + firm +
                "\n        Operating system: " + os;
    }
}