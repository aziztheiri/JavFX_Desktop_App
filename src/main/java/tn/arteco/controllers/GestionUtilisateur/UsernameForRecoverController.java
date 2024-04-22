package tn.arteco.controllers.GestionUtilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.arteco.controllers.GestionUtilisateur.CodeFormController;
import tn.arteco.models.UserRequest;
import tn.arteco.services.UserRequestService;
import tn.arteco.services.UserService;

import java.io.IOException;

public class UsernameForRecoverController {
    @FXML
    TextField usernameTF;
    @FXML
    Label errorL;
    @FXML
    BorderPane parentNode;
    @FXML
    VBox formBox;
    @FXML
    ImageView iv;
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
    public void routeToReset(){
        UserService userService=new UserService();
        if(userService.getUserByUsername(usernameTF.getText())==null){
            errorL.setVisible(true);
            errorL.setManaged(true);
            errorL.setText("Username does not exist");
        }
        else if(!userService.getUserByUsername(usernameTF.getText()).isEtat()){
            UserRequestService userRequestService=new UserRequestService();
            userRequestService.add(new UserRequest(0,"activation","pending",userService.getUserByUsername(usernameTF.getText())));
            try{
                Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
                parentNode.getScene().setRoot(root);
            }catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
        else {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/GestionUtilisateur/CodeForm.fxml"));
            CodeFormController codeFormController=new CodeFormController(userService.getUserByUsername(usernameTF.getText()),true);
            fxmlLoader.setController(codeFormController);
            try{
                Parent root = fxmlLoader.load();
                parentNode.getScene().setRoot(root);
                return;
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
