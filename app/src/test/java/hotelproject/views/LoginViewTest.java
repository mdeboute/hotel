package hotelproject.views;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class LoginViewTest extends ApplicationTest {


  @BeforeClass
  public static void setupSpec() {
    if (Boolean.getBoolean("headless")) {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("java.awt.headless", "true");
    }
  }


  @Override
  public void start (Stage stage) {
    LoginView loginView = new LoginView(true);
    stage.setScene(loginView.getScene());
    stage.show();
    stage.toFront();
  }

  @Test
  public void hasLoginViewButton() {

    TextInputControl textField = lookup(".text-field").nth(0).queryTextInputControl();
    clickOn(textField).sleep(1000);
    write("invalid").sleep(1000);
    Button button = lookup(".button").nth(0).queryButton();
    clickOn(button).sleep(1000);
  }
}