package tn.arteco.controllers.GestionMateriel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tn.arteco.models.NonArtiste;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.time.LocalDate;


public class HomePageController {
    @FXML
    BorderPane wrapper;
    @FXML
    BorderPane sideWrapper;
    @FXML
    BorderPane parentNode;

    @FXML
    HBox topWrapper;
    @FXML
    HBox midWrapper;
    @FXML
    VBox buttonWrapper;
    @FXML
    VBox matB;
    @FXML
    VBox pfB;
    @FXML
    VBox coursB;
    @FXML
    VBox eventB;
    @FXML
    VBox boutB;
    @FXML
    VBox contB;
    @FXML
    VBox calendarView;
    @FXML
    Label usernameLabel;
    @FXML
    Label pointsLabel;
    @FXML
    ImageView profileImage;
    boolean init = true;
    @FXML
    public void initialize(){
        if(init){
            parentNode.setPrefWidth(parentNode.getWidth()+850);
            parentNode.setPrefHeight(parentNode.getHeight());
            init=false;
        }
        profileImage.setImage(new Image(SessionManager.getInstance().getCurrentUser().getImageUrl()));
        Rectangle clip=new Rectangle(profileImage.getFitWidth(),profileImage.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        profileImage.setClip(clip);
        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setEditable(false);
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node sidecalendar=datePickerSkin.getPopupContent();
        sidecalendar.maxWidth(150.0);
        calendarView.getChildren().add(sidecalendar);
        usernameLabel.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        if(SessionManager.getInstance().getCurrentUser() instanceof NonArtiste na)
            pointsLabel.setText(String.valueOf(na.getPoints()));
        parentNode.widthProperty().addListener((obs,oldval,newval)->{
            if(newval.doubleValue() < 1000){
                sideWrapper.setManaged(false);
                sideWrapper.setVisible(false);
                wrapper.setPrefWidth(newval.doubleValue());
            }
            else {
                wrapper.setPrefWidth(newval.doubleValue() * 5/7);
                sideWrapper.setPrefWidth((newval.doubleValue() *2/7));
                sideWrapper.setManaged(true);
                sideWrapper.setVisible(true);
            }

        });
        topWrapper.widthProperty().addListener((obs, oldVal, newVal) -> {
            topWrapper.setSpacing(newVal.doubleValue()/2);
        });
        midWrapper.widthProperty().addListener((obs, oldVal, newVal) -> {
            midWrapper.setSpacing(newVal.doubleValue()/6);
        });
        wrapper.heightProperty().addListener((obs, oldVal, newVal) -> {
            buttonWrapper.setPrefHeight(newVal.doubleValue()/2);
            matB.setPrefHeight(newVal.doubleValue()/7);
            pfB.setPrefHeight(newVal.doubleValue()/7);
            coursB.setPrefHeight(newVal.doubleValue()/7);
            eventB.setPrefHeight(newVal.doubleValue()/7);
            boutB.setPrefHeight(newVal.doubleValue()/7);
            contB.setPrefHeight(newVal.doubleValue()/7);


        });
        wrapper.widthProperty().addListener((obs, oldVal, newVal) -> {
            matB.setPrefWidth(newVal.doubleValue()/4.5);
            pfB.setPrefWidth(newVal.doubleValue()/4.5);
            coursB.setPrefWidth(newVal.doubleValue()/4.5);
            eventB.setPrefWidth(newVal.doubleValue()/4.5);
            boutB.setPrefWidth(newVal.doubleValue()/4.5);
            contB.setPrefWidth(newVal.doubleValue()/4.5);
        });
        DropShadow dropShadow=new DropShadow(15,Color.GRAY);
        matB.setEffect(dropShadow);
        pfB.setEffect(dropShadow);
        contB.setEffect(dropShadow);
        coursB.setEffect(dropShadow);
        eventB.setEffect(dropShadow);
        boutB.setEffect(dropShadow);
    }
    @FXML
    public void logout(){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void naviguerVersProduits(MouseEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));
            Parent root = loader.load();
            wrapper.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void routeToProfile(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

}