package hotelproject.views;

import hotelproject.controllers.db.RoomsDB;
import hotelproject.controllers.objects.RoomType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.util.List;

public class NewRoomView {

    Connection conn;

    Scene scene;

    private final TextField numRoom = new TextField();
    private final TextField floor = new TextField();
    private final ComboBox roomType = new ComboBox();
    private final Button addRoomType = new Button("Add type");
    private final CheckBox booked = new CheckBox("Booked");
    private Button submit;

    public NewRoomView(Connection conn) {
        this.conn = conn;
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        Label title = new Label("Enter the new room specifications :");
        Label numRoomL = new Label("Room number : ");
        pane.add(title, 0, 0);
        pane.add(numRoomL, 0, 1);
        pane.add(numRoom, 1, 1);
        Label floorL = new Label("Floor : ");
        pane.add(floorL, 0, 2);
        pane.add(floor, 1, 2);
        Label type = new Label("Room type : ");
        pane.add(type, 0, 3);

        List<RoomType> roomTypes = RoomsDB.findAllRoomType(conn);
        for (RoomType value : roomTypes) {
            //MenuItem rType = new MenuItem(roomTypes.get(i).getT_name());
            roomType.getItems().add(value.getT_name());
        }
        roomType.setValue("Single");

        pane.add(roomType, 1, 3);
        pane.add(addRoomType, 2, 3);
        pane.add(booked, 0, 4);

        submit = new Button("Submit");
        pane.add(submit, 3, 6);

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
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

    public TextField getNumRoom() {
        return numRoom;
    }

    public ComboBox getRoomType() {
        return roomType;
    }

    public Button getAddRoomType() {
        return addRoomType;
    }
}
