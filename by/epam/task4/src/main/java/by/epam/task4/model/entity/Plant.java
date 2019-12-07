package by.epam.task4.model.entity;

public class Plant {
    private int id;
    private String name;
    private String description;

    public Plant(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Plant(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    // override methods equals and hashCode
}
