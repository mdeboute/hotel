package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Abstract class all views will extend to prevent code redundancy
 */
public abstract class View {

    // the view scene
    Scene scene;

    /**
     * @brief create the view scene with all it's nodes
     */
    abstract void createScene();

    /**
     * @param title    of the header
     * @param subtitle of the header
     * @return the header in the format VBox
     * @brief create the scene's header, called in createScene
     */
    protected VBox createHeader(String title, String subtitle) { //TODO: use createHeader in every view

        Label titleL = new Label(title);
        titleL.setStyle("-fx-font-weight: bold;");
        titleL.setFont(Font.font(18));

        Label subtitleL = new Label(subtitle);
        subtitleL.setFont(Font.font(14));

        VBox header = new VBox(titleL, subtitleL);
        //header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10.0, 10.0, 2.0, 10.0));
        header.setSpacing(5.5);

        return header;
    }

    abstract GridPane createBody();

    /**
     * @return the initial GridPane
     * @brief create the initial GridPane
     */
    protected GridPane createPane() {
        GridPane pane = new GridPane();
        //pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        return pane;
    }

    /**
     * Create a button with the image associated
     *
     * @param buttonImgView   path of the image associated to the button
     * @param button    the button concerned
     * @param minWidth  minimum width of the button
     * @param minHeight minimum height of the button
     * @return a StackPane with the result to add to the scene
     */
    protected StackPane createButton(ImageView buttonImgView, Button button, double minWidth, double minHeight) {
        StackPane stack = new StackPane();

        button.setStyle("-fx-opacity: 0;");
        button.setMinWidth(minWidth);
        button.setMaxHeight(minHeight);

        buttonImgView.setPreserveRatio(true);

        stack.getChildren().addAll(buttonImgView, button);
        stack.setAlignment(Pos.CENTER);
        return stack;
    }

    public Scene getScene() {
        return scene;
    }
}
