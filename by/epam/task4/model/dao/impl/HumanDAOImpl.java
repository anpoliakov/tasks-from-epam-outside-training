package by.epam.task4.model.dao.impl;

import by.epam.task4.model.dao.HumanDAO;
import by.epam.task4.model.entity.Human;

public class HumanDAOImpl implements HumanDAO  {

    @Override
    public int create(Human human) {
        return 0;
    }

    @Override
    public boolean delete(Human human) {
        return false;
    }

    @Override
    public Human find(Human human) {
        return null;
    }

    @Override
    public boolean update(Human human) {
        return false;
    }


    //private static Logger log = Logger.getLogger(MySQLHumanDAO.class.getName());

    //Клиент использует данные методы обьекта
    //Данный обьект создаёт/использует Сущности



}


//насчёт tru и finnaly -> https://youtu.be/flMFICpPfcI?t=619
//золотое правило: для каждого метода который работает с БД - свой Connection и PreparedStatement,ResultSet