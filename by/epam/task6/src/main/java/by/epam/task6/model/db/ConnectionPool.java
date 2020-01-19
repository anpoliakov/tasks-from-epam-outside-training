package by.epam.task6.model.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 *  This class pool connection (singleton based)
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class ConnectionPool {
    final static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool pool;
    //The queue will contain a list of connections
    private BlockingQueue <Connection> connectionList;

    private String login;
    private String password;
    private String driver;
    private String url;
    private Integer sizePool;

    //positions for inserting values
    private final int POSITION_LOGIN = 0;
    private final int POSITION_PASSWORD = 1;
    private final int POSITION_DRIVER = 2;
    private final int POSITION_URL = 3;
    private final int POSITION_SIZEPOOL = 4;

    private ConnectionPool(){}

    //method for obtaining an object of a static class 'ConnectionPool'
    public static ConnectionPool getConnectionPool(){
        if (pool == null) {
            synchronized (ConnectionPool.class) {
                if (pool == null){
                    pool = new ConnectionPool();
                }
            }
        }
        return  pool;
    }

    //method saves database settings and uses them
    public void setConfigPool(String [] configDB){

        this.login = configDB[POSITION_LOGIN];
        this.password = configDB[POSITION_PASSWORD];
        this.driver = configDB[POSITION_DRIVER];
        this.url = configDB[POSITION_URL];
        this.sizePool = Integer.parseInt(configDB[POSITION_SIZEPOOL]);

        //Starting the connection creation process in BlockingQueue
        fillPool();
    }

    //queue filling
    private void fillPool(){
        try {
            Class.forName(driver);
            connectionList = new ArrayBlockingQueue(sizePool);
            for (int i = 0; i < sizePool; i++){
                connectionList.put(createConnection());
            }
        } catch (ClassNotFoundException e) {
            logger.error("See ConnectionPool class in fillPool () method, driver loading error", e);
        } catch (InterruptedException e) {
            logger.error("See ConnectionPool class in fillPool () method, insert error in 'connectionList'", e);
        }
    }

    //Getting connection from queue
    public Connection getConnection(){
        synchronized (pool) {
            Connection conn = null;
            try {
                conn = connectionList.take();
            } catch (InterruptedException e) {
                logger.error("Error getting connection", e);
            }
            return conn;
        }
    }

    //Return the connection to the queue
    //fix the error - we can put any Connection
    public void closeConnection(Connection conn){
        try {
            connectionList.put(conn);
        } catch (InterruptedException e) {
            logger.error("See ConnectionPool class in closeConnection() method, insert error in 'connectionList'", e);
        }
    }

    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,login,password);
        } catch (Exception e) {
            logger.error("Error getting Connection in method 'createConnection' in class 'ConnectionPool'", e);
        }
        return conn;
    }
}
