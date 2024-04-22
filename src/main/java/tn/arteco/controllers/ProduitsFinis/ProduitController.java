package tn.arteco.controllers.ProduitsFinis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.arteco.models.ProduitFini;
import tn.arteco.services.ServicesProduitsFinis;

import java.io.IOException;

public class ProduitController   {
    @FXML
    private Label MaterielName;

    @FXML
    private Label categorie;

    @FXML
    private Label description;

    @FXML
    private ImageView imageMateriel;

    @FXML
    private Label totalRate;
    @FXML
    private Button showDetailsButton;

    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label artistelabel;
    @FXML
    private HBox boxbutton;
    private TextArea descrfield;
    private TextField libfield;
    private TextField idfield;
    private ImageView imgView;
    private ComboBox<String> artistComboBox;
    private ComboBox<String> categorieComboBox;
    ServicesProduitsFinis servicesProduitsFinis = new ServicesProduitsFinis();
    private GestionProduitFinisController gestionProduitController;

    private ShowDetailsProductController showDetailsProductController;
    private ProduitFini produitFini;

    public void setGestionProduitController(GestionProduitFinisController gestionProduitController) {
        this.gestionProduitController = gestionProduitController;
    }
    private GestionProduitFinisController getGestionProduitController() {
        return this.gestionProduitController;
    }
    @FXML
    public void showdetails(ActionEvent event){
        setupShowDetailsButtonAction(produitFini);
    }
    private void setupShowDetailsButtonAction(ProduitFini produitFini) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/showDetailsProduct.fxml"));
                    Parent root = loader.load();
                    showDetailsProductController = loader.getController();
                    showDetailsProductController.setProductDetails(produitFini);
                    Image icon = new Image("/Icons/LOGO.png");
                    Stage stage = new Stage();
                    stage.getIcons().add(icon);
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    public void setMaterielData(ProduitFini produitFini){
        Image image = new Image(produitFini.getImageUrl());
        //Image image = new Image(getClass().getResource("/Icons/filter.png").toExternalForm());
        imageMateriel.setImage(image);
        MaterielName.setText(produitFini.getLibProduit());
        description.setText(produitFini.getDescription());
        categorie.setText(produitFini.getCategorie());
        totalRate.setText(String.valueOf(produitFini.getTotalRate()));
        artistelabel.setText(produitFini.getArtiste().getNom());
        this.produitFini = produitFini;
        updateButton.setOnAction(event->{
         libfield.setText(produitFini.getLibProduit());
         descrfield.setText(produitFini.getDescription());
         artistComboBox.setValue(produitFini.getArtiste().getUsername());
         categorieComboBox.setValue(produitFini.getCategorie());
         idfield.setText(String.valueOf(produitFini.getIdProduit()));
         imgView.setImage(image);
         gestionProduitController.getFormpane().setVisible(true);
         gestionProduitController.getWelcomelabel().setText("Update Produit Fini");
         gestionProduitController.getArtisterror().setText("");
         gestionProduitController.getCaterror().setText("");
         gestionProduitController.getDescrerror().setText("");
         gestionProduitController.getLabelerror().setText("");
         gestionProduitController.getImgerror().setText("");
        });
    }
    public void deleteData(ProduitFini produitfini){
        deleteButton.setOnAction(event->{
            servicesProduitsFinis.delete(produitfini.getIdProduit());
            gestionProduitController.refreshProductList();
        });
    }
    public void setFields(TextArea descrfield, TextField libfield, ComboBox<String> artistComboBox, ComboBox<String> categorieComboBox, ImageView imgView,TextField idfield) {
        this.descrfield = descrfield;
        this.libfield = libfield;
        this.artistComboBox = artistComboBox;
        this.categorieComboBox = categorieComboBox;
        this.imgView = imgView;
        this.idfield=idfield;
    }
}
