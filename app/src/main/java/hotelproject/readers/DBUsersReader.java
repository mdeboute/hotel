package hotelproject.readers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import hotelproject.controllers.User;

public class DBUsersReader {
  
  public static ArrayList<User> DBAddUser(Connection conn) throws IOException {

    Statement stmt = null;
    ResultSet rs = null;
    
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

  public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
    
    String myDriver = "com.mysql.cj.jdbc.Driver";
    String myUrl = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    Class.forName(myDriver);
    Connection conn = DriverManager.getConnection(myUrl, "root", "root");

    ArrayList<User> users = DBAddUser(conn);
    for (User u : users) {
      System.out.println(u);
    }
  }

}
