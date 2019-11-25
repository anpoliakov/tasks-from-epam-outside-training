package by.epam.task4.dao;

import by.epam.task4.models.Human;

import java.util.List;

public class HumanDAO extends AbstractDAO <Human,Integer> {
    @Override
    public List<Human> getAll() {
        return null;
    }

    @Override
    public Human update(Human entity) {
        return null;
    }

    @Override
    public Human getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Human entity) {
        return false;
    }
}
