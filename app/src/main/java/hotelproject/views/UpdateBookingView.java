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

/**
 * Create update booking information view.
 */
public class UpdateBookingView extends View {

    private final DatabaseManager dbm;

    private final TextField newRoom = new TextField();
    private final CheckBox newPayment = new CheckBox("Paid by card ?");
    private final DatePicker newCheckIn = new DatePicker();
    private final DatePicker newCheckOut = new DatePicker();
    private final TextField newFee = new TextField();
    private final CheckBox newIsPaid = new CheckBox("Is paid ?");
    private final ComboBox<String> customer = new ComboBox<>();
    private final String IDLE_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
    private final String HOVER_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";
    private Button submit;
    private final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
    private final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";
    private Button cancel;

    /**
     * Constructor for this view.
     *
     * @param dbm the instance of DatabaseManager for creating connection.
     */
    public UpdateBookingView(DatabaseManager dbm) {
        this.dbm = dbm;
        createScene();
    }

    /**
     * Create scene for update booking view.
     */
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

        Label newFeeL = changeLabelDesign(new Label("New fee: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(newFeeL, 0, 5);
        pane.add(newFee, 1, 5);
        pane.add(newIsPaid, 0, 6);

        Label bookingCSSL = changeLabelDesign(new Label("Customer number: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(bookingCSSL, 0, 7);

        List<Customer> customers = dbm.cdb.findAllCustomers();
        for (Customer value : customers) {
            customer.getItems().add(value.getC_full_name() + " - " + value.getC_ss_number());
        }

        pane.add(customer, 1, 7);

        submit = createButton(35, IDLE_BUTTON_SUBMIT, HOVER_BUTTON_SUBMIT);
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 8);

        cancel = createButton(35, IDLE_BUTTON_CANCEL, HOVER_BUTTON_CANCEL);
        GridPane.setHalignment(cancel, javafx.geometry.HPos.CENTER);
        pane.add(cancel, 1, 9);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        pane.setVgap(15);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        paneTwo.getStyleClass().add("body-pane");

        scene = new Scene(paneTwo);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    /**
     * No body pane need to be created in this view.
     *
     * @return null
     */
    @Override
    GridPane createBody() {
        return null;
    }

    /*****************************Getters*********************************/
    public Button getSubmit() {
        return submit;
    }

    public Button getCancel() {
        return cancel; 
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

    public ComboBox<String> getCustomer() {
        return customer;
    }
}
