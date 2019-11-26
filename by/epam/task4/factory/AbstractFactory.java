package by.epam.task4.factory;

import by.epam.task4.configuration.ConfigurationManager;
import by.epam.task4.models.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractFactory {

    protected Connection connection;
    private ConfigurationManager configurationManager;

    public AbstractFactory() {
        configurationManager = ConfigurationManager.getConfigurationManager();
        connection = configurationManager.getConnection();
    }

    public abstract boolean create(Entity entity);
    public abstract List <Entity> getAll();
    public abstract Entity getEntityById(Entity id);
    public abstract Entity update(Entity entity);
    public abstract boolean delete(Entity id);

    // Возвращения экземпляра Connection в пул соединений
    public void returnConnectionInPool() {
        configurationManager.returnConnection(connection);
    }

    // Получение экземпляра PrepareStatement
    protected PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    // Закрытие PrepareStatement
    protected void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}











//Стоит помнить, что следует закрывать экземпляр PrepareStatement сразу
// после его отработки в блоках finally, а возвращать соединение в пул returnConnectionInPool()
// в части логики системы, где был вызван метод.