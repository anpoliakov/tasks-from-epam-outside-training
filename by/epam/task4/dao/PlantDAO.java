package by.epam.task4.dao;

import by.epam.task4.models.Plant;

import java.util.List;

public class PlantDAO extends AbstractDAO <Plant,Integer> {

    @Override
    public List<Plant> getAll() {
        return null;
    }

    @Override
    public Plant update(Plant entity) {
        return null;
    }

    @Override
    public Plant getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Plant entity) {
        return false;
    }
}
