package hotelproject.views;

import hotelproject.controllers.db.RoomsDB;
import hotelproject.controllers.objects.RoomType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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

        VBox header = createHeader("New room","Enter the new room specifications");

        Label numRoomL = new Label("Room number : ");
        pane.add(numRoomL, 0, 2);
        pane.add(numRoom, 1, 2);
        Label floorL = new Label("Floor : ");
        pane.add(floorL, 0, 3);
        pane.add(floor, 1, 3);
        Label type = new Label("Room type : ");
        pane.add(type, 0, 4);

        List<RoomType> roomTypes = RoomsDB.findAllRoomTypes(conn);
        for (RoomType value : roomTypes) {
            //MenuItem rType = new MenuItem(roomTypes.get(i).getT_name());
            roomType.getItems().add(value.getT_name());
        }
        roomType.setValue("Single");

        pane.add(roomType, 1, 4);
        pane.add(addRoomType, 2, 4);
        //pane.add(booked, 0, 5);

        submit = new Button("Submit");
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 7);


        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);


        scene = new Scene(paneTwo);
    }

    private VBox createHeader(String title, String subtitle) {
        Label titleL = new Label(title);
        titleL.setStyle("-fx-font-weight: bold;");
        titleL.setFont(Font.font(18));

        Label subtitleL = new Label(subtitle);
        subtitleL.setFont(Font.font(14));

        VBox header = new VBox(titleL, subtitleL);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10.0, 10.0, 2.0, 10.0));
        header.setSpacing(5.5);

        return header;
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
