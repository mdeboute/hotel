package hotelproject.readers;

import static org.junit.Assert.*;

import hotelproject.controllers.objects.Room;
import java.util.ArrayList;
import org.junit.Test;

public class RoomsReaderTest {

  @Test
  public void testReadRoomsFromCSV(){
    String filepath = "app/src/main/java/hotelproject/instances/room.csv";
    ArrayList<Room> rooms = RoomsReader.readRoomsFromCSV(filepath);
    assertTrue("Should equal true", rooms instanceof ArrayList);
  }

  @Test
  public void testCreateRoom() {

    String[] metadata = {"1","2","3","0"};
    assertEquals("Should be true",RoomsReader.createRoom(metadata).getR_num(),1);

  }

}
