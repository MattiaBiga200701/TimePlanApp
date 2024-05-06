package it.florentino.dark.timeplanapp.model.DAO;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionFactory {

    private static final String PROPERTIES_PATH = "it/florentino/dark/timeplanapp/persistenceProperties/timePlanDB.properties";
    private Connection connection;

    private static ConnectionFactory instance = null;

    private ConnectionFactory(){}

    public synchronized static ConnectionFactory getInstance(){
        if(instance == null){
            ConnectionFactory.instance = new ConnectionFactory();
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
                String connectionUrl = readProperties("CONNECTION_URL");
                String connectionUser = readProperties("CONNECTION_USER");
                String connectionPass = readProperties("CONNECTION_PASS");
                instance.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance.connection;
    }

}
