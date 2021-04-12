package hotelproject;

import hotelproject.controllers.User;
import hotelproject.views.Login;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class HotelProject extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        User user = new User("toto", "qwerty", 1);
        Login login = new Login(user);

        /*
        primaryStage.setScene(welcomeSceneBeforeAuth());
        primaryStage.setTitle("Hotel Manager");
        primaryStage.show();
        */

        primaryStage.setScene(login.getScene());
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private Scene welcomeSceneBeforeAuth() {
        Button quit = new Button("Quit");
        Label welcome = new Label("Welcome to your Hotel Manager");

        Group group = new Group(welcome, quit);

        return new Scene(group,500, 300);
    }

    private Scene welcomeSceneAfterAuth() {
        return new Scene(new Label(""), 500, 300);
    }
}
