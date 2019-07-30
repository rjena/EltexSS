package ru.eltex.app.java.lab2;

import java.io.Serializable;
import java.util.UUID;

public class Credentials implements Serializable {
    // класс для хранения и обработки персональных данных пользователей
    private UUID id;
    private String surname;
    private String name;
    private String patronym;
    private String email;

    public Credentials() {
        id = UUID.randomUUID();
    }

    public Credentials(String surname, String name, String patronym, String email) {
        id = UUID.randomUUID();
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void show() {
        System.out.println("ID: " + id);
        System.out.println("Surname: " + surname);
        System.out.println("Name: " + name);
        System.out.println("Patronym: " + patronym);
        System.out.println("Email: " + email);
    }
}
