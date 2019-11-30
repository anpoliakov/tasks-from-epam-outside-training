package by.epam.task4.businessLogic;

import by.epam.task4.model.dao.HumanDAO;
import by.epam.task4.model.entity.Human;
import by.epam.task4.model.entity.Role;
import by.epam.task4.model.factories.DAOFactory;

public class ServiceLogic {
    private static ServiceLogic instance = new ServiceLogic();

    private static DAOFactory mySQLFactory = DAOFactory.getFactory(); //я получаю mySQL фабрику или изменить код и сделать new MySQLDAOFactory();

    //если произвожу поиск человека по его ID
    public Human getHumanById(int id) {
        HumanDAO humanImpl = mySQLFactory.createHumanDAO(); //получаю имплементацию с методами и их реализацией
        return humanImpl.find(new Human("Andre","Прогрестинатор",new Role("Прогрестинатор")));
    }

}


































/*
        DAOFactory mySQLFactory = new MySQLDAOFactory();
        HumanDAO humDAO = mySQLFactory.getHumanDAO();

        // создать нового клиента
        int newhumDAO = humDAO.insertHuman();

        // найти объект human. Получить объект Transfer Object.
        Human human = humDAO.findHuman();

        // изменить значения в Transfer Object.
        human.setSurname();
        human.setName();

        // обновить объект customer, используя DAO
        humDAO.updateHuman(human);

        // удалить объект customer
        humDAO.deleteHuman();
 */