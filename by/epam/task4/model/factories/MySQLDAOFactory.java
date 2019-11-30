package by.epam.task4.model.factories;

import by.epam.task4.model.dao.HumanDAO;
import by.epam.task4.model.dao.IntructionDAO;
import by.epam.task4.model.dao.PlantDAO;
import by.epam.task4.model.dao.RoleDAO;
import by.epam.task4.model.dao.impl.HumanDAOImpl;
import by.epam.task4.model.dao.impl.PlantDAOImpl;
import by.epam.task4.model.dao.impl.RoleDAOImpl;

//Конкретная реализация фабрики (в данном случае mySQL фабрика)
public class MySQLDAOFactory extends DAOFactory{
    private static MySQLDAOFactory instance;
    //в этом классе можно производить инициализацию PoolConnection

    private MySQLDAOFactory() {}

    //как только я получу эту фабрику из главной фабрики DAOFactory произведётся
    // создание 1 единственного экземпляра MySQLDAOFactory, тут же вызывается configurationManager
    //который инициализирует pool (@продумать этот момент)
    public static synchronized MySQLDAOFactory getFactory() {
        if(instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    @Override
    public HumanDAO createHumanDAO() {
        return new HumanDAOImpl();
    }

    @Override
    public IntructionDAO createIntructionDAO() {
        return null;
    }

    @Override
    public PlantDAO createPlantDAO() {
        return new PlantDAOImpl();
    }

    @Override
    public RoleDAO createRoleDAO() {
        return new RoleDAOImpl();
    }


}
