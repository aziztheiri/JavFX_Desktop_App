package tn.arteco.controllers.ProduitsFinis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import tn.arteco.models.ProduitFini;
import tn.arteco.models.Rate;
import tn.arteco.services.ServicesProduitsFinis;
import tn.arteco.services.ServicesRating;
import tn.arteco.services.SessionManager;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

public class ProduitFrontController implements  Initializable{
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
    private Rating productrate;
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
    @FXML
    private Button compareButton;
    @FXML
    private Button removeproductcompare;
    @FXML
    private ImageView qrimage;
    ServicesProduitsFinis servicesProduitsFinis = new ServicesProduitsFinis();
    private GestionProduitsFrontController gestionProduitController;

    private ShowDetailsProductController showDetailsProductController;
    private CompareProductController compareProductController;
    private ProduitFini produitFini;
    @FXML
    private Button removeratebutton;
    private int connecteduser = SessionManager.getInstance().getCurrentUser().getUserId();
    private TextField idfielddonation;
    private boolean isAddRating = true;
    private Preferences userPrefs;
    @FXML
    private Button createpdf;
    @FXML
    private Button donation;


    public void setGestionProduitController(GestionProduitsFrontController gestionProduitController) {
        this.gestionProduitController = gestionProduitController;
    }
    private GestionProduitsFrontController getGestionProduitController() {
        return this.gestionProduitController;
    }
    ServicesRating servicesRating  = new ServicesRating();
    UserService userService = new UserService();
    @FXML
    public void showdetails(ActionEvent event){
        setupShowDetailsButtonAction(produitFini);
    }
    public void setupCompareProductsAction(Set<ProduitFini> set){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/compareProduct.fxml"));
            Parent root = loader.load();
            compareProductController = loader.getController();
            compareProductController.setFirstCompareProduct(set );
            Image icon = new Image("/Icons/LOGO.png");
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        imageMateriel.setImage(image);
        MaterielName.setText(produitFini.getLibProduit());
        description.setText(produitFini.getDescription());
        categorie.setText(produitFini.getCategorie());
        totalRate.setText(String.valueOf(produitFini.getTotalRate()));
        artistelabel.setText(produitFini.getArtiste().getNom());
        this.produitFini = produitFini;
        compareButton.setOnAction(event->{
            gestionProduitController.comparelist.add(produitFini);
            gestionProduitController.getCartnumber().setText("1");
            if (gestionProduitController.comparelist.size() == 2){
                gestionProduitController.getCartnumber().setText("2");
                gestionProduitController.getCompare().setVisible(true);
                gestionProduitController.getCompare().setOnAction(event1->{
                    setupCompareProductsAction(gestionProduitController.comparelist);
                });
            }
        });

        removeproductcompare.setOnAction(event1->{
            if (gestionProduitController.comparelist.contains(produitFini)){
                if(gestionProduitController.comparelist.size() == 1){
                    gestionProduitController.getCartnumber().setText("0");
                    gestionProduitController.comparelist.remove(produitFini);
                    gestionProduitController.getCompare().setVisible(false);
                } else if(gestionProduitController.comparelist.size() == 2){
                    gestionProduitController.getCartnumber().setText("1");
                    gestionProduitController.comparelist.remove(produitFini);
                    gestionProduitController.getCompare().setVisible(false);
                }

            }
        });
        productrate.setOnMouseClicked(event -> {
            int newRating = (int) productrate.getRating();
            System.out.println("New rating: " + newRating);
            Rate existinguserRate = servicesRating.getRateByUserId(connecteduser,produitFini.getIdProduit());
               if (existinguserRate != null) {
                   existinguserRate.setRate(newRating);
                   servicesRating.update(existinguserRate);
               }  else {
                   Rate newRate = new Rate(1, newRating, new Date(), produitFini,connecteduser);
                   servicesRating.add(newRate);
               }
            storeRating(connecteduser,produitFini.getIdProduit(), newRating);
            isAddRating = !isAddRating;
        });
        removeratebutton.setOnAction(event2->{
            productrate.setRating(0);
            Rate newr = servicesRating.getRateByUserId(connecteduser,produitFini.getIdProduit());
            servicesRating.updateRate0(newr);
            storeRating(connecteduser,produitFini.getIdProduit(), (int)productrate.getRating());
        });
        qrimage.setOnMouseClicked(event1->{
            gestionProduitController.showQrCode(produitFini);
        });
        createpdf.setOnAction(event2->{
            gestionProduitController.generatePDF(produitFini);
        });
        donation.setOnAction(event3->{
            gestionProduitController.getDonationpane().setVisible(true);
            idfielddonation.setText(String.valueOf(produitFini.getArtiste().getUserId()));
        });
        int storedRating = getStoredRating(connecteduser,produitFini.getIdProduit());
        productrate.setRating(storedRating);
    }


    public void setFields(TextArea descrfield, TextField libfield, ComboBox<String> categorieComboBox, ImageView imgView,TextField idfield,TextField idfielddonation) {
        this.descrfield = descrfield;
        this.libfield = libfield;
        this.categorieComboBox = categorieComboBox;
        this.imgView = imgView;
        this.idfield=idfield;
        this.idfielddonation=idfielddonation;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userPrefs = Preferences.userRoot().node("/tn/arteco/controllers");
    }
    public void storeRating(int userId, int productId, int rating) {
        String composedKey = userId + "_" + productId;
        userPrefs.putInt(composedKey, rating);
        try {
            userPrefs.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }
    public int getStoredRating(int userId, int productId) {
        String composedKey = userId + "_" + productId;

        return userPrefs.getInt(composedKey,  0);

    }
}
