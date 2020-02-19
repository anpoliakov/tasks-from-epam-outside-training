package system.model.managers;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 *
 *  This class is designed to receive data from a properties file (singleton based)
 *  This class is based on the singleton pattern.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class ConfigurationManager {
    final static Logger logger = Logger.getLogger(ConfigurationManager.class);
    private static ConfigurationManager configManager;

    //path to file with the database settings
    private final String PATH_CONFIG_DB = "configdb.properties";
    //path to file with sql requests
    private final String PATH_REQUESTS_SQL = "requestsSQL.properties";
    private Properties properties;
    //will store database settings for transfer in ConnectionPool
    private String [] configBD;

    private ConfigurationManager() {
        properties = new Properties();
        try {
            //load 2 files
            InputStream inFirstFromResources = ConfigurationManager.class.getClassLoader().getResourceAsStream(PATH_CONFIG_DB);
            InputStream inSecondFromResources = ConfigurationManager.class.getClassLoader().getResourceAsStream(PATH_REQUESTS_SQL);
            properties.load(inFirstFromResources);
            properties.load(inSecondFromResources);
        } catch (IOException e) {
            logger.error("error in load properties file", e);
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

    //get database settings from file
    public String[] getConfigBD(){
        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");
        String driver = properties.getProperty("db.driver");
        String url = properties.getProperty("db.url");
        String sizePool = properties.getProperty("sizePool");

        return configBD = new String[] {login,password,driver,url,sizePool};
    }

    //Receive requests from a file
    public String getSQLRequest(String key) {
        String value = null;
        if (properties.containsKey(key))
            value = (String) properties.get(key);
        else {
            logger.error("not this key! look method getSQLRequest class ConfigurationManager");
        }
        return value;
    }
}