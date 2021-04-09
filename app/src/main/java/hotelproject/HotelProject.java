package hotelproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hotelproject.controllers.User;
import hotelproject.views.Login;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class HotelProject extends Application {

    //private User user;

    @Override
    public void start(Stage primaryStage) throws Exception {

        User user = new User("toto", "qwerty", true);
        Login login = new Login(user);

        //connection to database
        /**try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/StarWars?user=tobias&password=abcd1234&useSSL=false");
            System.out.println("Driver found and connected");
        } catch (SQLException e) {
            System.out.println("\n An error has occurred" + e.getMessage());
        }**/

        /**primaryStage.setScene(welcomeSceneBeforeAuth());
        primaryStage.setTitle("Hotel Manager");
        primaryStage.show();**/

        Stage secondaryStage = new Stage();
        primaryStage.setScene(login.getScene());
        primaryStage.setTitle("Login");
        primaryStage.show();

        while (primaryStage.isShowing()) {
            if (login.isUserConnected()) {
                //exit login window and change welcomeScene
                //user = user;
                primaryStage.close();
                secondaryStage.setScene(welcomeSceneAfterAuth());
                secondaryStage.show();
            }
        }
    }

    private Scene welcomeSceneBeforeAuth() {
        Button quit = new Button("Quit");
        Label welcome = new Label("Welcome to your Hotel Manager");

        Group group = new Group(welcome, quit);
        Scene scene = new Scene(group,500, 300);

        return scene;
    }

    private Scene welcomeSceneAfterAuth() {
        Scene scene = new Scene(new Label(""), 500, 300);
        return scene;
    }
}
