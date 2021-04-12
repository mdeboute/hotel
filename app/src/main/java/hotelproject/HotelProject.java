package hotelproject;

import hotelproject.controllers.User;
import hotelproject.views.Login;
import hotelproject.views.Logout;
import hotelproject.views.MainPage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class HotelProject extends Application {

    User connectedUser = new User();
    User userTest = new User("toto", "qwerty", 1);

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
        Login login = new Login(userTest);

        login.getTestLoginButton().setOnAction(e -> { //test if the user exist in the database and has correct password
            if (login.getUsername().getText().equals(userTest.getU_name()) && login.getPassword().getText().equals(userTest.getU_password())) {
                login.getResult().setText("Success !");
                connectedUser.setU_name(login.getUsername().getText());
                connectedUser.setU_password(login.getPassword().getText());
                connectedUser.setU_is_admin(userTest.getU_is_admin());
                afterAuth(primaryStage);
            }else{
                login.getResult().setText("Fail !");
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

        logoutPage.getClose().setOnAction(e -> {
            Platform.exit();
        });

        logoutStage.setScene(logoutPage.getScene());
        logoutStage.setTitle("Logout");
        logoutStage.show();
        appStage.close();
    }
}
