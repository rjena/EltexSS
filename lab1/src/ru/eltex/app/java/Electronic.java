package ru.eltex.app.java;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public abstract class Electronic implements ICrudAction {
    static int count = 0;
    private UUID id;
    private String name;
    private double price;
    private String firm;
    private String model;
    private String os;

    Electronic() {}

    public int getCount() {
        return count;
    }

    public UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getFirm() {
        return firm;
    }

    public String getModel() {
        return model;
    }

    public String getOS() {
        return os;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setOS(String os) {
        this.os = os;
    }

    @Override
    public void create() {
        id = UUID.randomUUID();
        name = randomStringGenerator();
        price = new Random().nextDouble();
        firm = randomStringGenerator();
        model = randomStringGenerator();
        os = randomStringGenerator();
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

    static protected String randomStringGenerator() {
        String tmp = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < new Random().nextInt(11) + 5; i++)
            s.append(tmp.charAt(new Random().nextInt(tmp.length())));
        return s.toString();
    }

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
        String s = randomStringGenerator();
        System.out.printf(msg + " [default: %s]: ", s);
        String line = new Scanner(System.in).nextLine();
        return line.equals("") ? s : line;
    }
}