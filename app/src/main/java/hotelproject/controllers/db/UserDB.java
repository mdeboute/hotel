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
    private final Connection conn;

    public UserDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * @brief Checks if a user exists in the `users` table in the database.
     * @param user User object whose existence will be checked in the database
     * @return boolean regarding the existence of the user
     * @throws SQLException
     */
    public boolean userExists(User user) throws SQLException {
        //TODO fill in the @throws part of the javadoc (idk what to write there)
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM `users`");
        while (rs.next()) {
            if (rs.getString("u_name").equals(user.getU_name()) && rs.getString("u_password").equals(user.getU_password())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @brief Checks if a user is an administrator in the `users` table in the database.
     * @param user User object whose admin status will be checked
     * @return int that returns 1 if user is admin and 0 if not
     * @throws SQLException
     */
    public int getU_is_admin(User user) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM `users` WHERE u_name = '%s' AND u_password = '%s'";
        ResultSet rs = stmt.executeQuery(String.format(sql, user.getU_name(), user.getU_password()));
        rs.next();
        return parseInt(rs.getString("u_is_admin"));
    }

    /**
     * @brief Updates a row in the `user` table in the database.
     * @param user User object whose information will be updated in the database
     * @param new_username the new username
     * @param new_password the new password
     * @throws SQLException
     */
    public void updateUserInformation(User user, String new_username, String new_password) throws SQLException {
        Statement stmt = conn.createStatement();
        String previousUserName = user.getU_name();
        String sql = "UPDATE `users` SET u_name = '%s', u_password = '%s' WHERE u_name = '" + previousUserName + "'";
        stmt.executeUpdate(String.format(sql, new_username, new_password));
    }

    /**
     * @brief Updates a row in the `user` table in the database.
     * @param user User object whose information will be updated in the database
     * @param new_username the new username
     * @throws SQLException
     */
    public void updateUserInformation(User user, String new_username) throws SQLException {
        Statement stmt = conn.createStatement();
        String previousUserName = user.getU_name();
        String sql = "UPDATE `users` SET u_name = '%s' WHERE u_name = '" + previousUserName + "'";
        stmt.executeUpdate(String.format(sql, new_username));
    }

    /**
     * @brief Find all current users in the database
     * @return a list of User objects from all users in the database
     */
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User(rs.getString(1), rs.getString(2), rs.getInt(3));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * @brief Delete a user according to the user name
     * @param u_name username of the user who will be deleted from the database
     */
    public void deleteUser(String u_name) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM users WHERE u_name = '" + u_name + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (u_name, u_password, u_is_admin) VALUES ('%s','%s', %d)";
            stmt.executeUpdate(String.format(sql,user.getU_name(),user.getU_password(),user.getU_is_admin()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //TODO idk whats going on here, this method gets all users doesn't add user, and it already exists.
    public ArrayList<User> addUser() {

        Statement stmt;
        ResultSet rs;

        ArrayList<User> users = new ArrayList<>();

        try {
            String query = "SELECT * FROM users";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User(rs.getString(1), rs.getString(2), rs.getInt(3));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
