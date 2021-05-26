package hotelproject.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Create view for deleting a room.
 */
public class DeleteCustomerView extends View {

    private Button submit;
    private Button cancel;

    /**
     * No parameter is needed for this constructor.
     */
    public DeleteCustomerView() {
        createScene();
    }

    /**
     * Create scene for this view.
     */
    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("Delete selected customer?", "Confirm your action!");

        pane.setAlignment(Pos.CENTER);

        GridPane paneTwo = new GridPane();
        paneTwo.getStyleClass().add("body-pane");
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, HPos.CENTER);

        paneTwo.add(createFooter(), 0, 2);

        scene = new Scene(paneTwo);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    /**
     * Create VBox to contain submit and cancel buttons.
     *
     * @return an instance of VBox containing these buttons.
     */
    private VBox createFooter() {
        final String IDLE_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
        final String HOVER_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";
        final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
        final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";

        submit = createButton(35, IDLE_SUBMIT, HOVER_SUBMIT);
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

    /***************************** Getters *********************************/
    public Button getSubmit() {
        return submit;
    }

    public Button getCancel() {
        return cancel;
    }

}