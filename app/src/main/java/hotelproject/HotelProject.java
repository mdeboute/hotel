package hotelproject;

import hotelproject.controllers.db.DatabaseManager;
import hotelproject.controllers.db.HotelData;
import hotelproject.controllers.objects.*;
import hotelproject.views.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HotelProject extends Application {

    private final DatabaseManager dbm = new DatabaseManager();
    private final HotelData hdata = dbm.createDBObjects();
    private User connectedUser;
    private Stage mainPageStage;

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
     * @param secondaryStage parameter needed to call the updateInfoDisplay method
     * @param primaryStage   stage on which the login view is displayed
     * @param onlyPwd        if true this method displays the password input,
     *                       otherwise the login page
     * @brief Displays the login stage or the password input when the user wants to change
     * their personal info
     */
    private void credentialsDisplay(Stage secondaryStage, Stage primaryStage, boolean onlyPwd) {
        LoginView loginView = new LoginView(onlyPwd);

        if (onlyPwd) {

        }

        loginView.getPassword().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginView.getTestLoginButton().fire();
            }
        });

        //set login button on action
        loginView.getTestLoginButton().setOnAction(e -> {
            User userTest = new User(loginView.getUsername().getText(), loginView.getPassword().getText());
            try {
                if (!onlyPwd) { // login
                    if (dbm.udb.userExists(userTest)) { // test if the user exist in the database and has correct password
                        connectedUser = new User(loginView.getUsernameString(), loginView.getPasswordString(), dbm.udb.getU_is_admin(userTest));
                        mainPageDisplay(primaryStage); // if the user succeeded to login we open the main page of the application
                    } else {
                        loginView.getResult().setText("The username or password you have entered is wrong.");
                    }
                } else { // pwd input
                    if (connectedUser.getU_password().equals(userTest.getU_password())) {
                        updateInfoDisplay(secondaryStage, primaryStage);
                    } else {
                        loginView.getResult().setText("The password you have entered is wrong.");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        //open stage
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
        mainPageStage = new Stage();

        // Set buttons on action

        mainPageView.getMyPageButton().setOnAction(e -> {
            // display user info page
            myPageDisplay();
        });

        mainPageView.getViewBookingsButton().setOnAction(e -> {
            // display window with all bookings with search bar
        });

        mainPageView.getViewCustomers().setOnAction(e -> {
            // display window to see customers in db
        });

        mainPageView.getViewRooms().setOnAction(e -> roomsDisplay());
        mainPageView.getViewBookingsButton().setOnAction(e -> bookingsDisplay());
        mainPageView.getViewCustomers().setOnAction(e -> customersDisplay());

        // only admins can access to this button
        if (connectedUser.getU_is_admin() == 1) {
            mainPageView.getViewUsersButton().setOnAction(e -> {
                // display window to change the information of an user or delete one
                usersDisplay();
            });
        }

        mainPageStage.setScene(mainPageView.getScene());
        mainPageStage.setTitle("Hotel Manager - Menu");
        mainPageStage.show();
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
                updateInfoPage.setOutput("Username field is empty!\n");
                nothingEmpty = false;
            }

            // if the user clicked on change password but didn't input any character in any
            // textfield
            if (updateInfoPage.getFirstPassword().isVisible()
                    && (firstPassword.isEmpty() || secondPassword.isEmpty())) {
                updateInfoPage
                        .setOutput(updateInfoPage.getOutput().getText() + "Please enter the new password correctly!");
                nothingEmpty = false;
            }

            // if user wrote something in the visible fields
            if (nothingEmpty) {
                if (!updateInfoPage.getChangeUsername().isVisible()) { // if user chose to change username
                    connectedUser.setU_name(newUsername);
                }
                if (!updateInfoPage.getChangePwd().isVisible()) { // if user chose to change password
                    if (firstPassword.equals(secondPassword)) {
                        connectedUser.setU_password(firstPassword);
                    } else {
                        updateInfoPage.setOutput("First and second input for password are not equal!");
                    }
                }

                // update db
                try {
                    hdata.updateUserInformation(connectedUser, oldUsername);
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

        myPage.getBack().setOnAction(e -> myPageStage.close());

        myPage.getLogout().setOnAction(e -> logoutDisplay(myPageStage));

        myPageStage.setScene(myPage.getScene());
        myPageStage.setTitle("Hotel Manager - My Page");
        myPageStage.show();
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
            String roomType = newRoomViewPage.getRoomType().getValue();
            int roomBooked = 0;
            if (newRoomViewPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room newRoom = new Room(roomNb, roomFloor, roomType, roomBooked);
            hdata.addRoom(newRoom);

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
        NewUserView newUserViewPage = new NewUserView();
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
            hdata.addUser(newUser);
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
     * Displays the page to add a new booking in the database (only for admins)
     *
     * @param formerStage to close when the new stage is showed
     */
    private void newBookingDisplay(Stage formerStage) {
        NewBookingView newBookingViewPage = new NewBookingView();
        Stage newBookingStage = new Stage();

        // set buttons on action

        newBookingViewPage.getSubmit().setOnAction(e -> {
            int bookingID = hdata.getBookingAutoID();
            int roomNb = Integer.parseInt(newBookingViewPage.getNumRoom().getText());
            int paidByCard = 0;
            if (newBookingViewPage.getPaidByCard().isSelected()) {
                paidByCard = 1;
            }
            // Getting the datepicker dates
            DatePicker checkInDP = newBookingViewPage.getCheckIn();
            LocalDate datePicked = checkInDP.getValue();
            String formattedDate = datePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date sqlDate = java.sql.Date.valueOf(formattedDate);

            DatePicker checkOutDP = newBookingViewPage.getCheckOut();
            LocalDate secondDatePicked = checkOutDP.getValue();
            String secondFormattedDate = secondDatePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date secondSQLDate = java.sql.Date.valueOf(secondFormattedDate);

            int bookingFee = Integer.parseInt(newBookingViewPage.getBookingFee().getText());
            int isPaid = 0;
            if (newBookingViewPage.getIsPaid().isSelected()) {
                isPaid = 1;
            }

            Booking newBooking = new Booking(bookingID, roomNb, paidByCard, sqlDate, secondSQLDate, bookingFee, isPaid);
            hdata.addBooking(newBooking);

            bookingsDisplay();
            newBookingStage.close();
        });

        newBookingViewPage.getCancel().setOnAction(e -> {
            bookingsDisplay();
            newBookingStage.close();
        });

        newBookingStage.setScene(newBookingViewPage.getScene());
        newBookingStage.setTitle("Hotel Manager - New Booking");
        newBookingStage.show();
        formerStage.close();
    }

    /**
     * Displays the page with the hotel's bookings table
     */

    private void bookingsDisplay() {
        List<Booking> bookings = hdata.getBookings();
        BookingsView bookingsViewPage = new BookingsView(connectedUser, bookings);
        Stage bookingsStage = new Stage();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // admins can add a booking
        if (connectedUser.getU_is_admin() == 1) {
            bookingsViewPage.getAddBooking().setOnAction(e -> newBookingDisplay(bookingsStage));
        }

        DatePicker dated = bookingsViewPage.getDatePicker();

        dated.setOnAction(e -> {
            Stage newWindow = new Stage();
            ListView<Integer> bIDListView = new ListView<>();

            LocalDate datePicked = dated.getValue();
            Date sqlDate = java.sql.Date.valueOf(datePicked);

            String formattedDate = formatter.format(sqlDate);
            ArrayList<Integer> bookingIDS = dbm.bdb.getBookingsForSpecificDay(formattedDate);

            for (int bID : bookingIDS) {
                bIDListView.getItems().add(bID);
            }

            GridPane secBLayout = new GridPane();
            secBLayout.getChildren().add(bIDListView);

            Scene secondScene = new Scene(secBLayout, 300, 300);

            // New window (Stage) Stage newWindow = new Stage();
            newWindow.setTitle("Bookings available");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(bookingsStage.getX() + 200);
            newWindow.setY(bookingsStage.getY() + 100);

            newWindow.show();

        });

        bookingsStage.setScene(bookingsViewPage.getScene());
        bookingsStage.setTitle("Hotel Manager - Bookings");
        bookingsStage.show();
    }

    /**
     * Displays the page with the hotel's rooms table
     */
    private void roomsDisplay() {

        ArrayList<Room> rooms = hdata.getRooms();
        RoomsView roomsViewPage = new RoomsView(connectedUser, rooms);
        Stage roomsStage = new Stage();

        if (connectedUser.getU_is_admin() == 1) {
            roomsViewPage.getAddRoom().setOnAction(e -> newRoomDisplay(roomsStage));
            roomsViewPage.getDeleteRoom().setOnAction(
                    e -> deleteRoomDisplay(roomsStage, roomsViewPage.getRoomsTable().getSelectionModel().getSelectedItem()));
            roomsViewPage.getUpdateRoom().setOnAction(
                    e -> updateRoomDisplay(roomsStage,
                            roomsViewPage.getRoomsTable().getSelectionModel().getSelectedItem()));
        }

        roomsViewPage.getViewDetails().setOnAction(e -> {
            Stage newWindow = new Stage();

            Room rD = roomsViewPage.getRoomsTable().getSelectionModel().getSelectedItem();
            ListView<String> roomDListView = new ListView<>();
            Hashtable<String, String> roomsDetails = hdata.viewDetails(rD);

            roomDListView.getItems().add("It is of " + roomsDetails.get("t_name") + " type");
            roomDListView.getItems().add("Has " + roomsDetails.get("beds") + " bed/s");
            roomDListView.getItems().add(roomsDetails.get("r_size") + " square meters");

            if (roomsDetails.get("has_view").equals("1")) {
                roomDListView.getItems().add("It has a view");
            } else if (roomsDetails.get("has_view").equals("0")) {
                roomDListView.getItems().add("It does not have a view");
            }

            if (roomsDetails.get("has_kitchen").equals("1")) {
                roomDListView.getItems().add("It has a kitchen");
            } else if (roomsDetails.get("has_kitchen").equals("0")) {
                roomDListView.getItems().add("It does not have a kitchen");
            }

            if (roomsDetails.get("has_bathroom").equals("1")) {
                roomDListView.getItems().add("It has a bathroom");
            } else if (roomsDetails.get("has_bathroom").equals("0")) {
                roomDListView.getItems().add("It does not have a bathroom");
            }

            if (roomsDetails.get("has_workspace").equals("1")) {
                roomDListView.getItems().add("It has a workspace");
            } else if (roomsDetails.get("has_workspace").equals("0")) {
                roomDListView.getItems().add("It does not have a workspace");
            }

            if (roomsDetails.get("has_tv").equals("1")) {
                roomDListView.getItems().add("It has a TV");
            } else if (roomsDetails.get("has_tv").equals("0")) {
                roomDListView.getItems().add("It does not have a TV");
            }

            if (roomsDetails.get("has_coffee_maker").equals("1")) {
                roomDListView.getItems().add("It has a coffee maker");
            } else if (roomsDetails.get("has_view").equals("0")) {
                roomDListView.getItems().add("It does not have a coffee maker");
            }

            GridPane secBLayout = new GridPane();
            secBLayout.getChildren().add(roomDListView);

            Scene secondScene = new Scene(secBLayout, 250, 250);

            // New window (Stage) Stage newWindow = new Stage();
            newWindow.setTitle("Details");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(roomsStage.getX());
            newWindow.setY(roomsStage.getY());

            newWindow.show();
        });

        roomsStage.setScene(roomsViewPage.getScene());
        roomsStage.setTitle("Hotel Manager - Rooms");
        roomsStage.show();
    }

    /**
     * Displays the page to update a room in the database (only for admins)
     *
     * @param formerStage to close when the new stage is showed
     */
    private void updateRoomDisplay(Stage formerStage, Room room) {

        UpdateRoomView updateRoomViewPage = new UpdateRoomView(dbm);
        Stage updateRoomStage = new Stage();

        updateRoomViewPage.getSubmit().setOnAction(e -> {
            int roomNb = Integer.parseInt(updateRoomViewPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(updateRoomViewPage.getFloor().getText());
            String roomType = updateRoomViewPage.getRoomType().getValue();

            int roomBooked = 0;
            if (updateRoomViewPage.getBooked().isSelected()) {
                roomBooked = 1;
            }

            Room updateRoom = new Room(roomNb, roomFloor, roomType, roomBooked);

            hdata.updateRoom(updateRoom, room.getR_num());
            roomsDisplay();
            updateRoomStage.close();
        });

        updateRoomViewPage.getCancel().setOnAction(e -> {
            roomsDisplay();
            updateRoomStage.close();
        });

        updateRoomStage.setScene(updateRoomViewPage.getScene());
        updateRoomStage.setTitle("Hotel Manager - Updating Room");
        updateRoomStage.show();
        formerStage.close();
    }

    /**
     * Display to delete the selected room
     */
    private void deleteRoomDisplay(Stage formerStage, Room room) {

        DeleteRoomView deleteRoomViewPage = new DeleteRoomView();
        Stage deleteRoomStage = new Stage();

        deleteRoomViewPage.getSubmit().setOnAction(e -> {

            hdata.deleteRoom(room);
            roomsDisplay();
            deleteRoomStage.close();
        });

        deleteRoomViewPage.getCancel().setOnAction(e -> {
            roomsDisplay();
            deleteRoomStage.close();
        });

        deleteRoomStage.setScene(deleteRoomViewPage.getScene());
        deleteRoomStage.setTitle("Hotel Manager - Deleting Room");
        deleteRoomStage.show();
        formerStage.close();
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

    private void customersDisplay() {
        List<Customer> customers = hdata.getCustomers();
        CustomersView customersViewPage = new CustomersView(customers);
        Stage customerStage = new Stage();

        customersViewPage.getAddCustomer().setOnAction(e -> newCustomerDisplay(customerStage));

        customersViewPage.getUpdateCustomer().setOnAction(e -> updateCustomerDisplay(customerStage,
                customersViewPage.getCustomersTable().getSelectionModel().getSelectedItem()));

        customerStage.setScene(customersViewPage.getScene());
        customerStage.setTitle("Hotel Manager - Customers");
        customerStage.show();
    }

    private void newCustomerDisplay(Stage formerStage) {
        NewCustomerView newCustomerViewPage = new NewCustomerView();
        Stage newCustomerStage = new Stage();

        // set buttons on action

        newCustomerViewPage.getSubmit().setOnAction(e -> {
            int cSSNum = Integer.parseInt(newCustomerViewPage.getCSSNum().getText());
            String cAddress = newCustomerViewPage.getCAddress().getText();
            String cFullName = newCustomerViewPage.getCFullName().getText();
            int cPhoneNum = Integer.parseInt(newCustomerViewPage.getCPhoneNum().getText());
            String cEmail = newCustomerViewPage.getCEmail().getText();

            Customer newCustomer = new Customer(cSSNum, cAddress, cFullName, cPhoneNum, cEmail);
            hdata.addCustomer(newCustomer);

            customersDisplay();
            newCustomerStage.close();
        });

        newCustomerViewPage.getCancel().setOnAction(e -> {
            customersDisplay();
            newCustomerStage.close();
        });

        newCustomerStage.setScene(newCustomerViewPage.getScene());
        newCustomerStage.setTitle("Hotel Manager - New Customer");
        newCustomerStage.show();
        formerStage.close();
    }

    /**
     * Displays the page to update a customer in the database
     *
     * @param formerStage to close when the new stage is showed
     */
    private void updateCustomerDisplay(Stage formerStage, Customer customer) {

        UpdateCustomerView updateCustomerViewPage = new UpdateCustomerView();
        Stage updateCustomerStage = new Stage();

        updateCustomerViewPage.getSubmit().setOnAction(e -> {
            int cSSNum = Integer.parseInt(updateCustomerViewPage.getCSSNum().getText());
            String cAddress = updateCustomerViewPage.getCAddress().getText();
            String cFullName = updateCustomerViewPage.getCFullName().getText();
            int cPhoneNum = Integer.parseInt(updateCustomerViewPage.getCPhoneNum().getText());
            String cEmail = updateCustomerViewPage.getCEmail().getText();

            Customer updatedCustomer = new Customer(cSSNum, cAddress, cFullName, cPhoneNum, cEmail);
            hdata.updateCustomer(updatedCustomer, customer.getC_ss_number());

            customersDisplay();
            updateCustomerStage.close();
        });

        updateCustomerViewPage.getCancel().setOnAction(e -> {
            customersDisplay();
            updateCustomerStage.close();
        });

        updateCustomerStage.setScene(updateCustomerViewPage.getScene());
        updateCustomerStage.setTitle("Hotel Manager - Updating Customer");
        updateCustomerStage.show();
        formerStage.close();
    }

    /**
     * Displays the logout page
     *
     * @param myPageStage the main application's page to close after showing logout
     *                    page
     */
    private void logoutDisplay(Stage myPageStage) {
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
        mainPageStage.close();
        myPageStage.close();
    }
}
