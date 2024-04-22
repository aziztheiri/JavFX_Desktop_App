package tn.arteco.controllers.GestionUtilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.arteco.services.SessionManager;
import tn.arteco.services.UserService;
import tn.arteco.utils.HashPassword;

import java.io.IOException;

public class ResetPasswordController {
    @FXML
    BorderPane parentNode;
    @FXML
    VBox formBox;
    @FXML
    PasswordField confirmPasswordTF;
    @FXML
    PasswordField passwordTF;
    @FXML
    ImageView iv;
    @FXML
    Label errorL;

    public ResetPasswordController(){}
    @FXML
    public void initialize(){
        parentNode.widthProperty().addListener((obs,oldval,newval)->{
            formBox.setPrefWidth(newval.doubleValue()/2);
        });
        DropShadow dropShadow=new DropShadow(50, Color.BLACK);
        iv.setEffect(dropShadow);
        parentNode.widthProperty().addListener((obs, oldVal, newVal) -> {
            iv.setFitWidth((double)newVal*1.25);

        });
        parentNode.heightProperty().addListener((obs, oldVal, newVal) -> {
            iv.setFitHeight((double)newVal*1.25);
        });
        errorL.setManaged(false);
        errorL.setVisible(false);
    }
    @FXML
    public void resetPass(){
        if(!passwordTF.getText().
                equals(confirmPasswordTF.getText()) || passwordTF.getText().isEmpty()){
            errorL.setManaged(true);
            errorL.setVisible(true);
            errorL.setText("Passwords are not equal");
        }

        else {
            SessionManager.getInstance().getCurrentUser().setPassword(HashPassword.hashPassword(passwordTF.getText()));
            UserService userService=new UserService();
            userService.update(SessionManager.getInstance().getCurrentUser());
            SessionManager.getInstance().logout();
            try{
                Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
                parentNode.getScene().setRoot(root);
            }catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
