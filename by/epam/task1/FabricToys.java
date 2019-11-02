package by.epam.task1;

import by.epam.task1.models.AbstractToy;

import java.util.List;
import java.util.Random;

/* This Class generates random toy upon request */
public class FabricToys {
    private List <Class <? extends AbstractToy>> toyClasses;
    private Random rand = new Random();

    public FabricToys(List <Class<? extends AbstractToy>> toyClasses) {
        this.toyClasses = toyClasses;
    }

    public AbstractToy getRandomToy() {
        return genRandomToy();
    }

    private AbstractToy genRandomToy() {
        try {
            return toyClasses.get(rand.nextInt(toyClasses.size())).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
