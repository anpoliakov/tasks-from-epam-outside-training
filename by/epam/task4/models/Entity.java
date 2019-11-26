package by.epam.task4.models;

public abstract class Entity {
    private int id;
    private String name;

    public Entity() {
        id = 0;
        name = null;
    }

    public Entity(String name){
        this.name = name;
    }

    public Entity(int id, String name){
        this.id = id;
        this.name = name;
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
