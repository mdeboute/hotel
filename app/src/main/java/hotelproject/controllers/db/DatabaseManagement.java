package hotelproject.controllers.db;

import hotelproject.controllers.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseManagement {

    public static Connection createConnection(String url, String user, String password, String database) {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            connectionProps.put("database", database);
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

    public static List<Room> readRooms(Connection conn, ArrayList<String> log){
        List<Room> rooms = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM room";
            ResultSet rs = stmt.executeQuery(sql);
            int r_num = -1;
            int r_floor = -1;
            String r_type = null;
            int booked = -1;
            while(rs.next()){
                r_num = Integer.parseInt(rs.getString("r_num"));
                r_floor = Integer.parseInt(rs.getString("r_floor"));
                r_type = rs.getString("r_type");
                booked = Integer.parseInt(rs.getString("booked"));
                rooms.add(new Room(r_num,r_floor,r_type,booked));
            }

        } catch (SQLException e) {
            Logger.getLogger(DatabaseManagement.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return rooms;
    }




}
