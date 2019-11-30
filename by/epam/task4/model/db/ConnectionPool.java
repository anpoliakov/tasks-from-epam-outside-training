package by.epam.task4.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final ConnectionPool pool = new ConnectionPool();
    private BlockingQueue <Connection> connectionList;
    private String login;
    private String password;
    private String driver;
    private String url;
    private Integer sizePool;

    private ConnectionPool(){}

    public static ConnectionPool getConnectionPool(){
        return  pool;
    }

    public void setConfigPool(final String login, final String password, final String driver,
                              final String url, final Integer poolSize){
        this.login = login;
        this.password = password;
        this.driver = driver;
        this.url = url;
        this.sizePool = poolSize;

        //запускаю процесс создания соединений и заполняю BlockingQueue
        fillPool();
    }

    private void fillPool(){
        try {
            Class.forName(driver);
            connectionList = new ArrayBlockingQueue(sizePool);
            for (int i = 0; i < sizePool; i++){
                connectionList.put(createConnection());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public Connection getConnection(){
        synchronized (pool) {
            Connection conn = null;
            try {
                conn = connectionList.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return conn;
        }
    }

    public void closeConnection(Connection conn){ //может быть ошибка если conn = null; а что если вернули connection пораждённой не в этой очереди?
        try {
            connectionList.put(conn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
