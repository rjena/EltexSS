package ru.eltex.app.lab1;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Random;

@Entity
@DiscriminatorValue("phone")
public class Phone extends Electronic {
    @Size(max = 30)
    @Column(name = "case_type")
    private String caseType;

    public Phone() {
        //System.out.println("New Phone");
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    @Override
    public void create() {
        super.create();
        if (new Random().nextInt(2) == 0) caseType = "Classic";
        else caseType = "Flip";
    }

    @Override
    public void read() {
        super.read();
        System.out.println("Case type: " + caseType);
    }

    @Override
    public void update() {
        super.update();
        int type = 0;
        while (type < 1 || type > 2) {
            System.out.println("Case types:\n1. Classic\n2. Flip");
            type = (int) doubleOrIntegerInputWithMessage("Choose case type:", true);
        }
        if (type == 1) caseType = "Classic";
        else caseType = "Flip";
    }

    @Override
    public void delete() {
        super.delete();
        caseType = null;
    }
}
