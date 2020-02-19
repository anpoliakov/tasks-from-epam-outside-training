package controller;

import com.google.gson.Gson;
import constants.Constants;
import model.User;
import dataBase.DataBaseUsers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/firstservlet")
public class FirstServlet extends javax.servlet.http.HttpServlet {
    DataBaseUsers dataBaseUsers = null;
    Gson gson = null;

    @Override
    public void init() throws ServletException {
        //инициализируем сингл класс DataBaseUsers, инициализируем бибилеотеку Google для рабоыт с JSON
        super.init();
        dataBaseUsers = DataBaseUsers.getInstance();
        gson = new Gson();
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //ввожу переменную для отслеживания состояния запросов
        int statusOperation = Constants.DEFAULT_NUMBER;

        //получаю json из запроса
        String stringJson = getStringJson(request);

        //получаю пользователя из json
        User user = getUserFromJson(stringJson);

        //если запрос пришёл с формы РЕГИСТРАЦИИ (есть параметр email)
        if(user.getEmail() != null){
            boolean isAddNewUser = dataBaseUsers.addUser(user);
            //если пользователь был добавлен
            if(isAddNewUser){
                System.out.println("Новый пользователь добавлен! Состояние БД пользователей: ");
                dataBaseUsers.showAllBase();
                statusOperation = Constants.ADDED_NEW_USER;
            }else { //если такой пользователь есть в БД
                System.out.println("Такой пользователь существует");
                statusOperation = Constants.USER_EXIST;
            }
        }else{ //если запрос пришёл с формы ВХОДА
            boolean isFindUser = dataBaseUsers.findUser(user);
            //если пользователь найден
            if(isFindUser == true){
                statusOperation = Constants.USER_FOUND;
                System.out.println("пользователь найден!");

            }else{ // если пользователя не найдено
                statusOperation = Constants.USER_NOT_FOUND;
                System.out.println("пользователь НЕ найден!");
            }
        }

        user.setStatus(statusOperation);
        String jsonUser = gson.toJson(user);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.print(jsonUser);
        out.flush();
        out.close();
    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request, response);
    }


    private String getStringJson(HttpServletRequest request){
        BufferedReader buff = null;
        String json = "";

        try {
            buff = new BufferedReader(new InputStreamReader(request.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (buff != null) {
            try {
                json = buff.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    buff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Полученный JSON: " + json);
        return json;
    }

    private User getUserFromJson(String jsonStr){
        User user = gson.fromJson(jsonStr, User.class);
        return user;
    }
}
