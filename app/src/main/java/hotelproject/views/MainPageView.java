package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainPageView extends View {
    private final User user;
    //buttons
    private Button myPage = new Button("My Page");
    private Button logout = new Button("Logout");
    private Button addBooking = new Button("Add a booking");
    private Button viewRooms = new Button("View rooms");
    private Button viewBookings = new Button("View bookings");
    private Button update = new Button("Update bookings");
    private Button updateUser;

    public MainPageView(User user) {
        this.user = user;
        createScene();
    }

    @Override
    void createScene() {
        GridPane pane = createPane();

        if (user.getU_is_admin() == 1) {
            updateUser = new Button("Update a user");
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
