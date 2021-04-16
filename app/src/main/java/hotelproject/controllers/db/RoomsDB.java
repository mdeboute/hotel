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
    private final Connection conn;

    public RoomsDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * @brief Inserts new row into the `room_type` table in the database.
     * @param room RoomType object that will be added into the database
     */
    public void addRoomType(RoomType room) {
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
     * @brief Inserts new row into the `room` table in the database.
     * @param room Room object that will be added to the database
     */
    public void addRoom(Room room) {
        try {
            String sql = "INSERT INTO `room` (`r_num`, `r_floor`, `r_type`, `booked`) VALUES (%d, %d, '%s', %d)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format(sql, room.getR_num(), room.getR_floor(), room.getR_type(), room.getBooked()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Reads the `room` table in the database.
     * @return a list of room objects from the database
     */
    public List<Room> readRooms() {
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
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return rooms;
    }

    /**
     * @brief Deletes the room_type according to the room_type name
     * @param t_name name of the room_type that will be deleted from the database
     */
    public void deleteRoomType(String t_name) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM room_type WHERE t_name = '" + t_name + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Deletes the room according to the room number
     * @param conn Connection object that is connected to a database
     * @param r_num the number of the room that will be deleted from the database
     */
    public void deleteRoom(int r_num) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM room WHERE r_num = '" + r_num + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Update the room_type according to the room type name
     * @param roomType the updated RoomType object (must have the same name)
     */
    public void updateRoomType(RoomType roomType) {
        //TODO find a way to change the room_type's name
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE room_type SET beds = %d, r_size = %d, has_view = %d, has_kitchen = %d, has_bathroom = %d, has_workspace = %d, has_tv = %d, has_coffee_maker = %d WHERE t_name = '%s' ";
            stmt.executeUpdate(String.format(sql,roomType.getBeds(),roomType.getR_size(),roomType.getHas_view(),roomType.getHas_kitchen(),roomType.getHas_bathroom(),roomType.getHas_workspace(),roomType.getHas_tv(),roomType.getHas_coffee_maker(),roomType.getT_name()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Update the room according to the room number
     * @param room the updated Room object (must have the same room number)
     */
    public void updateRoom(Room room ) {
        //TODO find a way to change the room number
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE room SET r_floor = %d, r_type = '%s', booked = %d WHERE r_num = %d";
            stmt.executeUpdate(String.format(sql, room.getR_floor(), room.getR_type(), room.getBooked(), 
            room.getR_num()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Search all current available room types and return as ArrayList
     * @return list filled with all RoomType objects collected from the database
     */
    public List<RoomType> findAllRoomTypes() {
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
     * @brief Search all current available rooms and return as ArrayList
     * @return list filled with all Room objects collected from the database
     */
    public List<Room> findAllRooms() {
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

    /**
     * @brief Checks if the input room_type exists.
     * @param roomType RoomType object that will be searched in the database
     * @return a boolean regarding the existence of the room_type in the database
     * @throws SQLException
     */
    public boolean roomTypeExists(String roomType) throws SQLException {
        //TODO fill in the @throws part of the javadoc (idk what to write there)
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM room_type");
        while (rs.next()) {
            if (rs.getString("t_name").equals(roomType))
                return true;
        }
        return false;
    }
}