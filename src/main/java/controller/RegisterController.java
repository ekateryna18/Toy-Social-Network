package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import service.ServiceCredentials;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class RegisterController {
    @FXML
    private ImageView myImageView;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Button finalRegisterButton;
    @FXML
    private Label finalLabel;
    private ServiceCredentials service;

    public void setService(ServiceCredentials service){
        this.service = service;
    }
    public void finalregister(ActionEvent event) throws Exception {
        try {
            //service = new ServiceCredentials;
            String name = nameText.getText();
            String surname = surnameText.getText();
            String username = usernameText.getText();
            String password = passwordText.getText();
            if(name.equals("") || surname.equals("") || username.equals("") || password.equals("")){
                finalLabel.setVisible(true);
                finalLabel.setText("Blocks can't be empty");
                finalLabel.setTextFill(Paint.valueOf("red"));
            }
            else if (username.contains(" ")) {
                finalLabel.setVisible(true);
                finalLabel.setText("Username can't contain spaces");
                finalLabel.setTextFill(Paint.valueOf("red"));
            } else if (password.contains(" ")) {
                finalLabel.setVisible(true);
                finalLabel.setText("Password can't contain spaces");
                finalLabel.setTextFill(Paint.valueOf("red"));
            } else {
                finalLabel.setVisible(true);
                service.registerUser(name, surname, username, password);
                finalLabel.setText("Thanks for registering!");
            }

    }catch(Exception igonred) {
        igonred.printStackTrace();
        }
    }
}
