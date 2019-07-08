package ru.eltex.app.java;

import java.util.Random;

public class Smartphone extends Electronic {
    String simType;
    int simCount;

    Smartphone() {
        System.out.println("New Smartphone");
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public int getSimCount() {
        return simCount;
    }

    public void setSimCount(int simCount) {
        this.simCount = simCount;
    }

    @Override
    public void create() {
        super.create();
        simType = randomStringGenerator();
        simCount= new Random().nextInt(5) + 1;
    }

    @Override
    public void read() {
        super.read();
        System.out.println("Sim type: " + simType);
        System.out.println("Sim count: " + simCount);
    }

    @Override
    public void update() {
        super.update();
        simType = stringInputWithMessage("Input sim type");
        simCount = (int) doubleOrIntegerInputWithMessage("Input sim count", true);
    }

    @Override
    public void delete() {
        super.delete();
        simType = null;
        simCount = 0;
    }
}