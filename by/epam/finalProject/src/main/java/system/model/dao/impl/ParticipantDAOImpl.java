package system.model.dao.impl;


import org.apache.log4j.Logger;
import system.model.constants.Constants;
import system.model.dao.HumanDAO;
import system.model.dao.ParticipantDAO;
import system.model.dao.RoleDAO;
import system.model.db.ConnectionPool;
import system.model.entity.Human;
import system.model.entity.Participant;
import system.model.managers.ConfigurationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class represents methods for working with a table from a database "participant".
 * [!!! NOTICE !!!] From this class and below - a description of the methods will be contained in the interfaces
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * **/
public class ParticipantDAOImpl implements ParticipantDAO {
    final static Logger logger = Logger.getLogger(ParticipantDAOImpl.class);
    ConnectionPool pool;
    ConfigurationManager configManager;

    public ParticipantDAOImpl() {
        pool = ConnectionPool.getConnectionPool();
        configManager = ConfigurationManager.getConfigManager();
    }

    @Override
    public Participant find(Human fromHuman, Human forHuman) { // Human должен придти с ID - ОБЯЗАТЕЛЬНО (из БД)
        String sqlRequest = configManager.getSQLRequest("sql.participant.find");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Participant participant = null;

        int idHumanFrom = fromHuman.getId();
        int idHumanFor = forHuman.getId();

        if(idHumanFrom != idHumanFor){
            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, idHumanFrom);
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, idHumanFor);
                resultSet = statement.executeQuery();

                if (resultSet.next()){
                    int idParticipant = resultSet.getInt("id_participant");
                    participant = new Participant(idParticipant,fromHuman,forHuman);
                }
            } catch (SQLException e) {
                logger.error("Look class ParticipantDAOImpl, method find()", e);
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
        }else {
            logger.error("ERROR: man doesn’t give himself a task -> ParticipantDAOImpl -> method find()");
        }
        return participant;
    }

    @Override
    public Participant findHumanForester(Human human) {
        Participant participant = null;

        //Check that this person is from table 'Human'
        HumanDAO humanDAOImpl = new HumanDAOImpl();
        Human humanFromTableHuman = humanDAOImpl.findById(human.getId());

        if(humanFromTableHuman != null){

            String sqlRequest = configManager.getSQLRequest("sql.participant.findForesterHuman");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, humanFromTableHuman.getId());
                resultSet = statement.executeQuery();

                if (resultSet.next()){
                    int idParticipant = resultSet.getInt("id_participant");

                    ParticipantDAO participantDAOImpl = new ParticipantDAOImpl();
                    participant = participantDAOImpl.findById(idParticipant);
                }
            } catch (SQLException e) {
                logger.error("Look class ParticipantDAOImpl, method findHumanForester()", e);
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
        }
        return participant;
    }

    //Check for the correct role, the task from the owner to the forester
    private boolean isCorrectParticipant(Participant participant){
        boolean flag = false;
        Human fromHuman = participant.getFromHuman();
        Human forHuman = participant.getForHuman();

        int idRoleFromHuman = fromHuman.getRole().getId();
        int idRoleForHuman = forHuman.getRole().getId();

        RoleDAO roleDAOImpl = new RoleDAOImpl();
        String roleFromTableHuman1 = roleDAOImpl.findById(idRoleFromHuman).getName();
        String roleFromTableHuman2 = roleDAOImpl.findById(idRoleForHuman).getName();

        if(roleFromTableHuman1.equals("owner") && roleFromTableHuman2.equals("forester")){
            flag = true;
        }
        return flag;
    }

    @Override
    public int create(Participant participant) {
        int result = Constants.RESULT_OPERATION_NEGATIVE;
        boolean flag = false;
        Human fromHuman = participant.getFromHuman();
        Human forHuman = participant.getForHuman();

        Participant isParticipant = find(fromHuman,forHuman);
        flag = isCorrectParticipant(participant);

        if(isParticipant == null && flag == true){

            String sqlRequest = configManager.getSQLRequest("sql.participant.create");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, fromHuman.getId());
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, forHuman.getId());
                int i = statement.executeUpdate();

                if(i > 0) {
                    result = Constants.RESULT_OPERATION_POSITIVE;
                }
            } catch (SQLException e) {
                logger.error("Look class ParticipantDAOImpl, method create()", e);
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
    public boolean delete(Participant participant) {
        boolean flag = false;
        Human fromHuman = participant.getFromHuman();
        Human forHuman = participant.getForHuman();
        Participant isParticipant = find(fromHuman,forHuman);

        if(isParticipant != null){
            String sqlRequest = configManager.getSQLRequest("sql.participant.delete");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, fromHuman.getId());
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, forHuman.getId());
                int i = statement.executeUpdate();

                if(i > 0) {
                    flag = true;
                }
            } catch (SQLException e) {
                logger.error("Look class ParticipantDAOImpl, method delete()", e);
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

    /**
     *
     * Work only with 'participant' obtained from the database
     *
     * */
    @Override
    public boolean update(Participant participant) {
        boolean flag = false;
        Human fromHuman = participant.getFromHuman();
        Human forHuman = participant.getForHuman();

        Participant isParticipant = find(fromHuman,forHuman);
        if(isParticipant != null){
            String sqlRequest = configManager.getSQLRequest("sql.participant.update");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, fromHuman.getId());
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, forHuman.getId());
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, participant.getId());
                int i = statement.executeUpdate();

                if(i > 0) {
                    flag = true;
                }
            } catch (SQLException e) {
                logger.error("Look class ParticipantDAOImpl, method update()", e);
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
    public Participant findById(int id) {
        Participant participant = null;
        String sqlRequest = configManager.getSQLRequest("sql.participant.findById");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(Constants.INDEX_FIRST_PARAMETER, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int receivedIDFromHuman = resultSet.getInt("from_human");
                int receivedIDForHuman = resultSet.getInt("for_human");

                HumanDAO humanDAOImpl = new HumanDAOImpl();
                Human fromHuman = humanDAOImpl.findById(receivedIDFromHuman);
                Human forHuman = humanDAOImpl.findById(receivedIDForHuman);

                participant = new Participant(id,fromHuman,forHuman);
            }
        } catch (SQLException e) {
            logger.error("Look class ParticipantDAOImpl, method findById()", e);
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
         return participant;
    }

    @Override
    public List<Participant> findAll() {
        List <Participant> listParticipant = null;
        String sqlRequest = configManager.getSQLRequest("sql.participant.findAll");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            listParticipant = new ArrayList<Participant>();
            statement = connection.prepareStatement(sqlRequest);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int receivedIDParticipant = resultSet.getInt("id_participant");
                int receivedIDFromHuman = resultSet.getInt("from_human");
                int receivedIDForHuman = resultSet.getInt("for_human");

                HumanDAO humanDAOImpl = new HumanDAOImpl();
                Human fromHuman = humanDAOImpl.findById(receivedIDFromHuman);
                Human forHuman = humanDAOImpl.findById(receivedIDForHuman);

                listParticipant.add(new Participant(receivedIDParticipant,fromHuman,forHuman));
            }

        } catch (SQLException e) {
            logger.error("Look class ParticipantDAOImpl, method findAll()", e);
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
        return listParticipant;
    }

}
