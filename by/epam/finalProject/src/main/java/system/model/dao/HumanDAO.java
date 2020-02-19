package system.model.dao;

import system.model.entity.Human;

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
    Human findByNameSurname(String name, String surname);
    boolean findByHumanEntity(Human human);

}
