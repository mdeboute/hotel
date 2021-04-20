package hotelproject.controllers.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigManagerTest {

  private ConfigManager configManager;
  private String configPath;
  private String key1;

  @Before
  public void setUp() {
    configPath = "app.properties";
    configManager = new ConfigManager(configPath);
    key1 = "db.url";
  }

  @Test
  public void testGetConfigPath() {
    assertTrue(configManager.getConfigPath().equals(configPath));
  }

  @Test
  public void testGetPValue() {
    assertTrue(configManager.getPValue(key1) != null);
  }
}