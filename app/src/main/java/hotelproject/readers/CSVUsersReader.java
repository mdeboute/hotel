package hotelproject.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import hotelproject.controllers.objects.User;

public class CSVUsersReader {

  public static ArrayList<User> readUsersFromCSV(String filepath) {
    ArrayList<User> users = new ArrayList<>();
    BufferedReader br = null;
    FileReader fr;
    try {
      fr = new FileReader(filepath);
      br = new BufferedReader(fr);
      br.readLine();
      String line1;
      while ((line1 = br.readLine()) != null) {
        String[] attributes = line1.split(",");
        User room = createUser(attributes);
        users.add(room);
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
    return users;
  }

  public static User createUser(String[] metadata) {
    String u_name = metadata[0];
    String u_password = metadata[1];
    int is_admin = Integer.parseInt(metadata[2]);
    return new User(u_name, u_password, is_admin);
  }

  public static void main(String[] args) {
    ArrayList<User> users = readUsersFromCSV("app/src/main/java/instances/users.csv");
    for (User u : users) {
      System.out.println(u);
    }
  }
  
}
