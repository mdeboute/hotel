package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainPageView {
    private final User user;
    //buttons
    Button myPage;
    Button logout;
    Button addBooking;
    Button viewRooms;
    Button viewBookings;
    Button update;
    Button updateUser;
    private Scene scene;

    public MainPageView(User user) {
        this.user = user;
        createScene();
    }

    private void createScene() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.4));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        myPage = new Button("My Page");
        logout = new Button("Logout");
        addBooking = new Button("Add a booking");
        viewRooms = new Button("View rooms");
        viewBookings = new Button("View bookings");
        update = new Button("Update bookings");

        if (user.getU_is_admin() == 1) {
            updateUser = new Button("Update an user");
        }

        pane.add(myPage, 1, 0);
        pane.add(logout, 2, 0);
        pane.add(addBooking, 0, 1);
        pane.add(viewBookings, 0, 2);
        pane.add(update, 0, 3);
        if (user.getU_is_admin() == 1) {
            pane.add(updateUser, 0, 4);
        }
        pane.add(viewRooms, 0, 5);

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getMyPageButton() {
        return myPage;
    }

    public Button getLogoutButton() {
        return logout;
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

    public Button getUpdateUserButton() {
        return updateUser;
    }

    public Button getViewRooms() {
        return viewRooms;
    }
}
