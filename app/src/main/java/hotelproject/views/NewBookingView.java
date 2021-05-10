package hotelproject.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NewBookingView extends View {

    private final TextField numRoom = new TextField();
    private final CheckBox paidByCard = new CheckBox("Paid by card?");
    private final DatePicker checkIn = new DatePicker();
    private final DatePicker checkOut = new DatePicker();
    private final TextField bookingFee = new TextField();
    private final CheckBox isPaid = new CheckBox("Is paid?");
    private Button submit;
    private Button cancel;

    public NewBookingView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("New booking", "Enter the new booking specifications");

        Label numRoomL = new Label("Room number : ");
        pane.add(numRoomL, 0, 1);
        pane.add(numRoom, 1, 1); 
        pane.add(checkIn, 0, 2);
        pane.add(checkOut, 1, 2);
        Label bookingFeeL = new Label("Booking fee : ");
        pane.add(bookingFeeL, 0, 3);
        pane.add(bookingFee, 1, 3);
        pane.add(paidByCard, 0, 4);
        pane.add(isPaid, 0, 5);

        submit = new Button("Submit");
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 7);

        cancel = new Button("Cancel");
        GridPane.setHalignment(cancel, javafx.geometry.HPos.CENTER);
        pane.add(cancel, 1, 8);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);

        scene = new Scene(paneTwo);
    }

    @Override
    GridPane createBody() {
        return null;
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

}