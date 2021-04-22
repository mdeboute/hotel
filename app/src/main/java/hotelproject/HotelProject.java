package hotelproject;

import hotelproject.controllers.db.DatabaseManager;
import hotelproject.controllers.objects.Room;
import hotelproject.controllers.objects.RoomType;
import hotelproject.controllers.objects.User;
import hotelproject.views.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class HotelProject extends Application {

    private final DatabaseManager dbm = new DatabaseManager();
    private User connectedUser;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage secondaryStage = new Stage();
        credentialsDisplay(secondaryStage, primaryStage, false);
    }

    /**
     * Displays the Login stage or the password input when the user want to change
     * its pers. info
     *
     * @param secondaryStage parameter needed to call the updateInfoDisplay method
     * @param primaryStage   stage on which the login view is displayed
     * @param onlyPwd        if true this method displays the password input,
     *                       otherwise the login page
     */
    private void credentialsDisplay(Stage secondaryStage, Stage primaryStage, boolean onlyPwd) {
        LoginView loginView = new LoginView(onlyPwd);

        if (onlyPwd) {
            loginView.getCredentials().setText("Please enter your password first.");
        }

        loginView.getTestLoginButton().setOnAction(e -> {
            User userTest = new User(loginView.getUsername().getText(), loginView.getPassword().getText());
            try {
                if (!onlyPwd) { // login
                    if (dbm.udb.userExists(userTest)) { // test if the user exist in the database and has correct
                                                        // password
                        loginView.getResult().setText("Success!");
                        connectedUser = new User(loginView.getUsernameString(),loginView.getPasswordString(),dbm.udb.getU_is_admin(userTest));

                        mainPageDisplay(primaryStage); // if the user succeeded to login we open the main page of the
                                                       // application
                    } else {
                        loginView.getResult().setText("Fail! Your username or password is wrong.");
                    }
                } else { // pwd input
                    if (connectedUser.getU_password().equals(userTest.getU_password())) {
                        updateInfoDisplay(secondaryStage, primaryStage);
                    } else {
                        loginView.getResult().setText("Fail! Incorrect password.");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        primaryStage.setScene(loginView.getScene());
        primaryStage.setTitle("Hotel Manager - Login");
        primaryStage.show();
    }

    /**
     * Displays the main page of the application
     *
     * @param primaryStage former open stage to close after showing the main page's
     *                     window
     */
    private void mainPageDisplay(Stage primaryStage) {
        MainPageView mainPageView = new MainPageView(connectedUser);
        Stage appStage = new Stage();

        // Set buttons on action

        mainPageView.getMyPageButton().setOnAction(e -> {
            // display user info page
            myPageDisplay();
        });

        mainPageView.getLogoutButton().setOnAction(e -> logoutDisplay(appStage));

        mainPageView.getAddBookingButton().setOnAction(e -> {
            // display window with a form for adding a booking
        });

        mainPageView.getViewBookingsButton().setOnAction(e -> {
            // display window with all bookings with search bar
        });

        mainPageView.getUpdateButton().setOnAction(e -> {
            // display window to change or delete a booking --> shouldn't we display this
            // button on the view bookings page ?
        });

        mainPageView.getViewRooms().setOnAction(e -> roomsDisplay());

        // only admins can access to this button
        if (connectedUser.getU_is_admin() == 1) {
            mainPageView.getViewUsersButton().setOnAction(e -> {
                // display window to change the information of an user or delete one
                usersDisplay();
            });
        }

        appStage.setScene(mainPageView.getScene());
        appStage.setTitle("Hotel Manager - Menu");
        appStage.show();
        primaryStage.close();
    }

    /**
     * Displays the window where the user can update its pers. info
     *
     * @param myPageStage     former open stage to close after showing updateInfo
     *                        stage
     * @param updateInfoStage stage on which the page to change the user's info will
     *                        appear
     */
    private void updateInfoDisplay(Stage myPageStage, Stage updateInfoStage) {
        UpdateInfoView updateInfoPage = new UpdateInfoView();

        // if the user choose to change its username
        updateInfoPage.getChangeUsername().setOnAction(e -> {
            updateInfoPage.getChangeUsername().setVisible(false);
            updateInfoPage.getUsernameL().setVisible(true);
            updateInfoPage.getUsername().setVisible(true);
        });

        // if the user choose to change its password
        updateInfoPage.getChangePwd().setOnAction(e -> {
            updateInfoPage.getChangePwd().setVisible(false);
            updateInfoPage.getFstPwdL().setVisible(true);
            updateInfoPage.getFirstPassword().setVisible(true);
            updateInfoPage.getSndPwdL().setVisible(true);
            updateInfoPage.getSecondPassword().setVisible(true);
        });

        // to save the modifications
        updateInfoPage.getSave().setOnAction(e -> {
            String oldUsername = connectedUser.getU_name();
            String newUsername = updateInfoPage.getUsername().getText();
            String firstPassword = updateInfoPage.getFirstPassword().getText();
            String secondPassword = updateInfoPage.getSecondPassword().getText();

            boolean nothingEmpty = true;
            // if the user clicked on change username but didn't input any character
            if (updateInfoPage.getUsername().isVisible() && newUsername.isEmpty()) {
                updateInfoPage.setOutput("Username field is empty !\n");
                nothingEmpty = false;
            }

            // if the user clicked on change password but didn't input any character in any
            // textfield
            if (updateInfoPage.getFirstPassword().isVisible()
                    && (firstPassword.isEmpty() || secondPassword.isEmpty())) {
                updateInfoPage
                        .setOutput(updateInfoPage.getOutput().getText() + "Please enter the new password correctly !");
                nothingEmpty = false;
            }

            // if user wrote something in the visible fields
            if (nothingEmpty) {
                if (!updateInfoPage.getChangeUsername().isVisible()) { // if user choosed to change username
                    connectedUser.setU_name(newUsername);
                }
                if (!updateInfoPage.getChangePwd().isVisible()) { // if user choosed to change password
                    if (firstPassword.equals(secondPassword)) {
                        connectedUser.setU_password(firstPassword);
                    } else {
                        updateInfoPage.setOutput("First and second input for password are not equal !");
                    }
                }

                // update db
                try {
                    dbm.udb.updateUserInformation(connectedUser, oldUsername);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                myPageDisplay();
                updateInfoStage.close();
            }
        });

        updateInfoStage.setScene(updateInfoPage.getScene());
        updateInfoStage.setTitle("Hotel Manager - Update personal information");
        updateInfoStage.show();
        myPageStage.close();
    }

    /**
     * Displays the user's page (with pers. info)
     */
    private void myPageDisplay() {
        MyPageView myPage = new MyPageView(connectedUser);
        Stage myPageStage = new Stage();

        Stage loginStage = new Stage();
        myPage.getUpdateInfo().setOnAction(e -> credentialsDisplay(myPageStage, loginStage, true));

        myPage.getQuit().setOnAction(e -> myPageStage.close());

        myPageStage.setScene(myPage.getScene());
        myPageStage.setTitle("Hotel Manager - My Page");
        myPageStage.show();
    }

    /**
     * Delete a room in the database (only for admins)
     *
     * @param dRoomStage former stage to close when the new stage is displayed
     */
    private void deleteRoomDisplay(Stage dRoomStage) {
        DeleteRoomView deleteRoomPage = new DeleteRoomView(dbm);
        Stage deleteRoomStage = new Stage();

        // add the new room type to the database
        deleteRoomPage.getSubmit().setOnAction(e -> {
            
            int roomNb = Integer.parseInt(deleteRoomPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(deleteRoomPage.getFloor().getText());
            String roomType = deleteRoomPage.getRoomType().getValue().toString();
            int roomBooked = 0;
            if (deleteRoomPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room newRoom = new Room(roomNb, roomFloor, roomType, roomBooked);
            dbm.rdb.deleteRoom(connectedUser, newRoom);

            roomsDisplay();
            deleteRoomStage.close();

            deleteRoomDisplay(deleteRoomStage);
        });

        deleteRoomPage.getCancel().setOnAction(e -> {
            newRoomDisplay(deleteRoomStage);
        });

        deleteRoomStage.setScene(deleteRoomPage.getScene());
        deleteRoomStage.show();
        dRoomStage.close();
    }

    /**
     * @brief Update a room in the database (only for admins)
     * @param uRoomStage former stage to close when the new stage is displayed
     */
    private void updateRoomDisplay(Stage uRoomStage) {
        UpdateRoomView updateRoomPage = new UpdateRoomView(dbm);
        Stage updateRoomStage = new Stage();

        // add the new room type to the database
        updateRoomPage.getSubmit().setOnAction(e -> {
            
            int oldNumRoom = Integer.parseInt(updateRoomPage.getOldNumRoom().getText());
            int roomNb = Integer.parseInt(updateRoomPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(updateRoomPage.getFloor().getText());
            String roomType = updateRoomPage.getRoomType().getValue().toString();
            int roomBooked = 0;
            if (updateRoomPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room newRoom = new Room(roomNb, roomFloor, roomType, roomBooked);
            dbm.rdb.updateRoom(connectedUser, newRoom, oldNumRoom);

            roomsDisplay();
            updateRoomStage.close();

            updateRoomDisplay(updateRoomStage);
        });

        updateRoomPage.getCancel().setOnAction(e -> {
            newRoomDisplay(updateRoomStage);
        });

        updateRoomStage.setScene(updateRoomPage.getScene());
        updateRoomStage.show();
        uRoomStage.close();
    }

    /**
     * Display the page to add a room type in the database (only for admins)
     *
     * @param newRoomStage former stage to close when the new stage is displayed
     */
    private void addRoomTypeDisplay(Stage newRoomStage) {
        AddRoomTypeView addRoomTypePage = new AddRoomTypeView();
        Stage addTypeStage = new Stage();

        // add the new room type to the database
        addRoomTypePage.getSubmit().setOnAction(e -> {
            String typeName = addRoomTypePage.getName().getText();
            int nbBeds = Integer.parseInt(addRoomTypePage.getNbBeds().getText());
            int rSize = Integer.parseInt(addRoomTypePage.getRoomSize().getText());
            int hasView = addRoomTypePage.getHasView();
            int hasKitchen = addRoomTypePage.getHasKitchen();
            int hasBathroom = addRoomTypePage.getHasBathroom();
            int hasWorksp = addRoomTypePage.getHasWorksp();
            int hasTv = addRoomTypePage.getHasTv();
            int hasCoffeeMkr = addRoomTypePage.getHasCoffeeMkr();

            RoomType newRoomType = new RoomType(typeName, nbBeds, rSize, hasView, hasKitchen, hasBathroom, hasWorksp,
                    hasTv, hasCoffeeMkr);
            dbm.rdb.addRoomType(newRoomType);

            newRoomDisplay(addTypeStage);
        });

        addRoomTypePage.getCancel().setOnAction(e -> newRoomDisplay(addTypeStage));

        addTypeStage.setScene(addRoomTypePage.getScene());
        addTypeStage.setTitle("Add a new room type");
        addTypeStage.show();
        newRoomStage.close();
    }

    /**
     * Displays the page to add a new room in the database (only for admins)
     *
     * @param formerStage to close when the new stage is showed
     */
    private void newRoomDisplay(Stage formerStage) {
        NewRoomView newRoomViewPage = new NewRoomView(dbm);
        Stage newRoomStage = new Stage();

        // set buttons on action

        newRoomViewPage.getAddRoomType().setOnAction(e -> addRoomTypeDisplay(newRoomStage));

        newRoomViewPage.getSubmit().setOnAction(e -> {
            int roomNb = Integer.parseInt(newRoomViewPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(newRoomViewPage.getFloor().getText());
            String roomType = newRoomViewPage.getRoomType().getValue().toString();
            int roomBooked = 0;
            if (newRoomViewPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room newRoom = new Room(roomNb, roomFloor, roomType, roomBooked);
            dbm.rdb.addRoom(newRoom);

            roomsDisplay();
            newRoomStage.close();
        });

        newRoomViewPage.getCancel().setOnAction(e -> {
            roomsDisplay();
            newRoomStage.close();
        });

        newRoomStage.setScene(newRoomViewPage.getScene());
        newRoomStage.setTitle("Hotel Manager - New Room");
        newRoomStage.show();
        formerStage.close();
    }

    /**
     * Displays the page to add a new user in the database (only for admins)
     *
     * @param formerStage to close when the new stage is showed
     */
    private void newUserDisplay(Stage formerStage) {
        NewUserView newUserViewPage = new NewUserView(dbm);
        Stage newUserStage = new Stage();

        // set buttons on action

        newUserViewPage.getSubmit().setOnAction(e -> {
            String userN = newUserViewPage.getUserName().getText();
            String userP = newUserViewPage.getUserPassWord().getText();
            int userIA = 0;
            if (newUserViewPage.getAdmin().isSelected()) {
                userIA = 1;
            }

            User newUser = new User(userN, userP, userIA);
            dbm.udb.addUser(newUser);

            usersDisplay();
            newUserStage.close();
        });

        newUserViewPage.getCancel().setOnAction(e -> {
            usersDisplay();
            newUserStage.close();
        });

        newUserStage.setScene(newUserViewPage.getScene());
        newUserStage.setTitle("Hotel Manager - New User");
        newUserStage.show();
        formerStage.close();
    }

    /**
     * Displays the page with the hotel's rooms table
     */
    private void roomsDisplay() {
        List<Room> rooms = dbm.rdb.findAllRooms();
        RoomsView roomsViewPage = new RoomsView(connectedUser, rooms);
        Stage roomsStage = new Stage();

        // admins can add a room
        if (connectedUser.getU_is_admin() == 1) {
            roomsViewPage.getAddRoom().setOnAction(e -> newRoomDisplay(roomsStage));
        }

        // admins can delete a room
        if (connectedUser.getU_is_admin() == 1) {
            roomsViewPage.getDeleteRoom().setOnAction(e -> deleteRoomDisplay(roomsStage));
        }

        // admins can update a room
        if (connectedUser.getU_is_admin() == 1) {
            roomsViewPage.getUpdateRoom().setOnAction(e -> updateRoomDisplay(roomsStage));
        }

        roomsStage.setScene(roomsViewPage.getScene());
        roomsStage.setTitle("Hotel Manager - Rooms");
        roomsStage.show();
    }

    private void usersDisplay() {
        List<User> users = dbm.udb.getAllUsers();
        UsersView usersViewPage = new UsersView(connectedUser, users);
        Stage usersStage = new Stage();

        // admins can add a user
        if (connectedUser.getU_is_admin() == 1) {
            usersViewPage.getAddUser().setOnAction(e -> newUserDisplay(usersStage));
        }

        usersStage.setScene(usersViewPage.getScene());
        usersStage.setTitle("Hotel Manager - Users");
        usersStage.show();
    }

    /**
     * Displays the logout page
     *
     * @param appStage the main application's page to close after showing logout
     *                 page
     */
    private void logoutDisplay(Stage appStage) {
        LogoutView logoutViewPage = new LogoutView();
        Stage logoutStage = new Stage();

        logoutViewPage.getLogin().setOnAction(e -> {
            // display again login window
            Stage loginStage = new Stage();
            Stage secondStage = new Stage();
            credentialsDisplay(secondStage, loginStage, false);
            logoutStage.close();
        });

        logoutViewPage.getClose().setOnAction(e -> Platform.exit());

        logoutStage.setScene(logoutViewPage.getScene());
        logoutStage.setTitle("Hotel Manager - Logout");
        logoutStage.show();
        appStage.close();
    }
}
