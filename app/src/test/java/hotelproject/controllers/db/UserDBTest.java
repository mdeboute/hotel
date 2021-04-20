package hotelproject.controllers.db;

import hotelproject.controllers.objects.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING )
public class UserDBTest {

    private DatabaseManager dbm;
    private User userIsAdmin;
    private User userIsStaff;
    private String u_name_admin;
    private String u_name_staff;

    /**
     * @brief This method is used to prepare settings for all tests.
     */
    @Before
    public void setUp() {
        dbm = new DatabaseManager();
        u_name_admin = "IsAdmin";
        u_name_staff = "IsStaff";
        userIsAdmin = new User(u_name_admin,"admin123",1);
        userIsStaff = new User(u_name_staff,"staff123",0);
    }

    /**
     * @brief Test usrExist() method.
     * @result This user already exists in the database and the result should be true.
     */
    @Test
    public void test_001_IsUserExist(){
        try {
            assertTrue(dbm.udb.userExists(new User("admin", "root", 1)));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * @brief Test addUser() method.
     * @result These two user objects should be added in the users table of database. The result should be true.
     */
    @Test
    public void test_002_addUser(){
        dbm.udb.addUser(userIsAdmin);
        dbm.udb.addUser(userIsStaff);
        try{
            assertTrue(dbm.udb.userExists(userIsAdmin));
            assertTrue(dbm.udb.userExists(userIsStaff));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * @brief Test getU_is_admin() method.
     * @result The user 'userIsAdmin' should return '1'.
     * @result The user 'userIsStaff' should return '0'.
     */
    @Test
    public void test_003_GetU_is_admin(){
        try{
            int isAdmin = dbm.udb.getU_is_admin(userIsAdmin);
            int isStaff = dbm.udb.getU_is_admin(userIsStaff);
            Assert.assertEquals(isAdmin,1);
            Assert.assertEquals(isStaff,0);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * @brief Test getAllUsers() method.
     * @result All four users should be obtained from the database. The result should return true.
     */
    @Test
    public void test_004_GetAllUsers(){
        List<User> allUsersForTest = new ArrayList<>();
        allUsersForTest.add(new User("admin","root",1));
        allUsersForTest.add(new User("reception","staff",0));
        allUsersForTest.add(new User("IsAdmin", "admin123",1));
        allUsersForTest.add(new User("IsStaff","staff123",0));
        List<User> allUserInDatabase;
        int count = 0;
        allUserInDatabase = dbm.udb.getAllUsers();
        for (User userInDatabase: allUserInDatabase) {
            for (User userForTest: allUsersForTest) {
                if(userInDatabase.getU_name().equals(userForTest.getU_name()) &&
                        userInDatabase.getU_password().equals(userForTest.getU_password()) &&
                        userInDatabase.getU_is_admin() == userForTest.getU_is_admin()){
                    count++;
                }
            }
        }
        Assert.assertEquals(count,4);
    }

    /**
     * @brief Test updateUserInformation() method.
     * @result The user object's attributes should be updated accordingly and should return true.
     */
    @Test
    public void test_005_UpdateUserInformation(){
        boolean isUpdate = false;
        try{
            String old_username = userIsAdmin.getU_name();
            userIsAdmin.setU_name("userIsBoss");
            userIsAdmin.setU_password("boss123");
            dbm.udb.updateUserInformation(userIsAdmin,old_username);
            List<User> users = dbm.udb.getAllUsers();
            for (User user : users) {
                if(user.getU_name().equals("userIsBoss") && user.getU_password().equals("boss123")){
                    isUpdate = true;
                } else {
                    isUpdate = false;
                }
            }
            assertTrue(isUpdate);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * @brief Test updateUserInformation() method.
     * @result The user object's attributes should be updated accordingly and should return true.
     */
    @Test
    public void test_006_UpdateUserInformation2(){
        boolean isUpdate = false;
        String old_username = userIsStaff.getU_name();
        userIsStaff.setU_name("userIsWorker");
        try{
            dbm.udb.updateUserInformation(userIsStaff,old_username);
            List<User> users = dbm.udb.getAllUsers();
            for (User user : users) {
                if(user.getU_name().equals("userIsWorker") && user.getU_password().equals("staff123")){
                    isUpdate = true;
                } else {
                    isUpdate = false;
                }
            }
            assertTrue(isUpdate);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * @brief Test deleteUser() method.
     * @result The user 'userIsWorker' should be found and deleted in the database. The result should return true.
     */
    @Test
    public void test_007_DeleteUser(){
        boolean isDeleted = false;
        User userForDelete = new User ("userIsWorker", "staff123",0);
        dbm.udb.deleteUser(userForDelete);
        List<User> allUsersInDatabase = dbm.udb.getAllUsers();
        for (User user : allUsersInDatabase) {
            if(user.getU_name().equals("userIsWorker") &&
                    user.getU_password().equals("staff123") &&
                    user.getU_is_admin() == 0){
                isDeleted = false;
            } else {
                isDeleted = true;
            }
        }
        assertTrue(isDeleted);
    }

    /**
     * @brief Test deleteUser() method.
     * @result The user 'userIsBoss' should be found and deleted in the database. The result should return true.
     */
    @Test
    public void test_008_DeleteUser2() {
        boolean isDeleted = false;
        dbm.udb.deleteUser(new User("userIsBoss","boss123",1));
        List<User> allUsersInDatabase = dbm.udb.getAllUsers();
        for (User user : allUsersInDatabase) {
            if(user.getU_name().equals("userIsBoss") &&
                    user.getU_password().equals("boss123") &&
                    user.getU_is_admin() == 0){
                isDeleted = false;
            } else {
                isDeleted = true;
            }
        }
        assertTrue(isDeleted);
    }

}
