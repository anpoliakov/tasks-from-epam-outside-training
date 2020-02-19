package system.model.dao;

import system.model.entity.Action;

/**
 *
 *  Transitional interface to which individual methods can be added
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public interface ActionDAO extends GenericDAO <Action> {
    //unique method for the class ActionDAOImpl
    //looking for Action by name
    Action find(String name);
}
