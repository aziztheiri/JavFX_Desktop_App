package tn.arteco.controllers.GestionUtilisateur;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import tn.arteco.models.Roles;
import tn.arteco.services.JavaConnector;
import tn.arteco.services.RememberMeManager;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CaptchaController {

    @FXML
    private Label errorL;

    @FXML
    private VBox formBox;

    @FXML
    private ImageView iv;

    @FXML
    private BorderPane parentNode;

    @FXML
    private WebView webView;
    @FXML
    public void initialize(){
//            Preferences preferences = Preferences.userRoot().node("/tn/arteco/services");
//        try {
//            preferences.clear();
//        } catch (BackingStoreException e) {
//            throw new RuntimeException(e);
//        }
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
        errorL.setVisible(false);
        errorL.setManaged(false);
        WebEngine w=webView.getEngine();
        JavaConnector javaConnector=new JavaConnector();
        w.load("http://localhost/captcha.html");
        w.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) w.executeScript("window");
                window.setMember("javaConnector", javaConnector);
            }
        });
        javaConnector.captchaVerificationProperty().addListener((obs,oldValue,newValue)->{
            if(newValue){
               if(RememberMeManager.retrieveCredentials()!=null){
                   SessionManager.getInstance().login(RememberMeManager.retrieveCredentials()[0],RememberMeManager.retrieveCredentials()[1]);
                   if(SessionManager.getInstance().getCurrentUser().getRole().equals(Roles.ADMIN)){
                       try{
                           Parent root= FXMLLoader.load(getClass().getResource("/DashboardBack.fxml"));
                           parentNode.getScene().setRoot(root);
                       }catch (IOException ex){
                           System.err.println(ex.getMessage());
                       }
                   }
                   else{
                       try{
                           Parent root= FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
                           parentNode.getScene().setRoot(root);
                       }catch (IOException ex){
                           System.err.println(ex.getMessage());
                       }
                   }

               }
               else{
                   try{
                       Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
                       parentNode.getScene().setRoot(root);
                   }catch (IOException ex){
                       System.err.println(ex.getMessage());
                   }
               }
            }


        });

    }

}
