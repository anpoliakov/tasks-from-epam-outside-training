package by.epam.task4.model.entity;

public class Intruction {
    private int id;
    private String name;
    private Plant plant;
    private Human human;

    //под сомнением данный конструктор
    public Intruction(int id, String name, Plant plant, Human human) {
        this.id = id;
        this.name = name;
        this.plant = plant;
        this.human = human;
    }

    public Intruction(String name, Plant plant, Human human) {
        this.name = name;
        this.plant = plant;
        this.human = human;
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

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }
}
