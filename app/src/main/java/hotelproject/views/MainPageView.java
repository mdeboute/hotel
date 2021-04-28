package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainPageView extends View {

    // The user connected to the application
    private final User user;

    // The scene's nodes
    private ImageView imgMyPage = new ImageView(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_user_settings.png"));
    private Button myPage = new Button("My Page");
    private ImageView imgRooms = new ImageView(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_rooms.png"));
    private Button viewRooms = new Button("View rooms");
    private ImageView imgBookings = new ImageView(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_bookings.png"));
    private Button viewBookings = new Button("View bookings");
    private ImageView imgCustomers = new ImageView(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_customers.png"));
    private Button viewCustomers = new Button("View customers");
    private ImageView imgUsers = new ImageView(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_users.png"));
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

        StackPane stack = createButton(imgMyPage, myPage, 176, 47);
        stack.setAlignment(Pos.BOTTOM_RIGHT);

        HBox header = new HBox(logo, stack);
        header.setAlignment(Pos.CENTER);

        header.getChildren().get(0).setTranslateX(100);
        header.getChildren().get(1).setTranslateX(194);

        return header;
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        StackPane roomsStack = createButton(imgRooms, viewRooms, 342, 191);
        StackPane customersStack = createButton(imgCustomers, viewCustomers, 342, 191);
        HBox roomsCustomersButtons = new HBox(roomsStack, customersStack);
        roomsCustomersButtons.setSpacing(30);

        StackPane bookingsStack = createButton(imgBookings, viewBookings, 342, 191);
        HBox bookingsUsersButtons = new HBox(bookingsStack);
        if (user.getU_is_admin() == 1) {
            StackPane usersStack = createButton(imgUsers, viewUsers, 342, 191);
            bookingsUsersButtons.getChildren().add(usersStack);
        }
        bookingsUsersButtons.setSpacing(30);
        bookingsUsersButtons.setAlignment(Pos.CENTER);

        VBox menuButtons = new VBox(roomsCustomersButtons, bookingsUsersButtons);
        menuButtons.setSpacing(30);
        pane.add(menuButtons, 0, 2);

        // mouse hovering buttons

        //rooms button
        viewRooms.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            imgRooms.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/hover_button_rooms.png"));
        });
        viewRooms.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            imgRooms.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_rooms.png"));
        });

        //customers button
        viewCustomers.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            imgCustomers.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/hover_button_customers.png"));
        });
        viewCustomers.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            imgCustomers.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_customers.png"));
        });

        //bookings button
        viewBookings.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            imgBookings.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/hover_button_bookings.png"));
        });
        viewBookings.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            imgBookings.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_bookings.png"));
        });

        //users button
        viewUsers.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            imgUsers.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/hover_button_users.png"));
        });
        viewUsers.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            imgUsers.setImage(new Image("file:assets/img/ui_dev_pack/main_menu/idle_button_users.png"));
        });

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

    public ImageView getImgMyPage() {
        return imgMyPage;
    }

    public ImageView getImgBookings() {
        return imgBookings;
    }

    public ImageView getImgRooms() {
        return imgRooms;
    }

    public ImageView getImgCustomers() {
        return imgCustomers;
    }

    public ImageView getImgUsers() {
        return imgUsers;
    }
}
