package by.epam.task1;

import by.epam.task1.models.AbstractToy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* This class loads the value of toys from a file. And manipulates it */
public class PriceController {

    private final static String SOURCE_DATA = "src/by/epam/task1/resources/price.properties";
    private static Map <Class<? extends AbstractToy>, Double> price;
    private List <Class<? extends AbstractToy>> toyClasses;

    public PriceController (List <Class<? extends AbstractToy>> toyClasses) {
        price = new HashMap <Class<? extends AbstractToy>, Double>();
        this.toyClasses = toyClasses;
        getPriceFromFile();
    }

    private void getPriceFromFile() {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(SOURCE_DATA);
            properties.load(fis);
            for (int i = 0; i < toyClasses.size(); i++) {
                price.put(toyClasses.get(i), Double.valueOf(
                        properties.getProperty(
                                toyClasses.get(i).getSimpleName())));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /* communicate the type of toy into the parameter of the method - get its cost */
    public static double getPrice(Class<? extends AbstractToy> toyClass) {
        return price.get(toyClass);
    }
}
