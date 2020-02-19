package system.model.dao;

import system.model.entity.Plant;

/**
 *
 *  Transitional interface to which individual methods can be added
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public interface PlantDAO extends GenericDAO <Plant>{
    //looking for Plant by name
    Plant find(String string);
}
