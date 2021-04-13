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
        LoginView loginView = new LoginView();

        loginView.getTestLoginButton().setOnAction(e -> { //test if the user exist in the database and has correct password
            User userTest = new User(loginView.getUsername().getText(), loginView.getPassword().getText());
            try {
                if (UserDB.userExists(conn, userTest)) {
                    loginView.getResult().setText("Success !");
                    connectedUser.setU_name(loginView.getUsername().getText());
                    connectedUser.setU_password(loginView.getPassword().getText());
                    connectedUser.setU_is_admin(UserDB.getU_is_admin(conn, userTest));
                    afterAuth(primaryStage);
                }else{
                    loginView.getResult().setText("Fail !");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        primaryStage.setScene(loginView.getScene());
        primaryStage.setTitle("Hotel Manager - Login");
        primaryStage.show();
    }

    private void afterAuth(Stage primaryStage) {
        MainPageView mainPageView = new MainPageView(connectedUser);
        Stage appStage = new Stage();

        //handling buttons here
        mainPageView.getMyPageButton().setOnAction(e -> {
            //display user info page
            myPageDisplay(appStage);
        });

        mainPageView.getLogoutButton().setOnAction(e -> logoutDisplay(appStage));

        mainPageView.getAddBookingButton().setOnAction(e -> {
            // display window with a form for adding a booking
        });

        mainPageView.getViewBookingsButton().setOnAction(e -> {
            //display window with all bookings with search bar
        });

        mainPageView.getUpdateButton().setOnAction(e -> {
            // display window to change or delete a booking --> shouldn't we display this button on the view bookings page ?
        });

        mainPageView.getViewRooms().setOnAction(e -> roomsDisplay(appStage));

        if (connectedUser.getU_is_admin() == 1) {
            mainPageView.getUpdateUserButton().setOnAction(e -> {
                //display window to change the information of an user or delete one
            });
        }

        appStage.setScene(mainPageView.getScene());
        appStage.setTitle("Hotel Manager - Menu");
        appStage.show();
        primaryStage.close();
    }

    private void updateInfoDisplay(Stage myPageStage, Stage appStage) {
        UpdateInfoView updateInfoPage = new UpdateInfoView();
        Stage updateInfoStage = new Stage();

        updateInfoPage.getChangeUsername().setOnAction(e -> {
            updateInfoPage.getChangeUsername().setVisible(false);
            updateInfoPage.getUsernameL().setVisible(true);
            updateInfoPage.getUsername().setVisible(true);
        });

        updateInfoPage.getChangePwd().setOnAction(e -> {
            updateInfoPage.getChangePwd().setVisible(false);
            updateInfoPage.getFstPwdL().setVisible(true);
            updateInfoPage.getFirstPassword().setVisible(true);
            updateInfoPage.getSndPwdL().setVisible(true);
            updateInfoPage.getSecondPassword().setVisible(true);
        });

        updateInfoPage.getSave().setOnAction(e -> {
            String newUsername = updateInfoPage.getUsername().getText();
            String firstPassword = updateInfoPage.getFirstPassword().getText();
            String secondPassword = updateInfoPage.getSecondPassword().getText();

            boolean nothingEmpty = true;
            if (updateInfoPage.getUsername().isVisible() && newUsername.isEmpty()) {
                updateInfoPage.setOutput("Username field is empty !\n");
                nothingEmpty = false;
            }
            if (updateInfoPage.getFirstPassword().isVisible() && (firstPassword.isEmpty() || secondPassword.isEmpty())) {
                updateInfoPage.setOutput(updateInfoPage.getOutput().getText() + "Please enter the new password correctly !");
                nothingEmpty = false;
            }
            if (nothingEmpty) {
                if (firstPassword.equals(secondPassword)) {
                    if (!updateInfoPage.getChangeUsername().isVisible()) {
                        connectedUser.setU_name(newUsername);
                    }
                    if (!updateInfoPage.getChangePwd().isVisible()) {
                        connectedUser.setU_password(firstPassword);
                    }

                    myPageDisplay(appStage);
                    updateInfoStage.close();
                } else {
                    updateInfoPage.getFirstPassword().setVisible(false);
                    updateInfoPage.setOutput("First and second input for password are not equal !");
                }
            }
            try {
                UserDB.updateUserInformation(conn, connectedUser, newUsername, firstPassword);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        updateInfoStage.setScene(updateInfoPage.getScene());
        updateInfoStage.setTitle("Hotel Manager - Update personal information");
        updateInfoStage.show();
        myPageStage.close();
    }

    private void myPageDisplay(Stage appStage) {
        MyPageView myPage = new MyPageView(connectedUser);
        Stage myPageStage = new Stage();

        myPage.getUpdateInfo().setOnAction(e -> updateInfoDisplay(myPageStage, appStage));

        myPage.getQuit().setOnAction(e -> Platform.exit());

        myPageStage.setScene(myPage.getScene());
        myPageStage.setTitle("Hotel Manager - My Page");
        myPageStage.show();
    }

    private void newRoomDisplay(Stage appStage, Stage roomsStage) {
        NewRoomView newRoomViewPage = new NewRoomView();
        Stage newRoomStage = new Stage();

        //handle buttons
        newRoomViewPage.getSubmit().setOnAction(e -> {
            int roomNb = Integer.parseInt(newRoomViewPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(newRoomViewPage.getFloor().getText());
            String roomType = newRoomViewPage.getRoomType().getText();
            int roomBooked = 0;
            if (newRoomViewPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room newRoom = new Room(roomNb, roomFloor, roomType, roomBooked);
            RoomsDB.addRoom(conn, newRoom);

            newRoomStage.close();
        });

        newRoomStage.setScene(newRoomViewPage.getScene());
        newRoomStage.setTitle("Hotel Manager - New Room");
        newRoomStage.show();
    }

    private void roomsDisplay(Stage appStage) {
        RoomsView roomsViewPage = new RoomsView(connectedUser);
        Stage roomsStage = new Stage();

        //add display of rooms --> add parameter in constructor with rooms map ?

        if (connectedUser.getU_is_admin() == 1) {
            roomsViewPage.getAddRoom().setOnAction(e -> newRoomDisplay(appStage, roomsStage));
        }

        roomsStage.setScene(roomsViewPage.getScene());
        roomsStage.setTitle("Hotel Manager - Rooms");
        roomsStage.show();
    }

    private void logoutDisplay(Stage appStage) {
        LogoutView logoutViewPage = new LogoutView();
        Stage logoutStage = new Stage();

        logoutViewPage.getLogin().setOnAction(e -> {
            //display again login window
            Stage loginStage = new Stage();
            loginPage(loginStage);
            logoutStage.close();
        });

        logoutViewPage.getClose().setOnAction(e -> Platform.exit());

        logoutStage.setScene(logoutViewPage.getScene());
        logoutStage.setTitle("Hotel Manager - Logout");
        logoutStage.show();
        appStage.close();
    }
}
