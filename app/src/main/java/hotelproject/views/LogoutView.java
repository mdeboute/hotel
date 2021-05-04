package hotelproject.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class LogoutView extends View {

    // The scene's nodes
    private final Label title = new Label("Goodbye");
    private final Button login = new Button("Login page");
    private final Button close = new Button("Close");

    public LogoutView() {
        createScene();
    }

    @Override
    void createScene() {
        GridPane scenePane = new GridPane();
        VBox header = createHeader();
        scenePane.add(header, 0, 0);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);

        GridPane body = createBody();
        scenePane.add(body, 0, 1);
        GridPane.setHalignment(body, javafx.geometry.HPos.CENTER);

        scenePane.setStyle("-fx-background-color: #121212; -fx-alignment: center;");
        scene = new Scene(scenePane, 476, 610);
    }

    private VBox createHeader() {
        Image logoImg = new Image("file:assets/img/ui_dev_pack/main_menu/logo_hotel.png");
        ImageView logo = new ImageView(logoImg);
        logo.setPreserveRatio(true);
        logo.setFitWidth(100.0);
        logo.setFitHeight(125.0);

        title.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 50));
        title.setTextFill(Paint.valueOf("bb86fc"));

        VBox header = new VBox(logo, title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10.0, 10.0, 35.0, 10.0));
        header.setSpacing(5.5);

        return header;
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        pane.add(login, 0, 1);
        pane.add(close, 0, 2);

        return pane;
    }

    /*****************************Getters*********************************/

    public Button getLogin() {
        return login;
    }

    public Button getClose() {
        return close;
    }
}
