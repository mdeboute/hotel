package hotelproject.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NewUserView extends View {

    private final TextField userName = new TextField();
    private final TextField userPassword = new TextField();
    private final CheckBox userIA = new CheckBox("Is admin ?");
    private Button submit;
    private final String IDLE_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
    private final String HOVER_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";

    private Button cancel;
    private final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
    private final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";

    public NewUserView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("New user", "Enter the new user details");

        Label userNameL = new Label("Username: ");
        pane.add(userNameL, 0, 2);
        pane.add(userName, 1, 2);
        Label userPassL = new Label("User password: ");
        pane.add(userPassL, 0, 4);
        pane.add(userPassword, 1, 4);
        pane.add(userIA, 0, 5);

        submit = createButton(35, IDLE_BUTTON_SUBMIT, HOVER_BUTTON_SUBMIT);
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 7);

        cancel = createButton(35, IDLE_BUTTON_CANCEL, HOVER_BUTTON_CANCEL);
        GridPane.setHalignment(cancel, javafx.geometry.HPos.CENTER);
        pane.add(cancel, 1, 8);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        pane.getStyleClass().add("body-pane");
        paneTwo.getStyleClass().add("body-pane");

        scene = new Scene(paneTwo);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    @Override
    GridPane createBody() {
        return null;
    }

    public Button getSubmit() {
        return submit;
    }

    public CheckBox getAdmin() {
        return userIA;
    }

    public TextField getUserName() {
        return userName;
    }

    public TextField getUserPassWord() {
        return userPassword;
    }

    public Button getCancel() {
        return cancel;
    }

}
