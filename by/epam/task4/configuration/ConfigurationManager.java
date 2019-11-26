package by.epam.task4.configuration;

import by.epam.task4.ConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class ConfigurationManager {
    private static ConfigurationManager configManager;

    private String PATH_CONFIG_DB = "src/by/epam/task4/configuration/configDB.properties";
    private String PATH_REQUESTS_SQL = "src/by/epam/task4/configuration/requestsSQL.properties";
    private Properties properties;
    private ConnectionPool connectionPool;

    private ConfigurationManager() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH_CONFIG_DB));
            properties.load(new FileInputStream(PATH_REQUESTS_SQL));
            connectionPool = createConnectionPool();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigurationManager getConfigurationManager (){
        if (configManager == null) {
            synchronized (ConfigurationManager.class) {
                if (configManager == null)
                    configManager = new ConfigurationManager();
            }
        }
        return configManager;
    }

    //создать connection pool
    private ConnectionPool createConnectionPool(){
        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");
        String driver = properties.getProperty("db.driver");
        String url = properties.getProperty("db.url");
        int sizePool = Integer.parseInt(properties.getProperty("sizePool"));
        //файлы могут быть = null - нет таких строк
        return (new ConnectionPool(login,password,url,driver,sizePool));
    }

    //получить соединение
    public Connection getConnection(){
        if(connectionPool != null){
            return connectionPool.getConnection();
        }else {
            return null;
        }
    }

    //закрыть соединение
    public void returnConnection(Connection connection){
        connectionPool.returnConnection(connection);
    }

    // получаю sql запрос из файла
    public String getFromProperty(String key) {
        String value = null;
        if (properties.containsKey(key))
            value = (String) properties.get(key);
        else {
            throw new RuntimeException("not this key!");
    }
        return value;
    }

}