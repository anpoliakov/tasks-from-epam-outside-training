package by.epam.task4.models;

import java.util.Objects;

public class Human extends Entity {

    //id и name есть в родительском классе => тут точже есть
    private String surname;
    private int age;
    private Role role;

    public Human() {
        super();
    }

    //удалить возможно ( есть во всех моделях)
    public Human(int id) {
        super(id);
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Human {" +
                "surname='" + surname + '\'' +
                ", age=" + age +
                ", role=" + role.getName() +
                '}';
    }
}
