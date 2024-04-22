package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.arteco.controllers.GestionMateriel.AnimationController;
import tn.arteco.models.Evenement;
import tn.arteco.models.NonArtiste;
import tn.arteco.services.ServiceEvenement;
import tn.arteco.services.ServiceRatingE;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherFront implements Initializable {
    @FXML
    private Button btn1;
    @FXML
    private HBox cartev;

    //houni tabda
    @FXML
    private Button produitFiniText;
    @FXML
    private ImageView produitFiniIcon;
    @FXML
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private ImageView boutiqueIcon;

    @FXML
    private Button boutiqueText;

    @FXML
    private ImageView coursIcon;

    @FXML
    private Button coursText;

    @FXML
    private Text points;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label username;
    //houni toufa

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button eventText;
    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button remoboursementText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a) {
            points.setText(String.valueOf(a.getPoints()));
        }
        try {
            ServiceEvenement serviceEvenement = new ServiceEvenement();
            int size = serviceEvenement.listerEvenements().size();
            ServiceRatingE serviceRatingE = new ServiceRatingE();
            List<Evenement> evenements = serviceEvenement.listerEvenements();
            for (Evenement evenement : evenements) {
                int id = 0;
                FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/GestionEvenement/carteevenement.fxml"));
                HBox carteArticleBox = carteArticleLoader.load();
                carte carte = carteArticleLoader.getController();


                // You can interact with carteArticleController if needed

                // Add the loaded content to the cartev HBox
                cartev.getChildren().add(carteArticleBox);

                carte.setNom(evenement.getNomEvenement());
                carte.setDescription(evenement.getDescriptionEvenement());
                carte.setAdresse(evenement.getAdresseEvenement());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(evenement.getDateEvenement());
                carte.setDate(dateString);
                for (Evenement evenement2 : serviceEvenement.listerEvenements()) {
                    if (evenement2.getNomEvenement().equalsIgnoreCase(evenement.getNomEvenement())) {
                        id = evenement.getIdEvenement();

                    }
                }
                carte.setAvg(String.valueOf(serviceRatingE.getAverageRating(id)));
            }
        } catch (IOException e) {
            System.err.println("Error loading cartearticle.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        AnimationController ac = new AnimationController();
        ac.moveNavBar(eventIcon, eventText);
        if (AnimationController.getNomCaller().equals("ProduitFini")) {
            produitFiniIcon.setLayoutX(produitFiniIcon.getLayoutX() + 30);
            produitFiniText.setLayoutX(produitFiniText.getLayoutX() + 30);
            ac.moveBackNavBar(produitFiniIcon, produitFiniText);
        } else if (AnimationController.getNomCaller().equals("Cours")) {
            coursIcon.setLayoutX(coursIcon.getLayoutX() + 30);
            coursText.setLayoutX(coursText.getLayoutX() + 30);
            ac.moveBackNavBar(coursIcon, coursText);
        } else if (AnimationController.getNomCaller().equals("Code")) {
            remoboursementIcon.setLayoutX(remoboursementIcon.getLayoutX() + 30);
            remoboursementText.setLayoutX(remoboursementText.getLayoutX() + 30);
            ac.moveBackNavBar(remoboursementIcon, remoboursementText);

        } else if (AnimationController.getNomCaller().equals("Materiel")) {
            materielIcon.setLayoutX(materielIcon.getLayoutX() + 30);
            materielText.setLayoutX(materielText.getLayoutX() + 30);
            ac.moveBackNavBar(materielIcon, materielText);
        }
        if (AnimationController.getNomCaller().equals("Boutique")) {
            boutiqueIcon.setLayoutX(boutiqueIcon.getLayoutX() + 30);
            boutiqueText.setLayoutX(boutiqueText.getLayoutX() + 30);
            ac.moveBackNavBar(boutiqueIcon, boutiqueText);
        }
    }
    @FXML
    public void add(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AddReservation2.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AddReservationControllers2 addReservationControllers2 = loader.getController();

            // Pass any necessary data to the new interface


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn1.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    @FXML
    public void routeToMateriel(ActionEvent event){
        AnimationController.setNomCaller("Event");

        try{
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
            Parent root=fl.load();
            boutiqueText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void remboursementClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Event");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodePage.fxml"));
            Parent root=fl.load();
            boutiqueText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }

    @FXML
    void produitFiniClicked(ActionEvent event) {
        AnimationController.setNomCaller("Event");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));

            boutiqueIcon.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        } catch (IOException e) {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @FXML
    void coursClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Event");
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
    public void naviguerVersBoutique() {
        try {
            AnimationController.setNomCaller("Event");
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/Boutique.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
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
