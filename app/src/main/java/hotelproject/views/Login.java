package hotelproject.views;

import hotelproject.controllers.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Login {

    private Scene scene;
    static boolean isUserConnected = false;
    private Button testLogin;
    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final Label result = new Label();

    public Login(User user) {
        authentication(user);
    }

    private void authentication(User user) {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        pane.add(new Label("Username :"), 0, 0);
        pane.add(username, 1, 0);
        pane.add(new Label("Password :"), 0, 1);
        pane.add(password, 1, 1);
        pane.add(result, 0, 2);
        testLogin = new Button("Login");
        pane.add(testLogin, 1, 2);
        GridPane.setHalignment(testLogin, HPos.RIGHT);
        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getTestLoginButton() {
        return testLogin;
    }

    public TextField getUsername() {
        return username;
    }

    public PasswordField getPassword() {
        return password;
    }

    public Label getResult() {
        return result;
    }

    public boolean isUserConnected() {
        return isUserConnected;
    }
}
