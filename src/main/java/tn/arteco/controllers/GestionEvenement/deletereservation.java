package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.arteco.models.ReservationEv;
import tn.arteco.services.ServiceReservationEv;

import java.io.IOException;
import java.util.Optional;

public class deletereservation {


    @FXML
    private TextArea idReserv;


    @FXML
    private Button delete;

    @FXML
    private Button retour;

    @FXML
    void delete(ActionEvent event) { // Récupérer le texte du champ idArticle
        String idText = idReserv.getText();

        // Vérifier si le champ idArticle est vide
        if (idText.isEmpty()) {
            System.out.println("Veuillez entrer le mail responsable de cette reservation.");
            return; // Sortir de la méthode car il n'y a pas d'ID à convertir
        }

        // Vérifier si l'email est au bon format (contient '@')
        if (!idText.contains("@")) {
            // Afficher une alerte si l'email est mal formaté
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format d'email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse e-mail valide.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode
        }

        try {
            // Créer une instance de ServiceEvenement
            ServiceReservationEv serviceReservationEv = new ServiceReservationEv();
            boolean reservationTrouve = false;
            int idReserv = 0;

            for (ReservationEv reservationEv : serviceReservationEv.listerReservations())
            {
                if (reservationEv.getEmailClient().equalsIgnoreCase(idText) ) {
                    reservationTrouve = true;
                    idReserv=reservationEv.getIdReserv();

                }
            }
            if (reservationTrouve==true) {
            // Créer un objet Evenement avec l'ID spécifié
            ReservationEv eventToDelete = new ReservationEv();
            eventToDelete.setIdReserv(idReserv);

            // Créer une alerte de confirmation
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette reservation ?");

            // Ajouter les boutons OK et Annuler à l'alerte
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();

            // Vérifier la réponse de l'utilisateur
            if (result.isPresent() && result.get() == okButton) {
                // Si l'utilisateur a appuyé sur OK, supprimer l'événement
                serviceReservationEv.supprimer_Reservation(idReserv);

                // Afficher une confirmation de suppression
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setContentText("Reservation a été supprimé avec succès.");
                confirmation.show();
            } else {
                // Sinon, afficher un message d'annulation
                System.out.println("Suppression annulée.");
            }
            } else if (reservationTrouve==false) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Entrez l'email responsable d'une resrevation valide.");
                alert.show();

            }

        } catch (NumberFormatException e) {
            // Gérer l'erreur si le texte n'est pas un nombre entier valide
            System.out.println("Veuillez entrer un identifiant de reservation valide (nombre entier).");
        }
    }




    @FXML
    void retour(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/ajouterReservation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            ajouterReservation ajouterReservation = loader.getController();


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