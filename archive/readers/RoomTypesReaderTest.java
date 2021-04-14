package hotelproject.readers;

import static org.junit.Assert.*;

import hotelproject.controllers.objects.RoomType;
import java.util.ArrayList;
import org.junit.Test;

public class RoomTypesReaderTest {

  @Test
  public void readRoomTypesFromCSV() {
    String filepath = "app/src/main/java/hotelproject/instances/room_type.csv";
    ArrayList<RoomType> roomTypes = RoomTypesReader.readRoomTypesFromCSV(filepath);
    assertTrue("Should equal true",roomTypes instanceof ArrayList);
  }

  @Test
  public void createRoomType() {
    String[] metadata = {"1","0","0","0","0","0","0","0","0"};
    assertTrue("Should equal true", RoomTypesReader.createRoomType(metadata) instanceof RoomType);
  }
}