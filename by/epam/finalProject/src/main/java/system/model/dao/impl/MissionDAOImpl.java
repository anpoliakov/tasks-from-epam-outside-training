package system.model.dao.impl;

import org.apache.log4j.Logger;
import system.model.constants.Constants;
import system.model.dao.ActionDAO;
import system.model.dao.MissionDAO;
import system.model.dao.ParticipantDAO;
import system.model.dao.PlantDAO;
import system.model.db.ConnectionPool;
import system.model.entity.*;
import system.model.managers.ConfigurationManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class represents methods for working with a table from a database "mission".
 * [!!! NOTICE !!!] From this class and below - a description of the methods will be contained in the interfaces
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 *
 * */
public class MissionDAOImpl implements MissionDAO {
    final static Logger logger = Logger.getLogger(MissionDAOImpl.class);
    ConnectionPool pool;
    ConfigurationManager configManager;

    public MissionDAOImpl() {
        pool = ConnectionPool.getConnectionPool();
        configManager = ConfigurationManager.getConfigManager();
    }

    @Override
    public Mission find(Participant participant, Action action, Plant plant) {
        Mission mission = null;
        int idParticipant = participant.getId();
        int idAction = action.getId();
        int idPlant = plant.getId();

        String sqlRequest = configManager.getSQLRequest("sql.mission.find");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(Constants.INDEX_FIRST_PARAMETER, idParticipant);
            statement.setInt(Constants.INDEX_SECOND_PARAMETER, idAction);
            statement.setInt(Constants.INDEX_THIRD_PARAMETER, idPlant);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                int idMission = resultSet.getInt("id_mission");
                boolean status = resultSet.getBoolean("status");

                ParticipantDAO participantDAOImpl = new ParticipantDAOImpl();
                Participant participantObject = participantDAOImpl.findById(idParticipant);

                ActionDAO actionDAOImpl = new ActionDAOImpl();
                Action actionObject = actionDAOImpl.findById(idAction);

                PlantDAO plantDAOImpl = new PlantDAOImpl();
                Plant plantObject = plantDAOImpl.findById(idPlant);

                mission = new Mission(idMission,participantObject,actionObject,plantObject,status);
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
        return mission;
    }

    /**
     *
     * Complete the transferred mission
     *
     * */
    @Override
    public boolean makeMission(Mission mission) {
        boolean flag = false;
        Participant participant = mission.getParticipant();
        Action action = mission.getAction();
        Plant plant = mission.getPlant();
        boolean status = mission.getStatus();

        Mission isMission = find(participant, action, plant);

        if(isMission != null){
            mission.setStatus(true);
            update(mission);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Mission> findMissionHuman(Human human) { // Required ID requirement in human!
        List <Mission> listMissionsHuman = null;

        ParticipantDAO participantDAOImpl = new ParticipantDAOImpl();
        Participant participant = participantDAOImpl.findHumanForester(human);

        String sqlRequest = configManager.getSQLRequest("sql.mission.findAllMissionHuman");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(Constants.INDEX_FIRST_PARAMETER, participant.getId());
            resultSet = statement.executeQuery();
            listMissionsHuman = new ArrayList<>();

            while (resultSet.next()){
                int idMission = resultSet.getInt("id_mission");
                int idParticipant = resultSet.getInt("participant");
                int idAction = resultSet.getInt("action");
                int idPlant = resultSet.getInt("plant");
                boolean status = resultSet.getBoolean("status");

                Participant participantObject = participantDAOImpl.findById(idParticipant);

                ActionDAO actionDAOImpl = new ActionDAOImpl();
                Action actionObject = actionDAOImpl.findById(idAction);

                PlantDAO plantDAOImpl = new PlantDAOImpl();
                Plant plantObject = plantDAOImpl.findById(idPlant);

                listMissionsHuman.add(new Mission(idMission,participantObject,actionObject,plantObject,status));
            }

        } catch (SQLException e) {
            logger.error("Look class HumanDAOImpl, method findMissionHuman()", e);
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
        return listMissionsHuman;
    }


    @Override
    public int create(Mission mission) {
        int result = Constants.RESULT_OPERATION_NEGATIVE;

        Participant participant = mission.getParticipant();
        Action action = mission.getAction();
        Plant plant = mission.getPlant();

        //Сheck whether such a task already exists
        Mission isMission = find(participant, action, plant);

        if(isMission == null){
            String sqlRequest = configManager.getSQLRequest("sql.mission.create");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, participant.getId());
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, action.getId());
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, plant.getId());
                statement.setBoolean(Constants.INDEX_FOURTH_PARAMETER, false);
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
    public boolean delete(Mission mission) {
        boolean flag = false;

        Participant participant = mission.getParticipant();
        Action action = mission.getAction();
        Plant plant = mission.getPlant();

        Mission isMission = find(participant, action, plant );

        if(isMission != null){
            String sqlRequest = configManager.getSQLRequest("sql.mission.delete");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, participant.getId());
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, action.getId());
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, plant.getId());
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
    public boolean update(Mission mission) {
        boolean flag = false;

        int idMission = mission.getId();
        Participant participant = mission.getParticipant();
        Action action = mission.getAction();
        Plant plant = mission.getPlant();
        boolean status = mission.getStatus();

        Mission isMission = find(participant, action, plant);

        if(idMission != 0 && isMission != null){
            String sqlRequest = configManager.getSQLRequest("sql.mission.update");
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(Constants.INDEX_FIRST_PARAMETER, participant.getId());
                statement.setInt(Constants.INDEX_SECOND_PARAMETER, action.getId());
                statement.setInt(Constants.INDEX_THIRD_PARAMETER, plant.getId());
                statement.setBoolean(Constants.INDEX_FOURTH_PARAMETER, status);
                statement.setInt(Constants.INDEX_FIFTH_PARAMETER, idMission);
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
    public Mission findById(int id) {
        String sqlRequest = configManager.getSQLRequest("sql.mission.findById");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Mission mission = null;

        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(Constants.INDEX_FIRST_PARAMETER, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                int idParticipant = resultSet.getInt("participant");
                int idAction = resultSet.getInt("action");
                int idPlant = resultSet.getInt("plant");
                boolean status = resultSet.getBoolean("status");

                ParticipantDAO participantDAOImpl = new ParticipantDAOImpl();
                Participant participantObject = participantDAOImpl.findById(idParticipant);

                ActionDAO actionDAOImpl = new ActionDAOImpl();
                Action actionObject = actionDAOImpl.findById(idAction);

                PlantDAO plantDAOImpl = new PlantDAOImpl();
                Plant plantObject = plantDAOImpl.findById(idPlant);

                mission = new Mission(id,participantObject,actionObject,plantObject,status);
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
        return mission;
    }

    @Override
    public List<Mission> findAll() {
        List <Mission> listMission = null;
        String sqlRequest = configManager.getSQLRequest("sql.mission.findAll");
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            listMission = new ArrayList<Mission>();
            statement = connection.prepareStatement(sqlRequest);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int idMission = resultSet.getInt("id_mission");
                int idParticipant = resultSet.getInt("participant");
                int idAction = resultSet.getInt("action");
                int idPlant = resultSet.getInt("plant");
                boolean status = resultSet.getBoolean("status");

                ParticipantDAO participantDAOImpl = new ParticipantDAOImpl();
                Participant participantObject = participantDAOImpl.findById(idParticipant);

                ActionDAO actionDAOImpl = new ActionDAOImpl();
                Action actionObject = actionDAOImpl.findById(idAction);

                PlantDAO plantDAOImpl = new PlantDAOImpl();
                Plant plantObject = plantDAOImpl.findById(idPlant);

                listMission.add(new Mission(idMission,participantObject,actionObject,plantObject,status));
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
        return listMission;
    }
}
