package hotelproject;

import hotelproject.controllers.db.DatabaseManager;
import hotelproject.controllers.db.HotelData;
import hotelproject.controllers.objects.*;
import hotelproject.views.*;
import hotelproject.views.UpdateInfoView.Change;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
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

    public static boolean isNumeric(String str) {
        try {
            //Double.parseDouble(str);
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

        loginView.getPassword().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginView.getTestLoginButton().fire();
            }
        });

        loginView.getUsername().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginView.getTestLoginButton().fire();
            }
        });

        //set login button on action
        loginView.getTestLoginButton().setOnAction(e -> {
            User userTest = new User(loginView.getUsername().getText().trim(), loginView.getPassword().getText());
            try {
                if (!onlyPwd) { // login
                    if (dbm.udb.userExists(userTest)) { // test if the user exist in the database and has correct password
                        connectedUser = new User(loginView.getUsernameString(), loginView.getPasswordString(), dbm.udb.getU_is_admin(userTest));
                        mainPageDisplay(primaryStage); // if the user succeeded to login we open the main page of the application
                    } else {
                        loginView.getResult().setText("The username or password you have entered is wrong !");
                    }
                } else { // pwd input
                    if (connectedUser.getU_password().equals(userTest.getU_password())) {
                        myPageDisplay();
                        primaryStage.close();
                    } else {
                        loginView.getResult().setText("The password you have entered is wrong !");
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
        Stage myPageStage = new Stage();
        Stage loginStage = new Stage();
        mainPageStage = new Stage();

        // Set buttons on action

        mainPageView.getMyPageButton().setOnAction(e -> {
            // display user info page
            credentialsDisplay(myPageStage, loginStage, true);
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
    private void updateInfoDisplay(Stage myPageStage, Stage updateInfoStage, Change change) {

        myPageStage.close();
        UpdateInfoView updateInfoPage = new UpdateInfoView(change);

        // to save the modifications
        updateInfoPage.getSave().setOnAction(e -> {
            String oldUsername = connectedUser.getU_name();
            boolean isInfoCorrect = true;

            if (change == Change.USERNAME) {
                String firstUsername = updateInfoPage.getFirstUName().getText();
                String secondUsername = updateInfoPage.getSecondUName().getText();
                if (firstUsername.equals(secondUsername)) {
                    connectedUser.setU_name(firstUsername);
                } else {
                    updateInfoPage.setOutput("First and second input for usernames are not equal !");
                    updateInfoPage.getFirstUName().setText("");
                    updateInfoPage.getSecondUName().setText("");
                    isInfoCorrect = false;
                }
            } else {
                String firstPassword = updateInfoPage.getFirstPwd().getText();
                String secondPassword = updateInfoPage.getSecondPwd().getText();

                if (firstPassword.equals(secondPassword)) {
                    connectedUser.setU_password(firstPassword);
                } else {
                    updateInfoPage.setOutput("First and second input for passwords are not equal !");
                    updateInfoPage.getFirstPwd().setText("");
                    updateInfoPage.getSecondPwd().setText("");
                    isInfoCorrect = false;
                }
            }

            // update db
            try {
                hdata.updateUserInformation(connectedUser, oldUsername);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (isInfoCorrect) {
                myPageDisplay();
                updateInfoStage.close();
            }

        });

        updateInfoStage.setScene(updateInfoPage.getScene());
        updateInfoStage.setTitle("Hotel Manager - Update personal information");
        updateInfoStage.show();
    }

    /**
     * Displays the user's page (with pers. info)
     */
    private void myPageDisplay() {
        MyPageView myPage = new MyPageView(connectedUser);
        Stage myPageStage = new Stage();
        Stage updateInfoStage = new Stage();

        Stage loginStage = new Stage();
        myPage.getChPwd().setOnAction(e -> updateInfoDisplay(myPageStage, updateInfoStage, Change.PASSWORD));
        myPage.getChUser().setOnAction(e -> updateInfoDisplay(myPageStage, updateInfoStage, Change.USERNAME));

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
    private void addRoomTypeDisplay(HotelData hdata, Stage newRoomStage) {
        ArrayList<RoomType> allRoomTypes = hdata.getRoomTypes();
        AddRoomTypeView addRoomTypePage = new AddRoomTypeView();
        Stage addTypeStage = new Stage();

        // add the new room type to the database
        addRoomTypePage.getSubmit().setOnAction(e -> {
            String typeName = addRoomTypePage.getName().getText();
            String capTypeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1);

            int nbBeds = Integer.parseInt(addRoomTypePage.getNbBeds().getText());
            int rSize = Integer.parseInt(addRoomTypePage.getRoomSize().getText());
            int hasView = addRoomTypePage.getHasView();
            int hasKitchen = addRoomTypePage.getHasKitchen();
            int hasBathroom = addRoomTypePage.getHasBathroom();
            int hasWorksp = addRoomTypePage.getHasWorksp();
            int hasTv = addRoomTypePage.getHasTv();
            int hasCoffeeMkr = addRoomTypePage.getHasCoffeeMkr();

            boolean rtFlag = true;
            RoomType newRoomType = new RoomType(capTypeName, nbBeds, rSize, hasView, hasKitchen, hasBathroom, hasWorksp,
                    hasTv, hasCoffeeMkr);

            for (RoomType rT : allRoomTypes) {
                if (rT.getT_name().equals(newRoomType.getT_name())) {
                    rtFlag = false;

                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Room type already exists. Choose another one!");
                    a.showAndWait();

                    break;
                }
            }
            if (rtFlag) {
                hdata.addRoomType(newRoomType);
                addTypeStage.close();
                newRoomStage.close();
                newRoomDisplay(addTypeStage);
            }
        });

        addTypeStage.setOnCloseRequest(e -> addTypeStage.close());

        addTypeStage.initOwner(newRoomStage);
        addTypeStage.initModality(Modality.WINDOW_MODAL);

        addTypeStage.setScene(addRoomTypePage.getScene());
        addTypeStage.setTitle("Add a new room type");
        addTypeStage.show();
    }

    /**
     * Displays the page to add a new room in the database (only for admins)
     *
     * @param formerStage to close when the new stage is showed
     */
    private void newRoomDisplay(Stage formerStage) {
        ArrayList<Room> rooms = hdata.getRooms();
        NewRoomView newRoomViewPage = new NewRoomView(hdata);
        Stage newRoomStage = new Stage();

        // set buttons on action

        newRoomViewPage.getAddRoomType().setOnAction(e -> addRoomTypeDisplay(hdata, newRoomStage));

        newRoomViewPage.getSubmit().setOnAction(e -> {
            int roomNb = Integer.parseInt(newRoomViewPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(newRoomViewPage.getFloor().getText());
            String roomType = newRoomViewPage.getRoomType().getValue();

            Room newRoom = new Room(roomNb, roomFloor, roomType);

            boolean flag = true;

            for (Room r : rooms) {
                if (r.getR_num() == newRoom.getR_num()) {
                    flag = false;

                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Room already exists. Add a different one or update existing!");
                    a.showAndWait();

                    break;
                }
                // else do nothing
            }
            if (flag) {
                hdata.addRoom(newRoom);
                newRoomStage.close();
                formerStage.close();
                roomsDisplay();
            }
        });

        newRoomStage.setOnCloseRequest(e -> newRoomStage.close());

        // Specifies the modality for new window and the owner of window
        newRoomStage.initOwner(formerStage);
        newRoomStage.initModality(Modality.WINDOW_MODAL);
        newRoomStage.setScene(newRoomViewPage.getScene());
        newRoomStage.setTitle("Hotel Manager - New Room");
        newRoomStage.show();
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
        NewBookingView newBookingViewPage = new NewBookingView(dbm, hdata);
        Stage newBookingStage = new Stage();

        int MIN_ROOM_NUMBER = 1;
        int MAX_ROOM_NUMBER = hdata.getRooms().size();
        int MIN_BOOKING_FEE = 0;
        int MAX_BOOKING_FEE = 1000000;

        Alert warningBookingFee = new Alert(AlertType.WARNING, "Enter a number.");

        // Error handling
        if (newBookingViewPage.getNumRoom().getValue() == null ||
                newBookingViewPage.getBookingFee().getText().equals("") ||
                newBookingViewPage.getCheckIn().getValue() == null ||
                newBookingViewPage.getCheckOut().getValue() == null ||
                newBookingViewPage.getC_ss_number().getValue() == null) {
            newBookingViewPage.getSubmit().setDisable(true);
        } else {
            newBookingViewPage.getSubmit().setDisable(false);
        }

        // Error handling
        newBookingViewPage.getNumRoom().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue < MIN_ROOM_NUMBER ||
                    newValue > MAX_ROOM_NUMBER) {
                newBookingViewPage.getSubmit().setDisable(true);
            } else {
                if (newBookingViewPage.getNumRoom().getValue() != null &&
                        !newBookingViewPage.getBookingFee().getText().equals("") &&
                        isNumeric(newBookingViewPage.getBookingFee().getText()) &&
                        Integer.parseInt(newBookingViewPage.getBookingFee().getText()) >= MIN_BOOKING_FEE &&
                        Integer.parseInt(newBookingViewPage.getBookingFee().getText()) <= MAX_BOOKING_FEE &&
                        newBookingViewPage.getCheckIn().getValue() != null &&
                        newBookingViewPage.getCheckOut().getValue() != null &&
                        newBookingViewPage.getC_ss_number().getValue() != null) {
                    newBookingViewPage.getSubmit().setDisable(false);
                }
            }
        });

        // Error handling
        newBookingViewPage.getBookingFee().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isNumeric(newValue) ||
                    Integer.parseInt(newValue) < MIN_BOOKING_FEE ||
                    Integer.parseInt(newValue) > MAX_BOOKING_FEE) {
                newBookingViewPage.getSubmit().setDisable(true);
            } else {
                if (newBookingViewPage.getNumRoom().getValue() != null &&
                        !newBookingViewPage.getBookingFee().getText().equals("") &&
                        newBookingViewPage.getNumRoom().getValue() >= MIN_ROOM_NUMBER &&
                        newBookingViewPage.getNumRoom().getValue() <= MAX_ROOM_NUMBER &&
                        newBookingViewPage.getCheckIn().getValue() != null &&
                        newBookingViewPage.getCheckOut().getValue() != null &&
                        newBookingViewPage.getC_ss_number().getValue() != null) {
                    newBookingViewPage.getSubmit().setDisable(false);
                }
            }
            if ((!isNumeric(newValue) && isNumeric(oldValue)) ||
                    (!isNumeric(newValue) && oldValue.equals("")) ||
                    (isNumeric(newValue) && isNumeric(oldValue) && (Integer.parseInt(newValue) < MIN_BOOKING_FEE || Integer.parseInt(newValue) > MAX_BOOKING_FEE) &&
                            (Integer.parseInt(oldValue) >= MIN_BOOKING_FEE && Integer.parseInt(oldValue) <= MAX_BOOKING_FEE))) {
                warningBookingFee.showAndWait();
            }
        });

        // Error handling
        newBookingViewPage.getCheckIn().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newBookingViewPage.getNumRoom().getValue() != null &&
                    !newBookingViewPage.getBookingFee().getText().equals("") &&
                    newBookingViewPage.getNumRoom().getValue() >= MIN_ROOM_NUMBER &&
                    newBookingViewPage.getNumRoom().getValue() <= MAX_ROOM_NUMBER &&
                    isNumeric(newBookingViewPage.getBookingFee().getText()) &&
                    Integer.parseInt(newBookingViewPage.getBookingFee().getText()) >= MIN_BOOKING_FEE &&
                    Integer.parseInt(newBookingViewPage.getBookingFee().getText()) <= MAX_BOOKING_FEE &&
                    newBookingViewPage.getCheckOut().getValue() != null &&
                    newBookingViewPage.getC_ss_number().getValue() != null) {
                newBookingViewPage.getSubmit().setDisable(false);
            }
        });

        // Error handling
        newBookingViewPage.getCheckOut().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newBookingViewPage.getNumRoom().getValue() != null &&
                    !newBookingViewPage.getBookingFee().getText().equals("") &&
                    newBookingViewPage.getNumRoom().getValue() >= MIN_ROOM_NUMBER &&
                    newBookingViewPage.getNumRoom().getValue() <= MAX_ROOM_NUMBER &&
                    isNumeric(newBookingViewPage.getBookingFee().getText()) &&
                    Integer.parseInt(newBookingViewPage.getBookingFee().getText()) >= MIN_BOOKING_FEE &&
                    Integer.parseInt(newBookingViewPage.getBookingFee().getText()) <= MAX_BOOKING_FEE &&
                    newBookingViewPage.getCheckIn().getValue() != null &&
                    newBookingViewPage.getC_ss_number().getValue() != null) {
                newBookingViewPage.getSubmit().setDisable(false);
            }
        });

        // Error handling
        newBookingViewPage.getC_ss_number().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newBookingViewPage.getNumRoom().getValue() != null &&
                    !newBookingViewPage.getBookingFee().getText().equals("") &&
                    newBookingViewPage.getNumRoom().getValue() >= MIN_ROOM_NUMBER &&
                    newBookingViewPage.getNumRoom().getValue() <= MAX_ROOM_NUMBER &&
                    isNumeric(newBookingViewPage.getBookingFee().getText()) &&
                    Integer.parseInt(newBookingViewPage.getBookingFee().getText()) >= MIN_BOOKING_FEE &&
                    Integer.parseInt(newBookingViewPage.getBookingFee().getText()) <= MAX_BOOKING_FEE &&
                    newBookingViewPage.getCheckIn().getValue() != null &&
                    newBookingViewPage.getCheckOut().getValue() != null) {
                newBookingViewPage.getSubmit().setDisable(false);
            }
        });

        // set buttons on action

        newBookingViewPage.getSubmit().setOnAction(e -> {
            int bookingID = hdata.getBookingAutoID();
            int roomNb = newBookingViewPage.getNumRoom().getValue();
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
            int c_ss_number = newBookingViewPage.getC_ss_number().getValue();
            Booking newBooking = new Booking(bookingID, roomNb, paidByCard, sqlDate, secondSQLDate, bookingFee, isPaid, c_ss_number);
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
     * A page to update the bookings for reception staff and admin
     *
     * @param formerStage to close when the new stage is showed
     */
    private void updateBookingDisplay(Stage formerStage, Booking booking) {

        UpdateBookingView updateBookingViewPage = new UpdateBookingView(dbm);
        Stage updateRoomStage = new Stage();

        updateBookingViewPage.getNewRoom().setText(String.valueOf(booking.getR_num()));
        updateBookingViewPage.getNewPayment().setSelected(booking.getPaid_by_card() == 1);
        updateBookingViewPage.getNewFromDate().setValue(booking.getB_from().toLocalDate());
        updateBookingViewPage.getNewTillDate().setValue(booking.getB_till().toLocalDate());
        updateBookingViewPage.getNewFee().setText(String.valueOf(booking.getB_fee()));
        updateBookingViewPage.getNewIsPaid().setSelected(booking.getB_is_paid() == 1);
        updateBookingViewPage.getC_ss_number().setValue(booking.getC_ss_number());

        updateBookingViewPage.getSubmit().setOnAction(e -> {
            int newRoom = Integer.parseInt(updateBookingViewPage.getNewRoom().getText());

            int newPayment = 0;
            if (updateBookingViewPage.getNewPayment().isSelected()) {
                newPayment = 1;
            }

            // Getting the datepicker dates
            DatePicker checkInDP = updateBookingViewPage.getNewFromDate();
            LocalDate datePicked = checkInDP.getValue();
            String formattedDate = datePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date sqlDate = java.sql.Date.valueOf(formattedDate);

            DatePicker checkOutDP = updateBookingViewPage.getNewTillDate();

            LocalDate secondDatePicked = checkOutDP.getValue();
            String secondFormattedDate = secondDatePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date secondSQLDate = java.sql.Date.valueOf(secondFormattedDate);

            int bookingFee = Integer.parseInt(updateBookingViewPage.getNewFee().getText());
            int isPaid = 0;
            if (updateBookingViewPage.getNewIsPaid().isSelected()) {
                isPaid = 1;
            }

            Integer new_C_ss_number = updateBookingViewPage.getC_ss_number().getValue();

            booking.setR_num(newRoom);
            booking.setPaid_by_card(newPayment);
            booking.setB_from(sqlDate);
            booking.setB_till(secondSQLDate);
            booking.setB_fee(bookingFee);
            booking.setB_is_paid(isPaid);
            booking.setC_ss_number(new_C_ss_number);

            hdata.updateBooking(booking);
            updateRoomStage.close();
            formerStage.close();
            bookingsDisplay();
        });

        updateRoomStage.setOnCloseRequest(e -> updateRoomStage.close());

        updateRoomStage.setScene(updateBookingViewPage.getScene());
        updateRoomStage.setTitle("Hotel Manager - Updating Booking");

        // Specifies the modality for new window and the owner of window
        updateRoomStage.initOwner(formerStage);
        updateRoomStage.initModality(Modality.WINDOW_MODAL);

        updateRoomStage.show();
    }

    /**
     * Displays the page with the hotel's bookings table
     */

    private void bookingsDisplay() {
        List<Booking> bookings = hdata.getBookings();
        BookingsView bookingsViewPage = new BookingsView(connectedUser, bookings);
        Stage bookingsStage = new Stage();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // admins can add a booking
        if (connectedUser.getU_is_admin() == 1) {
            bookingsViewPage.getAddBooking().setOnAction(e -> newBookingDisplay(bookingsStage));
        }

        bookingsViewPage.getBookingsTable().setRowFactory(tableView -> {
            final TableRow<Booking> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();

            MenuItem updateItem = new MenuItem("Update");
            updateItem.setOnAction(event -> {
                Booking b = bookingsViewPage.getBookingsTable().getSelectionModel().getSelectedItem();
                updateBookingDisplay(bookingsStage, b);
            });

            rowMenu.getItems().addAll(updateItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));
            return row;
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
        RoomsView roomsViewPage = new RoomsView(connectedUser, rooms, hdata);
        Stage roomsStage = new Stage();

        // admins can add, update or delete a room through the context menu
        if (connectedUser.getU_is_admin() == 1) {
            roomsViewPage.getAddRoom().setOnAction(e -> newRoomDisplay(roomsStage));
            roomsViewPage.getRoomsTable().setRowFactory(tableView -> {
                final TableRow<Room> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();

                MenuItem updateItem = new MenuItem("Update");
                updateItem.setOnAction(event -> {
                    Room u = roomsViewPage.getRoomsTable().getSelectionModel().getSelectedItem();
                    updateRoomDisplay(roomsStage, u);
                });

                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setOnAction(event -> {
                    Room r = roomsViewPage.getRoomsTable().getSelectionModel().getSelectedItem();
                    deleteRoomDisplay(roomsStage, r);
                });

                MenuItem viewItem = new MenuItem("View details");
                viewItem.setOnAction(event -> {
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

                    newWindow.initOwner(roomsStage);
                    newWindow.initModality(Modality.WINDOW_MODAL);

                    // Set position of second window, related to primary window.
                    newWindow.setX(roomsStage.getX());
                    newWindow.setY(roomsStage.getY());

                    newWindow.show();
                });

                rowMenu.getItems().addAll(updateItem, deleteItem, viewItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
                        .otherwise((ContextMenu) null));
                return row;
            });
        }

        // reception staff can only view details through the context menu
        if (connectedUser.getU_is_admin() == 0) {
            roomsViewPage.getRoomsTable().setRowFactory(tableView -> {
                final TableRow<Room> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();

                MenuItem viewItem = new MenuItem("View details");
                viewItem.setOnAction(event -> {
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

                    newWindow.initOwner(roomsStage);
                    newWindow.initModality(Modality.WINDOW_MODAL);

                    // Set position of second window, related to primary window.
                    newWindow.setX(roomsStage.getX());
                    newWindow.setY(roomsStage.getY());

                    newWindow.show();
                });

                rowMenu.getItems().addAll(viewItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
                        .otherwise((ContextMenu) null));
                return row;
            });
        }
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
        ArrayList<Room> rooms = hdata.getRooms();
        UpdateRoomView updateRoomViewPage = new UpdateRoomView(hdata);
        Stage updateRoomStage = new Stage();
        String currentRoomNumber = String.valueOf(room.getR_num());
        String currentFloor = String.valueOf(room.getR_floor());

        updateRoomViewPage.getNumRoom().setText(currentRoomNumber);
        updateRoomViewPage.getFloor().setText(currentFloor);
        updateRoomViewPage.getRoomType().setValue(room.getR_type());

        updateRoomViewPage.getSubmit().setOnAction(e -> {
            int roomNb = Integer.parseInt(updateRoomViewPage.getNumRoom().getText());
            int roomFloor = Integer.parseInt(updateRoomViewPage.getFloor().getText());
            String roomType = updateRoomViewPage.getRoomType().getValue();

            Room updateRoom = new Room(roomNb, roomFloor, roomType);

            boolean flag = true;

            for (Room r : rooms) {
                if (r.getR_num() == room.getR_num()) {
                    flag = true;
                    continue;
                }
                if (r.getR_num() == updateRoom.getR_num()) {
                    flag = false;

                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Room number already exists. Add a different one or update existing room!");
                    a.showAndWait();

                    break;
                }
            }
            if (updateRoom.getR_num() == room.getR_num() || flag) {
                hdata.updateRoom(updateRoom, room.getR_num());
                updateRoomStage.close();
                formerStage.close();
                roomsDisplay();
            }

        });

        updateRoomStage.setOnCloseRequest(e -> updateRoomStage.close());

        updateRoomStage.setScene(updateRoomViewPage.getScene());
        updateRoomStage.setTitle("Hotel Manager - Updating Room");

        // Specifies the modality for new window and the owner of window
        updateRoomStage.initOwner(formerStage);
        updateRoomStage.initModality(Modality.WINDOW_MODAL);

        updateRoomStage.show();
    }

    /**
     * Display to delete the selected room
     */
    private void deleteRoomDisplay(Stage formerStage, Room room) {

        DeleteRoomView deleteRoomViewPage = new DeleteRoomView();
        Stage deleteRoomStage = new Stage();

        deleteRoomViewPage.getSubmit().setOnAction(e -> {
            hdata.deleteRoom(room);
            deleteRoomStage.close();
            formerStage.close();
            roomsDisplay();
        });

        deleteRoomStage.setOnCloseRequest(e -> deleteRoomStage.close());

        // Specifies the modality for new window and the owner of window
        deleteRoomStage.initOwner(formerStage);
        deleteRoomStage.initModality(Modality.WINDOW_MODAL);

        deleteRoomStage.setScene(deleteRoomViewPage.getScene());
        deleteRoomStage.setTitle("Hotel Manager - Deleting Room");
        deleteRoomStage.show();
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

        customersViewPage.getCustomersTable().setRowFactory(tableView -> {
            final TableRow<Customer> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();

            MenuItem updateItem = new MenuItem("Update");
            updateItem.setOnAction(event -> {
                Customer c = customersViewPage.getCustomersTable().getSelectionModel().getSelectedItem();
                updateCustomerDisplay(customerStage, c);
            });

            rowMenu.getItems().addAll(updateItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));
            return row;
        });

        customerStage.setScene(customersViewPage.getScene());
        customerStage.setTitle("Hotel Manager - Customers");
        customerStage.show();
    }

    private void newCustomerDisplay(Stage formerStage) {
        NewCustomerView newCustomerViewPage = new NewCustomerView();
        Stage newCustomerStage = new Stage();

        int LENGTH_PERSONAL_NUMBER = 8;
        int LENGTH_PHONE_NUMBER = 9;

        Alert warningPersonalNumber = new Alert(AlertType.WARNING, String.format("Enter a number consisting of %d digits.", LENGTH_PERSONAL_NUMBER));
        Alert warningPhoneNumber = new Alert(AlertType.WARNING, String.format("Enter a number consisting of %d digits.", LENGTH_PHONE_NUMBER));

        // Error handling
        if (newCustomerViewPage.getCSSNum().getText().equals("") ||
                newCustomerViewPage.getCAddress().getText().equals("") ||
                newCustomerViewPage.getCFullName().getText().equals("") ||
                newCustomerViewPage.getCPhoneNum().getText().equals("") ||
                newCustomerViewPage.getCEmail().getText().equals("")) {
            newCustomerViewPage.getSubmit().setDisable(true);
        } else {
            newCustomerViewPage.getSubmit().setDisable(false);
        }

        // Error handling
        newCustomerViewPage.getCSSNum().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isNumeric(newValue) ||
                    newValue.length() != LENGTH_PERSONAL_NUMBER) {
                newCustomerViewPage.getSubmit().setDisable(true);
            } else {
                if (!newCustomerViewPage.getCAddress().getText().equals("") &&
                        !newCustomerViewPage.getCFullName().getText().equals("") &&
                        isNumeric(newCustomerViewPage.getCPhoneNum().getText()) &&
                        newCustomerViewPage.getCPhoneNum().getText().length() == LENGTH_PHONE_NUMBER &&
                        !newCustomerViewPage.getCEmail().getText().equals("")) {
                    newCustomerViewPage.getSubmit().setDisable(false);
                }
            }
            if ((!isNumeric(newValue) && isNumeric(oldValue)) ||
                    (!isNumeric(newValue) && oldValue.equals("")) ||
                    (newValue.length() != LENGTH_PERSONAL_NUMBER && oldValue.length() == LENGTH_PERSONAL_NUMBER)) {
                warningPersonalNumber.showAndWait();
            }
        });

        // Error handling
        newCustomerViewPage.getCAddress().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                newCustomerViewPage.getSubmit().setDisable(true);
            } else {
                if (isNumeric(newCustomerViewPage.getCSSNum().getText()) &&
                        newCustomerViewPage.getCSSNum().getText().length() == LENGTH_PERSONAL_NUMBER &&
                        !newCustomerViewPage.getCFullName().getText().equals("") &&
                        isNumeric(newCustomerViewPage.getCPhoneNum().getText()) &&
                        newCustomerViewPage.getCPhoneNum().getText().length() == LENGTH_PHONE_NUMBER &&
                        !newCustomerViewPage.getCEmail().getText().equals("")) {
                    newCustomerViewPage.getSubmit().setDisable(false);
                }
            }
        });

        // Error handling
        newCustomerViewPage.getCFullName().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                newCustomerViewPage.getSubmit().setDisable(true);
            } else {
                if (isNumeric(newCustomerViewPage.getCSSNum().getText()) &&
                        newCustomerViewPage.getCSSNum().getText().length() == LENGTH_PERSONAL_NUMBER &&
                        !newCustomerViewPage.getCAddress().getText().equals("") &&
                        isNumeric(newCustomerViewPage.getCPhoneNum().getText()) &&
                        newCustomerViewPage.getCPhoneNum().getText().length() == LENGTH_PHONE_NUMBER &&
                        !newCustomerViewPage.getCEmail().getText().equals("")) {
                    newCustomerViewPage.getSubmit().setDisable(false);
                }
            }
        });

        // Error handling
        newCustomerViewPage.getCPhoneNum().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isNumeric(newValue) ||
                    newValue.length() != LENGTH_PHONE_NUMBER) {
                newCustomerViewPage.getSubmit().setDisable(true);
            } else {
                if (isNumeric(newCustomerViewPage.getCSSNum().getText()) &&
                        newCustomerViewPage.getCSSNum().getText().length() == LENGTH_PERSONAL_NUMBER &&
                        !newCustomerViewPage.getCAddress().getText().equals("") &&
                        !newCustomerViewPage.getCFullName().getText().equals("") &&
                        !newCustomerViewPage.getCEmail().getText().equals("")) {
                    newCustomerViewPage.getSubmit().setDisable(false);
                }
            }
            if ((!isNumeric(newValue) && isNumeric(oldValue)) ||
                    (!isNumeric(newValue) && oldValue.equals("")) ||
                    (newValue.length() != LENGTH_PHONE_NUMBER && oldValue.length() == LENGTH_PHONE_NUMBER)) {
                warningPhoneNumber.showAndWait();
            }
        });

        // Error handling
        newCustomerViewPage.getCEmail().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                newCustomerViewPage.getSubmit().setDisable(true);
            } else {
                if (isNumeric(newCustomerViewPage.getCSSNum().getText()) &&
                        newCustomerViewPage.getCSSNum().getText().length() == LENGTH_PERSONAL_NUMBER &&
                        !newCustomerViewPage.getCAddress().getText().equals("") &&
                        !newCustomerViewPage.getCFullName().getText().equals("") &&
                        isNumeric(newCustomerViewPage.getCPhoneNum().getText()) &&
                        newCustomerViewPage.getCPhoneNum().getText().length() == LENGTH_PHONE_NUMBER) {
                    newCustomerViewPage.getSubmit().setDisable(false);
                }
            }
        });

        // set buttons on action

        newCustomerViewPage.getSubmit().setOnAction(e -> {
            int cSSNum = Integer.parseInt(newCustomerViewPage.getCSSNum().getText());
            String cAddress = newCustomerViewPage.getCAddress().getText();
            String cFullName = newCustomerViewPage.getCFullName().getText();
            int cPhoneNum = Integer.parseInt(newCustomerViewPage.getCPhoneNum().getText());
            String cEmail = newCustomerViewPage.getCEmail().getText();

            Customer newCustomer = new Customer(cSSNum, cAddress, cFullName, cPhoneNum, cEmail);
            if (!dbm.cdb.customerExists(newCustomer)) {
                hdata.addCustomer(newCustomer);

                customersDisplay();
                newCustomerStage.close();
            } else {
                Alert errorCustomerExists = new Alert(AlertType.ERROR, "A customer with this social security number already exists in the database.");
                errorCustomerExists.showAndWait();
            }

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

        updateCustomerViewPage.getCSSNum().setText(String.valueOf(customer.getC_ss_number()));
        updateCustomerViewPage.getCAddress().setText(customer.getC_address());
        updateCustomerViewPage.getCFullName().setText(customer.getC_full_name());
        updateCustomerViewPage.getCPhoneNum().setText(String.valueOf(customer.getC_phone_num()));
        updateCustomerViewPage.getCEmail().setText(customer.getC_email());


        updateCustomerViewPage.getSubmit().setOnAction(e -> {
            int cSSNum = Integer.parseInt(updateCustomerViewPage.getCSSNum().getText());
            String cAddress = updateCustomerViewPage.getCAddress().getText();
            String cFullName = updateCustomerViewPage.getCFullName().getText();
            int cPhoneNum = Integer.parseInt(updateCustomerViewPage.getCPhoneNum().getText());
            String cEmail = updateCustomerViewPage.getCEmail().getText();

            Customer updatedCustomer = new Customer(cSSNum, cAddress, cFullName, cPhoneNum, cEmail);
            hdata.updateCustomer(updatedCustomer, customer.getC_ss_number());

            updateCustomerStage.close();
            formerStage.close();
            customersDisplay();
        });

        updateCustomerStage.setOnCloseRequest(e -> updateCustomerStage.close());

        updateCustomerStage.setScene(updateCustomerViewPage.getScene());
        updateCustomerStage.setTitle("Hotel Manager - Updating Customer");

        // Specifies the modality for new window and the owner of window
        updateCustomerStage.initOwner(formerStage);
        updateCustomerStage.initModality(Modality.WINDOW_MODAL);

        updateCustomerStage.show();
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
