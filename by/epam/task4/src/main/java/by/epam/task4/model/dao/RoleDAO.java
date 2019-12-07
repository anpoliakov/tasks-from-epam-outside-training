package by.epam.task4.model.dao;

import by.epam.task4.model.entity.Role;

/**
 *
 *  Transitional interface to which individual methods can be added
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public interface RoleDAO extends GenericDAO <Role>{
    //looking for Role by name
    Role find (String string);
}
