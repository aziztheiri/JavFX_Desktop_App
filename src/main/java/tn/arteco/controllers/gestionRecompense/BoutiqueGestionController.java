package tn.arteco.controllers.gestionRecompense;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.arteco.services.SessionManager;

import java.io.IOException;

public class BoutiqueGestionController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Pane btnFactureMarchandise;

    @FXML
    private Pane btnMarchandise;

    @FXML
    private Pane btnTransaction;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private Label username;


    @FXML
    void goToMarchandise(MouseEvent event) throws IOException {
     root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/MarchandiseList.fxml"));
    stage= (Stage)((Node)event.getSource()).getScene().getWindow();
    scene =new Scene(root);
    stage.setScene(scene);
    stage.show();
    }

    @FXML
    void goToMarchandiseFacture(MouseEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/MarchandiseFactureList.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToTransaction(MouseEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/TransactionList.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void materielBackClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void naviguerVersDashboard(ActionEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashBoardBack.fxml"));
            Parent root = loader.load();
            mainContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            mainContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            mainContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }


}
