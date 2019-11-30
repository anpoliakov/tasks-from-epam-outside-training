package by.epam.task4.model.managers;

import by.epam.task4.model.db.ConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private static ConfigurationManager configManager;

    private String PATH_CONFIG_DB = "src/by/epam/task4/configuration/configdb.properties";
    private String PATH_REQUESTS_SQL = "src/by/epam/task4/configuration/requestsSQL.properties";
    private Properties properties;

    private ConfigurationManager() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH_CONFIG_DB));
            properties.load(new FileInputStream(PATH_REQUESTS_SQL));
            initConnectionPool();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigurationManager getConfigManager(){
        if (configManager == null) {
            synchronized (ConfigurationManager.class) {
                if (configManager == null){
                    configManager = new ConfigurationManager();
                }
            }
        }
        return configManager;
    }

    //инициализация пула коннектов
    private void initConnectionPool(){
        ConnectionPool pool = ConnectionPool.getConnectionPool();

        //файлы могут быть = null - нет таких строк ??
        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");
        String driver = properties.getProperty("db.driver");
        String url = properties.getProperty("db.url");
        Integer sizePool = Integer.parseInt(properties.getProperty("sizePool"));

        pool.setConfigPool(login,password,driver,url,sizePool);
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