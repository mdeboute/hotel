package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MyPageView extends View {

    // The user connected to the application
    private final User user;

    // The scene's nodes
    String idleUrlUpdate = "file:assets/img/ui_dev_pack/user_information_menu/idle_button_update.png";
    String hoverUrlUpdate = "file:assets/img/ui_dev_pack/user_information_menu/hover_button_update.png";
    String idleUrlLogOut = "file:assets/img/ui_dev_pack/user_information_menu/idle_button_logout.png";
    String hoverUrlLogOut = "file:assets/img/ui_dev_pack/user_information_menu/hover_button_logout.png";
    private Button updateInfo;
    private Button logOut;

    public MyPageView(User user) {
        this.user = user;
        createScene();
    }

    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, javafx.geometry.HPos.CENTER);

        bodyPane.setStyle("-fx-background-color: #1e1e1e; -fx-alignment: center;");
        scene = new Scene(bodyPane);
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        String userStatus = "reception staff";
        if (user.getU_is_admin() == 1) {
            userStatus = "administrator";
        }

        Label title = changeLabelDesign(new Label("My Page"), "file:assets/font/SF_Pro.ttf", 20, "white");
        Label status = changeLabelDesign(new Label("Your status is : " + userStatus), "file:assets/font/SF_Pro.ttf", 20, "white");
        pane.add(title, 0, 0);
        pane.add(status, 0, 1);

        Label username = changeLabelDesign(new Label("Username : " + user.getU_name()), "file:assets/font/SF_Pro.ttf", 20, "white");
        String pwd = user.getU_password();
        Label password = changeLabelDesign(new Label("Password : " + "*".repeat(pwd.length())), "file:assets/font/SF_Pro.ttf", 20, "white");
        pane.add(username, 0, 3);
        pane.add(password, 0, 4);

        updateInfo = createButton(30, idleUrlUpdate, hoverUrlUpdate);
        logOut = createButton(30, idleUrlLogOut, hoverUrlLogOut);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(updateInfo, logOut);
        buttons.setSpacing(15);

        pane.add(buttons, 0, 6);

        return pane;
    }

    /*****************************Getters*********************************/

    public Button getUpdateInfo() {
        return updateInfo;
    }

    public Button getLogout() {
        return logOut;
    }
}
