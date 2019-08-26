package ru.eltex.app.lab2;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.eltex.app.lab3.Order;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** класс для хранения и обработки персональных данных пользователей */
@Entity(name = "Credential")
@Table(name = "credentials")
public class Credentials implements Serializable {
    @Id
    @Column(name = "cred_id")
    @Type(type = "uuid-char")
    //@GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(max = 30)
    private String surname;

    @Size(max = 30)
    private String name;

    @Size(max = 30)
    private String patronym;

    @Size(max = 50)
    private String email;

    //@Type(type = "ru.eltex.app.lab3.Order")
    @OneToMany(mappedBy = "credentials", targetEntity = Order.class, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Order> orders;

    public Credentials() {
        id = UUID.randomUUID();
        orders = new ArrayList<>();
    }

    public Credentials(String surname, String name, String patronym, String email) {
        id = UUID.randomUUID();
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.email = email;
        orders = new ArrayList<>();
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
