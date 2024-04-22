package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import tn.arteco.models.Evenement;
import tn.arteco.models.RatingEvenement;
import tn.arteco.services.ServiceEvenement;
import tn.arteco.services.ServiceRatingE;

import java.io.IOException;
import java.util.prefs.Preferences;

public class carte {
    @FXML
    private TextField adresse;

    @FXML
    private TextField date;

    @FXML
    private TextField description;

    @FXML
    private ImageView image;

    @FXML
    private TextField nom;

    @FXML
    private Text text;

    @FXML
    private Button btn;

    @FXML
    private Label nomLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;
    @FXML
    private Rating rating;
    @FXML
    private Label avgLabel;

    public void setNom(String nom) {
        nomLabel.setText(nom);
    }

    public void setAdresse(String adresse) {
        adresseLabel.setText(adresse);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setDate(String date) {
        dateLabel.setText(date);
    }
    public void setAvg(String avg) {avgLabel.setText(avg);}


    @FXML
    public void handleButtonAction(ActionEvent event) {
        ServiceRatingE serviceRatingE = new ServiceRatingE();
        ServiceEvenement se = new ServiceEvenement();
        int id = 0;
        String contenuNom = nomLabel.getText();
        for ( Evenement evenement : se.listerEvenements())
        {
            if (contenuNom.equalsIgnoreCase(evenement.getNomEvenement())){
                 id = evenement.getIdEvenement();

        }
        }
        String rateEv = avgLabel.getText();
        double rateEvDouble = Double.parseDouble(rateEv);
        double n = serviceRatingE.getNombreRates();
        rateEvDouble=((rateEvDouble+rating.getRating())/2 );
        setAvg(String.valueOf(rateEvDouble));
        int doble = (int) rating.getRating() ;
        RatingEvenement ratingEvenement = new RatingEvenement(id,doble);
        serviceRatingE.ajouter_RateE(ratingEvenement);

    }




}
