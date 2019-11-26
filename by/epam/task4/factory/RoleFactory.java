package by.epam.task4.factory;

import by.epam.task4.models.Entity;

import java.util.List;

public class RoleFactory extends AbstractFactory {

    @Override
    public boolean create(Entity entity) {
        return false;
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public Entity getEntityById(Entity id) {
        return null;
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }

    @Override
    public boolean delete(Entity id) {
        return false;
    }
}
