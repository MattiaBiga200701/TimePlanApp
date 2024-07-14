package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

    private static final String PROPERTIES_PATH = "src/main/resources/it/florentino/dark/timeplanapp/persistence/persistenceProperties/timePlanDB.properties";
    private static Connection connection = null;

    private ConnectionManager(){}

    private static String readProperties(String propertiesType) throws IOException {  //METODO DA ELIMINARE

        String value;
        Properties properties;
        try(InputStream inputFile = new FileInputStream(PROPERTIES_PATH)){  //ANCORA DA GESTIRE
            properties = new Properties();
            properties.load(inputFile);
            value = properties.getProperty(propertiesType);
        }
        return value;

    }

    public static Connection getConnection() throws ConnectionException {

        if(ConnectionManager.connection == null) {
            try {  //DA GESTIRE

                String connectionUrl = readProperties("CONNECTION_URL");
                String connectionUser = readProperties("CONNECTION_USER");
                String connectionPass = readProperties("CONNECTION_PASS");
                ConnectionManager.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);

            } catch (IOException | SQLException e) {

                throw new ConnectionException("ConnectionExcpetion:" + e.getMessage(), e.getCause());

            }

        }

        return ConnectionManager.connection;

    }

}
