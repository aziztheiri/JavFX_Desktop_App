package tn.arteco.controllers.GestionUtilisateur;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.services.*;
import tn.arteco.utils.GoogleSignInManager;
import tn.arteco.utils.HashPassword;

import java.io.IOException;


public class LoginController {
    @FXML
    BorderPane parentNode;
    @FXML
    TextField usernameTF;
    @FXML
    PasswordField passwordTF;
    @FXML
    Button loginB;
    @FXML
    Button registerB;
    @FXML
    private HBox googleLogin;
    @FXML
    VBox formBox;
    @FXML
    ImageView iv;
    @FXML
    CheckBox remembermeCheck;
    @FXML
    Label errorL;
    @FXML
    Button recover;
    @FXML
    Button activateB;
    @FXML
    public void initialize(){
        parentNode.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
        DropShadow dropShadow1=new DropShadow(10,Color.GREY);
        googleLogin.setEffect(dropShadow1);
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
        if(RememberMeManager.retrieveCredentials()!=null){
            SessionManager.getInstance().login(RememberMeManager.retrieveCredentials()[0], RememberMeManager.retrieveCredentials()[1]);
            if(SessionManager.getInstance().getCurrentUser().getRole().toString().equals("ADMIN")){
                Platform.runLater(()->{
                    try{
                        Parent root= FXMLLoader.load(getClass().getResource("/DashBoardBack.fxml"));
                        parentNode.getScene().setRoot(root);
                    }catch (IOException ex){
                        System.err.println(ex.getMessage());
                    }
                });
            }
            else{
                Platform.runLater(()->{
                    try{
                        Parent root= FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
                        parentNode.getScene().setRoot(root);
                    }catch (IOException ex){
                        System.err.println(ex.getMessage());
                    }
                });
            }
            Platform.runLater(()->{

            });
        }
        errorL.setVisible(false);
        errorL.setManaged(false);
        recover.setManaged(false);
        recover.setVisible(false);
        activateB.setManaged(false);
        activateB.setVisible(false);
    }
    @FXML public void login(){
        UserService userService=new UserService();

            errorL.setManaged(true);
            errorL.setVisible(true);
            activateB.setVisible(true);
            activateB.setManaged(true);
            errorL.setText("Account is inactive");

        if(userService.getUserByUsername(usernameTF.getText())==null){
            errorL.setManaged(true);
            errorL.setVisible(true);
            recover.setVisible(true);
            recover.setManaged(true);
            errorL.setText("incorrect username or password");
            passwordTF.setStyle("-fx-border-color: #DC143C");
            usernameTF.setStyle("-fx-border-color: #DC143C");
             recover.setManaged(true);
             recover.setVisible(true);
        }
        else if(!userService.getUserByUsername(usernameTF.getText()).getPassword().equals(HashPassword.hashPassword(passwordTF.getText()))){
            errorL.setManaged(true);
            errorL.setVisible(true);
             recover.setManaged(true);
             recover.setVisible(true);
            errorL.setText("incorrect username or password");
            passwordTF.setStyle("-fx-border-color: #DC143C");
            usernameTF.setStyle("-fx-border-color: #DC143C");
        }
        else if(!userService.getUserByUsername(usernameTF.getText()).isEtat() && userService.getUserByUsername(usernameTF.getText())!=null) {
            UserRequestService userRequestService = new UserRequestService();
            if (userRequestService.pendingActivationRequestExistPerUsername(usernameTF.getText())) {
                activateB.setText("activation Request is pending");
                activateB.setOnAction((e) -> {
                });
            } else {
                activateB.setOnAction(e -> routeToUsernameInput());
            }
        }
        else{
            if(remembermeCheck.isSelected()){
                RememberMeManager.saveCredentials(usernameTF.getText(),HashPassword.hashPassword(passwordTF.getText()));
            }
            if(TFAFirstLogin.isFirstTimeLogin(userService.getUserByUsername(usernameTF.getText()).getUsername())){
                if(SessionManager.getInstance().login(usernameTF.getText(), HashPassword.hashPassword(passwordTF.getText()))){
                    FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/GestionUtilisateur/CodeForm.fxml"));
                    CodeFormController codeFormController=new CodeFormController(userService.getUserByUsername(usernameTF.getText()));
                    fxmlLoader.setController(codeFormController);
                    try{
                        Parent root = fxmlLoader.load();
                        parentNode.getScene().setRoot(root);
                        return;
                    }catch (IOException e){
                        System.err.println(e.getMessage());
                    }
                }
            }else{
                if(SessionManager.getInstance().login(usernameTF.getText(), HashPassword.hashPassword(passwordTF.getText()))){
                    if(SessionManager.getInstance().getCurrentUser().getRole().toString().equals("ADMIN")){
                        try{
                            Parent root= FXMLLoader.load(getClass().getResource("/DashBoardBack.fxml"));
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
            }
        }

    }
    @FXML
    public void routeToRegister(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/Register.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void routeToUsernameInput(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UsernameCheckForReset.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void routeToCodeValidation(){
        UserService userService=new UserService();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/GestionUtilisateur/CodeForm.fxml"));
        CodeFormController codeFormController=new CodeFormController(userService.getUserByUsername(usernameTF.getText()),false,true);
        fxmlLoader.setController(codeFormController);
        try{
            Parent root = fxmlLoader.load();
            parentNode.getScene().setRoot(root);
            return;
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @FXML
    public void googleLogin(){
        UserService userService=new UserService();
        try {
            GoogleSignInManager.setHttpTransport(GoogleNetHttpTransport.newTrustedTransport());
            GoogleSignInManager.setDataStoreFactory(new FileDataStoreFactory(GoogleSignInManager.getDataStoreDir()));
            Credential credential = GoogleSignInManager.authorize();
            Oauth2 oauth2;
            oauth2 = new Oauth2.Builder(GoogleSignInManager.getHttpTransport(), GoogleSignInManager.getJsonFactory(), credential).setApplicationName(
                    "HyperNova/Arteco/1.0").build();
            Userinfoplus userinfo = oauth2.userinfo().get().execute();
            String[] s=userinfo.getEmail().split("@");
            if(!userService.emailExists(userinfo.getEmail())){

               User user =new NonArtiste(s[0],userinfo.getGivenName(),userinfo.getFamilyName(), HashPassword.hashPassword("google"),userinfo.getEmail(),userinfo.getPicture());
               userService.add(user);
               RememberMeManager.saveCredentials(s[0],HashPassword.hashPassword("google"));
            }
            else{
                SessionManager.getInstance().login(userService.getByEmail(userinfo.getEmail()).getUsername(),userService.getByEmail(userinfo.getEmail()).getPassword());
                RememberMeManager.saveCredentials(userService.getByEmail(userinfo.getEmail()).getUsername(),userService.getByEmail(userinfo.getEmail()).getPassword());
            }
            System.out.println("hey");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("heyy");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

}
