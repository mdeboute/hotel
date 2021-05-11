package hotelproject.controllers.objects;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    @Test
    public void testUsertoString() {
        User admin = new User();
        assertNotNull("User instance should have a toString message", admin.toString());
    }
}
