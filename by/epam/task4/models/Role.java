package by.epam.task4.models;

public class Role extends Entity {

    //id и name есть в родительском классе => тут точже есть
    private String description;

    public Role() {
        super();
    }

    public Role(int id) {
        super(id);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
