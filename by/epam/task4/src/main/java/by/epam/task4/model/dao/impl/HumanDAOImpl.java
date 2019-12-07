package by.epam.task4.model.dao.impl;

import by.epam.task4.model.constants.Constants;
import by.epam.task4.model.dao.HumanDAO;
import by.epam.task4.model.db.ConnectionPool;
import by.epam.task4.model.entity.Human;
import by.epam.task4.model.entity.Role;
import by.epam.task4.model.managers.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents methods for working with a table from a database "human".
 * [!!! NOTICE !!!] From this class and below - a description of the methods will be contained in the interfaces
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */

public class HumanDAOImpl implements HumanDAO  {
    final static Logger logger = Logger.getLogger(HumanDAOImpl.class);

    ConnectionPool pool;
    ConfigurationManager configManager;

    public HumanDAOImpl() {
        pool = ConnectionPool.getConnectionPool();
        configManager = ConfigurationManager.getConfigManager();
    }

    @Override
    public Human find(String name, String surname) {
        String sqlRequest = configManager.getSQLRequest("sql.human.find");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Human human = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
            statement.setString(Constants.INDEX_SECOND_PARAMETER, surname);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int receivedId = resultSet.getInt("id_human");
                String receivedName = resultSet.getString("name");
                String receivedSurname = resultSet.getString("surname");
                int receivedIdRole = resultSet.getInt("role");

                RoleDAOImpl roleImpl = new RoleDAOImpl();
                Role receivedRole = roleImpl.findById(receivedIdRole);

                human = new Human(receivedId,receivedName,receivedSurname,receivedRole);
            }

        } catch (SQLException e) {
            logger.error("Look class HumanDAOImpl, method find()", e);
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
        return human;
    }

    @Override
    public int create(Human human) {
        int result = Constants.RESULT_OPERATION_NEGATIVE;

        String name = human.getName();
        String surname = human.getSurname();
        Role role = human.getRole();

        //Does such a person already exist?
        //I did not use strict person identification
        Human isHuman = find(name, surname);

        //if not
        if(isHuman == null){

            RoleDAOImpl roleImpl = new RoleDAOImpl();
            roleImpl.create(role); // роли не повторяются - если такая существует то ничего не произодйёт
            role = roleImpl.find(role.getName());

            String sqlRequest = configManager.getSQLRequest("sql.human.create");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                statement.setString(Constants.INDEX_SECOND_PARAMETER, surname);
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, role.getId());
                int i = statement.executeUpdate();

                if(i > 0) {
                    result = Constants.RESULT_OPERATION_POSITIVE;
                }
            } catch (SQLException e) {
                logger.error("Look class HumanDAOImpl, method create()", e);
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
            result = Constants.RESULT_OPERATION_NEUTRAL;
        }
        return result;
    }

    @Override
    public boolean delete(Human human) {
        boolean flag = false;
        String name = human.getName();
        String surname = human.getSurname();

        Human isHuman = find(name, surname);

        if(isHuman != null){
            String sqlRequest = configManager.getSQLRequest("sql.human.delete");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                statement.setString(Constants.INDEX_SECOND_PARAMETER, surname);
                int i = statement.executeUpdate();

                if(i > 0) {
                    flag = true;
                }
            } catch (SQLException e) {
                logger.error("Look class HumanDAOImpl, method delete()", e);
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
    public boolean update(Human human) {
        boolean flag = false;

        int id = human.getId();
        String name = human.getName();
        String surname = human.getSurname();
        int role = human.getRole().getId();

        //check that this person was obtained from the table
        // and he have id,name and surname
        if(id != 0 && !name.isEmpty() && !surname.isEmpty()){

            String sqlRequest = configManager.getSQLRequest("sql.human.update");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                statement.setString(Constants.INDEX_SECOND_PARAMETER, surname);
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, role);
                statement.setInt(Constants.INDEX_FOURTH_PARAMETER, id);
                int i = statement.executeUpdate();

                if(i > 0){
                    flag = true;
                }

            } catch (SQLException e) {
                logger.error("Look class HumanDAOImpl, method update()", e);
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
    public Human findById(int id) {
        String sqlRequest = configManager.getSQLRequest("sql.human.findById");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Human human = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, String.valueOf(id));
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int receivedId = resultSet.getInt("id_human");
                String receivedName = resultSet.getString("name");
                String receivedSurname = resultSet.getString("surname");
                int receivedIdRole = resultSet.getInt("role");

                RoleDAOImpl roleImpl = new RoleDAOImpl();
                Role receivedRole = roleImpl.findById(receivedIdRole);

                human = new Human(receivedId,receivedName,receivedSurname,receivedRole);
            }

        } catch (SQLException e) {
            logger.error("Look class HumanDAOImpl, method findById()", e);
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
        return human;
    }

    @Override
    public List<Human> findAll() {
        List <Human> listHuman = null;
        String sqlRequest = configManager.getSQLRequest("sql.human.findAll");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            listHuman = new ArrayList<Human>();
            statement = connection.prepareStatement(sqlRequest);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int receivedId = resultSet.getInt("id_human");
                String receivedName = resultSet.getString("name");
                String receivedSurname = resultSet.getString("surname");
                int receivedIdRole = resultSet.getInt("role");

                RoleDAOImpl roleImpl = new RoleDAOImpl();
                Role receivedRole = roleImpl.findById(receivedIdRole);

                listHuman.add(new Human(receivedId,receivedName,receivedSurname,receivedRole));
            }

        } catch (SQLException e) {
            logger.error("Look class HumanDAOImpl, method findAll()", e);
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
        return listHuman;
    }
}