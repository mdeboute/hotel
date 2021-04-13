package hotelproject.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import hotelproject.controllers.objects.Room;

public class RoomsReader {

    /** Reads rows from a csv file (containing room info) and creates Room objects with that information. */
    public static ArrayList<Room> readRoomsFromCSV(String filepath) {
        ArrayList<Room> rooms = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr;
        try {
            fr = new FileReader(filepath);
            br = new BufferedReader(fr);
            br.readLine(); 
            String line1;
            while ((line1 = br.readLine()) != null) {
                String[] attributes = line1.split(",");
                Room room = createRoom(attributes);
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rooms;
    }

    /** Uses information from an input array to create Room objects. */
    public static Room createRoom(String[] metadata) {
        int num = Integer.parseInt(metadata[0]);
        int floor = Integer.parseInt(metadata[1]);
        String type = metadata[2];
        int booked = Integer.parseInt(metadata[3]);
        return new Room(num, floor, type, booked);
    }

  public static void main(String[] args) {
      ArrayList<Room> rooms = readRoomsFromCSV("app/src/main/java/hotelproject/instances/room.csv");
      for (Room r : rooms) {
          System.out.println(r);
        }
    }

}
