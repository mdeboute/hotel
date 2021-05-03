package hotelproject.controllers.db;

import hotelproject.controllers.objects.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class BookingsDB {
    private final Connection conn;

    public BookingsDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return list filled with all Room objects collected from the database.
     * @brief Search all current available rooms and return as ArrayList.
     */
    public ArrayList<Booking> findAllBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `booking`";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getDate(5), rs.getInt(6), rs.getInt(7));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * @param booking Booking object that will be added to the database
     * @brief Inserts new row into the `booking` table in the database.
     */
    public void addBooking(Booking booking) {
        try {
            String sql = "INSERT INTO `booking` VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, booking.getB_id());
            statement.setInt(2, booking.getR_num());
            statement.setInt(3, booking.getPaid_by_card());
            statement.setDate(4, booking.getB_from());
            statement.setDate(5, booking.getB_till());
            statement.setInt(6, booking.getB_fee());
            statement.setInt(7, booking.getB_is_paid());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param date Date string from front-end will be used for checking in database.
     * @return All booking ids that meet the conditions will be saved in ArrayList.
     * @brief Search the related booking ids by specific date.
     */
    public ArrayList<Integer> getBookingsForSpecificDay(String date) {
        ArrayList<Integer> bookings = new ArrayList<>();
        try {
            String sql = "SELECT `b_id` FROM `booking` WHERE b_from <= ? AND b_till >= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setString(2, date);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(rs.getInt("b_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }


    /**
     * @param b_id Booking id is used for checking detail information in the database.
     * @return Hashtable's keys are table's attributes and value is the related information.
     * @brief Retrieve booking information from database and save it in a hashtable.
     */
    public Hashtable<String, String> getBookingDetails(int b_id) {
        Hashtable<String, String> bookingDetails = new Hashtable<>();
        String[] bookingHeaders = {"b_id", "r_num", "paid_by_card", "b_from", "b_till", "b_fee", "b_is_paid"};
        try {
            String sql = "SELECT * FROM booking WHERE b_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, b_id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            for (String header : bookingHeaders) {
                bookingDetails.put(header, rs.getString(header));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingDetails;
    }
}
