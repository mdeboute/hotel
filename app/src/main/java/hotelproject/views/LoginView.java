package hotelproject.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView {

    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final Label result = new Label();
    Label credentials = new Label("Please enter your credentials first.");
    private Scene scene;
    private Button testLogin;

    public LoginView() {
        authentication();
    }

    private void authentication() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);


        credentials.setVisible(false);
        pane.add(credentials, 0, 0);
        pane.add(new Label("Username :"), 0, 1);
        pane.add(username, 1, 1);
        pane.add(new Label("Password :"), 0, 2);
        pane.add(password, 1, 2);
        pane.add(result, 0, 3);
        testLogin = new Button("Login");
        pane.add(testLogin, 1, 3);
        GridPane.setHalignment(testLogin, HPos.RIGHT);
        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Label getCredentials() {
        return credentials;
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
}
