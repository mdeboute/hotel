package hotelproject.controllers.db;

import static org.junit.Assert.assertTrue;

import hotelproject.controllers.objects.RoomType;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RoomsDBTest {

  /* Oscar, there are a lot of instances here where the conn variable is used to close
  a connection or create a new statement. You cannot do that anymore as I removed the conn
  variable, as connections are now handled from the DatabaseManager object, dbm.
  I have left TODOs down below to assist you. Please ask if you need some help.
  */

  private DatabaseManager dbm;
  private ArrayList<String> log;
  private String tableName;
  private RoomType roomType;
  private String t_name;

  @Before
  public void setUp() {
    dbm = new DatabaseManager();
    tableName = "room_type";
    log = new ArrayList<>();
    t_name = generateRandomString();
    roomType = new RoomType(t_name, 1, 1, 1, 1, 1, 1, 1, 1);
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
    byte[] array = new byte[7]; // length is bounded by 7
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  @Test
  public void testAddRoomType() {

    dbm.rdb.addRoomType(roomType);

    boolean successfulUpdate = false;

    if (dbm.tableExists(tableName, log)) {
      String sql = String.format("SELECT * FROM `room_type` WHERE t_name = '%s'", t_name);
      try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          if (t_name.equals(rs.getString("t_name"))) {
            successfulUpdate = true;
            break;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    assertTrue(successfulUpdate);
  }



  @Test
  public void testAddRoom() {
  }

  @Test
  public void testReadRooms() {
  }
}