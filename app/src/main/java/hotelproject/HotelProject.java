package hotelproject;

import hotelproject.controllers.User;
import hotelproject.views.Login;
import hotelproject.views.MainPage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class HotelProject extends Application {

    User connectedUser = new User();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        User user = new User("toto", "qwerty", true);

        Login login = new Login(user);
        boolean isConnected = false;

        /**primaryStage.setScene(welcomeSceneBeforeAuth());
        primaryStage.setTitle("Hotel Manager");
        primaryStage.show();**/

        login.getTestLoginButton().setOnAction(e -> { //test if the user exist in the database and has correct password
            if (login.getUsername().getText().equals(user.getUsername()) && login.getPassword().getText().equals(user.getPassword())) {
                login.getResult().setText("Success !");
                connectedUser.setUsername(login.getUsername().getText());
                connectedUser.setPassword(login.getPassword().getText());
                //is user admin ?
                //isConnected = login.isUserConnected();
                primaryStage.close();
                afterAuth(primaryStage);
            }else{
                login.getResult().setText("Fail !");
            }
        });

        primaryStage.setScene(login.getScene());
        primaryStage.setTitle("Login");
        //primaryStage.setOnCloseRequest(e -> );
        primaryStage.show();
    }

    /**private Scene welcomeSceneBeforeAuth() {
        Button quit = new Button("Quit");
        Label welcome = new Label("Welcome to your Hotel Manager");

        Group group = new Group(welcome, quit);

        return new Scene(group,500, 300);
    }**/

    private void afterAuth(Stage primaryStage) {
        MainPage mainPage = new MainPage(connectedUser);

        //button handling here

        primaryStage.setScene(mainPage.getScene());
        primaryStage.setTitle("Hotel Manager");
        primaryStage.show();
    }
}
