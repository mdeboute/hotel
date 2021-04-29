package hotelproject.views;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.List;

public class RoomsView extends View {

    // The scene's nodes
    // roomsTable is temporarily set to public (was private) for RoomsDisplay
    public TableView<Room> roomsTable = new TableView<>();
    // The user connected to the application
    private final User user;
    // Observable list with all the hotel's rooms
    private final ObservableList<Room> rooms;
    private final Button addRoom = new Button("New room...");
    private final Button deleteRoom = new Button("Delete room...");
    private final Button updateRoom = new Button("Update room...");

    public RoomsView(User user, List<Room> rooms) {
        this.user = user;
        this.rooms = FXCollections.observableList(rooms);
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        Label title = new Label("Hotel rooms");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.font(18));

        roomsTable.setEditable(true);

        // Create column in the table
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

        // Create a filtered list to put the rooms as items in the table
        FilteredList<Room> flRoom = new FilteredList(rooms, p -> true);
        roomsTable.setItems(flRoom);
        roomsTable.getColumns().addAll(roomNbCol, roomFloorCol, roomTypeCol, roomIsBookedCol);

        // Create choice box so the user can choose on the column he's searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox();
        whatToSearch.getItems().addAll("Room number", "Floor", "Room type");
        whatToSearch.setValue("Room number"); // default search

        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here!");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (whatToSearch.getValue()) //Switch on searchBar value
            {
                case "Room number" -> flRoom.setPredicate(p -> String.valueOf(p.getR_num()).contains(newValue.toLowerCase().trim()));
                case "Floor" -> flRoom.setPredicate(p -> String.valueOf(p.getR_floor()).contains(newValue.toLowerCase().trim()));
                case "Room type" -> flRoom.setPredicate(p -> p.getR_type().toLowerCase().contains(newValue.toLowerCase().trim()));
            }
        });

        //When the new choice is selected we reset
        whatToSearch.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {
            if (newVal != null) {
                searchBar.setText("");
            }
        });

        HBox search = new HBox(whatToSearch, searchBar);
        search.setAlignment(Pos.CENTER);

        pane.add(title, 0, 0);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
        pane.add(search, 0, 2);
        pane.add(roomsTable, 0, 4);
        if (user.getU_is_admin() == 1) {
            pane.add(addRoom, 0, 5);
            pane.add(deleteRoom, 0, 6);
            pane.add(updateRoom, 0, 7);
        }

        scene = new Scene(pane);
    }

    @Override
    GridPane createBody() {
        return null;
    }

    /**************************Getter**********************/

    public Button getAddRoom() {
        return addRoom;
    }

    public Button getDeleteRoom() {
        return deleteRoom;
    }

    public Button getUpdateRoom() {
        return updateRoom;
    }

}   
