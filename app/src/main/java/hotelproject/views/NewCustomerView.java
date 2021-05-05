package hotelproject.views;

import hotelproject.controllers.db.DatabaseManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NewCustomerView extends View {

  private final DatabaseManager dbm;

  private final TextField cSSNum = new TextField();
  private final TextField cAddress = new TextField();
  private final TextField cFullName = new TextField();
  private final TextField cPhoneNum = new TextField();
  private final TextField cEmail = new TextField();

  private Button submit;
  private Button cancel;

  public NewCustomerView(DatabaseManager dbm) {
    this.dbm = dbm;
    createScene();
  }

  @Override
  void createScene() {
    GridPane pane = createPane();

    VBox header = createHeader("New customer", "Enter the new customer specifications");

    Label cSSNumL = new Label("New personal number (8 digits) : ");
    pane.add(cSSNumL, 0, 1);
    pane.add(cSSNum, 1, 1);
    Label cAddressL = new Label("New address : ");
    pane.add(cAddressL, 0, 2);
    pane.add(cAddress, 1, 2);
    Label cFullNameL = new Label("New name : ");
    pane.add(cFullNameL, 0, 3);
    pane.add(cFullName, 1, 3);
    Label cPhoneNumL = new Label("New phone number (9 digits) : ");
    pane.add(cPhoneNumL, 0, 4);
    pane.add(cPhoneNum, 1, 4);
    Label cEmailL = new Label("New email : ");
    pane.add(cEmailL, 0, 5);
    pane.add(cEmail, 1, 5);

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

  public TextField getCSSNum() {
    return cSSNum;
  }

  public TextField getCAddress() {
    return cAddress;
  }

  public TextField getCFullName() {
    return cFullName;
  }

  public TextField getCPhoneNum() {
    return cPhoneNum;
  }

  public TextField getCEmail() {
    return cEmail;
  }
  
  public Button getCancel() {
    return cancel;
  }
}
