package tn.arteco.controllers.GestionEvenement;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.arteco.models.Evenement;
import tn.arteco.services.ServiceEvenement;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEvenementControllers  {

    @FXML
    private TextField FK_Dateevenement;

    @FXML
    private TextField FK_adresseEvenement;

    @FXML
    private TextField FKnom_Evenement;

    @FXML
    private TextField Fk_DescriptionEvenement;

    @FXML
    private HBox carteEV;
    @FXML
    private Button retour;



    @FXML
    void ajouterEvenement(ActionEvent event) {
        String nom = FKnom_Evenement.getText();
        String description = Fk_DescriptionEvenement.getText();
        String adresse = FK_adresseEvenement.getText();
        String dateText = FK_Dateevenement.getText();

        // Vérifie si les champs sont vides
        if (nom.isEmpty() || description.isEmpty() || adresse.isEmpty() || dateText.isEmpty()) {
            // Affiche une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode
        }

        // Tous les champs sont remplis, procédez à l'ajout de l'événement
        Evenement evenement = new Evenement(nom, description, adresse, Date.valueOf(LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AddEvenement.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AddEvenementControllers addEvenementControllers = loader.getController();
            serviceEvenement.ajouter_Evenement(evenement);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Événement ajouté avec succès");
            alert.show();

            // Pass any necessary data to the new interface

            //Stage stage = new Stage();
            //stage.setScene(new Scene(root));
            //stage.show();

            // Close the current window
            // btn1.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }



    @FXML
    public void retour(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            ajouterevenement ajouterevenement = loader.getController();


            // Pass any necessary data to the new interface


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window

            retour.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }







}








