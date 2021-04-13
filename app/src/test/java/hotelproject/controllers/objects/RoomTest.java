package hotelproject.controllers.objects;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RoomTest {

    @Test
    public void testRoomtoString() {
        Room room = new Room();
        assertNotNull("Room instance should have a toString message", room.toString());
    }
}
