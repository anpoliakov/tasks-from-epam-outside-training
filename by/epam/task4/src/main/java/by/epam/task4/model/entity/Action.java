package by.epam.task4.model.entity;

import java.util.Objects;

public class Action {
    private  int id;
    private String name;

    public Action(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                '}';
    }

    // override methods equals and hashCode
}
