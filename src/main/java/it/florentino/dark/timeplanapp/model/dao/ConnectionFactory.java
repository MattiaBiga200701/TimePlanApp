package it.florentino.dark.timeplanapp.model.dao;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionFactory {

    private static final String PROPERTIES_PATH = "it/florentino/dark/timeplanapp/persistenceProperties/timePlanDB.properties";
    private static Connection connection = null;

    public  ConnectionFactory(){}

    private static String readProperties(String propertiesType) {

        Properties properties = null;
        try( InputStream inputFile = new FileInputStream(PROPERTIES_PATH)) {  //ANCORA DA GESTIRE

            properties = new Properties();
            properties.load(inputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties.getProperty(propertiesType);

    }

    public static Connection getConnection(){
        if(ConnectionFactory.connection == null) {
            try {  //DA GESTIRE
                String connectionUrl = readProperties("CONNECTION_URL");
                String connectionUser = readProperties("CONNECTION_USER");
                String connectionPass = readProperties("CONNECTION_PASS");
                ConnectionFactory.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ConnectionFactory.connection;
    }

}
