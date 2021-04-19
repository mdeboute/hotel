package hotelproject.controllers.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookingsDB {
    private final Connection conn;

    public BookingsDB (Connection conn) { this.conn = conn; }

    public ArrayList<Integer> getBookingsForSpecificDay(String date) {
        ArrayList<Integer> bookings = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT `b_id` FROM `booking` WHERE b_from <= %s AND b_till >= %s";
            ResultSet rs = stmt.executeQuery(String.format(sql, date, date));
            while (rs.next()) {
                bookings.add(rs.getInt("b_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
