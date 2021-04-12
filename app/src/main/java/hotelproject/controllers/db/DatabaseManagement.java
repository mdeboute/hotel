package hotelproject.controllers.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseManagement {

    public static Connection createConnection(String url, String user, String password, String database) throws SQLException {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            connectionProps.put("database", database);
            Connection conn = DriverManager.getConnection(url, connectionProps);
            return conn;
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

    public static void addRoomType(Connection conn, String t_name, int beds, int r_size, int has_view, int has_kitchen,
                                   int has_bathroom, int has_workspace, int has_tv, int has_coffee_maker){
        try{
            String sql = "INSERT INTO TABLE `room_type` (`t_name`, `beds`, `r_size`, `has_view`, `has_kitchen`, " +
                    "`has_bathroom`, `has_workspace`, `has_tv`, `has_coffee_maker`) VALUES " +
                    "('%s', %d, %d, %d, %d, %d, %d, %d, %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, t_name, beds, r_size, has_view, has_kitchen, has_bathroom,
                    has_workspace, has_tv, has_coffee_maker));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void addRoom(Connection conn, int r_num, int r_floor, String r_type, int booked) {
        try{
            String sql = "INSERT INTO TABLE `room` (`r_num`, `r_floor`, `r_type`, `booked`) VALUES (%d, %d, %s, %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, r_num, r_floor, r_type, booked));
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
