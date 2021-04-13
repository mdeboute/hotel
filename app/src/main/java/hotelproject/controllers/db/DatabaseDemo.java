package hotelproject.controllers.db;

import java.sql.*;

public class DatabaseDemo {
  public static void main(String[] args) throws SQLException {
      // access database
      System.out.println("Accessing database...");
      Connection conn = DatabaseManagement.createConnection();

      // executing query
      System.out.println("Initializing Statement object...");
      assert conn != null;
      Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT u_name,u_password FROM users";
      ResultSet rs = stmt.executeQuery(sql);

      // show result
      while(rs.next()){
          // get result by keyword
          String name = rs.getString("u_name");
          String password = rs.getString("u_password");
          // output
          System.out.print("u_name: " + name);
          System.out.print(", u_password: " + password);
          System.out.print("\n");
      }
      // close connection
      rs.close();
      stmt.close();
      conn.close();
      System.out.println("Goodbye!");
  }
}