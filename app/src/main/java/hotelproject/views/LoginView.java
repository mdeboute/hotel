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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView extends View {

    boolean onlyPwd;

    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Label result = new Label();
    private Label credentials = new Label("Please enter your password.");
    private Button testLogin = new Button("");

    public LoginView(boolean onlyPwd) {
        this.onlyPwd = onlyPwd;
        createScene();
    }

    @Override
    void createScene() {
        GridPane scenePane = new GridPane();
        VBox header = createHeader();
        scenePane.add(header,0,0);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);

        GridPane body = createBody();
        scenePane.add(body,0,1);
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

        int pwdRow = 1;
        if (!onlyPwd) {
            pwdRow = 2;

            result.setTextFill(Color.RED);
            bodyPane.add(result, 0, pwdRow-1);

            StackPane stack = new StackPane();

            username.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            username.setFont(Font.font("Verdana",20));
            username.setAlignment(Pos.BASELINE_LEFT);
            username.setMaxWidth(300);
            username.setTranslateX(60);
            username.setTranslateY(10);

            password.setStyle("-fx-background-color: transparent; -fx-text-inner-color: white;");
            password.setFont(Font.font("Verdana",20));
            password.setAlignment(Pos.BASELINE_LEFT);
            password.setMaxWidth(300);
            password.setTranslateX(60);
            password.setTranslateY(30);

            VBox nodes = new VBox(username, password);

            Image loginImg = new Image("file:assets/img/ui_dev_pack/login_page/box_details.png");
            ImageView login = new ImageView(loginImg);
            login.setPreserveRatio(true);

            stack.getChildren().addAll(login, nodes);
            bodyPane.add(stack, 0, pwdRow);
            GridPane.setHalignment(stack, HPos.CENTER);
        } else {
            bodyPane.add(credentials, 0, pwdRow-1);
            bodyPane.add(password, 0, pwdRow);
        }

        //login button
        StackPane stack = new StackPane();

        testLogin.setStyle("-fx-opacity: 0;");
        testLogin.setMinWidth(400);
        testLogin.setMinHeight(55);

        Image buttonLoginImg = new Image("file:assets/img/ui_dev_pack/login_page/button_login.png");
        ImageView buttonLogin = new ImageView(buttonLoginImg);
        buttonLogin.setPreserveRatio(true);

        stack.getChildren().addAll(buttonLogin, testLogin);
        bodyPane.add(stack, 0, pwdRow+2);
        GridPane.setHalignment(stack, HPos.CENTER);

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

    public PasswordField getPassword() {
        return password;
    }

    public Label getResult() {
        return result;
    }
}
