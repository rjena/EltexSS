package ru.eltex.app.java;

public class Phone extends Electronic {
    private String caseType;

    Phone() {
        System.out.println("New Phone");
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
        caseType = randomStringGenerator();
    }

    @Override
    public void read() {
        super.read();
        System.out.println("Case type: " + caseType);
    }

    @Override
    public void update() {
        super.update();
        caseType = stringInputWithMessage("Input case type");
    }

    @Override
    public void delete() {
        super.delete();
        caseType = null;
    }
}
