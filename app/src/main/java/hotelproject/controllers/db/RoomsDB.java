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

    /** Inserts new row into the `room_type` table in the database. */
    public static void addRoomType(Connection conn, RoomType room){
        try{
            String sql = "INSERT INTO `room_type` (`t_name`, `beds`, `r_size`, `has_view`, `has_kitchen`, " +
                    "`has_bathroom`, `has_workspace`, `has_tv`, `has_coffee_maker`) VALUES " +
                    "('%s', %d, %d, %d, %d, %d, %d, %d, %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, room.getT_name(), room.getBeds(), room.getR_size(), room.getHas_view(), room.getHas_kitchen(), room.getHas_bathroom(),
                    room.getHas_workspace(), room.getHas_tv(), room.getHas_coffee_maker()));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /** Inserts new row into the `room` table in the database. */
    public static void addRoom(Connection conn, Room room) {
        try{
            String sql = "INSERT INTO `room` (`r_num`, `r_floor`, `r_type`, `booked`) VALUES (%d, %d, '%s', %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, room.getR_num(), room.getR_floor(), room.getR_type(), room.getBooked()));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /** Reads the `room` table in the database. */
    public static List<Room> readRooms(Connection conn){
        List<Room> rooms = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM room";
            ResultSet rs = stmt.executeQuery(sql);
            int r_num;
            int r_floor;
            String r_type;
            int booked;
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

    /** Deletes the room_type according to the room_type name */
    public static void deleteRoomType(Connection conn, String t_name) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM room_type WHERE t_name = '" + t_name + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Deletes the room according to the room number */
    public static void deleteRoom(Connection conn, int r_num) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM room WHERE r_num = '" + r_num + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
