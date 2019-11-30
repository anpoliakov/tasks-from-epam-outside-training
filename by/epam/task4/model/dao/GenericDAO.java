package by.epam.task4.model.dao;

/**
 * Generic DAO interface with CRUD methods
 */
public interface GenericDAO <E> {
    int create(E e);
    boolean delete(E e);
    E find(E e);
    boolean update(E e);
}
