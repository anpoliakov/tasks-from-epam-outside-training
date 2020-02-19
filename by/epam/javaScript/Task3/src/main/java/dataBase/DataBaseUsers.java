package dataBase;

import model.User;

import java.util.HashSet;
import java.util.Set;

public class DataBaseUsers {

    private static DataBaseUsers instance;
    private Set <User> dataBaseUsers;

    private DataBaseUsers() {
        dataBaseUsers = new HashSet<>();

    }

    public static DataBaseUsers getInstance(){
        if (instance == null) {
            synchronized (DataBaseUsers.class) {
                if (instance == null){
                    instance = new DataBaseUsers();
                }
            }
        }
        return instance;
    }

    //добавление пользователя
    public boolean addUser(User user){
        boolean isSuccessfully = false;
        if(user != null){
            isSuccessfully = dataBaseUsers.add(user);
        }
        return isSuccessfully;
    }

    //поиск пользователя в коллекции Set
    public boolean findUser(User user){
        boolean isUser = false;
        if(user != null){
            System.out.println("Результат поиска в коллекции -> " + user.toString() + " ==== " + dataBaseUsers.contains(user));
            isUser = dataBaseUsers.contains(user);
        }
        return isUser;
    }

    //Показать весь список пользователей в коллекции
    public void showAllBase(){
        for (User u : dataBaseUsers) {
            System.out.println(u.toString());
        }
    }

}
