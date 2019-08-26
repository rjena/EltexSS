package ru.eltex.app.lab1;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Random;

@Entity
@DiscriminatorValue("smartphone")
public class Smartphone extends Electronic {
    @Size(min = 5, max = 5)
    @Column(name = "sim_type")
    private String simType;

    @Column(name = "sim_count")
    private int simCount;

    public Smartphone() {
        //System.out.println("New Smartphone");
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
        if (new Random().nextInt(2) == 0) simType = "Micro";
        else simType = "Usual";
        simCount = new Random().nextInt(5) + 1;
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
        int type = 0;
        while (type < 1 || type > 2) {
            System.out.println("Sim types:\n1. Micro\n2. Usual");
            type = (int) doubleOrIntegerInputWithMessage("Choose sim type:", true);
        }
        if (type == 1) simType = "Micro";
        else simType = "Usual";
        simCount = (int) doubleOrIntegerInputWithMessage("Input sim count", true);
    }

    @Override
    public void delete() {
        super.delete();
        simType = null;
        simCount = 0;
    }
}