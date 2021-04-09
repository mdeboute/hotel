package hotelproject.controllers;

import java.sql.*;

public class DatabaseDemo {
 
  // MySQL 8.0 should use this
  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
  static final String DB_URL = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

  // remember to change here for use
  static final String USER = "admin";
  static final String PASSWORD = "root";

  public static void main(String[] args) {
      Connection conn = null;
      Statement stmt = null;
      try{
          // register driver
          Class.forName(JDBC_DRIVER);
      
          // access database
          System.out.println("Accessing database...");
          conn = DatabaseConnector.createConnection(DB_URL, USER, PASSWORD, "hotel");

          // executing query
          System.out.println("Initializing Statement object...");
          assert conn != null;
          stmt = conn.createStatement();
          String sql;
          sql = "SELECT u_name,u_password FROM users";
          ResultSet rs = stmt.executeQuery(sql);
      
          // show result
          while(rs.next()){
              // get result by keyword
              String name = rs.getString("u_name");
              String password = rs.getString("u_password");
              // output
              System.out.print("user_name: " + name);
              System.out.print(", u_password: " + password);
              System.out.print("\n");
          }
          // close connection
          rs.close();
          stmt.close();
          conn.close();
      } catch(Exception e){
          // handle JDBC exception
          e.printStackTrace();
      } finally{
          // shut down resources
          try{
              if(stmt!=null) stmt.close();
              if(conn!=null) conn.close();
          }catch(SQLException e) {
              e.printStackTrace();
          }
      }
      System.out.println("Goodbye!");
  }
}