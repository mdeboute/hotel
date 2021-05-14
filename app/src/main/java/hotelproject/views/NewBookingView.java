package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.time.LocalDate;

public class NewBookingView extends View {

    private final TextField numRoom = new TextField();
    private final CheckBox paidByCard = new CheckBox("Paid by card ?");
    private final DatePicker checkIn = new DatePicker();
    private final DatePicker checkOut = new DatePicker();
    private final TextField bookingFee = new TextField();
    private final TextField c_ss_number = new TextField();
    private final CheckBox isPaid = new CheckBox("Is paid ?");
    private final String idleSubmit = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
    private final String hoverSubmit = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";
    private Button submit;
    private Button cancel;

    public NewBookingView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("New booking", "Enter the new booking specifications");
        GridPane bodyPane = createBody();
        VBox footer = createFooter();

        pane.add(header, 0, 0);
        pane.add(bodyPane, 0, 1);
        pane.add(footer, 0, 2);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(footer, javafx.geometry.HPos.CENTER);

        pane.setStyle("-fx-background-color: #121212; -fx-alignment: center;");
        scene = new Scene(pane);
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        checkIn.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (checkOut.getValue() != null && checkOut.getValue().isBefore(checkIn.getValue())) {
                checkOut.setValue(checkIn.getValue().plusDays(1));
            }
        });
        checkOut.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (checkIn.getValue() != null && checkIn.getValue().isAfter(checkOut.getValue())) {
                checkIn.setValue(checkOut.getValue().minusDays(1));
            }
        });


        checkIn.valueProperty().addListener((observable, oldValue, newValue) -> {
            final Callback<DatePicker, DateCell> dayCellFactory =
                    new Callback<>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);

                                    if (item.isBefore(
                                            checkIn.getValue().plusDays(1))
                                    ) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    }
                                }
                            };
                        }
                    };
            checkOut.setDayCellFactory(dayCellFactory);
        });
        checkIn.setPromptText("FROM (DD/MM/YYYY)");
        checkOut.setPromptText("TO (DD/MM/YYYY)");

        Label bookingIDL = new Label("Booking number: ");
        //pane.add(bookingIDL, 0, 1);
        //pane.add(bookingIDL, 1, 1);
        Label numRoomL = changeLabelDesign(new Label("Room number: "), "file:assets/font/SF_Pro.ttf", 20, "white");
        pane.add(numRoomL, 0, 1);
        pane.add(numRoom, 1, 1);
        pane.add(checkIn, 0, 2);
        pane.add(checkOut, 1, 2);
        Label bookingFeeL = changeLabelDesign(new Label("Booking fee: "), "file:assets/font/SF_Pro.ttf", 20, "white");
        pane.add(bookingFeeL, 0, 3);
        pane.add(bookingFee, 1, 3);
        Label bookingCSSL = changeLabelDesign(new Label("Customer number: "), "file:assets/font/SF_Pro.ttf", 20, "white");
        pane.add(bookingCSSL, 0, 4);
        pane.add(c_ss_number, 1, 4);
        paidByCard.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 20));
        paidByCard.setTextFill(Paint.valueOf("white"));
        pane.add(paidByCard, 0, 5);
        isPaid.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 20));
        isPaid.setTextFill(Paint.valueOf("white"));
        pane.add(isPaid, 0, 6);

        //pane.getChildren().forEach(e -> e.setPadding(new Insets(15, 15, 25, 15)));
        return pane;
    }

    private VBox createFooter() {
        submit = createButton(35, idleSubmit, hoverSubmit);
        cancel = new Button("Cancel");

        VBox footer = new VBox(submit, cancel);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        footer.setSpacing(5.5);

        return footer;
    }

    public Button getSubmit() {
        return submit;
    }

    public TextField getNumRoom() {
        return numRoom;
    }

    public CheckBox getPaidByCard() {
        return paidByCard;
    }

    public DatePicker getCheckIn() {
        return checkIn;
    }

    public DatePicker getCheckOut() {
        return checkOut;
    }

    public TextField getBookingFee() {
        return bookingFee;
    }

    public CheckBox getIsPaid() {
        return isPaid;
    }

    public Button getCancel() {
        return cancel;
    }

    public TextField getC_ss_number() {
        return c_ss_number;
    }
}