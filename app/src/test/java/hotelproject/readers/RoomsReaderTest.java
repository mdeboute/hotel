package hotelproject.readers;

import static org.junit.Assert.*;

import hotelproject.controllers.Room;
import java.util.ArrayList;
import org.junit.Test;

public class RoomsReaderTest {

  @Test
  public void testReadRoomsFromCSV() {
    String filepath = "app/src/main/java/hotelproject/instances/room.csv";

    ArrayList<Room> rooms = RoomsReader.readRoomsFromCSV(filepath);
    assertTrue("Should be true",rooms instanceof ArrayList);
  }
}