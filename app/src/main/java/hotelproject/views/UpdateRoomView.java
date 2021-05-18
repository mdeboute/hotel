package hotelproject.views;

import hotelproject.controllers.db.HotelData;
import hotelproject.controllers.objects.RoomType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class UpdateRoomView extends View {

    private final HotelData hdata;

    private final TextField numRoom = new TextField();
    private final TextField floor = new TextField();
    private final ComboBox<String> roomType = new ComboBox<>();
    private Button submit;

    public UpdateRoomView(HotelData hdata) {
        this.hdata = hdata;
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("Update room", "Enter room specifics to update");

        Label numRoomL = new Label("New room number: ");
        pane.add(numRoomL, 0, 1);
        pane.add(numRoom, 1, 1);

        numRoom.setPromptText("1-256"); // to set the hint text
        numRoom.getParent().requestFocus();

        // force the field to be numeric only
        numRoom.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("(?:[1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])")) {
                Platform.runLater(() -> {
                    numRoom.clear();
                });
            }
        });

        Label floorL = new Label("New floor: ");
        pane.add(floorL, 0, 2);
        pane.add(floor, 1, 2);

        floor.setPromptText("1-10"); // to set the hint text
        floor.getParent().requestFocus();

        // force the field to be numeric only
        floor.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("(?:[1-9]|10)")) {
                Platform.runLater(() -> {
                    floor.clear();
                });
            }
        });

        Label type = new Label("Room type: ");
        pane.add(type, 0, 3);

        List<RoomType> roomTypes = hdata.getRoomTypes();
        for (RoomType value : roomTypes) {
            // MenuItem rType = new MenuItem(roomTypes.get(i).getT_name());
            roomType.getItems().add(value.getT_name());
        }
        roomType.setValue("Single");

        pane.add(roomType, 1, 3);

        submit = new Button("Submit");
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 4);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);

        scene = new Scene(paneTwo);
    }

    @Override
    GridPane createBody() {
        return null;
    }

    public Button getSubmit() {
        return submit;
    }

    public TextField getFloor() {
        return floor;
    }

    public TextField getNumRoom() {
        return numRoom;
    }

    public ComboBox<String> getRoomType() {
        return roomType;
    }

}
