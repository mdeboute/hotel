package hotelproject.views;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class RoomsView {

    User user;
    ObservableList<Room> rooms;

    private Scene scene;
    TableView<Room> roomsTable = new TableView<>();
    Button addRoom;

    public RoomsView(User user, List<Room> rooms) {
        this.user = user;
        this.rooms = FXCollections.observableList(rooms);

        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        Label title = new Label("Hotel rooms : ");

        roomsTable.setEditable(true);

        TableColumn roomNbCol = new TableColumn("Room number");
        roomNbCol.setMinWidth(100);
        roomNbCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("r_num"));

        TableColumn roomFloorCol = new TableColumn("Floor");
        roomFloorCol.setMinWidth(100);
        roomFloorCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("r_floor"));

        TableColumn roomTypeCol = new TableColumn("Room type");
        roomTypeCol.setMinWidth(100);
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("r_type"));

        TableColumn roomIsBookedCol = new TableColumn("Booked");
        roomIsBookedCol.setMinWidth(100);
        roomIsBookedCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("booked"));

        FilteredList<Room> flRoom = new FilteredList(rooms, p -> true);
        roomsTable.setItems(flRoom);
        roomsTable.getColumns().addAll(roomNbCol, roomFloorCol, roomTypeCol, roomIsBookedCol);

        ChoiceBox<String> whatToSearch = new ChoiceBox();
        whatToSearch.getItems().addAll("Room number", "Floor", "Room type");
        whatToSearch.setValue("Room number");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here!");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (whatToSearch.getValue())//Switch on searchBar value
            {
                case "Room number" -> flRoom.setPredicate(p -> String.valueOf(p.getR_num()).contains(newValue.toLowerCase().trim())); //filter table by room number
                case "Floor" -> flRoom.setPredicate(p -> String.valueOf(p.getR_floor()).contains(newValue.toLowerCase().trim())); //filter table by floor
                case "Room type" -> flRoom.setPredicate(p -> p.getR_type().toLowerCase().contains(newValue.toLowerCase().trim())); //filter table by room type
            }
        });

        whatToSearch.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//new choice is selected we reset
            if (newVal != null) {
                searchBar.setText("");
            }
        });

        HBox search = new HBox(whatToSearch, searchBar);
        search.setAlignment(Pos.CENTER);

        addRoom = new Button("New room");
        pane.add(title, 0, 0);
        pane.add(search, 0, 1);
        pane.add(roomsTable, 0, 2);
        if (user.getU_is_admin() == 1) {
            pane.add(addRoom, 0, 3);
        }

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getAddRoom() {
        return addRoom;
    }
}
