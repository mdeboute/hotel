package hotelproject.controllers.db;

import hotelproject.controllers.objects.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Integer.parseInt;

public class UserDB {
    public static boolean userExists(Connection conn, User user) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            if (rs.getString("u_name").equals(user.getU_name()) && rs.getString("u_password").equals(user.getU_password())) {
                return true;
            }
        }
        return false;
    }

    public static int getU_is_admin(Connection conn, User user) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM `users` WHERE u_name = %s AND u_password = %s";

        ResultSet rs = stmt.executeQuery(String.format(sql, user.getU_name(), user.getU_password()));
        return parseInt(rs.getString("us_is_admin"));
    }
}
