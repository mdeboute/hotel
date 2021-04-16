package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class  View {
    Scene scene;

    abstract void createScene();

     protected VBox createHeader(String title, String subtitle) { //TODO: use createHeader in every view
        Label titleL = new Label(title);
        titleL.setStyle("-fx-font-weight: bold;");
        titleL.setFont(Font.font(18));

        Label subtitleL = new Label(subtitle);
        subtitleL.setFont(Font.font(14));

        VBox header = new VBox(titleL, subtitleL);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10.0, 10.0, 2.0, 10.0));
        header.setSpacing(5.5);

        return header;
    }

    public Scene getScene() {
        return scene;
    }
}
