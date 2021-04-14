package hotelproject.controllers.db;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.RoomType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomsDB {

    /**
     * Inserts new row into the `room_type` table in the database.
     */
    public static void addRoomType(Connection conn, RoomType room) {
        try {
            String sql = "INSERT INTO `room_type` (`t_name`, `beds`, `r_size`, `has_view`, `has_kitchen`, " +
                    "`has_bathroom`, `has_workspace`, `has_tv`, `has_coffee_maker`) VALUES " +
                    "('%s', %d, %d, %d, %d, %d, %d, %d, %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, room.getT_name(), room.getBeds(), room.getR_size(), room.getHas_view(), room.getHas_kitchen(), room.getHas_bathroom(),
                    room.getHas_workspace(), room.getHas_tv(), room.getHas_coffee_maker()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts new row into the `room` table in the database.
     */
    public static void addRoom(Connection conn, Room room) {
        try {
            String sql = "INSERT INTO `room` (`r_num`, `r_floor`, `r_type`, `booked`) VALUES (%d, %d, '%s', %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, room.getR_num(), room.getR_floor(), room.getR_type(), room.getBooked()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the `room` table in the database.
     */
    public static List<Room> readRooms(Connection conn) {
        List<Room> rooms = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM room";
            ResultSet rs = stmt.executeQuery(sql);
            int r_num;
            int r_floor;
            String r_type;
            int booked;
            while (rs.next()) {
                r_num = Integer.parseInt(rs.getString("r_num"));
                r_floor = Integer.parseInt(rs.getString("r_floor"));
                r_type = rs.getString("r_type");
                booked = Integer.parseInt(rs.getString("booked"));
                rooms.add(new Room(r_num, r_floor, r_type, booked));
            }

        } catch (SQLException e) {
            Logger.getLogger(DatabaseManagement.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return rooms;
    }

    /**
     * Deletes the room_type according to the room_type name
     */
    public static void deleteRoomType(Connection conn, String t_name) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM room_type WHERE t_name = '" + t_name + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the room according to the room number
     */
    public static void deleteRoom(Connection conn, int r_num) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM room WHERE r_num = '" + r_num + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the room_type according to the room type name
     */
    public static void updateRoomType(Connection conn, String t_name, int beds, int r_size, int has_view, int has_kitchen, int has_bathroom, int has_workspace, int has_tv, int has_coffee_maker) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE room_type SET beds = '" + beds + "', r_size = '" + r_size + "', has_view = '" + has_view + "', has_kitchen = '" + has_kitchen + "', has_bathroom = '" + has_bathroom + "', has_workspace = '" + has_workspace + "', has_tv = '" + has_tv + "', has_coffee_maker = '" + has_coffee_maker + "' WHERE t_name = '" + t_name + "' ";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the room according to the room number
     */
    public static void updateRoom(Connection conn, int r_num, int r_floor, String r_type, int booked) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE room SET r_floor = '" + r_floor + "', r_type = '" + r_type + "', booked = '" + booked + "' WHERE r_num = '" + r_num + "' ";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search all current available room types and return as ArrayList
     */
    public static List<RoomType> findAllRoomType(Connection conn) {
        List<RoomType> roomTypes = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM room_type";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                RoomType roomtype = new RoomType(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
                roomTypes.add(roomtype);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    /**
     * Search all current available rooms and return as ArrayList
     */
    public static List<Room> findAllRoom(Connection conn) {
        List<Room> rooms = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM room";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Room room = new Room(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static boolean roomTypeExists(Connection conn, String roomType) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM room_type");
        while (rs.next()) {
            if (rs.getString("t_name").equals(roomType))
                return true;
        }
        return false;
    }
}
