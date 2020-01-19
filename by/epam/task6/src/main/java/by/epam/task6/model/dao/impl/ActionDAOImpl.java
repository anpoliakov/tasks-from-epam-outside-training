package by.epam.task6.model.dao.impl;

import by.epam.task6.model.constants.Constants;
import by.epam.task6.model.dao.ActionDAO;
import by.epam.task6.model.db.ConnectionPool;
import by.epam.task6.model.entity.Action;
import by.epam.task6.model.managers.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class represents methods for working with a table from a database "action".
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * **/
public class ActionDAOImpl implements ActionDAO {
    // Create a variable to work with Log4j
    final static Logger logger = Logger.getLogger(ActionDAOImpl.class);

    ConnectionPool pool;
    ConfigurationManager configManager;

    // Getting a connection with single classes ConnectionPool and ConfigurationManager
    public ActionDAOImpl() {
        pool = ConnectionPool.getConnectionPool();
        configManager = ConfigurationManager.getConfigManager();
    }

    //The method looks for an action by name in the "action" table
    @Override
    public Action find(String name) {
        //Get SQL request from file properties
        String sqlRequest = configManager.getSQLRequest("sql.action.find");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Action action = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, name.trim());
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                // By the received id and name return the object action
                int id = resultSet.getInt("id_action");
                action = new Action(id,name);
            }
        } catch (SQLException e) {
            logger.error("Look class ActionDAOImpl, method find() ", e);
        }finally {
            //Close resultSet, PreparedStatement and connection
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
        return action;
    }

    //Create in the database 'action'
    @Override
    public int create(Action action) {
        int result = Constants.RESULT_OPERATION_NEGATIVE;

        String name = action.getName();
        Action isAction = find(name);

        //If the action to be added does not exist yet
        if(isAction == null){
            String sqlRequest = configManager.getSQLRequest("sql.action.create");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                int i = statement.executeUpdate();

                if(i > 0) {
                    result = Constants.RESULT_OPERATION_POSITIVE; // display success information
                }
            } catch (SQLException e) {
                logger.error("Look class ActionDAOImpl, method create()", e);
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

        } else { // If such an action exists returns the result = 0
            result = Constants.RESULT_OPERATION_NEUTRAL;
        }
        return result;
    }

    @Override
    public boolean delete(Action action) {
        boolean flag = false;
        String name = action.getName();
        Action isAction = find(name);

        //Delete - if this action is found
        if(isAction != null){
            String sqlRequest = configManager.getSQLRequest("sql.action.delete");
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
                logger.error("Look class ActionDAOImpl, method delete()", e);
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

    //Updating in the database 'action'
    @Override
    public boolean update(Action action) {
        boolean flag = false;

        int id = action.getId();
        String name = action.getName();

        //update only if the action has id and name
        if(id != 0 && !name.isEmpty()){
            String sqlRequest = configManager.getSQLRequest("sql.action.update");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, id);
                int i = statement.executeUpdate();

                if(i > 0){
                    flag = true;
                }

            } catch (SQLException e) {
                logger.error("Look class ActionDAOImpl, method update()", e);
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

    //this method searches for action by id
    @Override
    public Action findById(int id) {
        String sqlRequest = configManager.getSQLRequest("sql.action.findById");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Action action = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, String.valueOf(id));
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int gottenId = resultSet.getInt("id_action");
                String name = resultSet.getString("name");
                action = new Action(gottenId,name);
            }

        } catch (SQLException e) {
            logger.error("Look class ActionDAOImpl, method findById()", e);
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
        return action;
    }

    //returns all actions
    @Override
    public List<Action> findAll() {
        List <Action> listAction = null;
        String sqlRequest = configManager.getSQLRequest("sql.action.findAll");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            listAction = new ArrayList<Action>();
            statement = connection.prepareStatement(sqlRequest);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id_action");
                String name = resultSet.getString("name");
                listAction.add(new Action(id,name));
            }

        } catch (SQLException e) {
            logger.error("Look class ActionDAOImpl, method findAll()", e);
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
        return listAction;
    }
}
