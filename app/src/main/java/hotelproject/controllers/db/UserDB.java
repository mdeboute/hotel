package hotelproject.controllers.db;

import hotelproject.controllers.objects.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class UserDB {

    /** Checks if a user exists in the `users` table in the database. */
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

    /** Checks if a user is an administrator in the `users` table in the database. */
    public static int getU_is_admin(Connection conn, User user) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM `users` WHERE u_name = '%s' AND u_password = '%s'";
        ResultSet rs = stmt.executeQuery(String.format(sql, user.getU_name(), user.getU_password()));
        rs.next();
        return parseInt(rs.getString("u_is_admin"));
    }

    /** Updates a row in the `user` table in the database. */
    public static void updateUserInformation(Connection conn, User user, String new_username, String new_password) throws SQLException {
        Statement stmt = conn.createStatement();
        String previousUserName = user.getU_name();
        String sql = "UPDATE users SET u_name = '%s', u_password = '%s' WHERE u_name = '"+previousUserName+"'";
        stmt.executeUpdate(String.format(sql, new_username, new_password));
    }

    /** Find all current users in the database */
    public static List<User> findAllUsers(Connection conn){
        List<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                User user = new User(rs.getString(1),rs.getString(2), rs.getInt(3));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
