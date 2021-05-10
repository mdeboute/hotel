package hotelproject.controllers.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PasswordAuthTest {

  private final PasswordAuth passwordAuth = new PasswordAuth();
  private final String password = "password";
  private final String token = passwordAuth.hash(password);


  @Test
  public void testHash() {
    String hashCode = passwordAuth.hash(password);
    assertNotNull(hashCode);
  }

  @Test
  public void testAuthenticate() {
    assertTrue(passwordAuth.authenticate(password, token));
  }
}