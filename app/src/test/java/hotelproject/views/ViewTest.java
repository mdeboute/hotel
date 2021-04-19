package hotelproject.views;

import static javafx.application.Application.launch;
import static org.junit.Assert.*;

import com.sun.javafx.application.PlatformImpl;
import hotelproject.HotelProject;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ViewTest {

  public class InnerView extends View {

    @Override
    void createScene() {
      scene = new Scene(new GridPane());
    }
  }

  private View classUnderTest;

  @Before
  public void setUp() {
    PlatformImpl.startup(() -> {}); // To avoid: java.lang.IllegalStateException: Toolkit not initialized javafx
    classUnderTest = new InnerView();
  }

  @After
  public void tearDown() {
    Platform.exit();
  }

  @Test
  public void testCreateScene() {
  }

  @Test
  public void testCreateHeader() {
    String title = "testTitle";
    String subtitle = "testSubTitle";
    VBox header = classUnderTest.createHeader(title, subtitle);
    assertTrue(header != null);
  }

  @Test
  public void testCreatePane() {
    GridPane gridPane = classUnderTest.createPane();
    assertTrue(gridPane != null);
  }

  @Test
  public void testGetScene() {
  }
}