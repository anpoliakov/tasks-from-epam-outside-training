package by.epam.task4.dao;

import by.epam.task4.ConnectionPool;
import by.epam.task4.configuration.ConfigurationManager;
import by.epam.task4.models.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO <E extends Entity , K> {
    private Connection connection;
    private ConnectionPool connectionPool;
    private ConfigurationManager configurationManager;

    public AbstractDAO() {
        configurationManager = ConfigurationManager.getConfigurationManager();
        /* тут создаю пул - преедаю login,password,url */
        connectionPool = new ConnectionPool(null,null,null,null,0);
        connection = connectionPool.getConnection(); //получаю соединение с БД
    }

    public abstract List<E> getAll();
    public abstract E update(E entity);
    public abstract E getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);

    // Возвращения экземпляра Connection в пул соединений
    public void returnConnectionInPool() {
        connectionPool.returnConnection(connection);
    }

    // Получение экземпляра PrepareStatement
    //Стоит помнить, что следует закрывать экземпляр PrepareStatement сразу
    // после его отработки в блоках finally, а возвращать соединение в пул returnConnectionInPool()
    // в части логики системы, где был вызван метод.
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
