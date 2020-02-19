package system.model.dao.impl;


import org.apache.log4j.Logger;
import system.model.constants.Constants;
import system.model.dao.RoleDAO;
import system.model.db.ConnectionPool;
import system.model.entity.Role;
import system.model.managers.ConfigurationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *  This class represents methods for working with a table from a database "role".
 * [!!! NOTICE !!!] From this class and below - a description of the methods will be contained in the interfaces
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 *
 * */
public class RoleDAOImpl implements RoleDAO {
    final static Logger logger = Logger.getLogger(RoleDAOImpl.class);
    private ConnectionPool pool;
    private ConfigurationManager configManager;

    public RoleDAOImpl() {
        pool = ConnectionPool.getConnectionPool();
        configManager = ConfigurationManager.getConfigManager();
    }


    public Role find(String string) {
        String sqlRequest = configManager.getSQLRequest("sql.role.find");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Role role = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, correctString(string));
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int id = resultSet.getInt("id_role");
                String name = resultSet.getString("name");
                role = new Role(id,name);
            }

        } catch (SQLException e) {
            logger.error("Look class RoleDAOImpl, method find()", e);
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
        return role;
    }

    public int create(Role role) {
        int result = Constants.RESULT_OPERATION_NEGATIVE;

        String name = correctString(role.getName());
        Role resultSearchRole = find(name);

        if(resultSearchRole == null){
            String sqlRequest = configManager.getSQLRequest("sql.role.create");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setString(Constants.INDEX_FIRST_PARAMETER, name);
                int i = statement.executeUpdate();

                if(i > 0) {
                    result = Constants.RESULT_OPERATION_POSITIVE; // то говорим что результат операции положительный
                }
            } catch (SQLException e) {
                logger.error("Look class RoleDAOImpl, method create()", e);
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

    public boolean delete(Role role) {
        boolean flag = false;
        String name = correctString(role.getName());
        Role resultSearchRole = find(name);

        if(resultSearchRole != null){
            String sqlRequest = configManager.getSQLRequest("sql.role.delete");
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
                logger.error("Look class RoleDAOImpl, method delete()", e);
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

    public boolean update(Role role) {
        boolean flag = false;
        int id = role.getId();
        String name = correctString(role.getName());

        if(id != 0 && !name.isEmpty()){
            String sqlRequest = configManager.getSQLRequest("sql.role.update");
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
                logger.error("Look class RoleDAOImpl, method update()", e);
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

    public Role findById(int id) {
        String sqlRequest = configManager.getSQLRequest("sql.role.findById");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Role role = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setString(Constants.INDEX_FIRST_PARAMETER, String.valueOf(id));
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int gottenId = resultSet.getInt("id_role");
                String name = resultSet.getString("name");
                role = new Role(gottenId,name);
            }

        } catch (SQLException e) {
            logger.error("Look class RoleDAOImpl, method findById()", e);
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
        return role;
    }

    public List<Role> findAll() {
        List <Role> listRoles = null;
        String sqlRequest = configManager.getSQLRequest("sql.role.findAll");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            listRoles = new ArrayList<Role>();
            statement = connection.prepareStatement(sqlRequest);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id_role");
                String name = resultSet.getString("name");
                listRoles.add(new Role(id,name));
            }

        } catch (SQLException e) {
            logger.error("Look class RoleDAOImpl, method findAll()", e);
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
        return listRoles;
    }

    private String correctString(String string){
        return string.toLowerCase().trim();
    }
}