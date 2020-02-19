package system.model.dao;

import java.util.List;

/**
 * Generic DAO interface with CRUD methods
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 */
public interface GenericDAO <E> {
    //Methods are present in all implementations of DAO interfaces.

    //Create an entity in the database
    int create(E e);
    //Delete an entity in the database
    boolean delete(E e);
    //Updates the transferred entity in the database
    //changes through set() methods and then method call
    boolean update(E e);
    //Method for searching an entity by id in a table
    E findById(int id);
    //Getting all entities from the database
    List <E> findAll();
}
