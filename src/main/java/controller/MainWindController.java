package controller;

import domain.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainWindController {
    ObservableList<Utilizator> modelUser = FXCollections.observableArrayList();
    private String usernameMain;
    private String firstnameMain;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
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

    public void setService(Service srv, String n, String u){
        this.srv = srv;
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
    public void handleDeleteMessage(ActionEvent actionEvent) {
        Utilizator selected = (Utilizator) tableV.getSelectionModel().getSelectedItem();
        /*if(selected != null){
            try{
                srv.deleteUserSrv(selected.getId());
            }catch(Exception ex){
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes!");
            }
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!");*/
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "User succesfully deleted!");

    }
}
