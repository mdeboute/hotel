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

        testLogin.setOnAction(e -> { //test if the user exist in the database and has correct password
            if (username.getText().equals(user.getUsername()) && password.getText().equals(user.getPassword())) {
                result.setText("Success !");
                user.setUsername(username.getText());
                user.setPassword(password.getText());
                //is user admin ?
                isUserConnected = true;
            }else{
                result.setText("Fail !");
            }
        });

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isUserConnected() {
        return isUserConnected;
    }
}
