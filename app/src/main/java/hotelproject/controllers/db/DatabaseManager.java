package hotelproject.controllers.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseManager {
    private Connection conn;
    public final RoomsDB rdb;
    public final UserDB udb;

    /**
     * @brief Creates a connection to the database with default login details.
     */
    public DatabaseManager() {
        try {
            String USER = "root";
            String PASSWORD = "root";
            String URL = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            Properties connectionProps = new Properties();
            connectionProps.put("user", USER);
            connectionProps.put("password", PASSWORD);
            conn = DriverManager.getConnection(URL, connectionProps);
        } catch (SQLException e) {
            System.err.println("Error : " + e);
            conn = null;
        }
        rdb = new RoomsDB(conn);
        udb = new UserDB(conn);
    }

    /**
     * @brief Creates a connection to the database with input login details.
     * @param url url for the database connection
     * @param user username for the database connection
     * @param password password for the database connection
     */
    public DatabaseManager(String url, String user, String password) {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            conn = DriverManager.getConnection(url, connectionProps);
        } catch (SQLException e) {
            System.err.println("Error : " + e);
            conn = null;
        }
        rdb = new RoomsDB(conn);
        udb = new UserDB(conn);
    }

    /**
     * @brief Checks if a table exists.
     * @param tableName name of the table whose existence will be verified
     * @param log an ArrayList of strings to store audit logs
     * @return boolean regarding the existence of the input table
     */
    public boolean tableExists(String tableName, ArrayList<String> log) {
        try {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (tables.next()) {
                log.add(tableName + " exists.");
                return true;
            }
            log.add(tableName + " doesn't exist.");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    /**
     * @brief Creates a table in the provided database connection.
     * @param tableName name of the table which will be created
     * @param log an ArrayList of strings to store audit logs
     * @param body column names for the new table separated by commas
     */
    public void createTable(String tableName, String body, ArrayList<String> log) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE " + tableName + "(" + body + ")");
            log.add(tableName + " is created.");
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @brief Drops a table in the database if it exists.
     * @param tableName name of the table which will be dropped
     * @param log an ArrayList of strings to store audit logs
     */
    public void dropTable(String tableName, ArrayList<String> log) {
        try {
            Statement stmt = conn.createStatement();
            if (stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName) != 0)
                log.add(tableName + " is deleted.");
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
