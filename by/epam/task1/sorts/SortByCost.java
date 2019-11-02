package by.epam.task1.sorts;

import by.epam.task1.models.AbstractToy;

import java.util.Comparator;

/* This class sorts toys by cost */
public class SortByCost implements Comparator<AbstractToy> {
    public int compare(AbstractToy o1, AbstractToy o2) {
        return ((Double) o1.getCost()).compareTo(o2.getCost());
    }
}
