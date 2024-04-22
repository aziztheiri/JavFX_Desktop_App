package tn.arteco.controllers.GestionUtilisateur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.arteco.models.Roles;
import tn.arteco.models.User;
import tn.arteco.services.SessionManager;
import tn.arteco.services.TFAFirstLogin;
import tn.arteco.services.TFAemailSenderTask;
import tn.arteco.services.UserService;

import java.io.IOException;


public class CodeFormController {
    private User user;
    private boolean initilized=false;
    private boolean passwordRecover=false;
    private boolean activateAccount=false;
    private String code= TFAFirstLogin.getRandomNumberString();
    @FXML
    TextField codeTF;
    @FXML
    Label errorL;
    @FXML
    HBox errorH;
    @FXML
    BorderPane parentNode;
    @FXML
    VBox formBox;
    @FXML
    ImageView iv;
    String sub;

    public CodeFormController(User user){
        this.user=user;
    }
    public CodeFormController(User user,boolean passwordRecover){
        this.user=user;
        this.passwordRecover=passwordRecover;
    }
    public CodeFormController(User user,boolean passwordRecover,boolean activateAcount){
        this.user=user;
        this.passwordRecover=passwordRecover;
        this.activateAccount =activateAcount;
    }
    public CodeFormController(){}
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
        if(!initilized){
            if(passwordRecover){
                sub="pass";
            }
            else
                sub="auth";
            TFAemailSenderTask task=new TFAemailSenderTask(user.getEmail(), sub,code);
            new Thread(task).start();
            initilized=true;
        }
        errorH.setVisible(false);
        errorH.setManaged(false);
    }
    @FXML
    public void enter(){
        if(passwordRecover){
            if(codeTF.getText().equals(code)){
                try{
                    SessionManager.getInstance().login(user.getUsername(), user.getPassword());
                    Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/RecoverPassword.fxml"));
                    codeTF.getScene().setRoot(root);
                }catch (IOException ex){
                    System.err.println(ex.getMessage());
                }
            }
            else {
                errorH.setManaged(true);
                errorH.setVisible(true);
                codeTF.setText("");
                errorL.setText("incorrect validation code");
            }
        } else if (activateAccount) {
            if(codeTF.getText().equals(code)){
                user.setEtat(true);
                UserService userService=new UserService();
                userService.update(user);
                try{
                    Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
                    codeTF.getScene().setRoot(root);
                }catch (IOException ex){
                    System.err.println(ex.getMessage());
                }
            }else {
                errorH.setManaged(true);
                errorH.setVisible(true);
                codeTF.setText("");
                errorL.setText("incorrect validation code");
            }

        } else if(codeTF.getText().equals(code)){
            TFAFirstLogin.storeUsername(user.getUsername(),false);
            if (SessionManager.getInstance().getCurrentUser().getRole().equals(Roles.ADMIN))
            {
                try{
                    Parent root= FXMLLoader.load(getClass().getResource("/DashboardBack.fxml"));
                    codeTF.getScene().setRoot(root);
                }catch (IOException ex){
                    System.err.println(ex.getMessage());
                }
            }
            else
            {
                try{
                    Parent root= FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
                    codeTF.getScene().setRoot(root);
                }catch (IOException ex){
                    System.err.println(ex.getMessage());
                }
            }
        }
        else {
            errorH.setManaged(true);
            errorH.setVisible(true);
            codeTF.setText("");
            errorL.setText("incorrect validation code");
        }
    }
    @FXML
    public void resend(ActionEvent e){
        code= TFAFirstLogin.getRandomNumberString();
        TFAemailSenderTask task=new TFAemailSenderTask(user.getEmail(), sub,code);
        new Thread(task).start();
        errorH.setVisible(false);
        errorH.setManaged(false);
    }
}
