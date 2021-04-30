package hotelproject;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class HotelProjectTest extends ApplicationTest {

  @Before
  public void setupSpec() throws Exception {
    FxToolkit.registerPrimaryStage();
  }

  @Before
  public void setUp() throws Exception {
    FxToolkit.setupApplication(HotelProject.class);
  }

  @Override
  public void start(Stage stage) {
    stage.show();
    stage.toFront();
  }

  @Test
  public void testHotelProject() {
    TextInputControl usernameTextField = lookup(".text-field").nth(0).queryTextInputControl();
    TextInputControl passwordTextField = lookup(".text-field").nth(1).queryTextInputControl();
    clickOn(usernameTextField).sleep(1000);
    write("admin").sleep(1000);
    clickOn(passwordTextField).sleep(1000);
    write("admin").sleep(1000);
    Button loginButton = lookup(".button").nth(0).queryButton();
    clickOn(loginButton).sleep(1000);
    //Button viewBookingsButton = lookup(".button").nth(2).queryButton();
    //clickOn(viewBookingsButton).sleep(1000);

  }

}