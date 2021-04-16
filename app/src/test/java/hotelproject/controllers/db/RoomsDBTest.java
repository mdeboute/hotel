package hotelproject.controllers.db;

import static org.junit.Assert.assertTrue;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.RoomType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;


public class RoomsDBTest<Arraylist> {


  private DatabaseManager dbm;
  private ArrayList<String> log;
  private Room testRoom;
  private RoomType testRoomType;
  private String t_name;
  private int r_num;

  @Before
  public void setUp() {
    dbm = new DatabaseManager();
    log = new ArrayList<>();
    r_num = 666;
    t_name = generateRandomString();
    testRoomType = new RoomType(t_name, 1, 1, 1, 1, 1, 1, 1, 1);
  }


  public String generateRandomString() {
    byte[] array = new byte[7]; // length is bounded by 7
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  @Test
  public void testAddRoomType() {

    dbm.rdb.addRoomType(testRoomType);
    boolean successfulUpdate = false;

    List<RoomType> roomTypes = dbm.rdb.findAllRoomTypes();
    for (RoomType roomType : roomTypes) {
      if (testRoomType.equals(roomType)) {
        successfulUpdate = true;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }



  @Test
  public void testAddRoom() {
    dbm.rdb.addRoom(testRoom);
    boolean successfulUpdate = false;

    List<Room> rooms = dbm.rdb.findAllRooms();
    for (Room room : rooms) {
      if (room.equals(testRoom)) {
        successfulUpdate = true;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }

  @Test
  public void testReadRooms() {
  }
}