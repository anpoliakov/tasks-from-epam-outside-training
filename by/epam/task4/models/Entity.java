package by.epam.task4.models;

public class Entity {
    private int id;
    private String name;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
