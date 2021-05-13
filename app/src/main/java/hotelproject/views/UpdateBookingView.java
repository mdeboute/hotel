package hotelproject.views;

import java.time.LocalDate;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class UpdateBookingView extends View {

  private final TextField newRoom = new TextField();
  private final CheckBox newPayment = new CheckBox("Paid by card?");
  private final DatePicker newCheckIn = new DatePicker();
  private final DatePicker newCheckOut = new DatePicker();
  private final TextField newFee = new TextField();
  private final CheckBox newIsPaid = new CheckBox("Is paid?");

  private Button submit;

  public UpdateBookingView() {
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

    submit = new Button("Submit");
    GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
    pane.add(submit, 1, 7);

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

}
