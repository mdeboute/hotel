package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This view aims for adding a new customer in the database.
 * All users have access to this view.
 * Adding a new customer needs social security number, address, name ,telephone number, email adress.
 */
public class NewCustomerView extends View {

    private final TextField cSSNum = new TextField();
    private final TextField cAddress = new TextField();
    private final TextField cFullName = new TextField();
    private final TextField cPhoneNum = new TextField();
    private final TextField cEmail = new TextField();
    private Button submit;
    private Button cancel;

    /**
     * No parameter is needed for this constructor.
     */
    public NewCustomerView() {
        createScene();
    }

    /**
     * Create scene for adding a new customer.
     */
    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("New customer", "Enter the new customer specifications");

        Label cSSNumL = changeLabelDesign(new Label("Social security number: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cSSNumL, 0, 1);
        pane.add(cSSNum, 1, 1);
        cSSNum.setPromptText("8 digits");
        Label cAddressL = changeLabelDesign(new Label("Address: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cAddressL, 0, 2);
        pane.add(cAddress, 1, 2);
        Label cFullNameL = changeLabelDesign(new Label("Name: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cFullNameL, 0, 3);
        pane.add(cFullName, 1, 3);
        Label cPhoneNumL = changeLabelDesign(new Label("Phone number: "), "file:assets/font/SF_Pro.ttf", 17, "white");
        pane.add(cPhoneNumL, 0, 4);
        pane.add(cPhoneNum, 1, 4);
        cPhoneNum.setPromptText("9 digits");
        Label cEmailL = changeLabelDesign(new Label("Email: "), "file:assets/font/SF_Pro.ttf", 17, "white");
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

    /**
     * VBox for containing submit and cancel buttons.
     *
     * @return an instance of VBox.
     */
    private VBox createFooter() {
        final String IDLE_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
        final String HOVER_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";
        final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
        final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";
        submit = createButton(35, IDLE_BUTTON_SUBMIT, HOVER_BUTTON_SUBMIT);
        cancel = createButton(35, IDLE_BUTTON_CANCEL, HOVER_BUTTON_CANCEL);

        VBox footer = new VBox(submit, cancel);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        footer.setSpacing(15);

        return footer;
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

    /************************** Getter **********************/
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
