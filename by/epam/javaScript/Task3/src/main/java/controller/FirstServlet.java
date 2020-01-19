package controller;

import com.google.gson.Gson;
import model.User;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/firstservlet")
public class FirstServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("Enter doPost");
        request.setCharacterEncoding("UTF-8");

        BufferedReader buff = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if (buff != null) {
            json = buff.readLine();
            System.out.println(json);
        }

        Gson gson = new Gson();
        User us = gson.fromJson(json, User.class);
        System.out.println("Емайл полученный: " + us.getEmail());
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request, response);
    }
}
