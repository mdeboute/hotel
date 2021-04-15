package hotelproject.controllers.db;

import static org.junit.Assert.assertTrue;

import hotelproject.controllers.objects.RoomType;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RoomsDBTest {

  private Connection conn;
  private ArrayList<String> log;
  private String tableName;
  private RoomType roomType;
  private Statement stmt;
  private ResultSet rs;
  private String t_name;

  @Before
  public void setUp() {
    conn = DatabaseManagement.createConnection();
    tableName = "room_type";
    log = new ArrayList<>();
    t_name = generateRandomString();
    roomType = new RoomType(t_name, 1, 1, 1, 1, 1, 1, 1, 1);
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
    byte[] array = new byte[7]; // length is bounded by 7
    new Random().nextBytes(array);
    String generatedString = new String(array, Charset.forName("UTF-8"));
    return generatedString;
  }

  @Test
  public void testAddRoomType() {

    RoomsDB.addRoomType(conn, roomType);

    boolean successfulUpdate = false;

    if (DatabaseManagement.tableExists(conn, tableName, log)) {
      String sql = String.format("SELECT * FROM `room_type` WHERE t_name = '%s'", t_name);
      try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
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