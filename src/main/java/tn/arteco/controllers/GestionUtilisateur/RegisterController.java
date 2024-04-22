package tn.arteco.controllers.GestionUtilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.arteco.models.Artiste;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.services.UserService;
import tn.arteco.utils.HashPassword;

import java.io.IOException;

public class RegisterController {
    @FXML
    ImageView iv;
    @FXML
    VBox formBox;
    @FXML
    BorderPane parentNode;
    @FXML
    TextField usernameField;
    @FXML
    TextField nomField;
    @FXML
    TextField prenomField;
    @FXML
    TextField emailField;
    @FXML
    CheckBox roleCheck;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    Button enregistreB;
    @FXML
    Label errorL;
    @FXML
    public void initialize(){
        parentNode.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register();
            }
        });
        parentNode.widthProperty().addListener((obs,oldval,newval)->{
            formBox.setPrefWidth(newval.doubleValue()/2);
        });
        DropShadow dropShadow=new DropShadow(50,Color.BLACK);
        iv.setEffect(dropShadow);
        parentNode.widthProperty().addListener((obs, oldVal, newVal) -> {
            iv.setFitWidth((double)newVal*1.25);

        });

        parentNode.heightProperty().addListener((obs, oldVal, newVal) -> {
            iv.setFitHeight((double)newVal*1.25);
        });
        errorL.setManaged(false);
    }
    @FXML
    public void routeToLogin(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void register(){
        UserService userService=new UserService();
        String username = usernameField.getText();
        String nom= nomField.getText();
        String prenom= prenomField.getText();
        String email= emailField.getText();
        String password= passwordField.getText();
        String confirmedPassword=confirmPasswordField.getText();

        if(userService.usernameExists(username)) {
            errorL.setText("Username already exists");
            usernameField.setStyle("-fx-border-color: #DC143C");
            errorL.setManaged(true);
        } else if(username.isEmpty()) {
            errorL.setText("username empty");
            usernameField.setStyle("-fx-border-color: #DC143C");
            errorL.setManaged(true);
        } else if(nom.isEmpty() || prenom.isEmpty()) {
            usernameField.setStyle("-fx-border-color: black");
            nomField.setStyle("-fx-border-color: #DC143C");
            prenomField.setStyle("-fx-border-color: #DC143C");
            errorL.setText("name is empty");
            errorL.setManaged(true);
        }
        else if(!email.matches("[A-Za-z.]+@[A-Za-z]+.[a-zA-Z]{2,7}")) {
            errorL.setText("invalid email");
            nomField.setStyle("-fx-border-color: black");
            prenomField.setStyle("-fx-border-color: black");
            passwordField.setStyle("-fx-border-color: black");
            confirmPasswordField.setStyle("-fx-border-color: black");
            emailField.setStyle("-fx-border-color: #DC143C");
            errorL.setManaged(true);
        }else if(userService.emailExists(email)) {
            errorL.setText("email already exists");
            nomField.setStyle("-fx-border-color: black");
            prenomField.setStyle("-fx-border-color: black");
            passwordField.setStyle("-fx-border-color: black");
            confirmPasswordField.setStyle("-fx-border-color: black");
            emailField.setStyle("-fx-border-color: #DC143C");
            errorL.setManaged(true);
        }
        else if(email.isEmpty()) {
            errorL.setText("email is empty");
            emailField.setStyle("-fx-border-color: #DC143C");
            errorL.setManaged(true);
        }else if(!password.equals(confirmedPassword) || password.isEmpty()) {
            errorL.setText("passwords arent equal");
            nomField.setStyle("-fx-border-color: black");
            prenomField.setStyle("-fx-border-color: black");
            passwordField.setStyle("-fx-border-color: #DC143C");
            confirmPasswordField.setStyle("-fx-border-color: #DC143C");
            errorL.setManaged(true);
        }
        else{
            errorL.setManaged(false);
            User user=null;
            if(roleCheck.isSelected()){
                user =new Artiste(username,nom,prenom, HashPassword.hashPassword(password),email,"http://localhost/images/user.png");
            }
            else{
                user =new NonArtiste(username,nom,prenom, HashPassword.hashPassword(password),email,"http://localhost/images/user.png");
            }
            userService.add(user);
            try{
                Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
                parentNode.getScene().setRoot(root);
            }catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    public void resetBorderColor(){
        usernameField.setStyle("-fx-border-color: black");
        nomField.setStyle("-fx-border-color: black");
        prenomField.setStyle("-fx-border-color: black");
        passwordField.setStyle("-fx-border-color: black");
        confirmPasswordField.setStyle("-fx-border-color: black");
        emailField.setStyle("-fx-border-color: black");
    }
}
