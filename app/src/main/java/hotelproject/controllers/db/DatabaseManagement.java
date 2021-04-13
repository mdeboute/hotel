package hotelproject.controllers.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseManagement {

    public static Connection createConnection() {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", "root");
            connectionProps.put("password", "root");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", connectionProps);
        } catch (SQLException e) {
            System.err.println("Error : " + e);
            return null;
        }
    }

    public static Connection createConnection(String url, String user, String password) {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            return DriverManager.getConnection(url, connectionProps);
        } catch (SQLException e) {
            System.err.println("Error : " + e);
            return null;
        }
    }

    public static boolean tableExists(Connection conn, String tableName, ArrayList<String> log){
        try {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (tables.next()){
                log.add(tableName + " exists.");
                return true;
            }
            log.add(tableName + " doesn't exist.");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManagement.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public static void createTable(Connection conn, String tableName, String body, ArrayList<String> log){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE " + tableName + "(" + body + ")");
            log.add(tableName + " is created.");
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManagement.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void dropTable(Connection conn, String tableName, ArrayList<String> log){
        try {
            Statement stmt = conn.createStatement();
            if (stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName)!=0)
                log.add(tableName + " is deleted.");
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManagement.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
