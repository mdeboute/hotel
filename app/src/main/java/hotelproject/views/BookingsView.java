package hotelproject.views;

import hotelproject.controllers.objects.Booking;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;

public class BookingsView extends View {

    // The user connected to the application
    private final User user;

    // Observable list with all the hotel's rooms
    private final ObservableList<Booking> bookings;

    // The scene's nodes
    private final TableView<Booking> bookingsTable = new TableView<>();
    private final Button addBooking = new Button("New booking...");
    private final DatePicker date = new DatePicker();

    public BookingsView(User user, List<Booking> bookings) {
        this.user = user;
        this.bookings = FXCollections.observableList(bookings);
        createScene();
    }

    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, javafx.geometry.HPos.CENTER);

        bodyPane.getStyleClass().add("body-pane");
        bookingsTable.getStyleClass().add("table-view");

        scene = new Scene(bodyPane);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        Label title = new Label("Hotel bookings");
        title.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 25));
        title.setStyle("-fx-font-weight: bold;");
        title.setTextFill(Paint.valueOf("bb86fc"));

        bookingsTable.setEditable(true);

        // Create column in the table
        TableColumn<Booking, Integer> bookIDCol = new TableColumn<>("Booking ID");
        //bookIDCol.setMinWidth(100);
        bookIDCol.setPrefWidth(80.0F);
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("b_id"));

        TableColumn<Booking, Integer> roomNumCol = new TableColumn<>("Room number");
        roomNumCol.setPrefWidth(80.0F);
        roomNumCol.setCellValueFactory(new PropertyValueFactory<>("r_num"));

        TableColumn<Booking, Integer> paidBCCol = new TableColumn<>("Paid by card");
        paidBCCol.setPrefWidth(80.0F);
        paidBCCol.setCellValueFactory(new PropertyValueFactory<>("paid_by_card"));

        TableColumn<Booking, Integer> bookFromCol = new TableColumn<>("From");
        bookFromCol.setPrefWidth(80.0F);
        bookFromCol.setCellValueFactory(new PropertyValueFactory<>("b_from"));

        TableColumn<Booking, Integer> bookTillCol = new TableColumn<>("Till");
        bookTillCol.setPrefWidth(80.0F);
        bookTillCol.setCellValueFactory(new PropertyValueFactory<>("b_till"));

        TableColumn<Booking, Integer> bookFeeCol = new TableColumn<>("Booking fee");
        bookFeeCol.setPrefWidth(80.0F);
        bookFeeCol.setCellValueFactory(new PropertyValueFactory<>("b_fee"));

        TableColumn<Booking, Integer> bIPCol = new TableColumn<>("Is paid");
        bIPCol.setPrefWidth(80.0F);
        bIPCol.setCellValueFactory(new PropertyValueFactory<>("b_is_paid"));

        // Create a filtered list to put the rooms as items in the table
        FilteredList<Booking> flBooking = new FilteredList<>(bookings, p -> true);
        bookingsTable.setItems(flBooking);
        bookingsTable.getColumns().addAll(bookIDCol, roomNumCol, paidBCCol, bookFromCol, bookTillCol, bookFeeCol,
                bIPCol);

        // Create choice box so the user can choose on the column he's searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox<>();
        whatToSearch.getItems().addAll("Booking ID", "Room number");
        whatToSearch.setValue("Booking ID"); // default search

        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here!");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (whatToSearch.getValue() == "Booking ID") {
                flBooking.setPredicate(p -> String.valueOf(p.getB_id()).contains(newValue.toLowerCase().trim())); 
            } else if (whatToSearch.getValue() == "Room number") {
                flBooking.setPredicate(p -> String.valueOf(p.getR_num()).contains(newValue.toLowerCase().trim())); // filter

            }
        });

        // When the new choice is selected we reset
        whatToSearch.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                searchBar.setText("");
            }
        });

        date.setPromptText("Select date to view bookings");
        date.setMaxWidth(300);

        HBox search = new HBox(whatToSearch, searchBar);
        search.setAlignment(Pos.CENTER);

        pane.add(title, 0, 0);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
        pane.add(search, 0, 2);
        pane.add(date, 0, 3);
        GridPane.setHalignment(date, javafx.geometry.HPos.CENTER);
        pane.add(bookingsTable, 0, 4);
        if (user.getU_is_admin() == 1) {
            pane.add(addBooking, 0, 5);
        }

        return pane;
    }

    /************************** Getter **********************/

    public Button getAddBooking() {
        return addBooking;
    }

    public DatePicker getDatePicker() {
        return date;
    }

}
