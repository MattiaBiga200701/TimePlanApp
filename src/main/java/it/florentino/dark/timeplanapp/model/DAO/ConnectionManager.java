package it.florentino.dark.timeplanapp.model.DAO;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

    private static final String PROPERTIES_PATH = "it/florentino/dark/timeplanapp/persistenceProperties/timePlanDB.properties";
    private Connection connection;

    private static ConnectionManager instance = null;

    private ConnectionManager(){}

    public static ConnectionManager getInstance(){
        if(instance == null){
            ConnectionManager.instance = new ConnectionManager();
        }
        return instance;
    }

    private static String readProperties(String propertiesType) {

        Properties properties = null;
        try {  //ANCORA DA GESTIRE

            InputStream inputFile = new FileInputStream(PROPERTIES_PATH);
            properties = new Properties();
            properties.load(inputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertiesType);

    }

    public Connection getConnection(){
        if(instance.connection == null) {
            try {  //DA GESTIRE
                String connection_url = readProperties("CONNECTION_URL");
                String connection_user = readProperties("CONNECTION_USER");
                String connection_pass = readProperties("CONNECTION_PASS");
                instance.connection = DriverManager.getConnection(connection_url, connection_user, connection_pass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance.connection;
    }

}
