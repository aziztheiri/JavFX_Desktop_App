package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.arteco.models.Evenement;
import tn.arteco.services.ServiceEvenement;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Optional;


public class deleteevenement {

    @FXML
    private TextArea idArticle;

    @FXML
    private Button retour;

    @FXML
    public void deleteArticle(ActionEvent event) {
        // Récupérer le texte du champ idArticle
        String idText = idArticle.getText();

        // Vérifier si le champ idArticle est vide
        if (idText.isEmpty()) {
            System.out.println("Veuillez entrer un Nom de l'événement à supprimer.");
            return; // Sortir de la méthode car il n'y a pas d'ID à convertir
        }

        try {
            // Tenter de convertir le texte en entier
            //int articleId = Integer.parseInt(idText);
            int articleId = 0;
            // Créer une instance de ServiceEvenement
            ServiceEvenement serviceEvenement = new ServiceEvenement();
            //trouver levenement par son nom
            boolean evenementTrouve = false;
            for (Evenement evenement : serviceEvenement.listerEvenements())
            {
                if (evenement.getNomEvenement().equalsIgnoreCase(idText) ) {
                    evenementTrouve = true;
                    articleId=evenement.getIdEvenement();
                }

            }
            if (evenementTrouve==true) {
                // Créer un objet Evenement avec l'ID spécifié
                Evenement eventToDelete = new Evenement();
                eventToDelete.setIdEvenement(articleId);

                // Créer une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

                // Ajouter les boutons OK et Annuler à l'alerte
                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);

                // Afficher l'alerte et attendre la réponse de l'utilisateur
                Optional<ButtonType> result = alert.showAndWait();

                // Vérifier la réponse de l'utilisateur
                if (result.isPresent() && result.get() == okButton) {
                    // Si l'utilisateur a appuyé sur OK, supprimer l'événement
                    serviceEvenement.supprimer_Evenement(articleId);

                    // Afficher une confirmation de suppression
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setContentText("L'événement a été supprimé avec succès.");
                    confirmation.show();
                } else {
                    // Sinon, afficher un message d'annulation
                    System.out.println("Suppression annulée.");
                }
            } else if (evenementTrouve==false) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Entrez un nom d'événement valide.");
                alert.show();

            }

        } catch (NumberFormatException e) {
            // Gérer l'erreur si le texte n'est pas un nombre entier valide
            System.out.println("Veuillez entrer un identifiant d'événement valide (nombre entier).");
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


