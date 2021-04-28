package hotelproject.views;

import hotelproject.controllers.db.DatabaseManager;
import hotelproject.controllers.objects.RoomType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class UpdateRoomView extends View {

    private final DatabaseManager dbm;

    private final TextField oldNumRoom = new TextField();
    private final TextField numRoom = new TextField();
    private final TextField floor = new TextField();
    private final ComboBox roomType = new ComboBox();
    private final CheckBox booked = new CheckBox("Booked");
    private Button submit;
    private Button cancel;

    public UpdateRoomView(DatabaseManager dbm) {
        this.dbm = dbm;
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("Update room", "Enter room specifics to update");


        Label oldNumRoomL = new Label("Old room number : ");
        pane.add(oldNumRoomL, 0, 1);
        pane.add(oldNumRoom, 1, 1);
        Label numRoomL = new Label("New room number : ");
        pane.add(numRoomL, 0, 2);
        pane.add(numRoom, 1, 2);
        Label floorL = new Label("New floor : ");
        pane.add(floorL, 0, 3);
        pane.add(floor, 1, 3);
        Label type = new Label("Room type : ");
        pane.add(type, 0, 4);

        List<RoomType> roomTypes = dbm.rdb.findAllRoomTypes();
        for (RoomType value : roomTypes) {
            // MenuItem rType = new MenuItem(roomTypes.get(i).getT_name());
            roomType.getItems().add(value.getT_name());
        }
        roomType.setValue("Single");

        pane.add(roomType, 1, 4);
        // pane.add(booked, 0, 5);

        submit = new Button("Submit");
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 6);

        cancel = new Button("Cancel");
        GridPane.setHalignment(cancel, javafx.geometry.HPos.CENTER);
        pane.add(cancel, 1, 7);

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

    public CheckBox getBooked() {
        return booked;
    }

    public TextField getFloor() {
        return floor;
    }

    public TextField getOldNumRoom() {
        return oldNumRoom;
    }

    public TextField getNumRoom() {
        return numRoom;
    }

    public ComboBox getRoomType() {
        return roomType;
    }

    public Button getCancel() {
        return cancel;
    }

}
