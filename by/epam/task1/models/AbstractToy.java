package by.epam.task1.models;

import by.epam.task1.PriceController;

/**
 * @version 1.0.0
 * @author anpoliakov
 */
/* All Toy's models are built on this class */
public abstract class AbstractToy {

    private String name;
    private double cost;

    public AbstractToy (String name) {
        this.cost = PriceController.getPrice(this.getClass());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "name: " + this.name + ", cost: " + this.cost;
    }
}
