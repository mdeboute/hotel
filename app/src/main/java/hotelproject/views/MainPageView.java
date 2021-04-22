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

public class MainPageView extends View {

    // The user connected to the application
    private final User user;

    // The scene's nodes
    private Button myPage = new Button("My Page");
    private Button addBooking = new Button("Add a booking");
    private Button viewRooms = new Button("View rooms");
    private Button viewBookings = new Button("View bookings");
    private Button update = new Button("Update bookings");
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
        scene = new Scene(scenePane, 1476, 914);
    }

    private HBox createHeader() {
        Image logoImg = new Image("file:assets/img/ui_dev_pack/main_menu/logo_hotel.png");
        ImageView logo = new ImageView(logoImg);
        logo.setPreserveRatio(true);
        logo.setFitWidth(150.0);
        logo.setFitHeight(175.0);

        StackPane stack = createButton("file:assets/img/ui_dev_pack/main_menu/button_user_details.png", myPage, 176, 47);

        HBox header = new HBox(logo, stack);
        return header;
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        pane.add(addBooking, 0, 1);
        pane.add(viewBookings, 0, 2);
        pane.add(update, 0, 3);
        if (user.getU_is_admin() == 1) {
            pane.add(viewUsers, 0, 4);
        }
        pane.add(viewRooms, 0, 5);

        return pane;
    }

    /***************************** Getters *********************************/

    public Button getMyPageButton() {
        return myPage;
    }

    public Button getAddBookingButton() {
        return addBooking;
    }

    public Button getViewBookingsButton() {
        return viewBookings;
    }

    public Button getUpdateButton() {
        return update;
    }

    public Button getViewUsersButton() {
        return viewUsers;
    }

    public Button getViewRooms() {
        return viewRooms;
    }
}
