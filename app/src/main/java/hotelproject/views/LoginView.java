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

public class LoginView extends View {

    boolean onlyPwd;

    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Label result = new Label();
    private Label credentials = new Label("Please enter your password first.");
    private Button testLogin = new Button("Login");

    public LoginView(boolean onlyPwd) {
        this.onlyPwd = onlyPwd;
        createScene();
    }

    @Override
    void createScene() {

        GridPane pane = createPane();

        credentials.setVisible(false);
        pane.add(credentials, 0, 0);
        int pwdRow = 1;
        if (!onlyPwd) {
            pane.add(new Label("Username :"), 0, 1);
            pane.add(username, 1, 1);
            pwdRow = 2;
        }
        pane.add(new Label("Password :"), 0, pwdRow);
        pane.add(password, 1, pwdRow);
        pane.add(result, 0, pwdRow+1);
        pane.add(testLogin, 1, pwdRow+1);
        GridPane.setHalignment(testLogin, HPos.RIGHT);

        scene = new Scene(pane);
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
