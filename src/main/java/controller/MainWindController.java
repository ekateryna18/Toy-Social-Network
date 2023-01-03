package controller;

import domain.FriendRequest;
import domain.Friendship;
import domain.Utilizator;
import domain.validators.FriendshipValidator;
import domain.validators.UtilizatorValidator;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import repository.database.DBRepositoryCredentials;
import repository.database.FriendshipDBRepo;
import repository.database.RequestsDBRepository;
import repository.database.UserDBRepository;
import service.Service;
import service.ServiceCredentials;
import service.ServiceFriendships;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainWindController {
    ObservableList<Utilizator> modelUser = FXCollections.observableArrayList();
    private String usernameMain;
    private String firstnameMain;
    private Long ID;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button yesAddButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button declineButton;
    @FXML
    private Button showButton;
    @FXML
    private Button requestsButton;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Utilizator> tableV;
    @FXML
    private TableColumn<Utilizator,String> nameCol;
    @FXML
    private TableColumn<Utilizator,String> surnameCol;
    @FXML
    private TableColumn<Utilizator,String> usernameCol;
    private Service srv;
    private ServiceFriendships srvFr;
    private ChangeListener<Utilizator> userListener ;

    public void setService(Service srv ,ServiceFriendships srvFr,String n, String u, Long id){
        this.srv = srv;
        this.srvFr = srvFr;
        this.ID = id;
        this.usernameMain = u;
        this.firstnameMain = n;
        nameLabel.setText(this.firstnameMain);
        usernameLabel.setText(this.usernameMain);
        modelUser.setAll(getUsers());
    }
    @FXML
    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("FirstName"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("LastName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("Username"));
        tableV.setItems(modelUser);
        searchField.textProperty().addListener(o -> handleFilter());
    }

    private List<Utilizator> getUsers(){
        List<Utilizator> users = new ArrayList<>();
        for(Utilizator u : srv.getAll_Users()){
            users.add(u);
        }
        return users;
    }

    private void handleFilter(){
        Predicate<Utilizator> u = n -> n.getUsername().startsWith(searchField.getText());
        modelUser.setAll(getUsers()
                .stream()
                .filter(u)
                .collect(Collectors.toList()));
    }

    public void handleAddFriend(ActionEvent event){
        modelUser.setAll(srv.getNONFriends(this.usernameMain));
        infoLabel.setVisible(true);
        infoLabel.setText("Select user to add as friend!");
        yesAddButton.setVisible(true);
    }

    public void handleAcceptAddFriend(ActionEvent event){
        Utilizator selected = (Utilizator) tableV.getSelectionModel().getSelectedItem();
        if(selected != null){
            String msg = srvFr.send_request(this.ID,selected.getId());
            if(msg.equals("accepted")){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Added", "You are now friends!");
            }
            else if(msg.equals("pending")){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Sent", "Friend request sent!");
            }
            else if(msg.equals("sent")){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Already", "Request already sent!");
            }
        }else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!");
        yesAddButton.setVisible(false);
        infoLabel.setVisible(false);
    }
    @FXML public void handleShowFriends(ActionEvent event){
        modelUser.setAll(srv.getFriends(this.usernameMain));
    }

    @FXML public void handleFriendRequest(ActionEvent event) {
        List<FriendRequest> pending = srvFr.getUserRequests(this.ID);
        List<Utilizator> users = new ArrayList<>();
        for(FriendRequest fr: pending){
            users.add(srv.findUser(fr.getId_sender()));
        }
        modelUser.setAll(users);
        acceptButton.setVisible(true);
        cancelButton.setVisible(true);
        declineButton.setVisible(true);
        infoLabel.setVisible(true);
        infoLabel.setText("Request sent at : ");
        userListener =  (obs, oldSelection, newSelection) -> {
            Utilizator u = tableV.getSelectionModel().getSelectedItem();
            if (u != null) {
                infoLabel.setText("Request sent at: " + srvFr.getReq(u.getId(),this.ID).getTime_sent());
                //tableV.getSelectionModel().clearSelection();
            }};
        tableV.getSelectionModel().selectedItemProperty().addListener(userListener);
        //Utilizator selected = (Utilizator) tableV.getSelectionModel().getSelectedItem();
        //infoLabel.setText("Request sent at: " + srvFr.getReq(selected.getId(),this.ID));

    }
    @FXML public void acceptReq(ActionEvent event){
        Utilizator selected = (Utilizator) tableV.getSelectionModel().getSelectedItem();
        srvFr.requestResponse(selected.getId(),this.ID,"accepted");
        acceptButton.setVisible(false);
        infoLabel.setVisible(false);
        declineButton.setVisible(false);
        cancelButton.setVisible(false);
    }
    @FXML public void declinetReq(ActionEvent event){
        Utilizator selected = (Utilizator) tableV.getSelectionModel().getSelectedItem();
        srvFr.requestResponse(selected.getId(),this.ID,"declined");
        acceptButton.setVisible(false);
        infoLabel.setVisible(false);
        declineButton.setVisible(false);
        cancelButton.setVisible(false);

    }
    @FXML public void cancelB(ActionEvent event){
        acceptButton.setVisible(false);
        infoLabel.setVisible(false);
        declineButton.setVisible(false);
        cancelButton.setVisible(false);
        //tableV.getSelectionModel().selectedItemProperty().removeListener(userListener);
    }
    @FXML public void handleRemoveFriends(ActionEvent event) throws Exception{
        Utilizator selected = (Utilizator) tableV.getSelectionModel().getSelectedItem();

        if (selected != null)
            try{
                srv.deleteFriendshipSrv(this.ID, selected.getId());
                srvFr.removeReq(this.ID, selected.getId());
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Friend removed!");
            }catch(Exception ex) {
                ex.printStackTrace();
            }
         else MessageAlert.showErrorMessage(null, "You are not friends!");
    }
    @FXML public void logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        UserDBRepository userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UtilizatorValidator());
        DBRepositoryCredentials credRepo = new DBRepositoryCredentials("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres");
        loginController.setService(new ServiceCredentials(userRepo,credRepo));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Social-network-app");
        stage.setScene(scene);
        stage.show();
    }
}
