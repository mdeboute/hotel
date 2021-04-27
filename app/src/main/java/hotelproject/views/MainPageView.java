package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainPageView extends View {

    // The user connected to the application
    private final User user;

    // The scene's nodes
    private Button myPage = new Button("My Page");
    private Button viewRooms = new Button("View rooms");
    private Button viewBookings = new Button("View bookings");
    private Button viewCustomers = new Button("View customers");
    private Button viewUsers = new Button("View users");

    public MainPageView(User user) {
        this.user = user;
        createScene();
    }

    @Override
    void createScene() {
        if (user.getU_is_admin() == 1) {
            viewUsers = new Button("View users");
        }

        GridPane scenePane = createPane();

        HBox header = createHeader();
        scenePane.add(header,0,0);

        GridPane body = createBody();
        scenePane.add(body,0,1);
        //GridPane.setHalignment(body, javafx.geometry.HPos.CENTER);

        scenePane.setStyle("-fx-background-color: #1e1e1e; -fx-alignment: center;");
        scene = new Scene(scenePane, 775, 658);
    }

    private HBox createHeader() {
        Image logoImg = new Image("file:assets/img/ui_dev_pack/main_menu/logo_hotel.png");
        ImageView logo = new ImageView(logoImg);
        logo.setPreserveRatio(true);
        logo.setFitWidth(150.0);
        logo.setFitHeight(175.0);

        StackPane stack = createButton("file:assets/img/ui_dev_pack/main_menu/idle_button_user_settings.png", myPage, 176, 47);
        stack.setAlignment(Pos.BOTTOM_RIGHT);

        HBox header = new HBox(logo, stack);
        header.setAlignment(Pos.CENTER);

        header.getChildren().get(0).setTranslateX(100);
        header.getChildren().get(1).setTranslateX(195);

        return header;
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        StackPane roomsStack = createButton("file:assets/img/ui_dev_pack/main_menu/idle_button_rooms.png", viewRooms, 342, 191);
        StackPane customersStack = createButton("file:assets/img/ui_dev_pack/main_menu/idle_button_customers.png", viewCustomers, 342, 191);
        HBox roomsCustomersButtons = new HBox(roomsStack, customersStack);
        roomsCustomersButtons.setSpacing(30);

        StackPane bookingsStack = createButton("file:assets/img/ui_dev_pack/main_menu/idle_button_bookings.png", viewBookings, 342, 191);
        HBox bookingsUsersButtons = new HBox(bookingsStack);
        if (user.getU_is_admin() == 1) {
            StackPane usersStack = createButton("file:assets/img/ui_dev_pack/main_menu/idle_button_users.png", viewUsers, 342, 191);
            bookingsUsersButtons.getChildren().add(usersStack);
        }
        bookingsUsersButtons.setSpacing(30);
        bookingsUsersButtons.setAlignment(Pos.CENTER);

        VBox menuButtons = new VBox(roomsCustomersButtons, bookingsUsersButtons);
        menuButtons.setSpacing(30);
        pane.add(menuButtons, 0, 2);

        return pane;
    }

    /***************************** Getters *********************************/

    public Button getMyPageButton() {
        return myPage;
    }

    public Button getViewBookingsButton() {
        return viewBookings;
    }

    public Button getViewCustomers() {
        return viewCustomers;
    }

    public Button getViewUsersButton() {
        return viewUsers;
    }

    public Button getViewRooms() {
        return viewRooms;
    }
}
