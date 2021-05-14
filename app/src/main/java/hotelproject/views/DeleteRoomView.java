package hotelproject.views;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DeleteRoomView extends View {

    private Button submit;

    public DeleteRoomView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        VBox header = createHeader("Delete selected room ?", "Confirm your action !");

        pane.setAlignment(Pos.CENTER);

        submit = new Button("Yes");
        GridPane.setHalignment(submit, HPos.CENTER);
        pane.add(submit, 0, 2);

        GridPane paneTwo = new GridPane();
        paneTwo.add(header, 0, 0);
        paneTwo.add(pane, 0, 1);
        GridPane.setHalignment(header, HPos.CENTER);

        scene = new Scene(paneTwo);
    }

    @Override
    GridPane createBody() {
        return null;
    }

    public Button getSubmit() {
        return submit;
    }

}