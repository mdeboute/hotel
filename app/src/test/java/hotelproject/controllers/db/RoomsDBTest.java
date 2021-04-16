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


public class RoomsDBTest {


  private DatabaseManager dbm;
  private ArrayList<String> log;
  private Room testRoom1;
  private RoomType testRoomType1;
  private String t_name;
  private int r_num;

  @Before
  public void setUp() {
    dbm = new DatabaseManager();
    log = new ArrayList<>();
    r_num = 666;
    t_name = generateRandomString();
    testRoom1 = new Room(r_num,6,"Single", 1);
    testRoomType1 = new RoomType(t_name, 1, 1, 1, 1, 1, 1, 1, 1);
  }


  public String generateRandomString() {
    byte[] array = new byte[7]; // length is bounded by 7
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  @Test
  public void testAddRoomType() {

    // Ensure first that the database does not contain testRoomType
    dbm.rdb.deleteRoomType(testRoomType1);
    dbm.rdb.addRoomType(testRoomType1);
    boolean successfulUpdate = false;

    List<RoomType> roomTypes = dbm.rdb.findAllRoomTypes();
    for (RoomType roomType : roomTypes) {
      if (testRoomType1.getT_name().equals(roomType.getT_name())) {
        successfulUpdate = true;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }

  @Test
  public void testDeleteRoomType() {
    dbm.rdb.addRoomType(testRoomType1);
    dbm.rdb.deleteRoomType(testRoomType1);
    boolean successfulUpdate = true; // Note: 'true'

    List<RoomType> roomTypes = dbm.rdb.findAllRoomTypes();
    for (RoomType roomType : roomTypes) {
      if (roomType.getT_name().equals(testRoomType1.getT_name())) {
        successfulUpdate = false;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }

  @Test
  public void testAddRoom() {
    // Have to ensure that testRoom does not already exist in the database
    dbm.rdb.deleteRoom(testRoom1);
    dbm.rdb.addRoom(testRoom1);
    boolean successfulUpdate = false;

    List<Room> rooms = dbm.rdb.findAllRooms();
    for (Room room : rooms) {
      if (room.getR_num() == testRoom1.getR_num()) {
        successfulUpdate = true;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }

  @Test
  public void testDeleteRoom() {
    // Have to ensure that testRoom does exist in the database; hence the reverse order compared to previous test
    dbm.rdb.deleteRoom(testRoom1);
    dbm.rdb.addRoom(testRoom1);
    dbm.rdb.deleteRoom(testRoom1);
    boolean successfulUpdate = true;

    List<Room> rooms = dbm.rdb.findAllRooms();
    for (Room room : rooms) {
      if (room.getR_num() == testRoom1.getR_num()) {
        successfulUpdate = false;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }

  @Test
  public void testUpdateRoomType() {
    dbm.rdb.deleteRoomType(testRoomType1);
    dbm.rdb.addRoomType(testRoomType1);

    // Update testRoomType1, from has bed (1) to does not have bed (0)
    testRoomType1.setBeds(0);

    // test
    dbm.rdb.updateRoomType(testRoomType1, testRoomType1.getT_name());

    boolean successfulUpdate = false;

    List<RoomType> roomTypes = dbm.rdb.findAllRoomTypes();
    for (RoomType roomType : roomTypes) {
      if (testRoomType1.getT_name().equals(roomType.getT_name())) {
        successfulUpdate = true;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }

  @Test
  public void testUpdateRoom() {
    dbm.rdb.deleteRoom(testRoom1);
    dbm.rdb.addRoom(testRoom1);

    // Update testRoom1, from has r_type "Single" r_type "Double"
    testRoom1.setR_type("Double");

    // test
    dbm.rdb.updateRoom(testRoom1, testRoom1.getR_num());

    boolean successfulUpdate = false;

    List<Room> rooms = dbm.rdb.findAllRooms();
    for (Room room : rooms) {
      if (testRoom1.getR_num() == room.getR_num()) {
        successfulUpdate = true;
        break;
      }
    }
    assertTrue(successfulUpdate);
  }
}