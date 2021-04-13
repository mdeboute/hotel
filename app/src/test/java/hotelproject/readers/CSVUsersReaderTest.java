package hotelproject.readers;

import static org.junit.Assert.*;

import hotelproject.controllers.User;
import java.util.ArrayList;
import org.junit.Test;

public class CSVUsersReaderTest {

  @Test
  public void testReadUsersFromCSV() {
    String filepath = "app/src/main/java/instances/users.csv";
    ArrayList<User> users = CSVUsersReader.readUsersFromCSV(filepath);
    assertTrue("Should equal true",users instanceof ArrayList);
  }

  @Test
  public void testCreateUser() {
    String u_name = "Robert";
    String u_password = "1234";
    String is_admin = "0";
    String[] metadata = {u_name, u_password, is_admin};
    assertTrue("Should equal true",CSVUsersReader.createUser(metadata) instanceof User);
    assertTrue("", CSVUsersReader.createUser(metadata).getU_name().equals(u_name));
  }
}