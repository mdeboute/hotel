package hotelproject.readers;

import hotelproject.controllers.db.DatabaseManagement;
import hotelproject.controllers.objects.User;

import java.sql.*;
import java.util.ArrayList;

public class DBUsersReader {

  /** Reads rows from the `users` table in the database and creates User objects with that information. */
  public static ArrayList<User> DBAddUser(Connection conn) {

    Statement stmt;
    ResultSet rs;

    ArrayList<User> users = new ArrayList<>();

    try{
      String query = "SELECT * FROM users";
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        User user = new User(rs.getString(1), rs.getString(2), rs.getInt(3));
        users.add(user);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return users;
  }

  public static void main(String[] args) {

    Connection conn = DatabaseManagement.createConnection();

    ArrayList<User> users = DBAddUser(conn);
    for (User u : users) {
      System.out.println(u);
    }
  }

}
