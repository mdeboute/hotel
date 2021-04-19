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

public class LoginView extends View {

    boolean onlyPwd;

    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Label result = new Label();
    private Label credentials = new Label("Please enter your password.");
    private Button testLogin = new Button("Login");

    public LoginView(boolean onlyPwd) {
        this.onlyPwd = onlyPwd;
        createScene();
    }

    @Override
    void createScene() {
        GridPane scenePane = new GridPane();
        VBox header = createHeader();
        scenePane.add(header,0,0);

        GridPane body = createBody();
        scenePane.add(body,0,1);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);

        scenePane.setStyle("-fx-background-color: #1e1e1e;");
        scene = new Scene(scenePane);
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
        header.setPadding(new Insets(10.0, 10.0, 2.0, 10.0));
        header.setSpacing(5.5);

        return header;
    }

    @Override
    GridPane createBody() {
        GridPane bodyPane = createPane();

        int pwdRow = 1;
        if (!onlyPwd) {
            bodyPane.add(username, 1, pwdRow);
            pwdRow = 2;

            StackPane stack = new StackPane();

            username.setStyle("-fx-background-color: #303030; -fx-text-inner-color: black;"); // #303030
            username.setAlignment(Pos.CENTER);
            username.setMaxWidth(300);
            username.setTranslateX(60);
            username.setTranslateY(20);

            password.setStyle("-fx-background-color: #303030; -fx-text-inner-color: black;");
            password.setAlignment(Pos.CENTER);
            password.setMaxWidth(300);
            password.setTranslateX(60);
            password.setTranslateY(55);

            VBox nodes = new VBox(username, password);

            Image loginImg = new Image("file:assets/img/ui_dev_pack/login_page/box_details.png");
            ImageView login = new ImageView(loginImg);
            login.setPreserveRatio(true);

            stack.getChildren().addAll(login, nodes);
            bodyPane.add(stack, 0, pwdRow);
        } else {
            bodyPane.add(credentials, 0, pwdRow-1);
            bodyPane.add(password, 0, pwdRow);
        }

        bodyPane.add(result, 0, pwdRow+1);
        bodyPane.add(testLogin, 1, pwdRow+1);
        GridPane.setHalignment(testLogin, HPos.RIGHT);

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
