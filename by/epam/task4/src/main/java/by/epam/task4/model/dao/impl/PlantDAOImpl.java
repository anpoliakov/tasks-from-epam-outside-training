package by.epam.task4.model.dao.impl;

import by.epam.task4.model.constants.Constants;
import by.epam.task4.model.dao.PlantDAO;
import by.epam.task4.model.db.ConnectionPool;
import by.epam.task4.model.entity.Plant;
import by.epam.task4.model.managers.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *  This class represents methods for working with a table from a database "plant".
 * [!!! NOTICE !!!] From this class and below - a description of the methods will be contained in the interfaces
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 *
 * */
public class PlantDAOImpl implements PlantDAO {
    final static Logger logger = Logger.getLogger(PlantDAOImpl.class);
    private ConnectionPool pool;
    private ConfigurationManager configManager;

    public PlantDAOImpl() {
        pool = ConnectionPool.getConnectionPool();
        configManager = ConfigurationManager.getConfigManager();
    }

    @Override
    public Plant find(String string) {
        String sqlRequest = configManager.getSQLRequest("sql.plant.find");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Plant plant = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, string.trim());
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int id = resultSet.getInt("id_plant");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                plant = new Plant(id,name,description);
            }

        } catch (SQLException e) {
            logger.error("Look class PlantDAOImpl, method find()", e);
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Close error 'resultSet' ", e);
                }
            }
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Close error 'statement' ", e);
                }
            }
            if(connection != null){
                pool.closeConnection(connection);
            }
        }
        return plant;
    }

    @Override
    public int create(Plant plant) {
        int result = Constants.RESULT_OPERATION_NEGATIVE;

        String name = plant.getName();
        String description = plant.getDescription();

        Plant isPlant = find(name);

        if(isPlant == null){
            String sqlRequest = configManager.getSQLRequest("sql.plant.create");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                statement.setString(Constants.INDEX_SECOND_PARAMETER, description);
                int i = statement.executeUpdate();

                if(i > 0) {
                    result = Constants.RESULT_OPERATION_POSITIVE; // то говорим что результат операции положительный
                }
            } catch (SQLException e) {
                logger.error("Look class PlantDAOImpl, method create()", e);
            }finally {
                if (statement != null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("Close error 'statement' ", e);
                    }
                }

                if(connection != null){
                    pool.closeConnection(connection);
                }
            }

        } else { // если такая роль найдена то ничего не делаем
            result = Constants.RESULT_OPERATION_NEUTRAL;
        }
        return result;
    }

    @Override
    public boolean delete(Plant plant) {
        boolean flag = false;
        String name = plant.getName();
        Plant isPlant = find(name);

        if(isPlant != null){
            String sqlRequest = configManager.getSQLRequest("sql.plant.delete");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                int i = statement.executeUpdate();

                if(i > 0) {
                    flag = true;
                }
            } catch (SQLException e) {
                logger.error("Look class PlantDAOImpl, method delete()", e);
            }finally {
                if (statement != null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("Close error 'statement' ", e);
                    }
                }

                if(connection != null){
                    pool.closeConnection(connection);
                }
            }

        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean update(Plant plant) {
        boolean flag = false;

        int id = plant.getId();
        String name = plant.getName();
        String description = plant.getDescription();

        if(id != 0 && !name.isEmpty()){
            String sqlRequest = configManager.getSQLRequest("sql.plant.update");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                statement.setString(Constants.INDEX_SECOND_PARAMETER, description);
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, id);
                int i = statement.executeUpdate();

                if(i > 0){
                    flag = true;
                }

            } catch (SQLException e) {
                logger.error("Look class PlantDAOImpl, method update()", e);
            }finally {
                if (statement != null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("Close error 'statement' ", e);
                    }
                }
                if(connection != null){
                    pool.closeConnection(connection);
                }
            }
        }
        return flag;
    }

    @Override
    public Plant findById(int id) {
        String sqlRequest = configManager.getSQLRequest("sql.plant.findById");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Plant plant = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, String.valueOf(id));
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int receiveId = resultSet.getInt("id_plant");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                plant = new Plant(id,name,description);
            }

        } catch (SQLException e) {
            logger.error("Look class PlantDAOImpl, method findById()", e);
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Close error 'resultSet' ", e);
                }
            }
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Close error 'statement' ", e);
                }
            }
            if(connection != null){
                pool.closeConnection(connection);
            }
        }
        return plant;
    }

    @Override
    public List<Plant> findAll() {
        List <Plant> listPlant = null;
        String sqlRequest = configManager.getSQLRequest("sql.plant.findAll");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            listPlant = new ArrayList<Plant>();
            statement = connection.prepareStatement(sqlRequest);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int receiveId = resultSet.getInt("id_plant");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                listPlant.add(new Plant(receiveId,name,description));
            }

        } catch (SQLException e) {
            logger.error("Look class PlantDAOImpl, method findAll()", e);
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Close error 'statement' ", e);
                }
            }
            if(connection != null){
                pool.closeConnection(connection);
            }
        }
        return listPlant;
    }
}
