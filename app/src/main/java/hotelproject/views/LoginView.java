package hotelproject.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class LoginView extends View {

    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final Label credentials = new Label("Please enter your password");
    private final Label result = new Label();
    private final String idlePathTestLogin = "file:assets/img/ui_dev_pack/login_page/idle_button_login.png";
    private final String hoverPathTestLogin = "file:assets/img/ui_dev_pack/login_page/hover_button_login.png";
    boolean onlyPwd;
    private Button testLogin;

    public LoginView(boolean onlyPwd) {
        this.onlyPwd = onlyPwd;
        createScene();
    }

    @Override
    void createScene() {
        GridPane scenePane = new GridPane();
        VBox header = createHeader();
        scenePane.add(header, 0, 0);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        if (onlyPwd) {
            scenePane.setVgap(60);
        }

        GridPane body = createBody();
        scenePane.add(body, 0, 1);
        GridPane.setHalignment(body, javafx.geometry.HPos.CENTER);

        scenePane.setStyle("-fx-background-color: #1e1e1e; -fx-alignment: center;");
        scene = new Scene(scenePane, 476, 610);
    }

    private VBox createHeader() {
        Image logoImg = new Image("file:assets/img/ui_dev_pack/main_menu/logo_hotel.png");
        ImageView logo = new ImageView(logoImg);
        logo.setPreserveRatio(true);
        logo.setFitWidth(100.0);
        logo.setFitHeight(125.0);

        Image welcomeImg = new Image("file:assets/img/ui_dev_pack/login_page/text_welcome.png");
        ImageView welcome = new ImageView(welcomeImg);
        welcome.setPreserveRatio(true);

        VBox header = new VBox(logo, welcome);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10.0, 10.0, 35.0, 10.0));
        header.setSpacing(5.5);

        return header;
    }

    @Override
    GridPane createBody() {
        GridPane bodyPane = createPane();

        result.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 17));
        result.setTextFill(Paint.valueOf("cf6679"));

        password.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
        password.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 18));
        password.setAlignment(Pos.BASELINE_LEFT);
        password.setMaxWidth(300);
        password.setTranslateX(60);
        password.setTranslateY(1);
        password.setPromptText("Password");

        if (!onlyPwd) {

            bodyPane.add(result, 0, 0);

            StackPane stack = new StackPane();

            username.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            username.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 18));
            username.setAlignment(Pos.BASELINE_LEFT);
            username.setMaxWidth(300);
            username.setTranslateX(60);
            username.setTranslateY(11);
            username.setPromptText("Username");

            password.setTranslateY(37.5);

            VBox nodes = new VBox(username, password);

            Image loginImg = new Image("file:assets/img/ui_dev_pack/login_page/box_details.png");
            ImageView login = new ImageView(loginImg);
            login.setPreserveRatio(true);

            stack.getChildren().addAll(login, nodes);
            bodyPane.add(stack, 0, 1);
            GridPane.setHalignment(stack, HPos.CENTER);
        } else {
            password.setTranslateY(1);
            bodyPane.add(result, 0, 0);

            Image loginImg = new Image("file:assets/img/ui_dev_pack/login_page/box_password.png");
            ImageView login = new ImageView(loginImg);
            login.setPreserveRatio(true);

            bodyPane.add(login, 0, 1);
            bodyPane.add(password, 0, 1);
        }

        //login button
        testLogin = createButton(55, idlePathTestLogin, hoverPathTestLogin);
        bodyPane.add(testLogin, 0, 2);

        return bodyPane;
    }

    public Label getCredentials() {
        return credentials;
    }

    public Button getTestLoginButton() {
        return testLogin;
    }

    public TextField getUsername() {
        return username;
    }

    public String getUsernameString() {
        return username.getText();
    }

    public PasswordField getPassword() {
        return password;
    }

    public String getPasswordString() {
        return password.getText();
    }

    public Label getResult() {
        return result;
    }
}
