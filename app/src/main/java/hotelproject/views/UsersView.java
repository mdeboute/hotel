package hotelproject.views;

import hotelproject.controllers.objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class UsersView extends View {
  
  // The user connected to the application
  private User user;

  // Observable list with all the hotel's rooms
  private ObservableList<User> users;

  // The scene's nodes
  private TableView<User> usersTable = new TableView<>();
  private Button addUser = new Button("Add user...");

  public UsersView(User user, List<User> users) {
    this.user = user;
    this.users = FXCollections.observableList(users);
    createScene();
  }

  @Override
    void createScene() {
        GridPane pane = createPane();

        Label title = new Label("Hotel users");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.font(18));

        usersTable.setEditable(true);

        // Create column in the table
        TableColumn userNCol = new TableColumn("Username");
        userNCol.setMinWidth(100);
        userNCol.setCellValueFactory(new PropertyValueFactory<User, String>("u_name"));

        TableColumn userPCol = new TableColumn("User password");
        userPCol.setMinWidth(100);
        userPCol.setCellValueFactory(new PropertyValueFactory<User, String>("u_password"));

        TableColumn userIACol = new TableColumn("Is admin");
        userIACol.setMinWidth(100);
        userIACol.setCellValueFactory(new PropertyValueFactory<User, Integer>("u_is_admin"));

        // Create a filtered list to put the users as items in the table
        FilteredList<User> flUser = new FilteredList(users, p -> true);
        usersTable.setItems(flUser);
        usersTable.getColumns().addAll(userNCol, userPCol, userIACol);

        // Create choice box so the user can choose on the column they are searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox();
        whatToSearch.getItems().addAll("Username", "User password");
        whatToSearch.setValue("Username"); // default search

        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here!");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (whatToSearch.getValue()) //Switch on searchBar value
            {
                case "Username":
                    flUser.setPredicate(p -> String.valueOf(p.getU_name()).contains(newValue.toLowerCase().trim())); //filter table by room number
                    break;
                case "User password":
                    flUser.setPredicate(p -> p.getU_password().toLowerCase().contains(newValue.toLowerCase().trim())); //filter table by room type
                    break;
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

        pane.add(title, 0, 0);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
        pane.add(search, 0, 2);
        pane.add(usersTable, 0, 4);
        if (user.getU_is_admin() == 1) {
            pane.add(addUser, 0, 5);
        }

        scene = new Scene(pane);
    }

    @Override
    GridPane createBody() {
        return null;
    }

    /**************************Getter**********************/

    public Button getAddUser() {
        return addUser;
    }
}