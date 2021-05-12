package hotelproject.views;

import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;

public class RoomsView extends View {

    // The scene's nodes

    // The user connected to the application
    private final User user;
    // Observable list with all the hotel's rooms
    private final ObservableList<Room> rooms;
    private final TableView<Room> roomsTable = new TableView<>();
    private final String idlePathAddRoom = "file:assets/img/ui_dev_pack/room_menu/idle_button_new_room.png";
    private final String hoverPathAddRoom = "file:assets/img/ui_dev_pack/room_menu/hover_button_new_room.png";
    private Button addRoom;

    public RoomsView(User user, List<Room> rooms) {
        this.user = user;
        this.rooms = FXCollections.observableList(rooms);
        createScene();
    }

    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, HPos.CENTER);

        //roomsTable.setTextFill(Paint.valueOf("white"));
        //roomsTable.setStyle("-fx-background-color: #121212;");

        bodyPane.getStyleClass().add("body-pane");
        roomsTable.getStyleClass().add("table-view");

        scene = new Scene(bodyPane);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");

        // Disable horizontal scroll bar in TableView
        scene.getStylesheets().add("file:assets/css/JavaFx2HideTableViewHScrollBar.css");
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        Label title = new Label("Hotel rooms");
        //title.setFont(Font.font(18));
        title.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 25));
        title.setStyle("-fx-font-weight: bold;");
        title.setTextFill(Paint.valueOf("bb86fc"));

        roomsTable.setEditable(true);
        // Create column in the table
        TableColumn<Room, Integer> roomNbCol = new TableColumn<>("Room number");
        roomNbCol.setPrefWidth(120.0F);
        roomNbCol.setCellValueFactory(new PropertyValueFactory<>("r_num"));

        TableColumn<Room, Integer> roomFloorCol = new TableColumn<>("Floor");
        roomFloorCol.setPrefWidth(100.0F);
        roomFloorCol.setCellValueFactory(new PropertyValueFactory<>("r_floor"));

        TableColumn<Room, String> roomTypeCol = new TableColumn<>("Room type");
        roomTypeCol.setPrefWidth(100.0F);
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("r_type"));

        TableColumn<Room, String> roomIsBookedCol = new TableColumn<>("Is booked");
        roomIsBookedCol.setPrefWidth(80.0F);
        roomIsBookedCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().is_booked()));

        // Create a filtered list to put the rooms as items in the table
        FilteredList<Room> flRoom = new FilteredList<>(rooms, p -> true);
        roomsTable.setItems(flRoom);
        roomsTable.getColumns().addAll(roomNbCol, roomFloorCol, roomTypeCol, roomIsBookedCol);

        // Create choice box so the user can choose on the column he's searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox<>();
        whatToSearch.getItems().addAll("Room number", "Floor", "Room type", "Booked");
        whatToSearch.setValue("Room number"); // default search

        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (whatToSearch.getValue().equals("Room number")) {
                flRoom.setPredicate(p -> String.valueOf(p.getR_num()).contains(newValue.toLowerCase().trim()));
            } else if (whatToSearch.getValue().equals("Floor")) {
                flRoom.setPredicate(p -> String.valueOf(p.getR_floor()).contains(newValue.toLowerCase().trim()));
            } else if (whatToSearch.getValue().equals("Room type")) {
                flRoom.setPredicate(p -> p.getR_type().toLowerCase().contains(newValue.toLowerCase().trim()));
            } else if (whatToSearch.getValue().equals("Booked")) {
                flRoom.setPredicate(p -> p.is_booked().toLowerCase().contains(newValue.toLowerCase().trim()));
            }
        });

        // When the new choice is selected we reset
        whatToSearch.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                searchBar.setText("");
            }
        });

        HBox search = new HBox(whatToSearch, searchBar);
        search.setAlignment(Pos.CENTER);

        pane.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);
        pane.add(search, 0, 2);
        pane.add(roomsTable, 0, 4);
        addRoom = createButton(35, idlePathAddRoom, hoverPathAddRoom);
        addRoom.setVisible(false);

        if (user.getU_is_admin() == 1) {
            pane.add(addRoom, 0, 5);
            addRoom.setVisible(true);
            GridPane.setHalignment(addRoom, javafx.geometry.HPos.CENTER);
        }

        return pane;
    }

    /************************** Getter **********************/

    public Button getAddRoom() {
        return addRoom;
    }

    public TableView<Room> getRoomsTable() {
        return roomsTable;
    }
}
