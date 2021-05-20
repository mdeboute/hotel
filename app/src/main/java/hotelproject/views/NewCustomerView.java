package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private final String IDLE_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
    private final String HOVER_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";
    private final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
    private final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";
    private Button submit;
    private Button cancel;

    public NewCustomerView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("New customer", "Enter the new customer specifications");

        Label cSSNumL = changeLabelDesign(new Label("New personal number (8 digits): "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cSSNumL, 0, 1);
        pane.add(cSSNum, 1, 1);
        Label cAddressL = changeLabelDesign(new Label("New address: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cAddressL, 0, 2);
        pane.add(cAddress, 1, 2);
        Label cFullNameL = changeLabelDesign(new Label("New name: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cFullNameL, 0, 3);
        pane.add(cFullName, 1, 3);
        Label cPhoneNumL = changeLabelDesign(new Label("New phone number (9 digits): "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cPhoneNumL, 0, 4);
        pane.add(cPhoneNum, 1, 4);
        Label cEmailL = changeLabelDesign(new Label("New email: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cEmailL, 0, 5);
        pane.add(cEmail, 1, 5);

        GridPane paneTwo = new GridPane();
        paneTwo.getStyleClass().add("body-pane");

        paneTwo.add(header, 0, 0);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);

        pane.setVgap(15);
        paneTwo.add(pane, 0, 1);

        VBox footer = createFooter();
        paneTwo.add(footer, 0, 2);

        scene = new Scene(paneTwo);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    private VBox createFooter() {
        submit = createButton(35, IDLE_BUTTON_SUBMIT, HOVER_BUTTON_SUBMIT);
        cancel = createButton(35, IDLE_BUTTON_CANCEL, HOVER_BUTTON_CANCEL);

        VBox footer = new VBox(submit, cancel);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        footer.setSpacing(15);

        return footer;
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
