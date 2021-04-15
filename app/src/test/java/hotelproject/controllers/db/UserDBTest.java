package hotelproject.controllers.db;

import hotelproject.controllers.objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import static org.junit.Assert.assertTrue;

public class UserDBTest {
    private Connection conn;
    private User userIsAdmin;
    private User userIsStaff;
    private String u_name_admin;
    private String u_name_staff;


    @Before
    public void setUp() {
        conn = DatabaseManagement.createConnection();
        u_name_admin = "IsAdmin";
        u_name_staff = "IsStaff";
        String u_password = generateRandomString();
        userIsAdmin = new User(u_name_admin,u_password,1);
        userIsStaff = new User(u_name_staff,u_password,0);
    }

    @After
    public void tearDown() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateRandomString() {
        byte[] array = new byte[5]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @Test
    public void addUserAdminTest(){
        boolean isInsert = false;
        UserDB.addUser(conn,userIsAdmin);
        String sql = "SELECT * From `users` WHERE u_name = '"+u_name_admin+"' ";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("u_name").equals("IsAdmin")){
                    isInsert = true;
                    break;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(isInsert);
    }

    @Test
    public void addUserTest2(){
        boolean isInsert = false;
        UserDB.addUser(conn,userIsStaff);
        String sql = "SELECT * From `users` WHERE u_name = '"+u_name_staff+"' ";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("u_name").equals("IsStaff")){
                    isInsert = true;
                    break;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(isInsert);
    }


}
