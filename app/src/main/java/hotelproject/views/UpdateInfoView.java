package hotelproject.views;

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

public class UpdateInfoView {

    Scene scene;

    Label usernameL = new Label("Username :");
    Label fstPwdL = new Label("New Password : ");
    Label sndPwdL = new Label("Please enter your new password again : ");

    private final TextField username = new TextField();
    private final PasswordField firstPassword = new PasswordField();
    private final PasswordField secondPassword = new PasswordField();
    private Label output = new Label();

    Button changeUsername = new Button("Change username");
    Button changePwd = new Button("Change password");
    Button save = new Button("Save");

    public UpdateInfoView() {
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        pane.add(changeUsername, 0, 0);
        pane.add(changePwd,0, 3);

        usernameL.setVisible(false);
        pane.add(usernameL, 0, 0);
        username.setVisible(false);
        pane.add(username, 0, 1);

        fstPwdL.setVisible(false);
        pane.add(fstPwdL, 0, 3);
        firstPassword.setVisible(false);
        pane.add(firstPassword, 0, 4);

        sndPwdL.setVisible(false);
        pane.add(sndPwdL, 0, 6);
        secondPassword.setVisible(false);
        pane.add(secondPassword, 0, 7);


        pane.add(save, 1, 9);
        pane.add(output, 0, 10);

        scene = new Scene(pane, 500, 300);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getSave() {
        return save;
    }

    public Button getChangeUsername() {
        return changeUsername;
    }

    public Button getChangePwd() {
        return changePwd;
    }

    public Label getUsernameL() {
        return usernameL;
    }

    public Label getFstPwdL() {
        return fstPwdL;
    }

    public Label getSndPwdL() {
        return sndPwdL;
    }

    public TextField getUsername() {
        return username;
    }

    public PasswordField getFirstPassword() {
        return firstPassword;
    }

    public PasswordField getSecondPassword() {
        return secondPassword;
    }

    public Label getOutput() {
        return output;
    }

    public void setOutput(String outputTxt) {
        output.setText(outputTxt);
    }
}