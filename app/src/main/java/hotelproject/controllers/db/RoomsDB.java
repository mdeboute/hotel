package hotelproject.controllers.db;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.RoomType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            stmt.executeUpdate(String.format(sql, room.getT_name(), room.getBeds(), room.getR_size(), room.getHas_view(),
                    room.getHas_kitchen(), room.getHas_bathroom(), room.getHas_workspace(), room.getHas_tv(), room.getHas_coffee_maker()));
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
     * @brief Deletes the room_type according to the room_type name
     * @param roomType object of the room_type that will be deleted from the database
     */
    public void deleteRoomType(RoomType roomType) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM `room_type` WHERE `t_name` = '%s'";
            stmt.executeUpdate(String.format(sql, roomType.getT_name()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Deletes the room according to the room number
     * @param room the number of the room that will be deleted from the database
     */
    public void deleteRoom(Room room) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM `room` WHERE `r_num` = '%s'";
            stmt.executeUpdate(String.format(sql, room.getR_num()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Update the room_type according to the room type name
     * @param roomType the updated RoomType object (must have the same name)
     * @param oldRoomType the old room type's name that is to be updated
     */
    public void updateRoomType(RoomType roomType, String oldRoomType) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE `room_type` SET `t_name` ='%s', `beds` = %d, `r_size` = %d, `has_view` = %d, " +
                    "`has_kitchen` = %d, `has_bathroom` = %d, `has_workspace` = %d, `has_tv` = %d, `has_coffee_maker`" +
                    " = %d WHERE `t_name` = '%s' ";
            stmt.executeUpdate(String.format(sql,roomType.getT_name(),roomType.getBeds(),roomType.getR_size(),
                    roomType.getHas_view(),roomType.getHas_kitchen(),roomType.getHas_bathroom(),
                    roomType.getHas_workspace(),roomType.getHas_tv(),roomType.getHas_coffee_maker(),oldRoomType));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Update the room according to the room number
     * @param room the updated Room object (must have the same room number)
     * @param oldRNum the room number of the previous state of the room
     */
    public void updateRoom(Room room, int oldRNum) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE `room` SET `r_num` = %d, `r_floor` = %d, `r_type` = '%s', `booked` = %d WHERE `r_num` = %d";
            stmt.executeUpdate(String.format(sql,  room.getR_num(), room.getR_floor(), room.getR_type(),
                    room.getBooked(), oldRNum));
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
            String sql = "SELECT * FROM `room_type`";
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
            String sql = "SELECT * FROM `room`";
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
     */
    public boolean roomTypeExists(RoomType roomType) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `room_type`");
            while (rs.next()) {
                if (rs.getString("t_name").equals(roomType.getT_name()))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}