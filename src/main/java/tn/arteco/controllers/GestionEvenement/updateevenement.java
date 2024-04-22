package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.arteco.models.Evenement;
import tn.arteco.services.ServiceEvenement;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class updateevenement {

    @FXML
    private TextArea nom;

    @FXML
    private TextArea adresse;

    @FXML
    private TextArea dateE;

    @FXML
    private TextArea idArticle;



    @FXML
    private TextArea description;


    @FXML
    private Button updatearticle;

    @FXML
    private Button retour;

    @FXML
    void updatearticle(ActionEvent event) {
        // Récupérer les données de l'article à mettre à jour depuis l'interface utilisateur
        int articleId = Integer.parseInt(idArticle.getText());
        // Si vous avez d'autres champs à mettre à jour, récupérez-les de la même manière

        // Créer une instance de ServiceArticle
        ServiceEvenement serviceEvenement = new ServiceEvenement();


        // Récupérer l'article à partir de son ID
        Evenement eventToUpdate = serviceEvenement.getEventById(articleId);


        // Vérifier si l'article existe
        if (eventToUpdate != null) {
            // Mettre à jour les champs de l'article avec les nouvelles valeurs
            eventToUpdate.setIdEvenement(eventToUpdate.getIdEvenement());
            eventToUpdate.setNomEvenement(nom.getText());
            eventToUpdate.setAdresseEvenement(adresse.getText());
            eventToUpdate.setDescriptionEvenement(description.getText());
            Date date = Date.valueOf(LocalDate.parse(dateE.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            eventToUpdate.setDateEvenement(date);

            // Exemple : articleToUpdate.setTitre(nouveauTitre);
            // Exemple : articleToUpdate.setContenu(nouveauContenu);
            // ... et ainsi de suite pour les autres champs

            // Appeler la méthode update de ServiceArticle pour mettre à jour l'article

            serviceEvenement.modifier_Evenement(articleId,nom.getText(),adresse.getText(),description.getText(),date);


        } else {
            System.out.println("L'evenement avec l'ID spécifié n'existe pas.");
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

