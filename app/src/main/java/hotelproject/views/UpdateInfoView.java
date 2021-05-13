package hotelproject.views;

import hotelproject.controllers.utils.Default;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class UpdateInfoView extends View {

    private final Change change;
    private final Label output = new Label();
    private final Label Lbl1;
    private final Label Lbl2;
    private TextField firstUName;
    private TextField secondUName;
    private PasswordField firstPwd;
    private PasswordField secondPwd;
    private final Button save = new Button("Save");

    public UpdateInfoView(Change change) {
        this.change = change;

        // The scene's nodes
        String chOption;
        if (change == Change.USERNAME) {
            chOption = "username";
            firstUName = new TextField();
            firstUName.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            firstUName.setFont(Default.getSFPro(18));
            secondUName = new TextField();
            secondUName.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            secondUName.setFont(Default.getSFPro(18));
        } else {
            chOption = "password";
            firstPwd = new PasswordField();
            firstPwd.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            firstPwd.setFont(Default.getSFPro(18));
            secondPwd = new PasswordField();
            secondPwd.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            secondPwd.setFont(Default.getSFPro(18));
        }

        Lbl1 = new Label(String.format("Please enter your new %s: ", chOption));
        Lbl2 = new Label(String.format("Please enter your new %s again: ", chOption));
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createBody();
        GridPane.setHalignment(pane, javafx.geometry.HPos.CENTER);

        pane.setStyle("-fx-background-color: #1e1e1e; -fx-alignment: center;");
        scene = new Scene(pane, 500, 300);
    }

    private void createTextFields(Node field1, Node field2, GridPane pane) {
        Lbl1.setFont(Default.getSFPro(20));
        Lbl1.setTextFill(Paint.valueOf("white"));

        Lbl2.setFont(Default.getSFPro(20));
        Lbl2.setTextFill(Paint.valueOf("white"));

        pane.add(Lbl1, 0, 0);
        pane.add(field1, 0, 1);

        pane.add(Lbl2, 0, 3);
        pane.add(field2, 0, 4);
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        //font and color of labels
        output.setFont(Default.getSFPro(17));
        output.setTextFill(Paint.valueOf("cf6679"));

        if (change == Change.USERNAME) {
            createTextFields(firstUName, secondUName, pane);
        } else {
            createTextFields(firstPwd, secondPwd, pane);
        }

        pane.add(save, 1, 9);
        pane.add(output, 0, 10);

        return pane;
    }

    public enum Change { USERNAME, PASSWORD; }

    /*****************************Getters*********************************/

    public Button getSave() {
        return save;
    }

    public Label getOutput() {
        return output;
    }

    public TextField getFirstUName() {
        return firstUName;
    }

    public TextField getSecondUName() {
        return secondUName;
    }

    public PasswordField getFirstPwd() {
        return firstPwd;
    }

    public PasswordField getSecondPwd() {
        return secondPwd;
    }

    /***************************Setter*******************************/

    public void setOutput(String outputTxt) {
        output.setText(outputTxt);
    }
}
