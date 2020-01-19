package by.epam.task6.controller;

import by.epam.task6.model.dao.HumanDAO;
import by.epam.task6.model.entity.Human;
import by.epam.task6.model.factories.DAOFactory;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/mainservlet")
public class Controller extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Human human = null;

        String nameFromForm = request.getParameter("name");
        String surnameFromForm = request.getParameter("surname");
        System.out.println("данные: " + nameFromForm + " " + surnameFromForm);

        //There's a little problem with that
        DAOFactory mySQLFactory = null;
        mySQLFactory = DAOFactory.getFactory();
        HumanDAO humanImpl = mySQLFactory.createHumanDAOImpl();
        human = humanImpl.find(nameFromForm,surnameFromForm);

        String humanJson = gson.toJson(human);
        System.out.println("С базы данных: " + humanJson);
        out.write(humanJson);
        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request, response);
    }
}