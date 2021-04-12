package hotelproject.instance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import hotelproject.controllers.Room;

public class TestingReadingRooms {
 
    private static ArrayList<Room> readRoomsFromCSV(String fileName) {
        ArrayList<Room> rooms = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile)){

            br.readLine(); 
            String line1 = null;
            while ((line1 = br.readLine()) != null) {
                String[] attributes = line1.split(",");
                Room room = createRoom(attributes);
                rooms.add(room);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
      return rooms; 
    }

    private static Room createRoom(String[] metadata) {
        int num = Integer.parseInt(metadata[0]);
        int floor = Integer.parseInt(metadata[1]);
        String type = metadata[2];
        int booked = Integer.parseInt(metadata[3]);

        return new Room(num, floor, type, booked);
    }

  public static void main(String[] args) {
      ArrayList<Room> rooms = readRoomsFromCSV("/hotel/app/src/main/java/hotelproject/instance/room.csv");

      for (Room r : rooms) {
          System.out.println(r);
        }
    }

}
