package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MyPageView {

    User user;
    Scene scene;

    Button updateInfo = new Button("Change personal information");
    Button quit = new Button("Back");

    public MyPageView(User user) {
        this.user = user;
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        String userStatus = "Reception staff";
        if (user.getU_is_admin() == 1) {
            userStatus = "Administrator";
        }

        Label title = new Label("My Page");
        Label status = new Label("Your status is : " + userStatus);
        pane.add(title, 0, 0);
        pane.add(status, 0, 1);

        Label username = new Label("Username : " + user.getU_name());
        String pwd = user.getU_password();
        String hiddenPwd = "";
        for (int i = 0 ; i < pwd.length() ; i++) {
            hiddenPwd += "*";
        }
        Label password = new Label("Password : " + hiddenPwd);
        pane.add(username, 0, 3);
        pane.add(password, 0, 4);

        pane.add(updateInfo, 0, 6);
        pane.add(quit, 0, 7);

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getUpdateInfo() {
        return updateInfo;
    }

    public Button getQuit() {
        return quit;
    }
}
