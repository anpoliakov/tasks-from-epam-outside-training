package by.epam.task4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private List <Connection> availableConnections = new ArrayList<>();
    private List <Connection> usedConnections = new ArrayList<>();
    private String url;
    private String login;
    private String password;

    public ConnectionPool(String login, String password, String url, String driver, int initNumberConnections) {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            //LOG файл
            e.printStackTrace();
        }

        this.url = url;
        this.login = login;
        this.password = password;
        for (int i = 0; i < initNumberConnections; i++) {
            availableConnections.add(createConnection());
        }
    }

    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,login,password);
        } catch (Exception e) {
            //в LOG!!!!
            e.printStackTrace();
        }
        return conn;
    }

    // взять 1 Connection
    public synchronized Connection getConnection(){
        Connection newConn = null;

        if (availableConnections.size() == 0) {
            newConn = createConnection();
        } else {
            newConn = availableConnections.remove(availableConnections.size()-1);
        }

        usedConnections.add(newConn);
        return newConn;
    }

    //  вернуть 1 Connection
    public synchronized void returnConnection(Connection c) throws NullPointerException {
        if (c != null) {
            if (usedConnections.remove(c)) {
                availableConnections.add(c);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }

    //получаю число свободных подключений
    public int getNumberAvailableConnections() {
        return availableConnections.size();
    }
}
