package hotelproject;

import hotelproject.controllers.db.RoomsDB;
import hotelproject.controllers.db.UserDB;
import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.User;
import hotelproject.controllers.db.DatabaseManagement;
import hotelproject.views.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;


public class HotelProject extends Application {

    Connection conn = DatabaseManagement.createConnection();
    User connectedUser = new User();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loginPage(primaryStage);
    }

    private void loginPage(Stage primaryStage) {
        Login login = new Login();

        login.getTestLoginButton().setOnAction(e -> { //test if the user exist in the database and has correct password
            User userTest = new User(login.getUsername().getText(), login.getPassword().getText());
            try {
                if (UserDB.userExists(conn, userTest)) {
                    login.getResult().setText("Success !");
                    connectedUser.setU_name(login.getUsername().getText());
                    connectedUser.setU_password(login.getPassword().getText());
                    connectedUser.setU_is_admin(UserDB.getU_is_admin(conn, userTest));
                    afterAuth(primaryStage);
                }else{
                    login.getResult().setText("Fail !");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        primaryStage.setScene(login.getScene());
        primaryStage.setTitle("Hotel Manager - Login");
        primaryStage.show();
    }

    private void afterAuth(Stage primaryStage) {
        MainPage mainPage = new MainPage(connectedUser);
        Stage appStage = new Stage();

        //handling buttons here
        mainPage.getMyPageButton().setOnAction(e -> {
            //display user info page
        });

        mainPage.getLogoutButton().setOnAction(e -> logoutDisplay(appStage));

        mainPage.getAddBookingButton().setOnAction(e -> {
            // display window with a form for adding a booking
        });

        mainPage.getViewBookingsButton().setOnAction(e -> {
            //display window with all bookings with search bar
        });

        mainPage.getUpdateButton().setOnAction(e -> {
            // display window to change or delete a booking --> shouldn't we display this button on the view bookings page ?
        });

        mainPage.getViewRooms().setOnAction(e -> {
            roomsDisplay(appStage);

        });

        if (connectedUser.getU_is_admin() == 1) {
            mainPage.getUpdateUserButton().setOnAction(e -> {
                //display window to change the information of an user or delete one
            });
        }

        appStage.setScene(mainPage.getScene());
        appStage.setTitle("Hotel Manager - Menu");
        appStage.show();
        primaryStage.close();
    }

    private void newRoomDisplay(Stage appStage, Stage roomsStage) {
        NewRoom newRoomPage = new NewRoom();
        Stage newRoomStage = new Stage();

        //handle buttons
        newRoomPage.getSubmit().setOnAction(e -> {
            int roomNb = Integer.parseInt(newRoomPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(newRoomPage.getFloor().getText());
            String roomType = newRoomPage.getRoomType().getText();
            int roomBooked = 0;
            if (newRoomPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room newRoom = new Room(roomNb, roomFloor, roomType, roomBooked);
            RoomsDB.addRoom(conn, newRoom);

            newRoomStage.close();
        });

        newRoomStage.setScene(newRoomPage.getScene());
        newRoomStage.setTitle("Hotel Manager - New Room");
        newRoomStage.show();
    }

    private void roomsDisplay(Stage appStage) {
        Rooms roomsPage = new Rooms();
        Stage roomsStage = new Stage();

        //add display of rooms --> add parameter in constructor with rooms map ?

        roomsPage.getAddRoom().setOnAction(e -> {
            newRoomDisplay(appStage, roomsStage);
        });

        roomsStage.setScene(roomsPage.getScene());
        roomsStage.setTitle("Hotel Manager - Rooms");
        roomsStage.show();
    }

    private void logoutDisplay(Stage appStage) {
        Logout logoutPage = new Logout();
        Stage logoutStage = new Stage();

        logoutPage.getLogin().setOnAction(e -> {
            //display again login window
            Stage loginStage = new Stage();
            loginPage(loginStage);
            logoutStage.close();
        });

        logoutPage.getClose().setOnAction(e -> Platform.exit());

        logoutStage.setScene(logoutPage.getScene());
        logoutStage.setTitle("Hotel Manager - Logout");
        logoutStage.show();
        appStage.close();
    }
}
