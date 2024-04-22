package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.arteco.models.NonArtiste;
import tn.arteco.services.ServiceCode;
import tn.arteco.services.SessionManager;

import java.io.IOException;

public class CodePageController {
    AnimationController ac;
    String parentCalling="";
    int pointsMod;

    @FXML
    private Label user;

    @FXML
    private ImageView userPhoto;
    @FXML
    private Text points;


    @FXML
    TextField codeField;

    Stage errorStage;
    @FXML
    private ImageView  boutiqueIcon;

    @FXML
    private ImageView coursIcon;

    @FXML
    private Button coursText;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button eventText;

    @FXML
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private Button boutiqueText;

    @FXML
    private ImageView produitFiniIcon;

    @FXML
    private Button produitFiniText;

    private int etatRemboursement=0;

    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button remoboursementText;
    @FXML
    private AnchorPane navbarHolder;
    @FXML
    private AnchorPane main;
    @FXML
    public void initialize() throws IOException {
        ac=new AnimationController();
        user.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }
        ac.moveNavBar(remoboursementIcon, remoboursementText);
            if(AnimationController.getNomCaller().equals("Materiel")) {
                materielIcon.setLayoutX(materielIcon.getLayoutX()+30);
                materielText.setLayoutX(materielText.getLayoutX()+30);
                ac.moveBackNavBar(materielIcon, materielText);
            }

        else if(AnimationController.getNomCaller().equals("ProduitFini")) {
            produitFiniIcon.setLayoutX(materielIcon.getLayoutX()+30);
            produitFiniText.setLayoutX(materielText.getLayoutX()+30);
            ac.moveBackNavBar(produitFiniIcon, produitFiniText);
        }

            else if(AnimationController.getNomCaller().equals("Cours")) {
                coursIcon.setLayoutX(coursIcon.getLayoutX()+30);
                coursText.setLayoutX(coursText.getLayoutX()+30);
                ac.moveBackNavBar(coursIcon, coursText);
            }


            else if(AnimationController.getNomCaller().equals("Event")) {
                eventIcon.setLayoutX(eventIcon.getLayoutX()+30);
                eventText.setLayoutX(eventText.getLayoutX()+30);
                ac.moveBackNavBar(eventIcon, eventText);
            }

            else if(AnimationController.getNomCaller().equals("Boutique")) {
                boutiqueText.setLayoutX(boutiqueText.getLayoutX()+30);
                boutiqueIcon.setLayoutX(boutiqueIcon.getLayoutX()+30);
                ac.moveBackNavBar(boutiqueText, boutiqueIcon);
            }


    }

    public void blurPage()
    {
        navbarHolder.setStyle("-fx-background-color: #E5E5E5");
        main.setStyle("-fx-background-color: rgba(0, 0, 0, 0.44);");
        materielText.setStyle("-fx-text-fill: #929292");
        produitFiniText.setStyle("-fx-text-fill: #929292");
        coursText.setStyle("-fx-text-fill: #929292");
        eventText.setStyle("-fx-text-fill: #929292");
        boutiqueText.setStyle("-fx-text-fill: #929292");
        remoboursementText.setStyle("-fx-text-fill: #929292");
        remoboursementText.setStyle("-fx-background-color: none");
    }
    @FXML
    public void confirmCode() throws IOException {

        ServiceCode sc=new ServiceCode();
        String code=codeField.getText();
       int points=sc.verifierCode(code,SessionManager.getInstance().getCurrentUser().getUserId());
       if(points==0) {
           //blurPage();

           FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/codeError.fxml"));
           Parent root=fl.load();
           CodeErrorController cec=fl.getController();
           cec.setParent(this);
           errorStage=new Stage();
           errorStage.initModality(Modality.APPLICATION_MODAL);
           errorStage.setScene(new Scene(root));
           errorStage.setResizable(false);
           

           errorStage.show();
        errorStage.setOnHiding(e->resetBackgroundColor());

       }
       else {
           int p=Integer.parseInt(this.points.getText())+points;
           sc.ajouterPoints(p, SessionManager.getInstance().getCurrentUser().getUserId());
           if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a) {
               this.points.setText(String.valueOf(p));
           }
           Stage correctStage=new Stage();
           codeField.clear();
           FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodeCorrect.fxml"));
           Parent root=fl.load();
           CodeCorrectController cec=fl.getController();
           cec.setParent(this);
           correctStage=new Stage();
           correctStage.initModality(Modality.APPLICATION_MODAL);
           correctStage.setScene(new Scene(root));
           correctStage.setResizable(false);
           correctStage.show();
           correctStage.setOnHiding(e->resetBackgroundColor());
           sc.updateEtatCode(code);

       }


    }
    public void resetBackgroundColor()
    {
        main.setStyle("-fx-background-color:  #023047;");
        navbarHolder.setStyle("-fx-background-color: white");
        materielText.setStyle("-fx-text-fill: #1d364d");

        produitFiniText.setStyle("-fx-text-fill: #1d364d");
        coursText.setStyle("-fx-text-fill: #1d364d");
        eventText.setStyle("-fx-text-fill: #1d364d");
        boutiqueText.setStyle("-fx-text-fill: #1d364d");
        remoboursementText.setStyle("-fx-text-fill: #1d364d");
        remoboursementText.setStyle("-fx-background-color: none");
    }



    public void materielClicked(ActionEvent event) throws IOException {
        AnimationController.setNomCaller("Code");
        Parent root=FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
        materielText.getScene().setRoot(root);

    }

    @FXML
    void produitFiniClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Code");
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));

            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
        }

    @FXML
    void coursClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Code");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionCour/GestionCour.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }
    @FXML
    void eventClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Code");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/GestionEvenement/AfficherFront.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }


    }
    @FXML
    public void naviguerVersBoutique() {
        try {
            AnimationController.setNomCaller("Code");
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/Boutique.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void setCaller(String s)
    {
         parentCalling=s;
    }

    @FXML
    public void disconnect(MouseEvent event) {


        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void routeToProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void homeClicked(MouseEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
