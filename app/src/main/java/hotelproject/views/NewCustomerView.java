package hotelproject.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NewCustomerView extends View {

    private final TextField cSSNum = new TextField();
    private final TextField cAddress = new TextField();
    private final TextField cFullName = new TextField();
    private final TextField cPhoneNum = new TextField();
    private final TextField cEmail = new TextField();

    private Button submit;
    private final String IDLE_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
    private final String HOVER_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";

    private Button cancel;
    private final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
    private final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";

    public NewCustomerView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("New customer", "Enter the new customer specifications");

        Label cSSNumL = new Label("New personal number (8 digits): ");
        pane.add(cSSNumL, 0, 1);
        pane.add(cSSNum, 1, 1);
        Label cAddressL = new Label("New address: ");
        pane.add(cAddressL, 0, 2);
        pane.add(cAddress, 1, 2);
        Label cFullNameL = new Label("New name: ");
        pane.add(cFullNameL, 0, 3);
        pane.add(cFullName, 1, 3);
        Label cPhoneNumL = new Label("New phone number (9 digits): ");
        pane.add(cPhoneNumL, 0, 4);
        pane.add(cPhoneNum, 1, 4);
        Label cEmailL = new Label("New email: ");
        pane.add(cEmailL, 0, 5);
        pane.add(cEmail, 1, 5);

        submit = createButton(35, IDLE_BUTTON_SUBMIT, HOVER_BUTTON_SUBMIT);;
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 7);

        cancel = createButton(35, IDLE_BUTTON_CANCEL, HOVER_BUTTON_CANCEL);
        GridPane.setHalignment(cancel, javafx.geometry.HPos.CENTER);
        pane.add(cancel, 1, 8);

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
