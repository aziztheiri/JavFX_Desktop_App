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
import tn.arteco.models.Evenement;
import tn.arteco.models.ReservationEv;
import tn.arteco.services.EmailSender;
import tn.arteco.services.ServiceEvenement;
import tn.arteco.services.ServiceReservationEv;

import java.io.IOException;

public class AddReservationControllers {

    @FXML
    private TextField Emailclient;

    @FXML
    private TextField EtatReserv;

    @FXML
    private TextField dateReserv;

    @FXML
    private TextField idEventReserv;

    @FXML
    private TextField nbrPersonnes;

    @FXML
    private Button retour;
    EmailSender  emailSender = new EmailSender();

    @FXML
    void ajouterReservation(ActionEvent event) {
        String nom = idEventReserv.getText();
        String dateReservation = dateReserv.getText();
        String nombre = nbrPersonnes.getText();
        String etatReservation = EtatReserv.getText();
        String emailClient = Emailclient.getText();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || dateReservation.isEmpty() || etatReservation.isEmpty() || emailClient.isEmpty()) {
            // Afficher une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode
        }

        // Vérifier si l'email est au bon format (contient '@')

        boolean evenementTrouve = false;
        ServiceReservationEv serviceReservationEv =new ServiceReservationEv();
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        int id = 0 ;
        for (Evenement evenement : serviceEvenement.listerEvenements() )
        {
            if ((evenement.getNomEvenement().equalsIgnoreCase(nom)))
            {
                 evenementTrouve = true;
                 id = evenement.getIdEvenement();
            }
        }
        /*if (!emailClient.contains("@")) {
            // Afficher une alerte si l'email est mal formaté
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format d'email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse e-mail valide.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode
        }*/

        // Récupérer les valeurs des champs de saisie

        int nbr = Integer.parseInt(nbrPersonnes.getText());
        ReservationEv reservationEv = new ReservationEv(id, dateReserv.getText(),nbr, EtatReserv.getText(), Emailclient.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AddReservation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AddReservationControllers addReservationControllers= loader.getController();
            if (evenementTrouve==true) {
                if (!emailClient.contains("@")) {
                    // Afficher une alerte si l'email est mal formaté
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Format d'email incorrect");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir une adresse e-mail valide.");
                    alert.showAndWait();
                    return; // Arrêter l'exécution de la méthode
                }
            serviceReservationEv.ajouter_Reservation(reservationEv);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Reservation a ete ajoutee");
                alert.show();
            }
            else if (evenementTrouve==false) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Entrez un nom d'événement valide.");
                alert.show();

            }

            // Close the current window
            // btn1.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    public void retour(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/ajouterReservation.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            ajouterReservation ajouterReservation = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());
            stage.show();
            // Close the current window
            retour.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }






}

