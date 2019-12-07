package by.epam.task4.model.entity;

import java.util.Objects;

public class Human{
    private int id;
    private String name;
    private String surname;
    private Role role;

    public Human(int id, String name, String surname, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public Human(String name, String surname, Role role) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role.getName() +
                '}';
    }

    // override methods equals and hashCode
}
