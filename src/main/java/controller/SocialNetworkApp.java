package controller;

import domain.validators.FriendshipValidator;
import domain.validators.UtilizatorValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.database.DBRepositoryCredentials;
import repository.database.FriendshipDBRepo;
import repository.database.UserDBRepository;
import service.Service;
import service.ServiceCredentials;

import java.io.IOException;

public class SocialNetworkApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("/view/LoginView.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/view/MainWindowView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //LoginController loginController = fxmlLoader.getController();
        /*UserDBRepository userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UtilizatorValidator());
        DBRepositoryCredentials credRepo = new DBRepositoryCredentials("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres");
        loginController.setService(new ServiceCredentials(userRepo,credRepo));*/
        MainWindController mainController = fxmlLoader.getController();
        String name = "Ecaterina";
        UserDBRepository userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UtilizatorValidator());
        FriendshipDBRepo frRepo = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendshipValidator());
        mainController.setService(new Service(userRepo,frRepo),name,"fati");
        stage.setTitle("Social-network-app");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
