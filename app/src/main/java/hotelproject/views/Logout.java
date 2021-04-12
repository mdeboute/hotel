package hotelproject.views;

import hotelproject.controllers.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Logout {

    private Scene scene;

    Button login;
    Button close;

    public Logout() {
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        Label title = new Label("You've been logged out successfully");
        login = new Button("Login page");
        close = new Button("Close");
        pane.add(title, 0, 0);
        pane.add(login, 0, 1);
        pane.add(close, 0, 2);

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getLogin() {
        return login;
    }

    public Button getClose() {
        return close;
    }
}
