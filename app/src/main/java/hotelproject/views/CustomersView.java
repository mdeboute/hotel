package hotelproject.views;

import hotelproject.controllers.objects.Customer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;

public class CustomersView extends View {

    private final ObservableList<Customer> customers;
    private final Button addCustomer = new Button("New customer...");
    private final ImageView imgAddCustomer = new ImageView(new Image("file:assets/img/ui_dev_pack/customer_menu/idle_button_new_customer.png"));
    private final Button updateCustomer = new Button("Update customer...");
    public TableView<Customer> customersTable = new TableView<>();

    public CustomersView(List<Customer> customers) {
        this.customers = FXCollections.observableList(customers);
        createScene();
    }

    @Override
    void createScene() {
        GridPane bodyPane = createBody();
        GridPane.setHalignment(bodyPane, javafx.geometry.HPos.CENTER);

        bodyPane.getStyleClass().add("body-pane");
        customersTable.getStyleClass().add("table-view");

        scene = new Scene(bodyPane);
        scene.getStylesheets().add("file:assets/css/Stylesheet.css");
    }

    @Override
    GridPane createBody() {
        GridPane pane = createPane();

        Label title = new Label("Hotel customers");
        title.setFont(Font.loadFont("file:assets/font/SF_Pro.ttf", 25));
        title.setStyle("-fx-font-weight: bold;");
        title.setTextFill(Paint.valueOf("bb86fc"));

        customersTable.setEditable(true);

        // Create column in the table
        TableColumn<Customer, Integer> cSSNumCol = new TableColumn<>("Customer's social security number");
        cSSNumCol.setMinWidth(200);
        cSSNumCol.setCellValueFactory(new PropertyValueFactory<>("c_ss_number"));

        TableColumn<Customer, String> cAddressCol = new TableColumn<>("Address");
        cAddressCol.setMinWidth(200);
        cAddressCol.setCellValueFactory(new PropertyValueFactory<>("c_address"));

        TableColumn<Customer, String> cFullNameCol = new TableColumn<>("Full name");
        cFullNameCol.setMinWidth(200);
        cFullNameCol.setCellValueFactory(new PropertyValueFactory<>("c_full_name"));

        TableColumn<Customer, Integer> cNumCol = new TableColumn<>("Phone number");
        cNumCol.setMinWidth(150);
        cNumCol.setCellValueFactory(new PropertyValueFactory<>("c_phone_num"));

        TableColumn<Customer, String> cEmailCol = new TableColumn<>("Email");
        cEmailCol.setMinWidth(200);
        cEmailCol.setCellValueFactory(new PropertyValueFactory<>("c_email"));

        // Create a filtered list to put the rooms as items in the table
        FilteredList<Customer> flCustomer = new FilteredList<>(customers, p -> true);
        customersTable.setItems(flCustomer);
        customersTable.getColumns().addAll(cSSNumCol, cAddressCol, cFullNameCol, cNumCol, cEmailCol);

        // Create choice box so the user can choose on the column he's searching in
        ChoiceBox<String> whatToSearch = new ChoiceBox<>();
        whatToSearch.getItems().addAll("Customer's social security number", "Full name", "Phone number");
        whatToSearch.setValue("Full name"); // default search

        // Create search bar with listener to update according to the user's input
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here!");
        searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (whatToSearch.getValue().equals("Customer's social security number")) {
                flCustomer.setPredicate(p -> String.valueOf(p.getC_ss_number()).contains(newValue.toLowerCase().trim()));
            } else if (whatToSearch.getValue().equals("Full name")) {
                flCustomer.setPredicate(p -> p.getC_full_name().toLowerCase().contains(newValue.toLowerCase().trim()));
            } else if (whatToSearch.getValue().equals("Phone number")) {
                flCustomer.setPredicate(p -> String.valueOf(p.getC_phone_num()).contains(newValue.toLowerCase().trim()));
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
        pane.add(customersTable, 0, 4);
        addCustomer.setCursor(Cursor.HAND);
        StackPane addCustomerStack = createButton(imgAddCustomer, addCustomer, 200, 35);
        pane.add(addCustomerStack, 0, 5);

        updateCustomer.disableProperty().bind(Bindings.isEmpty(customersTable.getSelectionModel().getSelectedItems()));
        pane.add(updateCustomer, 0, 6);

        return pane;
    }

    /**************************Getter**********************/

    public Button getAddCustomer() {
        return addCustomer;
    }

    public TableView<Customer> getCustomersTable() {
        return customersTable;
    }

    public Button getUpdateCustomer() {
        return updateCustomer;
    }

}   
