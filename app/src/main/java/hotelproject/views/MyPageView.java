package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MyPageView extends View {

    // The user connected to the application
    private final User user;

    // The scene's nodes
    private final Button updateInfo = new Button("Change personal information");
    private final Button back = new Button("Back");
    private final Button logout = new Button("Logout");

    public MyPageView(User user) {
        this.user = user;
        createScene();
    }

    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, javafx.geometry.HPos.CENTER);

        bodyPane.setStyle("-fx-background-color: #121212; -fx-alignment: center;");
        scene = new Scene(bodyPane);
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        String userStatus = "Reception staff";
        if (user.getU_is_admin() == 1) {
            userStatus = "Administrator";
        }

        Label title = new Label("My Page");
        title.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 20));
        title.setTextFill(Paint.valueOf("white"));
        Label status = new Label("Your status is : " + userStatus);
        status.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 20));
        status.setTextFill(Paint.valueOf("white"));
        pane.add(title, 0, 0);
        pane.add(status, 0, 1);

        Label username = new Label("Username : " + user.getU_name());
        username.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 20));
        username.setTextFill(Paint.valueOf("white"));
        String pwd = user.getU_password();
        Label password = new Label("Password : " + "*".repeat(pwd.length()));
        password.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 20));
        password.setTextFill(Paint.valueOf("white"));
        pane.add(username, 0, 3);
        pane.add(password, 0, 4);

        pane.add(updateInfo, 0, 6);
        pane.add(back, 0, 7);
        pane.add(logout, 0, 8);

        return pane;
    }

    /*****************************Getters*********************************/

    public Button getUpdateInfo() {
        return updateInfo;
    }

    public Button getBack() {
        return back;
    }

    public Button getLogout() {
        return logout;
    }
}
