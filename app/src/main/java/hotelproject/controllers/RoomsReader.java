package hotelproject.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoomsReader {
 
    private static ArrayList<Room> readRoomsFromCSV(String filepath) {
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

    private static Room createRoom(String[] metadata) {
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