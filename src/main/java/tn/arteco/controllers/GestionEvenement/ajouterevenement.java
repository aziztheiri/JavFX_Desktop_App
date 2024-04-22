package tn.arteco.controllers.GestionEvenement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.arteco.models.Evenement;
import tn.arteco.services.ServiceEvenement;
import tn.arteco.services.ServiceRatingE;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ajouterevenement implements Initializable {
    @FXML
    private HBox cartev;

    @FXML
    private TextField text;
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;
    @FXML
    private Button button;

    @FXML
    private Button stat;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceEvenement serviceEvenement = new ServiceEvenement();
            int size = serviceEvenement.listerEvenements().size();
            ServiceRatingE serviceRatingE = new ServiceRatingE();
            List<Evenement> evenements = serviceEvenement.listerEvenements();
            for (Evenement evenement : evenements) {
                int id =0;
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
                for ( Evenement evenement2 : serviceEvenement.listerEvenements())
                {
                    if (evenement2.getNomEvenement().equalsIgnoreCase(evenement.getNomEvenement())){
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
    }
    @FXML
    public void add(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AddEvenement.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AddEvenementControllers addevenement = loader.getController();

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
    public void delete(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/deleteevenement.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            deleteevenement deletearticle = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn2.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }

    }
    public void update(javafx.event.ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/updateevenement.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            updateevenement updatearticle = loader.getController();
            // Pass any necessary data to the new interface

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn3.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }



    @FXML
    // le bouton yhezek lel reservation
    public void button(javafx.event.ActionEvent event) {
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
            button.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    @FXML
    // le bouton yhezek lel Statistique
    public void stat(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/statistique.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            statistique statistique = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Close the current window
           stat.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }

    }

    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            btn1.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            btn1.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void materielBackClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
            btn1.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            btn1.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            btn1.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersDashboard(ActionEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashBoardBack.fxml"));
            Parent root = loader.load();
            btn1.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            btn1.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}






