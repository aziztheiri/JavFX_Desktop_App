package tn.arteco.controllers.GestionUtilisateur;
import com.mailjet.client.errors.MailjetException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import tn.arteco.services.EmailSender;
import tn.arteco.services.SessionManager;

import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;

public class ContactFormController {
    @FXML
    private HBox disconnect;

    @FXML
    private BorderPane parentNode;

    @FXML
    private Label points;

    @FXML
    private VBox routingWrapper;

    @FXML
    private BorderPane sideBar;

    @FXML
    private TextArea text;

    @FXML
    private ImageView userProfile;

    @FXML
    private Label username;
    @FXML
    private BorderPane content;
    @FXML
    private Button but;
    @FXML
    public void initialize(){
        userProfile.setImage(new Image( SessionManager.getInstance().getCurrentUser().getImageUrl()));
        Rectangle clip=new Rectangle(userProfile.getFitWidth(),userProfile.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        userProfile.setClip(clip);
        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste)
            points.setText(String.valueOf(((NonArtiste) SessionManager.getInstance().getCurrentUser()).getPoints()));
        parentNode.widthProperty().addListener((obs, oldval, newval) -> {
            sideBar.setPrefWidth(newval.doubleValue() / 5);
            content.setPrefWidth(newval.doubleValue() * 4 / 5);
        });
        DropShadow dropShadow = new DropShadow(15, Color.GREY);
        text.setEffect(dropShadow);
        but.setEffect(dropShadow);
    }
    @FXML
    void disconnect(MouseEvent event) {
        SessionManager.getInstance().logout();

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            Scene scene = parentNode.getScene();
            if (scene != null) {
                scene.setRoot(root  );
            } else {
                System.err.println("Scene is null.");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void routeToHome(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void sendEmail(){
        new Thread(() -> {
            try {
                EmailSender.sendCustumerContactEmail("mamadouhl@gmail.com",text.getText(),SessionManager.getInstance().getCurrentUser().getEmail(),SessionManager.getInstance().getCurrentUser().getUsername());
                text.clear();
            } catch (MailjetException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    @FXML
    public void routeToProfile(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
