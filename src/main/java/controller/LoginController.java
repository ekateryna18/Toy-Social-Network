package controller;
import domain.Utilizator;
import domain.validators.FriendshipValidator;
import domain.validators.UtilizatorValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import repository.database.DBRepositoryCredentials;
import repository.database.FriendshipDBRepo;
import repository.database.RequestsDBRepository;
import repository.database.UserDBRepository;
import service.Service;
import service.ServiceCredentials;
import service.ServiceFriendships;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    private ServiceCredentials srv;

    public void setService(ServiceCredentials srv){
        this.srv = srv;
    }
    public void login(ActionEvent event) throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();
        Utilizator u = srv.loginUser(username,password);
        if(u == null){
            loginLabel.setText("Username or password incorrect");
            loginLabel.setTextFill(Paint.valueOf("red"));
        }
        else try {
            loginLabel.setText("Succesfully logging in!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/MainWindowView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainWindController mainController = fxmlLoader.getController();
            UserDBRepository userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UtilizatorValidator());
            FriendshipDBRepo frRepo = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendshipValidator());
            RequestsDBRepository reqRepo = new RequestsDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres");
            mainController.setService(new Service(userRepo,frRepo),new ServiceFriendships(frRepo,reqRepo),u.getFirstName(),username,u.getId());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Social-network-app");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(ActionEvent event) throws IOException{
       try {
           FXMLLoader fxmlLoader = new FXMLLoader();
           fxmlLoader.setLocation(getClass().getResource("/view/RegisterView.fxml"));
           Scene scene = new Scene(fxmlLoader.load());
           RegisterController registerController = fxmlLoader.getController();
           /*UserDBRepository userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UtilizatorValidator());
           DBRepositoryCredentials credRepo = new DBRepositoryCredentials("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres");
           registerController.setService(new ServiceCredentials(userRepo,credRepo));*/
           registerController.setService(srv);
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           stage.setTitle("Social-network-app");
           stage.setScene(scene);
           stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
