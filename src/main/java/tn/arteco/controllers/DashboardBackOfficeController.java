package tn.arteco.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tn.arteco.services.*;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class DashboardBackOfficeController implements Initializable {

    @FXML
    private Label nombreproduitsfinis;

    @FXML
    private Label username;
    @FXML
    private Label totalArtistes;

    @FXML
    private PieChart piechartproduits;
    //a ajouter dans le Controller et ajouter l'id dans le FXML

    @FXML
    private BarChart<String, Integer> materielbarchart;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private PieChart materielpiechart;

    @FXML
    private Pane waitingpane;
    //a ajouter pour materiel
    @FXML
    private Label nombremateriel;
    @FXML
            private Label totalMarchandies;
    ServicesProduitsFinis servicesProduitsFinis = new ServicesProduitsFinis();
    //ajouter service materiel :
    ServiceMateriel serviceMateriel = new ServiceMateriel();
    MarchandiseService marchandiseService=new MarchandiseService();
    UserService userService = new UserService();

    @FXML
    private BarChart<String, Integer> produitsparartistebar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        totalMarchandies.setText(String.valueOf(marchandiseService.getAll().size()));
        // code pour les libellée en haut
        nombreproduitsfinis.setText(String.valueOf(servicesProduitsFinis.nbrProduits()));
        totalArtistes.setText(String.valueOf(userService.getArtists().size()));
        // a ajouter pour libelle en haut en FXML on lui donne un ID
        nombremateriel.setText(String.valueOf(serviceMateriel.getAll().size()));


        // Code pour les données du PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Integer> produitsParCategorie = servicesProduitsFinis.nbrProduitsParCategorie();
        for (Map.Entry<String, Integer> entry : produitsParCategorie.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        piechartproduits.setData(pieChartData);
        piechartproduits.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        for (Node node : piechartproduits.lookupAll(".chart-pie-label")) {
            node.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        }
        //Code pour le pie chart materiel :
        ObservableList<PieChart.Data> pieChartMaterielData = FXCollections.observableArrayList();
        Map<String, Integer> materielParAdresse = serviceMateriel.getCountByAddress();

        for (Map.Entry<String, Integer> entry : materielParAdresse.entrySet()) {
            pieChartMaterielData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        materielpiechart.setData(pieChartMaterielData);
        materielpiechart.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        for (Node node : materielpiechart.lookupAll(".chart-pie-label")) {
            node.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        }
        // code pour barchart materiel :
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Nombre de materiel");

        Map<String, Integer> materielParArtiste = serviceMateriel.getCountByArtist();
        for (Map.Entry<String, Integer> entry : materielParArtiste.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        materielbarchart.getData().add(series);
        Random random = new Random();
        for (XYChart.Data<String, Integer> data : series.getData()) {
            Color randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            data.getNode().setStyle("-fx-bar-fill: " + toRGBCode(randomColor) + ";");
        }

        materielbarchart.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        // Code pour les données du BarChart produit
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        series2.setName("Produits par Artiste");
        Map<String, Integer> artistProducts = servicesProduitsFinis.nbrProduitsParArtiste();
        for (Map.Entry<String, Integer> entry : artistProducts.entrySet()) {
            series2.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        produitsparartistebar.getData().add(series2);
        for (XYChart.Data<String, Integer> data : series2.getData()) {
            Color randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            data.getNode().setStyle("-fx-bar-fill: " + toRGBCode(randomColor) + ";");
        }
        produitsparartistebar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    }




    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        waitingpane.setVisible(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            new Thread(() -> {
                try {
                    Parent root = loader.load();
                    Platform.runLater(() -> {
                        mainpane.getScene().setRoot(root);
                        waitingpane.setVisible(false);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            mainpane.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void materielBackClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
            mainpane.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            mainpane.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            mainpane.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            mainpane.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gestionRecomponse/BoutiqueGestion.fxml"));
            mainpane.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}