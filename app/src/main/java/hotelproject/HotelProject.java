package hotelproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hotelproject.controllers.User;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class HotelProject extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        pane.add(new Label("Username :"), 0, 0);
        final TextField username = new TextField();
        pane.add(username, 1, 0);
        pane.add(new Label("Password :"), 0, 1);
        final PasswordField password = new PasswordField();
        pane.add(password, 1, 1);
        final Label result = new Label();
        pane.add(result, 0, 2);
        Button testLogin = new Button("Login");
        pane.add(testLogin, 1, 2);
        GridPane.setHalignment(testLogin, HPos.RIGHT);


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/StarWars?user=tobias&password=abcd1234&useSSL=false");
            System.out.println("Driver found and connected");
        } catch (SQLException e) {
            System.out.println("\n An error has occurred" + e.getMessage());
        }

        //test
        User admin = new User("mdeboute", "qwerty", true);

        testLogin.setOnAction(e -> {
            if (username.getText().equals(admin.getUsername()) && password.getText().equals(admin.getPassword())) {
                result.setText("Success !");
            }else{
                result.setText("Fail !");
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
