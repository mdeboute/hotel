package hotelproject.views;

import static org.junit.Assert.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

public class LoginViewTest extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    LoginView loginView = new LoginView(false);
    //primaryStage.setScene(loginView.getScene());
    //primaryStage.show();
    //primaryStage.close();
    Platform.exit();
  }

  @Test
  public void testLoginView() throws Exception {
    launch();
  }


}