package by.epam.task4;

import by.epam.task4.factory.AbstractFactory;
import by.epam.task4.factory.HumanFactory;
import by.epam.task4.factory.PlantFactory;
import by.epam.task4.models.Entity;

public class Runner {
    public static void main(String[] args) {
        AbstractFactory factory = new HumanFactory();
        Entity entity = factory.create(/*ЗАПРОС?*/);



//        AbstractDAO abstractDAO = getByName("Human");
//        Entity entity = abstractDAO.create();
//        entity.getName();
    }

    public static AbstractFactory getByName (String name){
        if (name.equals("Human")){
            return new HumanFactory();
        }else if (name.equals("Plant")){
            return new PlantFactory();
        }

        throw new RuntimeException("нету такой реализации " + name);
    }
}
