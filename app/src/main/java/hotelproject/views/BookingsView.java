package hotelproject.views;

import hotelproject.controllers.objects.Booking;
import hotelproject.controllers.objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class BookingsView extends View {

    // The user connected to the application
    private final User user;

    // Observable list with all the hotel's rooms
    private final ObservableList<Booking> bookings;

    // The scene's nodes
    private final TableView<Booking> bookingsTable = new TableView<>();
    private final DatePicker startDatePicker = new DatePicker(); // private final DatePicker date = new DatePicker();
    private final DatePicker endDatePicker = new DatePicker();
    private final String idlePathAddBooking = "file:assets/img/ui_dev_pack/booking_menu/idle_button_booking_menu.png";
    private final String hoverPathAddBooking = "file:assets/img/ui_dev_pack/booking_menu/hover_button_booking_menu.png";
    private Button addBooking;

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

        startDatePicker.setValue(LocalDate.now().minusMonths(1));
        endDatePicker.setValue(LocalDate.now().plusMonths(1));

        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            final Callback<DatePicker, DateCell> dayCellFactory =
                    new Callback<>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);

                                    if (item.isBefore(
                                            startDatePicker.getValue())
                                    ) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    }
                                }
                            };
                        }
                    };
            endDatePicker.setDayCellFactory(dayCellFactory);
        });

        bookingsTable.setEditable(true);

        // Create column in the table
        TableColumn<Booking, Integer> bookIDCol = new TableColumn<>("Booking ID");
        //bookIDCol.setMinWidth(100);
        bookIDCol.setMinWidth(100);
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("b_id"));

        TableColumn<Booking, Integer> roomNumCol = new TableColumn<>("Room number");
        roomNumCol.setMinWidth(100);
        roomNumCol.setCellValueFactory(new PropertyValueFactory<>("r_num"));

        TableColumn<Booking, Integer> paidBCCol = new TableColumn<>("Paid by card");
        paidBCCol.setMinWidth(100);
        paidBCCol.setCellValueFactory(new PropertyValueFactory<>("paid_by_card"));

        TableColumn<Booking, Integer> bookFromCol = new TableColumn<>("From");
        bookFromCol.setMinWidth(100);
        bookFromCol.setCellValueFactory(new PropertyValueFactory<>("b_from"));

        TableColumn<Booking, Integer> bookTillCol = new TableColumn<>("Till");
        bookTillCol.setMinWidth(100);
        bookTillCol.setCellValueFactory(new PropertyValueFactory<>("b_till"));

        TableColumn<Booking, Integer> bookFeeCol = new TableColumn<>("Booking fee");
        bookFeeCol.setMinWidth(100);
        bookFeeCol.setCellValueFactory(new PropertyValueFactory<>("b_fee"));

        TableColumn<Booking, Integer> bIPCol = new TableColumn<>("Is paid");
        bIPCol.setMinWidth(100);
        bIPCol.setCellValueFactory(new PropertyValueFactory<>("b_is_paid"));

        // Create a filtered list to put the rooms as items in the table
        FilteredList<Booking> flBooking = new FilteredList<>(bookings, p -> true);
        bookingsTable.setItems(flBooking);
        bookingsTable.getColumns().addAll(bookIDCol,roomNumCol,paidBCCol,bookFromCol,bookTillCol,bookFeeCol,bIPCol);

        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                startDatePicker.setValue(endDatePicker.getValue());
            }
            flBooking.setPredicate(item -> {
                // If filter text is empty, display all items.
                LocalDate from = item.getB_from().toLocalDate();
                LocalDate to = item.getB_till().toLocalDate();
                LocalDate leftEndpoint = startDatePicker.getValue();
                LocalDate rightEndpoint = endDatePicker.getValue();

                if ((to.equals(leftEndpoint) | to.isAfter(leftEndpoint)) & (to.equals(rightEndpoint) | to.isBefore(rightEndpoint))) {
                    return true;
                }
                if ((from.equals(leftEndpoint) | from.isAfter(leftEndpoint)) & (from.equals(rightEndpoint) | from.isBefore(rightEndpoint))) {
                    return true;
                }
                return from.isBefore(leftEndpoint) & to.isAfter(rightEndpoint);
            });
        });

        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
                endDatePicker.setValue(startDatePicker.getValue());
            }
            flBooking.setPredicate(item -> {
                // If filter text is empty, display all items.
                LocalDate from = item.getB_from().toLocalDate();
                LocalDate to = item.getB_till().toLocalDate();
                LocalDate leftEndpoint = startDatePicker.getValue();
                LocalDate rightEndpoint = endDatePicker.getValue();

                if ((to.equals(leftEndpoint) | to.isAfter(leftEndpoint)) & (to.equals(rightEndpoint) | to.isBefore(rightEndpoint))) {
                    return true;
                }
                if ((from.equals(leftEndpoint) | from.isAfter(leftEndpoint)) & (from.equals(rightEndpoint) | from.isBefore(rightEndpoint))) {
                    return true;
                }
                return from.isBefore(leftEndpoint) & to.isAfter(rightEndpoint);
            });
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Booking> sortedData = new SortedList<>(flBooking);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(bookingsTable.comparatorProperty());

        bookingsTable.setItems(flBooking);

        bookingsTable.getColumns().addAll(bookIDCol, roomNumCol, paidBCCol, bookFromCol, bookTillCol, bookFeeCol, bIPCol);

        // Create choice box so the user can choose on the column he's searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox<>();
        whatToSearch.getItems().addAll("Booking ID", "Room number");
        whatToSearch.setValue("Booking ID"); // default search

        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here!");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (whatToSearch.getValue().equals("Booking ID")) {
                flBooking.setPredicate(p -> String.valueOf(p.getB_id()).contains(newValue.toLowerCase().trim()));
            } else if (whatToSearch.getValue().equals("Room number")) {
                flBooking.setPredicate(p -> String.valueOf(p.getR_num()).contains(newValue.toLowerCase().trim())); // filter

            }
        });

        // When the new choice is selected we reset
        whatToSearch.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                searchBar.setText("");
            }
        });

        //date.setPromptText("Select date to view bookings");
        startDatePicker.setPromptText("FROM");
        endDatePicker.setPromptText("TO");
        startDatePicker.setMaxWidth(200);
        endDatePicker.setMaxWidth(200);
        //date.setMaxWidth(300);
        HBox dateRange = new HBox(startDatePicker, endDatePicker);
        dateRange.setAlignment(Pos.CENTER);
        pane.add(dateRange, 0, 3);

        HBox search = new HBox(whatToSearch, searchBar);
        search.setAlignment(Pos.CENTER);

        pane.add(title, 0, 0);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
        pane.add(search, 0, 2);

        pane.add(bookingsTable, 0, 4);
        if (user.getU_is_admin() == 1) {
            addBooking = createButton(35, idlePathAddBooking, hoverPathAddBooking);
            pane.add(addBooking, 0, 5);
            GridPane.setHalignment(addBooking, javafx.geometry.HPos.CENTER);
        }

        return pane;
    }

    /************************** Getter **********************/

    public Button getAddBooking() {
        return addBooking;
    }

    public DatePicker getDatePicker() {
        return startDatePicker;
    }

}
