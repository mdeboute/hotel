package hotelproject.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import hotelproject.controllers.objects.RoomType;

public class RoomTypesReader {
  
  public static ArrayList<RoomType> readRoomTypesFromCSV(String filepath) {
    ArrayList<RoomType> roomtypes = new ArrayList<>();
    BufferedReader br = null;
    FileReader fr;
    try {
      fr = new FileReader(filepath);
      br = new BufferedReader(fr);
      br.readLine();
      String line1;
      while ((line1 = br.readLine()) != null) {
        String[] attributes = line1.split(",");
        RoomType roomtype = createRoomType(attributes);
        roomtypes.add(roomtype);
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
    return roomtypes;
  }
  
  public static RoomType createRoomType(String[] metadata) {
    String t_name = metadata[0];
    int beds = Integer.parseInt(metadata[1]);
    int r_size = Integer.parseInt(metadata[2]);
    int has_view = Integer.parseInt(metadata[3]);
    int has_kitchen = Integer.parseInt(metadata[4]);
    int has_bathroom = Integer.parseInt(metadata[5]);
    int has_workspace = Integer.parseInt(metadata[6]);
    int has_tv = Integer.parseInt(metadata[7]);
    int has_coffee_maker = Integer.parseInt(metadata[8]);
    return new RoomType(t_name, beds, r_size, has_view, has_kitchen, has_bathroom, has_workspace, has_tv, has_coffee_maker);
  }

  public static void main(String[] args) {
    ArrayList<RoomType> roomtypes = readRoomTypesFromCSV("app/src/main/java/hotelproject/instances/room_type.csv");
    for (RoomType rt : roomtypes) {
      System.out.println(rt);
    }
  }

}
