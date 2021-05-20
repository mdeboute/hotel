package hotelproject.controllers.db;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class RoomsDB {
    private final Connection conn;

    public RoomsDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param room RoomType object that will be added into the database
     * @brief Inserts new row into the `room_type` table in the database.
     */
    public void addRoomType(RoomType room) {
        try {
            String sql = "INSERT INTO `room_type` (`t_name`, `beds`, `r_size`, `has_view`, `has_kitchen`, "
                    + "`has_bathroom`, `has_workspace`, `has_tv`, `has_coffee_maker`) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, room.getT_name());
            statement.setInt(2, room.getBeds());
            statement.setInt(3, room.getR_size());
            statement.setInt(4, room.getHas_view());
            statement.setInt(5, room.getHas_kitchen());
            statement.setInt(6, room.getHas_bathroom());
            statement.setInt(7, room.getHas_workspace());
            statement.setInt(8, room.getHas_tv());
            statement.setInt(9, room.getHas_coffee_maker());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param room Room object that will be added to the database
     * @brief Inserts new row into the `room` table in the database.
     */
    public void addRoom(Room room) {
        try {
            String sql = "INSERT INTO `room` (`r_num`, `r_floor`, `r_type`) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, room.getR_num());
            statement.setInt(2, room.getR_floor());
            statement.setString(3, room.getR_type());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param roomType object of the room_type that will be deleted from the
     *                 database
     * @brief Deletes the room_type according to the room_type name
     */
    public void deleteRoomType(RoomType roomType) {
        try {
            String sql = "DELETE FROM `room_type` WHERE `t_name` = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, roomType.getT_name());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param room the number of the room that will be deleted from the database
     * @brief Deletes the room according to the room number
     */
    public void deleteRoom(Room room) {
        try {
            String sql = "DELETE FROM `room` WHERE `r_num` = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, room.getR_num());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param roomType    the updated RoomType object (must have the same name)
     * @param oldRoomType the old room type's name that is to be updated
     * @brief Update the room_type according to the room type name
     */
    public void updateRoomType(RoomType roomType, String oldRoomType) {
        try {
            String sql = "UPDATE `room_type` SET `t_name` = ?, `beds` = ?, `r_size` = ?, `has_view` = ?, "
                    + "`has_kitchen` = ?, `has_bathroom` = ?, `has_workspace` = ?, `has_tv` = ?, `has_coffee_maker`"
                    + " = ? WHERE `t_name` = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, roomType.getT_name());
            statement.setInt(2, roomType.getBeds());
            statement.setInt(3, roomType.getR_size());
            statement.setInt(4, roomType.getHas_view());
            statement.setInt(5, roomType.getHas_kitchen());
            statement.setInt(6, roomType.getHas_bathroom());
            statement.setInt(7, roomType.getHas_workspace());
            statement.setInt(8, roomType.getHas_tv());
            statement.setInt(9, roomType.getHas_coffee_maker());
            statement.setString(10, oldRoomType);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param room the updated Room object (must have the same room number)
     * @brief Update the room according to the room number
     */
    public void updateRoom(Room room, int oldRNum) {
        try {
            String sql = "UPDATE `room` SET `r_num` = ?, `r_floor` = ?, `r_type` = ? WHERE `r_num` = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, room.getR_num());
            statement.setInt(2, room.getR_floor());
            statement.setString(3, room.getR_type());
            statement.setInt(4, oldRNum);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param room the room object which is to be expected
     * @return hashtable filled with all of the room details retrieved
     * @brief View the room details according to its number
     */
    public Hashtable<String, String> viewRoomDetails(Room room) {
        Hashtable<String, String> roomDetails = new Hashtable<>();
        String[] roomData = {"t_name", "beds", "r_size", "has_view", "has_kitchen", "has_bathroom", "has_workspace",
                "has_tv", "has_coffee_maker"};
        try {
            String sql = "SELECT * FROM room_type WHERE t_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, room.getR_type());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                for (String elem : roomData) {
                    roomDetails.put(elem, rs.getString(elem));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomDetails;
    }

    /**
     * @return list filled with all RoomType objects collected from the database
     * @brief Search all current available room types and return as ArrayList
     */
    public ArrayList<RoomType> findAllRoomTypes() {
        ArrayList<RoomType> roomTypes = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM `room_type`";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                RoomType roomtype = new RoomType(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
                roomTypes.add(roomtype);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    /**
     * @return list filled with all Room objects collected from the database
     * @brief Search all current available rooms and return as ArrayList
     */
    public ArrayList<Room> findAllRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM `room`";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Room room = new Room(rs.getInt(1), rs.getInt(2), rs.getString(3));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     * @param roomType RoomType object that will be searched in the database
     * @return a boolean regarding the existence of the room_type in the database
     * @brief Checks if the input room_type exists.
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

    /**
     * @param room Room object that will be searched in the database
     * @return a boolean regarding the existence of the room in the database
     * @brief Checks if the input room exists.
     */
    public boolean roomExists(Room room) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `room`");
            while (rs.next()) {
                if (Integer.parseInt(rs.getString("r_num")) == room.getR_num())
                    // System.out.println(rs.getString("r_num"));
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return list filled with all booked room numbers collected from the database
     * @brief Search all current booked rooms and return as ArrayList
     */
    public ArrayList<Integer> findAllBookedRooms(Date checkIn, Date checkOut) {
        ArrayList<Integer> bookedRNums = new ArrayList<>();
        try {
            String sql = ("SELECT r.r_num FROM room as r JOIN booking as b ON r.r_num = b.r_num " +
            "WHERE (? BETWEEN b.b_from AND b.b_till) OR (? BETWEEN b_from AND b_till)");
            String firstDate = checkIn.toString();
            String secondDate = checkOut.toString();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstDate);
            stmt.setString(2, secondDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookedRNums.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedRNums;
    }

    /**
     * @return list filled with all room numbers collected from the database
     * @brief Search all current available rooms and return as ArrayList
     */
    public ArrayList<Integer> findAllRoomNums() {
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT r_num FROM `room`";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                roomNumbers.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomNumbers;
    }
}