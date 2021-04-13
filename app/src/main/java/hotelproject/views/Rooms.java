package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Rooms {

    private Scene scene;
    Button addRoom;

    public Rooms() {
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        Label title = new Label("Hotel rooms : ");
        addRoom = new Button("New room");
        pane.add(title, 0, 0);
        pane.add(addRoom, 0, 1);

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getAddRoom() {
        return addRoom;
    }
}
