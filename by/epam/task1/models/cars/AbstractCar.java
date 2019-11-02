package by.epam.task1.models.cars;

import by.epam.task1.models.AbstractToy;

public class AbstractCar extends AbstractToy {

    public AbstractCar() {
        this("Car");
    }

    public AbstractCar(String name) {
        super(name);
    }

}
