package hotelproject;

import hotelproject.controllers.db.UserDB;
import hotelproject.controllers.objects.User;
import hotelproject.controllers.db.DatabaseManagement;
import hotelproject.views.Login;
import hotelproject.views.Logout;
import hotelproject.views.MainPage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;


public class HotelProject extends Application {

    static final String DB_URL = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // remember to change here for use
    static final String USER = "root";
    static final String PASSWORD = "root";

    Connection conn = DatabaseManagement.createConnection(DB_URL, USER, PASSWORD);
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
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void afterAuth(Stage primaryStage) {
        MainPage mainPage = new MainPage(connectedUser);
        Stage appStage = new Stage();

        //button handling here
        mainPage.getMyPageButton().setOnAction(e -> {
            //display user info page
        });

        mainPage.getLogoutButton().setOnAction(e -> {
            //display logout window
            logoutDisplay(appStage);
        });

        mainPage.getAddBookingButton().setOnAction(e -> {
            // display window with a form for adding a booking
        });

        mainPage.getViewBookingsButton().setOnAction(e -> {
            //display window with all bookings with search bar
        });

        mainPage.getUpdateButton().setOnAction(e -> {
            // display window to change or delete a booking --> shouldn't we display this button on the view bookings page ?
        });

        if (connectedUser.getU_is_admin() == 1) {
            mainPage.getUpdateUserButton().setOnAction(e -> {
                //display window to change the information of an user or delete one
            });
        }


        appStage.setScene(mainPage.getScene());
        appStage.setTitle("Hotel Manager");
        appStage.show();
        primaryStage.close();
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
        logoutStage.setTitle("Logout");
        logoutStage.show();
        appStage.close();
    }
}
