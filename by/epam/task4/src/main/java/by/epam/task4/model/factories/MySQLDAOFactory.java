package by.epam.task4.model.factories;

import by.epam.task4.model.dao.*;
import by.epam.task4.model.dao.impl.*;
import by.epam.task4.model.db.ConnectionPool;
import by.epam.task4.model.managers.ConfigurationManager;

/**
 * The class extends the methods of an abstract class
 * and determines what they will return
 * This class is based on the singleton pattern.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class MySQLDAOFactory extends DAOFactory{
    private static MySQLDAOFactory instance;
    public ConnectionPool pool;

    //As soon as we create a factory - we prepare ConfigurationManager and ConnectionPool for work
    private MySQLDAOFactory() {
        ConfigurationManager configManager = ConfigurationManager.getConfigManager();
        pool = ConnectionPool.getConnectionPool();

        String [] configBD = configManager.getConfigBD();
        pool.setConfigPool(configBD);
    }

    public static synchronized MySQLDAOFactory getFactory() {
        if(instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    @Override
    public ActionDAO createActionDAOImpl() {
        return new ActionDAOImpl();
    }

    @Override
    public HumanDAO createHumanDAOImpl() {
        return new HumanDAOImpl();
    }

    @Override
    public MissionDAO createMissionDAOImpl() {
        return new MissionDAOImpl();
    }

    @Override
    public ParticipantDAO createParticipantDAOImpl() {
        return new ParticipantDAOImpl();
    }

    @Override
    public PlantDAO createPlantDAOImpl() {
        return new PlantDAOImpl();
    }

    @Override
    public RoleDAO createRoleDAOImpl() {
        return new RoleDAOImpl();
    }
}
