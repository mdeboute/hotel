package hotelproject.controllers.db;

import hotelproject.controllers.objects.User;
import org.junit.After;
import org.junit.Assert;
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

    /* Oscar, there are a lot of instances here where the conn variable is used to close
    a connection or create a new statement. You cannot do that anymore as I removed the conn
    variable, as connections are now handled from the DatabaseManager object, dbm.
    I have left TODOs down below to assist you. Please ask if you need some help.
    */

    private DatabaseManager dbm;
    private User userIsAdmin;
    private User userIsStaff;
    private String u_name_admin;
    private String u_name_staff;

    @Before
    public void setUp() {
        dbm = new DatabaseManager();
        u_name_admin = "IsAdmin";
        u_name_staff = "IsStaff";
        userIsAdmin = new User(u_name_admin,"admin123",1);
        userIsStaff = new User(u_name_staff,"staff123",0);
    }

    @After
    //TODO: Adapt this to the new DatabaseManager class (You don't need to close the connection.)
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
    //TODO: Adapt this to the new DatabaseManager class (You can ask me for help :) )
    public void addUserAdminTest(){
        boolean isInsert = false;
        dbm.udb.addUser(userIsAdmin);
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
    //TODO: Adapt this to the new DatabaseManager class (You can ask me for help :) )
    public void addUserTest2(){
        boolean isInsert = false;
        dbm.udb.addUser(userIsStaff);
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

    @Test
    public void testGetU_is_admin(){
        try{
            int isAdmin = dbm.udb.getU_is_admin(userIsAdmin);
            int isStaff = dbm.udb.getU_is_admin(userIsStaff);
            Assert.assertEquals(isAdmin,1);
            Assert.assertEquals(isStaff,0);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    //TODO: Adapt this to the new DatabaseManager class (You can ask me for help :) )
    public void testUpdateUserInformation(){
        try{
            dbm.udb.updateUserInformation(userIsAdmin,"userIsBoss","boss123");
            String sql = "SELECT * FROM users WHERE u_name = 'userIsBoss' AND u_password = 'boss123' ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Assert.assertEquals(rs.getString("u_name"),"userIsBoss");
                Assert.assertEquals(rs.getString("u_password"), "boss123");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    //TODO: Adapt this to the new DatabaseManager class (You can ask me for help :) )
    public void testUpdateUserInformation2(){
        try{
            dbm.udb.updateUserInformation(userIsStaff,"userIsWorker");
            String sql = "SELECT * FROM users WHERE u_name = 'userIsWorker'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Assert.assertEquals(rs.getString("u_name"),"userIsWorker");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
