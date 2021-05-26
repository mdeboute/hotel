package hotelproject.views;

import hotelproject.controllers.objects.User;
import hotelproject.controllers.utils.Default;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * This view aims for presenting user status information.
 * All users have access to this view.
 * User can change its username and password.
 */
public class MyPageView extends View {

    // The user connected to the application
    private final User user;

    // The scene's nodes
    private final String IDLE_CHANGE = "file:assets/img/ui_dev_pack/user_information_menu/idle_button_change.png";
    private final String HOVER_CHANGE = "file:assets/img/ui_dev_pack/user_information_menu/hover_button_change.png";
    private final String IDLE_LOGOUT = "file:assets/img/ui_dev_pack/user_information_menu/idle_button_logout.png";
    private final String HOVER_LOGOUT = "file:assets/img/ui_dev_pack/user_information_menu/hover_button_logout.png";
    private Button chUser;
    private Button chPwd;
    private Button logOut;

    /**
     * Constructor for initiating MyPageView.
     *
     * @param user the login user
     */
    public MyPageView(User user) {
        this.user = user;
        createScene();
    }

    /**
     * Create a scene for this view.
     */
    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, javafx.geometry.HPos.CENTER);

        bodyPane.setStyle("-fx-background-color: #1e1e1e; -fx-alignment: center;");
        scene = new Scene(bodyPane);
    }

    /**
     * Create MyPageView based on different user status.
     *
     * @return an instance of Grid pane.
     */
    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        String SFPath = Default.SFPath;
        String pwd = user.getU_password();
        String userStatus = "reception staff";
        if (user.getU_is_admin() == 1) {
            userStatus = "administrator";
        }

        Label status = changeLabelDesign(new Label("Your status is: " + userStatus), SFPath, 20, "white");
        pane.add(status, 0, 1);
        Label username = changeLabelDesign(new Label("Username: " + user.getU_name()), SFPath, 20, "white");
        Label password = changeLabelDesign(new Label("Password: " + "*".repeat(pwd.length())), SFPath, 20, "white");
        chUser = createButton(25, IDLE_CHANGE, HOVER_CHANGE);
        chPwd = createButton(25, IDLE_CHANGE, HOVER_CHANGE);

        pane.add(username, 0, 3);
        pane.add(password, 0, 4);
        pane.add(chUser, 1, 3);
        pane.add(chPwd, 1, 4);

        logOut = createButton(25, IDLE_LOGOUT, HOVER_LOGOUT);

        pane.add(logOut, 0, 7);

        pane.setVgap(15);
        return pane;
    }

    /*****************************Getters*********************************/

    public Button getChUser() {
        return chUser;
    }

    public Button getChPwd() {
        return chPwd;
    }

    public Button getLogout() {
        return logOut;
    }
}
