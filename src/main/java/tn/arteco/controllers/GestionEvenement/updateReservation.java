package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.arteco.models.ReservationEv;
import tn.arteco.services.ServiceReservationEv;

import java.io.IOException;

public class updateReservation {

    @FXML
    private TextField dateR;

    @FXML
    private TextField emailclient;

    @FXML
    private TextField etatreserv;

    @FXML
    private TextField idEvent;

    @FXML
    private TextField nbrPersonnes;
    @FXML
    private  TextField idReserv;

    @FXML
    private Button updateReserv;
    @FXML
    private Button retour;

    @FXML
    void updateReserv(ActionEvent event) {

        // Récupérer les données de l'article à mettre à jour depuis l'interface utilisateur
        int ReservationId = Integer.parseInt(idReserv.getText());
        // Si vous avez d'autres champs à mettre à jour, récupérez-les de la même manière

        // Créer une instance de ServiceArticle
        ServiceReservationEv serviceReservationEv = new ServiceReservationEv();


        // Récupérer l'article à partir de son ID
        ReservationEv eventToUpdate = serviceReservationEv.getReservationById(ReservationId);


        // Vérifier si l'article existe
        if (eventToUpdate != null) {
            // Mettre à jour les champs de l'article avec les nouvelles valeurs
            eventToUpdate.setIdReserv(eventToUpdate.getIdReserv());
            eventToUpdate.setIdEvent(Integer.parseInt(idEvent.getText()));
            eventToUpdate.setDateReserv(dateR.getText());
            eventToUpdate.setNbrPersonnes(Integer.parseInt(nbrPersonnes.getText()));
            eventToUpdate.setEtatReserv(etatreserv.getText());
            eventToUpdate.setEmailClient(emailclient.getText());



            // Exemple : articleToUpdate.setTitre(nouveauTitre);
            // Exemple : articleToUpdate.setContenu(nouveauContenu);
            // ... et ainsi de suite pour les autres champs

            // Appeler la méthode update de ServiceArticle pour mettre à jour l'article

            serviceReservationEv.modifier_Reservation(ReservationId,Integer.parseInt(idEvent.getText()),dateR.getText(),Integer.parseInt(nbrPersonnes.getText()),etatreserv.getText(),emailclient.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reservation a ete Modifie");
            alert.show();




        } else {
            System.out.println("La Reservation avec l'ID spécifié n'existe pas.");
        }
    }


    @FXML
    public void retour(javafx.event.ActionEvent event) {
        try{
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



