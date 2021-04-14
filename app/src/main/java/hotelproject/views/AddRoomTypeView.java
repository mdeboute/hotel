package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddRoomTypeView {

    Scene scene;

    private TextField name = new TextField();
    private TextField nbBeds = new TextField();
    private TextField roomSize = new TextField();
    private final CheckBox hasView = new CheckBox("with view");
    private final CheckBox hasKitchen = new CheckBox("with kitchen");
    private final CheckBox hasBathroom = new CheckBox("with bathroom");
    private final CheckBox hasWorksp = new CheckBox("with a workspace");
    private final CheckBox hasTv = new CheckBox("with TV");
    private final CheckBox hasCoffeeMkr = new CheckBox("with a coffee maker");
    private Button submit = new Button("Submit");

    public AddRoomTypeView() {
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        //labels and text fields
        Label title = new Label("Please enter the new room type parameters : ");
        pane.add(title, 0, 0);
        Label nameL = new Label("Type name : ");
        pane.add(nameL, 0, 2);
        pane.add(name, 1, 2);
        Label nbBedsL = new Label("Number of beds : ");
        pane.add(nbBedsL, 0, 3);
        pane.add(nbBeds, 1, 3);
        Label roomSizeL = new Label("Room size : ");
        pane.add(roomSizeL, 0, 4);
        pane.add(roomSize, 1, 4);
        //checkboxes
        pane.add(hasView, 0, 5);
        pane.add(hasKitchen, 0, 6);
        pane.add(hasBathroom, 0, 7);
        pane.add(hasWorksp, 0, 8);
        pane.add(hasTv, 0, 9);
        pane.add(hasCoffeeMkr, 0, 10);

        pane.add(submit, 2, 12);

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getSubmit() {
        return submit;
    }

    public int getHasBathroom() {
        int isSelected = 0;
        if (hasBathroom.isSelected()) {
            isSelected = 1;
        }
        return isSelected;
    }

    public int getHasCoffeeMkr() {
        int isSelected = 0;
        if (hasCoffeeMkr.isSelected()) {
            isSelected = 1;
        }
        return isSelected;
    }

    public int getHasKitchen() {
        int isSelected = 0;
        if (hasKitchen.isSelected()) {
            isSelected = 1;
        }
        return isSelected;
    }

    public int getHasTv() {
        int isSelected = 0;
        if (hasTv.isSelected()) {
            isSelected = 1;
        }
        return isSelected;
    }

    public TextField getName() {
        return name;
    }

    public int getHasView() {
        int isSelected = 0;
        if (hasView.isSelected()) {
            isSelected = 1;
        }
        return isSelected;
    }

    public int getHasWorksp() {
        int isSelected = 0;
        if (hasWorksp.isSelected()) {
            isSelected = 1;
        }
        return isSelected;
    }

    public TextField getNbBeds() {
        return nbBeds;
    }

    public TextField getRoomSize() {
        return roomSize;
    }
}
