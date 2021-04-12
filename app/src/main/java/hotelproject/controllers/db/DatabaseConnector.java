package hotelproject.controllers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnector {
    private static Connection conn = null;

    public DatabaseConnector() {
    }

    public static Connection getConnection() {
        return conn;
    }

    public static Connection createConnection(String url, String user, String password, String database) throws SQLException {
        if (conn != null) conn.close();
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            connectionProps.put("database", database);
            conn = DriverManager.getConnection(url, connectionProps);
        } catch(SQLException e) {
            System.err.println("Error : " + e);
            return null;
        }
        return conn;
    }
}
