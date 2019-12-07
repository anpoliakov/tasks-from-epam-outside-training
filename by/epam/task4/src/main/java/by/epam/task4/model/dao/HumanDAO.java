package by.epam.task4.model.dao;

import by.epam.task4.model.entity.Human;

/**
 *
 *  Transitional interface to which individual methods can be added
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public interface HumanDAO extends GenericDAO <Human> {
    //unique method for the class HumanDAOImpl
    Human find(String name, String surname);

}
