package system.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import system.model.dao.HumanDAO;
import system.model.entity.Human;
import system.model.factories.DAOFactory;
import system.model.managers.ConfigurationManager;
import system.model.managers.ObjectJson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/mainServlet")
public class MainServlet extends javax.servlet.http.HttpServlet {
    final static Logger logger = Logger.getLogger(ConfigurationManager.class);

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Gson gson = new Gson();
        String strJson = getJsonFromClient(request);
        System.out.println("JSON считанный: " + strJson); //чисто для визуального образа

        ObjectJson objectJson = gson.fromJson(strJson, ObjectJson.class);
        String name = objectJson.getName();
        String surname = objectJson.getSurname();
        String email = objectJson.getEmail();
        Human human = new Human(name,surname,email);

        DAOFactory mySQLFactory = DAOFactory.getFactory();

        HumanDAO humanDAOImpl = mySQLFactory.createHumanDAOImpl();
        Human humanDB = humanDAOImpl.findByNameSurname(name,surname);
        String existHuman;

        if(humanDB != null){
            System.out.println("Пользователь" + humanDB.getName()+ " в БД существует!");
            existHuman = "exist";
        }else {
            System.out.println("Пользователь не был найден! " + human.toString());
            existHuman = "notExist";
        }

        response.setContentType("text/plain");
        response.getWriter().println(existHuman);

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request,response);
    }

    //метод - возвращает JSON из потока запроса
    private String getJsonFromClient(HttpServletRequest request){
        BufferedReader buff = null;
        String stringJson = "";

        try {
            buff = new BufferedReader(new InputStreamReader(request.getInputStream()));
        } catch (IOException e) {
            logger.error("Error getting information from a stream");
        }

        if (buff != null) {
            try {
                stringJson = buff.readLine();
            } catch (IOException e) {
                logger.error("The line is not read");
            }finally {
                try {
                    buff.close();
                } catch (IOException e) {
                    logger.error("Closing error BufferedReader");
                }
            }
        }
        return stringJson;
    }
}
