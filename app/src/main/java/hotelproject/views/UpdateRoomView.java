package hotelproject.views;

import hotelproject.controllers.db.HotelData;
import hotelproject.controllers.objects.RoomType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Create an update room view based on abstract class view.
 */
public class UpdateRoomView extends View {

    private final HotelData hdata;

    private final TextField numRoom = new TextField();
    private final TextField floor = new TextField();
    private final ComboBox<String> roomType = new ComboBox<>();
    private final String IDLE_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/idle_button_submit.png";
    private final String HOVER_BUTTON_SUBMIT = "file:assets/img/ui_dev_pack/general/hover_button_submit.png";
    private Button submit;
    private final String IDLE_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/idle_button_cancel.png";
    private final String HOVER_BUTTON_CANCEL = "file:assets/img/ui_dev_pack/general/hover_button_cancel.png";
    private Button cancel;

    /**
     * Constructor for update room view.
     *
     * @param hdata the original data of the room.
     */
    public UpdateRoomView(HotelData hdata) {
        this.hdata = hdata;
        createScene();
    }

    /**
     * Create the scene for the update room view.
     */
    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("Update room", "Enter room specifics to update");

        Label numRoomL = new Label("New room number: ");
        pane.add(numRoomL, 0, 1);
        pane.add(numRoom, 1, 1);

        numRoom.setPromptText("1-256"); // to set the hint text
        numRoom.getParent().requestFocus();

        // force the field to be numeric only
        numRoom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]")) {
                Platform.runLater(numRoom::clear);
            }
        });

        Label floorL = new Label("New floor: ");
        pane.add(floorL, 0, 2);
        pane.add(floor, 1, 2);

        floor.setPromptText("1-10"); // to set the hint text
        floor.getParent().requestFocus();

        // force the field to be numeric only
        floor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9]|10")) {
                Platform.runLater(floor::clear);
            }
        });

        Label type = new Label("Room type: ");
        pane.add(type, 0, 3);

        List<RoomType> roomTypes = hdata.getRoomTypes();
        for (RoomType value : roomTypes) {
            roomType.getItems().add(value.getT_name());
        }
        roomType.setValue("Single");

        pane.add(roomType, 1, 3);

        submit = createButton(35, IDLE_BUTTON_SUBMIT, HOVER_BUTTON_SUBMIT);
        GridPane.setHalignment(submit, javafx.geometry.HPos.CENTER);
        pane.add(submit, 1, 4);

        cancel = createButton(35, IDLE_BUTTON_CANCEL, HOVER_BUTTON_CANCEL);
        GridPane.setHalignment(cancel, javafx.geometry.HPos.CENTER);
        pane.add(cancel, 1, 5);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        paneTwo.getStyleClass().add("body-pane");

        scene = new Scene(paneTwo);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    /**
     * No body need to be created in this view.
     *
     * @return null
     */
    @Override
    GridPane createBody() {
        return null;
    }

    /**************************Getter**********************/

    public Button getSubmit() {
        return submit;
    }

    public Button getCancel() {
        return cancel; 
    }

    public TextField getFloor() {
        return floor;
    }

    public TextField getNumRoom() {
        return numRoom;
    }

    public ComboBox<String> getRoomType() {
        return roomType;
    }

}
