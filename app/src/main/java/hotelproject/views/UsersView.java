package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.List;

public class UsersView extends View {

    // The user connected to the application
    private final User user;

    // Observable list with all the hotel's rooms
    private final ObservableList<User> users;

    // The scene's nodes
    private final TableView<User> usersTable = new TableView<>();
    private final String IDLE_ADD_USER = "file:assets/img/ui_dev_pack/user_menu/idle_button_new_user.png";
    private final String HOVER_ADD_USER = "file:assets/img/ui_dev_pack/user_menu/hover_button_new_user.png";
    private Button addUser;

    public UsersView(User user, List<User> users) {
        this.user = user;
        this.users = FXCollections.observableList(users);
        createScene();
    }

    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, javafx.geometry.HPos.CENTER);

        bodyPane.getStyleClass().add("body-pane");
        usersTable.getStyleClass().add("table-view");

        scene = new Scene(bodyPane);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        Label title = new Label("Hotel users");
        title.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 25));
        title.getStyleClass().add("purple");

        usersTable.setEditable(true);

        // Create column in the table
        TableColumn<User, String> userNCol = new TableColumn<>("Username");
        userNCol.setMinWidth(100);
        userNCol.setCellValueFactory(new PropertyValueFactory<>("u_name"));

        TableColumn<User, String> userPCol = new TableColumn<>("Password");
        userPCol.setMinWidth(200);
        userPCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("*".repeat(20)));

        TableColumn<User, String> userIACol = new TableColumn<>("Is admin");
        userIACol.setMinWidth(100);
        userIACol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().is_admin()));

        // Create a filtered list to put the users as items in the table
        FilteredList<User> flUser = new FilteredList<>(users, p -> true);
        usersTable.setItems(flUser);
        usersTable.getColumns().addAll(userNCol, userPCol, userIACol);

        // Create choice box so the user can choose on the column they are searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox<>();
        whatToSearch.getItems().addAll("Username");
        whatToSearch.getItems().addAll("Admin");
        whatToSearch.setValue("Username"); // default search


        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (whatToSearch.getValue().equals("Username")) {
                flUser.setPredicate(p -> String.valueOf(p.getU_name()).contains(newValue.toLowerCase().trim()));                                                                                        // number
            } else if (whatToSearch.getValue().equals("Admin")) {
                flUser.setPredicate(p -> p.is_admin().contains(newValue.toLowerCase().trim()));                                                                                        // number
            }
        });

        //When the new choice is selected we reset
        whatToSearch.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {
            if (newVal != null) {
                searchBar.setText("");
            }
        });

        HBox search = new HBox(whatToSearch, searchBar);
        search.setAlignment(Pos.CENTER);
        search.getStyleClass().add("search");

        pane.add(title, 0, 0);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
        pane.add(search, 0, 2);
        pane.add(usersTable, 0, 4);

        addUser = createButton(35, IDLE_ADD_USER, HOVER_ADD_USER);
        if (user.getU_is_admin() == 1) {
            pane.add(addUser, 0, 5);
            GridPane.setHalignment(addUser, javafx.geometry.HPos.CENTER);
        }

        return pane;
    }

    /**************************Getter**********************/

    public Button getAddUser() {
        return addUser;
    }
}