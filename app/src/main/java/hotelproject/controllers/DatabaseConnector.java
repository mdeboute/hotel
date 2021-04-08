package hotelproject.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnector {
    private Connection conn = null;

    public DatabaseConnector() {
    }

    public static DatabaseConnector getInstance() {
        return new DatabaseConnector();
    }

    public Connection getConnection() {
        return conn;
    }

    public Connection createConnection(String url, String user, String password, String database) throws SQLException {
        if (conn != null) conn.close();
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            connectionProps.put("database", database);
            conn = DriverManager.getConnection(url + database, connectionProps);
        } catch(SQLException e) {
            System.out.println("Error : " + e);
            return null;
        }
        return conn;
    }
}
