package hotelproject.views;

import hotelproject.controllers.objects.Booking; 
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class BookingsView extends View {

  // The user connected to the application
  private User user;

  // Observable list with all the hotel's rooms
  private ObservableList<Booking> bookings;

  // The scene's nodes
  private TableView<Booking> bookingsTable = new TableView<>();
  private Button addBooking = new Button("New booking...");

  public BookingsView(User user, List<Booking> bookings) {
    this.user = user;
    this.bookings= FXCollections.observableList(bookings);
    createScene();
  }

  @Override
  void createScene() {
    GridPane pane = createPane();

    Label title = new Label("Hotel bookings");
    title.setStyle("-fx-font-weight: bold;");
    title.setFont(Font.font(18));

    bookingsTable.setEditable(true);

    // Create column in the table
    TableColumn bookIDCol = new TableColumn("Booking ID");
    bookIDCol.setMinWidth(100);
    bookIDCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("b_id"));

    TableColumn roomNumCol = new TableColumn("Room number");
    roomNumCol.setMinWidth(100);
    roomNumCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("r_num"));

    TableColumn paidBCCol = new TableColumn("Paid by card");
    paidBCCol.setMinWidth(100);
    paidBCCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("paid_by_card"));

    TableColumn bookFromCol = new TableColumn("From");
    bookFromCol.setMinWidth(100);
    bookFromCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("b_from"));

    TableColumn bookTillCol = new TableColumn("Till");
    bookTillCol.setMinWidth(100);
    bookTillCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("b_till"));

    TableColumn bookFeeCol = new TableColumn("Booking fee");
    bookFeeCol.setMinWidth(100);
    bookFeeCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("b_fee"));

    TableColumn bIPCol = new TableColumn("Is paid");
    bIPCol.setMinWidth(100);
    bIPCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("b_is_paid"));

    // Create a filtered list to put the rooms as items in the table
    FilteredList<Booking> flBooking = new FilteredList(bookings, p -> true);
    bookingsTable.setItems(flBooking);
    bookingsTable.getColumns().addAll(bookIDCol, roomNumCol, paidBCCol, bookFromCol, bookTillCol, bookFeeCol, bIPCol);

    // Create choice box so the user can choose on the column he's searching in
    ChoiceBox<String> whatToSearch = new ChoiceBox();
    whatToSearch.getItems().addAll("Booking ID", "Room number");
    whatToSearch.setValue("Booking ID"); // default search

    // Create search bar with listener to update according to the user's input
    TextField searchBar = new TextField();
    searchBar.setPromptText("Search here!");
    searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
      switch (whatToSearch.getValue()) // Switch on searchBar value
      {
      case "Booking ID":
        flBooking.setPredicate(p -> String.valueOf(p.getB_id()).contains(newValue.toLowerCase().trim())); // filter table
                                                                                                        // by booking id
        break;
      
      case "Room number":
        flBooking.setPredicate(p -> String.valueOf(p.getR_num()).contains(newValue.toLowerCase().trim())); // filter
                                                                                                          // table by
                                                                                                          // room number
        break;
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
    GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
    pane.add(search, 0, 2);
    pane.add(bookingsTable, 0, 4);
    if (user.getU_is_admin() == 1) {
      pane.add(addBooking, 0, 5);
    }

    scene = new Scene(pane);
  }

  @Override
  GridPane createBody() {
    return null;
  }

  /************************** Getter **********************/

  public Button getAddBooking() {
    return addBooking; 
  }

}
