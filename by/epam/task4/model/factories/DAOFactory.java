package by.epam.task4.model.factories;

import by.epam.task4.model.dao.HumanDAO;
import by.epam.task4.model.dao.IntructionDAO;
import by.epam.task4.model.dao.PlantDAO;
import by.epam.task4.model.dao.RoleDAO;

//для каждоый СУБД будет своя реализация данного класса, это вершина ДАО
//мог бы сделать interface - но сюда я влажил метод static который определяет с какой СУБД я работаю
public abstract class DAOFactory {

    public abstract HumanDAO createHumanDAO();
    public abstract IntructionDAO createIntructionDAO();
    public abstract PlantDAO createPlantDAO();
    public abstract RoleDAO createRoleDAO();

    public static DAOFactory getFactory() {
        return MySQLDAOFactory.getFactory();
    }
}
