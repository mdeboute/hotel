package hotelproject.views;

import hotelproject.controllers.db.DatabaseManager;
import hotelproject.controllers.objects.Customer;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class UpdateBookingView extends View {

    private final DatabaseManager dbm;

    private final TextField newRoom = new TextField();
    private final CheckBox newPayment = new CheckBox("Paid by card ?");
    private final DatePicker newCheckIn = new DatePicker();
    private final DatePicker newCheckOut = new DatePicker();
    private final TextField newFee = new TextField();
    private final CheckBox newIsPaid = new CheckBox("Is paid ?");
    private final ComboBox<Integer> c_ss_number = new ComboBox<>();
    private Button submit;

    public UpdateBookingView(DatabaseManager dbm) {
        this.dbm = dbm;
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("Update booking", "Enter booking specifics to update");

        Label newRoomL = new Label("New room: ");
        pane.add(newRoomL, 0, 1);
        pane.add(newRoom, 1, 1);
        pane.add(newPayment, 0, 2);
        pane.add(newCheckIn, 0, 3);
        pane.add(newCheckOut, 1, 3);

        newCheckIn.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newCheckOut.getValue() != null && newCheckOut.getValue().isBefore(newCheckIn.getValue())) {
                newCheckOut.setValue(newCheckIn.getValue().plusDays(1));
            }
        });
        newCheckOut.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newCheckIn.getValue() != null && newCheckIn.getValue().isAfter(newCheckOut.getValue())) {
                newCheckIn.setValue(newCheckOut.getValue().minusDays(1));
            }
        });

        newCheckIn.valueProperty().addListener((observable, oldValue, newValue) -> {
            final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isBefore(newCheckIn.getValue().plusDays(1))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
            newCheckOut.setDayCellFactory(dayCellFactory);
        });
        newCheckIn.setPromptText("FROM (DD/MM/YYYY)");
        newCheckOut.setPromptText("TO (DD/MM/YYYY)");

        Label newFeeL = new Label("New fee: ");
        pane.add(newFeeL, 0, 5);
        pane.add(newFee, 1, 5);
        pane.add(newIsPaid, 0, 6);

        Label bookingCSSL = new Label("Customer number: ");
        pane.add(bookingCSSL, 0, 7);

        List<Customer> customers = dbm.cdb.findAllCustomers();
        for (Customer value : customers) {
            c_ss_number.getItems().add(value.getC_ss_number());
        }

        pane.add(c_ss_number, 1, 7);

        submit = new Button("Submit");
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 8);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        paneTwo.getStyleClass().add("body-pane");

        scene = new Scene(paneTwo);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    @Override
    GridPane createBody() {
        return null;
    }

    public Button getSubmit() {
        return submit;
    }

    public TextField getNewRoom() {
        return newRoom;
    }

    public CheckBox getNewPayment() {
        return newPayment;
    }

    public DatePicker getNewFromDate() {
        return newCheckIn;
    }

    public DatePicker getNewTillDate() {
        return newCheckOut;
    }

    public TextField getNewFee() {
        return newFee;
    }

    public CheckBox getNewIsPaid() {
        return newIsPaid;
    }

    public ComboBox<Integer> getC_ss_number() {
        return c_ss_number;
    }
}
