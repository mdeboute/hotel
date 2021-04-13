package hotelproject.controllers;

import hotelproject.controllers.objects.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUsertoString() {
        User admin = new User();
        assertNotNull("User instance should have a toString message", admin.toString());
    }
}
