package system.model.factories;

import system.model.dao.*;

/**
 * This abstract class provides a factory for the production of a factory specific "СУБД"
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public abstract class DAOFactory {

    public abstract ActionDAO createActionDAOImpl();
    public abstract HumanDAO createHumanDAOImpl();
    public abstract MissionDAO createMissionDAOImpl();
    public abstract ParticipantDAO createParticipantDAOImpl();
    public abstract PlantDAO createPlantDAOImpl();
    public abstract RoleDAO createRoleDAOImpl();

    //Since I only work with mySQL
    //I get a factory to work with mySQL
    public static DAOFactory getFactory() {
        return MySQLDAOFactory.getFactory();
    }
}
