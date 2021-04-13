package hotelproject.readers;

import static org.junit.Assert.*;

import hotelproject.controllers.Room;
import java.lang.reflect.Method;
import org.junit.Test;

public class RoomsReaderTest {

  @Test
  public void testCreateRoom() throws Exception {

    /*
    Method[] methods = RoomsReader.class.getDeclaredMethods();

    for (Method m : methods) {
      System.out.println(m.toString());
    }
    */
    String[] metadata = {"1","2","3","0"};
    RoomsReader roomsReader = new RoomsReader();
    Class[] argClasses = new Class[1];
    argClasses[0] = String[].class;
    Method privateMethod = RoomsReader.class.getDeclaredMethod("createRoom", argClasses);
    privateMethod.setAccessible(true);
    Room returnValue = (Room) privateMethod.invoke(roomsReader, new Object[] {metadata});
    assertTrue("Should be true", returnValue.getR_num() == 1);
  }

}