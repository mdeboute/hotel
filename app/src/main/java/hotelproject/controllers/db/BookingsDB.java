package hotelproject.controllers.db;

import hotelproject.controllers.objects.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class BookingsDB {
    private final Connection conn;

    public BookingsDB (Connection conn) { this.conn = conn; }

    /**
     * @brief Search all current available rooms and return as ArrayList
     * @return list filled with all Room objects collected from the database
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

    public Hashtable<String,String> getBookingDetails(int b_id) {
        Hashtable<String, String> bookingDetails = new Hashtable<>();
        String[] bookingHeaders = { "b_id", "r_num", "paid_by_card", "b_from", "b_till", "b_fee", "b_is_paid" };
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
