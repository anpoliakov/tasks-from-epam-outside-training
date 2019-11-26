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

    public Human(int id, String name, String surname, int age, int role){
        super(id,name);
        this.surname = surname;
        this.age = age;

        switch (role){
            case 1:
                this.role = new Role("Владелец");
                break;
            case 2:
                this.role = new Role("Лесник");
                break;
            case 3:
                this.role = new Role("Посетитель");
                break
            default:
                this.role = new Role("Отсутствует");
                break;
        }
    }

    public Human(String name, String surname, int age, Role role){
        super(name);
        this.surname = surname;
        this.age = age;
        this.role = role;
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
        return "Human{ name = '" + super.getName() + "', " +
                "surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
