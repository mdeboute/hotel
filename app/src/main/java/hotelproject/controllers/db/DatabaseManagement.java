package hotelproject.controllers.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseManagement {
    public static boolean isTableExist(Connection conn, String tableName, ArrayList<String> log){
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
