package by.epam.task4.configuration;

import by.epam.task4.ConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


//цель класса - с читать данные для Connection из properties файла и считать SQL запросы для дальнейшей работы
public class ConfigurationManager {

    private static ConfigurationManager configurationManager;
    private String FILE_PROPERTIES = "src/by/epam/task4/configuration/configdb.properties";
    private Properties properties;

    private ConfigurationManager() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(FILE_PROPERTIES));

            String login = properties.getProperty("db.login");
            String password = properties.getProperty("db.password");
            String driver = properties.getProperty("db.driver.name");
            String url = properties.getProperty("db.connection.url");
            int initialNumberConnections = Integer.parseInt(properties.getProperty("initialNumberConnections"));

            connectionPool = new ConnectionPool(login,password,url,driver,initialNumberConnections);

        } catch (IOException e) {
            //НИЧЕГО НЕ ВЫВОДИМ ВСЁ В LOG файл!!!!!!!
            e.printStackTrace();
        }
    }



    public static ConfigurationManager getConfigurationManager(){
        if(ConfigurationManager.configurationManager == null){
            ConfigurationManager.configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    public Connection getConnection(){
        if(configurationManager == null){
            return connectionPool.retrieve(); // остановиолся тут
        }
    }


}
