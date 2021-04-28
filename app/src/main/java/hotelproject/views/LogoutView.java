package hotelproject.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LogoutView extends View {

    // The scene's nodes
    private final Label title = new Label("You've been logged out successfully");
    private final Button login = new Button("Login page");
    private final Button close = new Button("Close");

    public LogoutView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        pane.add(title, 0, 0);
        pane.add(login, 0, 1);
        pane.add(close, 0, 2);

        scene = new Scene(pane);
    }

    @Override
    GridPane createBody() {
        return null;
    }

    /*****************************Getters*********************************/

    public Button getLogin() {
        return login;
    }

    public Button getClose() {
        return close;
    }
}
