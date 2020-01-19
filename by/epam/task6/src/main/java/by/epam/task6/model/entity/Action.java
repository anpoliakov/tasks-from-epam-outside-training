package by.epam.task6.model.entity;

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
}
