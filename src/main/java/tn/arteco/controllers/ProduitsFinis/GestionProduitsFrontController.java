package tn.arteco.controllers.ProduitsFinis;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import tn.arteco.controllers.GestionMateriel.AnimationController;
import tn.arteco.controllers.GestionMateriel.CodePageController;
import tn.arteco.models.Artiste;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.ProduitFini;
import tn.arteco.models.User;
import tn.arteco.services.*;

import java.io.*;
import java.net.URL;
import java.time.Year;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestionProduitsFrontController implements Initializable {
    EmailSender emailSender = new EmailSender();

    private final ServicesProduitsFinis servicesProduitsFinis = new ServicesProduitsFinis();
    private final UserService userService = new UserService();
    private Map<String, User> artistMap = new HashMap<>();
    ProduitFrontController produitFrontController;
    private ProduitFrontController produitController;
    @FXML
    private ImageView addproductbutton;
    public void setProduitController(ProduitFrontController produitController) {
        this.produitController = produitController;
    }
    List<String> artistNames = userService.getArtists().stream()
            .map(User::getNom)
            .collect(Collectors.toList());
    List <String> artistsusernme = userService.getArtists().stream()
            .map(User::getUsername)
            .collect(Collectors.toList());
    List<String> artistNamesmodified = Stream.concat(
                    userService.getArtists().stream().map(User::getNom),
                    Stream.of("default")
            )
            .collect(Collectors.toList());
    Set<String> productcategories = servicesProduitsFinis.getAll().stream().map(ProduitFini::getCategorie).collect(Collectors.toSet());
    Set<String> productscategoriesmodified = Stream.concat(servicesProduitsFinis.getAll().stream().map(ProduitFini::getCategorie),Stream.of("default")).collect(Collectors.toSet());
    private String imageUrl;
    @FXML
    private Button btnfile;
    @FXML
    private Label erreurpoints;
    @FXML
    private Label imgerror;
    @FXML
    private Label username;

    @FXML
    private Button produitFiniText;
    @FXML
    private ImageView produitFiniIcon;
    @FXML
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private ImageView boutiqueIcon;

    @FXML
    private Button boutiqueText;

    @FXML
    private ImageView coursIcon;

    @FXML
    private Button coursText;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button eventText;
    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button remoboursementText;
    @FXML
    private ImageView userPhoto;
    public Pane getDonationpane() {
        return donationpane;
    }

    @FXML
    private Label nommois;
    @FXML
    private ImageView imagemois;
    @FXML
    private Label cartnumber;
    @FXML
    private ComboBox<Integer> anneeexpiration;
    @FXML
    private TextField creditcard;
    @FXML
    private TextField cvc;
    @FXML
    private ComboBox<Integer> moisexpiration;
    @FXML
    private TextField montant;
    @FXML
    private TextField pointsdonate;


    public Label getCartnumber() {
        return cartnumber;
    }

    @FXML
    private Label prenommois;
    @FXML
    private Button donatepointsbutton;
    @FXML
    private Label description;
    @FXML
    private ImageView imgView;
    @FXML
    private Label libproduit;
    @FXML
    private ComboBox<String> tribox;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private TextArea descrfield;
    @FXML
    private TextField libfield;
    @FXML
    private AnchorPane mainContainer;
    private int userconnectedid = SessionManager.getInstance().getCurrentUser().getUserId();
    @FXML
    private Button donatebutton;
    @FXML
    private GridPane materielGrid;
    @FXML
    private ScrollPane materielContainer;
    @FXML
    private TextField idfield;
    @FXML
    private AnchorPane formpane;
    @FXML
    private Label welcomelabel;
    @FXML
    private TextField recherche;
    @FXML
    private ComboBox<String> artistbox;
    @FXML
    private ComboBox<String> categoriebox;
    @FXML
    private Label caterror;
    @FXML
    private Label descrerror;
    @FXML
    private Label labelerror;
    @FXML
    private Button compare;

    @FXML
    private ImageView qrimage;
    @FXML
    private Pane paneqr;
    @FXML
    private Label erreurannee;

    @FXML
    private Label erreurcredit;

    @FXML
    private Label erreurcvc;

    @FXML
    private Label erreurmois;

    @FXML
    private Label erreurmontant;
    @FXML
    private TextField idfielddonation;
    public Button getCompare() {
        return compare;
    }


    public Label getImgerror() {
        return imgerror;
    }

    public Label getCaterror() {
        return caterror;
    }

    public Label getDescrerror() {
        return descrerror;
    }

    public Label getLabelerror() {
        return labelerror;
    }

    public Button getDonatepointsbutton() {
        return donatepointsbutton;
    }

    public TextField getPointsdonate() {
        return pointsdonate;
    }

    @FXML
    private Circle circle;

    @FXML
    private Label pointmois;
    @FXML
    private Text points;
    @FXML
    private Pane donationpane;
    public Label getWelcomelabel(){
        return welcomelabel;
    }
    int row = 1;
    int col ;
    private User selectedArtist;
    private String categorie ;
    private String triselection  = null;
    private String lastSelectedArtist = null;
    private String lastSelectedCategory = null;
    public Set<ProduitFini> comparelist  = new HashSet<>();
    public AnchorPane getFormpane(){
        return formpane;
    }
    ServicesRating servicesRating = new ServicesRating();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (SessionManager.getInstance().getCurrentUser().getRole().toString().equals("ARTISTE")){
            addproductbutton.setVisible(true);
            addproductbutton.setManaged(true);
        }else {
            addproductbutton.setVisible(false);
            addproductbutton.setManaged(false);
        }
        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }
        Artiste artistedumois = servicesRating.findArtistWithBestRating();
        if (artistedumois != null) {
            nommois.setText(artistedumois.getNom());
            prenommois.setText(artistedumois.getPrenom());
            pointmois.setText(String.valueOf(artistedumois.getPoints()));
            Image image = new Image(artistedumois.getImageUrl());
            circle.setFill(new ImagePattern(image));
        }
        List<ProduitFini> produitFinis = servicesProduitsFinis.getAll();
        setGridProduits(produitFinis);
        List<User> allUsers = userService.getArtists();
        for (User user : allUsers) {
            artistMap.put(user.getUsername(), user);
        }
        //form
        ObservableList<String> observableCategories = FXCollections.observableArrayList(productcategories);
        categorieComboBox.setItems(observableCategories);
        categorieComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categorie = categorieComboBox.getValue();
        });
        //donation
        ObservableList<Integer> moisList = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            moisList.add(i);
        }
        moisexpiration.setItems(moisList);
        ObservableList<Integer> anneeList = FXCollections.observableArrayList();
        int currentYear = Year.now().getValue();
        for (int i = currentYear; i <= currentYear + 10; i++) {
            anneeList.add(i);
        }
        anneeexpiration.setItems(anneeList);
        //page
        String tri[] = {"TotalRate croissant","Total Rate decroissant","Libelle,A a Z","Libelle, Z a A","default"};
        List<String> triList = Arrays.asList(tri);
        ObservableList<String> observabletri = FXCollections.observableArrayList(triList);
        ObservableList<String> observableArtistNamesmodified = FXCollections.observableArrayList(artistNamesmodified);
        ObservableList<String> observablecategoriesmodified = FXCollections.observableArrayList(productscategoriesmodified);

        artistbox.setItems(observableArtistNamesmodified);
        artistbox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            lastSelectedArtist = artistbox.getValue();
            System.out.println(lastSelectedArtist);
            applyFilters();

        });
        categoriebox.setItems(observablecategoriesmodified);
        categoriebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            lastSelectedCategory = categoriebox.getValue();
            System.out.println(lastSelectedCategory);
            applyFilters();

        });
        //tri
        tribox.setItems(observabletri);
        tribox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            triselection = tribox.getValue();
            if (triselection == null) {
                System.out.println("No selection made.");
            } else if (lastSelectedArtist == null && lastSelectedCategory == null) {
                switch (triselection) {
                    case "TotalRate croissant" -> triRateUp();
                    case "Total Rate decroissant" -> triRateDown();
                    case "Libelle,A a Z" -> triLibUp();
                    case "Libelle, Z a A" -> triLibDown();
                    case "default" -> refreshProductList();
                    default -> System.out.println("xx");
                }
            }
            if (newValue != null) {
                System.out.println("Selected value: " + newValue);
                if ((lastSelectedArtist != null && !"default".equals(lastSelectedArtist) ) && (lastSelectedCategory != null && !"default".equals(lastSelectedCategory) )) {
                    switch (triselection) {
                        case "TotalRate croissant":
                            System.out.println("Sorting by totalRate croissant for category  :" + lastSelectedCategory + " and artist: " + lastSelectedArtist);
                            List<ProduitFini> filteredproducts = servicesProduitsFinis.filtreparArtisteCategorieTriRateAsc(lastSelectedArtist, lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();

                            break;
                        case "Libelle,A a Z":
                            System.out.println("Sorting by libelle croissant for category :" + lastSelectedCategory + " and artist: " + lastSelectedArtist);
                            filteredproducts = servicesProduitsFinis.filtreparArtisteCategorieTrilibAsc(lastSelectedArtist, lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Total Rate decroissant":
                            System.out.println("Sorting by totalRate decroissant for category :" + lastSelectedCategory + " and artist: " + lastSelectedArtist);
                            filteredproducts = servicesProduitsFinis.filtreparArtisteCategorieTriRateDesc(lastSelectedArtist, lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Libelle, Z a A":
                            System.out.println("Sorting by libelle decroissant for category :" + lastSelectedCategory + " and artist: " + lastSelectedArtist);
                            filteredproducts = servicesProduitsFinis.filtreparArtisteCategorieTrilibDesc(lastSelectedArtist, lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                    }
                } else if (lastSelectedArtist != null && "default".equals(lastSelectedCategory)) {
                    switch (newValue) {
                        case "TotalRate croissant":
                            System.out.println("Sorting by totalRate croissant for artist :" + lastSelectedArtist );
                            List<ProduitFini> filteredproducts = servicesProduitsFinis.filtreetTriRateAscParArtiste(lastSelectedArtist);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Libelle,A a Z":
                            System.out.println("Sorting by libelle croissant for artist :" + lastSelectedArtist );
                            filteredproducts = servicesProduitsFinis.filtreetTriLibAscParNomArtiste(lastSelectedArtist);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Total Rate decroissant":
                            System.out.println("Sorting by totalRate decroissant for artist :" + lastSelectedArtist );
                            filteredproducts = servicesProduitsFinis.filtreetTriRateDescParArtiste(lastSelectedArtist);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Libelle, Z a A":
                            System.out.println("Sorting by libelle decroissant for artist :" + lastSelectedArtist );
                            filteredproducts = servicesProduitsFinis.filtreetTriLibDescParNomArtiste(lastSelectedArtist);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                    }

                } else if (lastSelectedCategory != null && ("default".equals(lastSelectedArtist))) {
                    switch (newValue) {
                        case "TotalRate croissant":
                            System.out.println("Sorting by totalRate croissant for category :" + lastSelectedCategory );
                            List<ProduitFini> filteredproducts = servicesProduitsFinis.filtreParCategorieEtTriParRateAsc(lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Libelle,A a Z":
                            System.out.println("Sorting by libelle croissant for category :" + lastSelectedCategory );
                            filteredproducts = servicesProduitsFinis.filtreEtTriLibAscParCategorie(lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Total Rate decroissant":
                            System.out.println("Sorting by totalRate decroissant for category :" + lastSelectedCategory );
                            filteredproducts = servicesProduitsFinis.filtreParCategorieEtTriParRateDesc(lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                        case "Libelle, Z a A":
                            System.out.println("Sorting by libelle decroissant for category :" + lastSelectedCategory );
                            filteredproducts = servicesProduitsFinis.filtreEtTriLibDescParCategorie(lastSelectedCategory);
                            setGridProduits(filteredproducts);
                            resetColumns();
                            break;
                    }

                }
            }
            if ("default".equals(triselection) && "default".equals(lastSelectedArtist) && "default".equals(lastSelectedCategory)){
                refreshProductList();
            }
        });
        //recherche
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                materielGrid.getChildren().clear();
                List<ProduitFini> products = servicesProduitsFinis.rechercheProduit(recherche.getText());
                setGridProduits(products);
                resetColumns();
            } else {
                refreshProductList();
                resetColumns();
            }
        });
        AnimationController ac=new AnimationController();
        ac.moveNavBar(produitFiniIcon, produitFiniText);
        if(AnimationController.getNomCaller().equals("Materiel")) {
            materielIcon.setLayoutX(materielIcon.getLayoutX()+30);
            materielText.setLayoutX(materielText.getLayoutX()+30);
            ac.moveBackNavBar(materielIcon, materielText);

        }

        else if(AnimationController.getNomCaller().equals("Code")) {
            remoboursementIcon.setLayoutX(remoboursementIcon.getLayoutX()+30);
            remoboursementText.setLayoutX(remoboursementText.getLayoutX()+30);
            ac.moveBackNavBar(remoboursementIcon, remoboursementText);
        }

        else if (AnimationController.getNomCaller().equals("Boutique")) {
            boutiqueIcon.setLayoutX(boutiqueIcon.getLayoutX() + 30);
            boutiqueText.setLayoutX(boutiqueText.getLayoutX() + 30);
            ac.moveBackNavBar(boutiqueIcon, boutiqueText);

        }

        else if (AnimationController.getNomCaller().equals("Event")) {
            eventIcon.setLayoutX(eventIcon.getLayoutX() + 30);
            eventText.setLayoutX(eventText.getLayoutX() + 30);
            ac.moveBackNavBar(eventIcon, eventText);
        }
    }
    public void resetColumns(){
        row = 1 ;
        col = 0;
    }
    public void applyFilters() {
        materielGrid.getChildren().clear();
        if (lastSelectedArtist != null && lastSelectedCategory != null) {
            List<ProduitFini> filteredProducts = servicesProduitsFinis.filtreParArtisteEtCategorie(lastSelectedArtist, lastSelectedCategory);
            setGridProduits(filteredProducts);
            resetColumns();
        } else if ((lastSelectedArtist != null )) {
            List<ProduitFini> filterartistproducts = servicesProduitsFinis.filtreParNomArtiste(lastSelectedArtist);
            setGridProduits(filterartistproducts);
            resetColumns();
        } else if ((lastSelectedCategory != null)) {
            List<ProduitFini> filtercategorieproducts = servicesProduitsFinis.filtreParCategorie(lastSelectedCategory);
            setGridProduits(filtercategorieproducts);
            resetColumns();
        }
    }
    List<Boolean> donationerrors = new ArrayList<>(List.of(false,false,false,false,false));
    public void setGridProduits(List<ProduitFini> produitFinis){
        for (ProduitFini p :produitFinis){
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ProduitsFinisFXML/ProduitsFront.fxml"));
            VBox vbox= null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProduitFrontController pr=fxmlLoader.getController();
            pr.setGestionProduitController(this);
            pr.setMaterielData(p);
            pr.setFields(descrfield, libfield,categorieComboBox,imgView,idfield,idfielddonation);
            donatebutton.setOnAction(event1->{
                if(makePayment()){
                    showAlert("Payment Succeeded", "Your payment was successful.");
                    clearDonationFields();
                } else{
                    showAlert("Payment Failed", "An error occurred while processing your payment.");
                }
            });
            donatepointsbutton.setOnAction(event2->{
                boolean test = true ;
                String artistId = idfielddonation.getText();
                Artiste updatedartist= (Artiste)userService.getById(Integer.parseInt(artistId));
                if (pointsdonate.getText().isEmpty()){
                    erreurpoints.setText("Veuillez saisir un nombre de points !");
                    erreurpoints.setStyle("-fx-text-fill: red;");
                    test =false;
                }else{
                    erreurpoints.setText("");
                    test = true;
                }
               if (test) {
                   if (userconnectedid == Integer.parseInt(artistId)) {
                       showAlert("Donation Failed", "Your can't donate to yourself");
                       pointsdonate.clear();
                   } else if ((userService.getById(userconnectedid) instanceof NonArtiste a)) {
                       if (Integer.parseInt(pointsdonate.getText()) < a.getPoints()) {
                           updatedartist.setPoints(updatedartist.getPoints() + Integer.parseInt(pointsdonate.getText()));
                           a.setPoints(a.getPoints() - Integer.parseInt(pointsdonate.getText()));
                           userService.updateArtiste(a);
                           userService.updateArtiste(updatedartist);
                           points.setText(String.valueOf(a.getPoints()));
                           pointsdonate.clear();
                           Artiste artistedumois = servicesRating.findArtistWithBestRating();
                           if (artistedumois != null) {
                               nommois.setText(artistedumois.getNom());
                               prenommois.setText(artistedumois.getPrenom());
                               pointmois.setText(String.valueOf(artistedumois.getPoints()));
                               Image image = new Image(artistedumois.getImageUrl());
                               circle.setFill(new ImagePattern(image));
                           }
                           showAlert("Donation Succeeded", "Your points donation was successful.");
                       }
                       else {
                           showAlert("Donation Failed", "Your don't have enough points");
                           pointsdonate.clear();
                       }
                   }
               }
            });
            materielGrid.add(vbox,col++,row);
            if (col == 3){
                col=0;
                row ++ ;
            }
            GridPane.setMargin(vbox,new Insets(10));
        }
    }
    public void clearDonationFields(){
        creditcard.clear();
        montant.clear();
        cvc.clear();
        moisexpiration.getSelectionModel().clearSelection();
        anneeexpiration.getSelectionModel().clearSelection();
    }
    @FXML
    void closedonation(ActionEvent event) {
        donationpane.setVisible(false);
    }

    @FXML
    public void openForm(MouseEvent event){
        welcomelabel.setText("Ajouter Produit Fini");
        formpane.setVisible(true);
    }
    @FXML
    public void closeForm(MouseEvent event){
        formpane.setVisible(false);
    }
    @FXML
    public void triRateUp(){
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.triProduitByRateAsc();
        setGridProduits(products);
        resetColumns();
    }
    @FXML
    public void triRateDown(){
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.triProduitByRateDesc();
        setGridProduits(products);
        resetColumns();
    }
    public void triLibUp(){
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.triProduitByLibelleAsc();
        setGridProduits(products);
        resetColumns();
    }
    public void triLibDown(){
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.triProduitByLibelleDesc();
        setGridProduits(products);
        resetColumns();
    }
    @FXML
    public void refresh(){
        refreshProductList();
        tribox.getSelectionModel().clearSelection();
        artistbox.getSelectionModel().clearSelection();
        categoriebox.getSelectionModel().clearSelection();
        tribox.setValue(null);
        artistbox.setValue(null);
        categoriebox.setValue(null);
    }
    @FXML
    public void rechercheProduit(){
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                materielGrid.getChildren().clear();
                List<ProduitFini> products = servicesProduitsFinis.rechercheProduit(recherche.getText());
                setGridProduits(products);
                resetColumns();
            } else {
                refreshProductList();
                resetColumns();
            }
        });
    }
    public void refreshProductList() {
        materielGrid.getChildren().clear();
        List<ProduitFini> produitFinis = servicesProduitsFinis.getAll();
        Artiste artistedumois = servicesRating.findArtistWithBestRating();
        if (artistedumois != null) {
            nommois.setText(artistedumois.getNom());
            prenommois.setText(artistedumois.getPrenom());
            pointmois.setText(String.valueOf(artistedumois.getPoints()));
            Image image = new Image(artistedumois.getImageUrl());
            circle.setFill(new ImagePattern(image));
        }
        setGridProduits(produitFinis);
        resetColumns();

    }
    private void clearFormFields() {
        libfield.clear();
        descrfield.clear();
        imgView.setImage(null);
        imageUrl = null;
        categorieComboBox.getSelectionModel().clearSelection();
    }
    public boolean isValidCardDetails() {
        if (!creditcard.getText().matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
            erreurcredit.setText("Veuillez saisir une carte de crédit valide");
            erreurcredit.setStyle("-fx-text-fill: red;");
            donationerrors.set(0, true);
        } else{
            erreurcredit.setText("");
            donationerrors.set(0, false);
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(anneeexpiration.getValue() == null ){
            erreurannee.setText("Veuillez saisir une date valide !");
            erreurannee.setStyle("-fx-text-fill: red;");
            donationerrors.set(1, true);

        }else{
            erreurannee.setText("");
            donationerrors.set(1, false);
        }
        if(moisexpiration.getValue() == null){
            erreurmois.setText("Veuillez saisir une date valide !");
            erreurmois.setStyle("-fx-text-fill: red;");
            donationerrors.set(2, true);
        }else{
            erreurmois.setText("");
            donationerrors.set(2,false);
        }
        if (anneeexpiration.getValue() < currentYear  || (anneeexpiration.getValue() == currentYear && moisexpiration.getValue() < Calendar.getInstance().get(Calendar.MONTH) + 1)) {
            erreurannee.setText("Veuillez saisir une date valide !");
            erreurannee.setStyle("-fx-text-fill: red;");
            erreurmois.setText("Veuillez saisir une date valide !");
            erreurmois.setStyle("-fx-text-fill: red;");
            donationerrors.set(1, true);
            donationerrors.set(2, true);
        }else{
            erreurannee.setText("");
            erreurmois.setText("");
            donationerrors.set(1, false);
            donationerrors.set(2, false);
        }
        if(cvc.getText().isEmpty()){
            erreurcvc.setText("Veuillez saisir un cvc valide !");
            erreurcvc.setStyle("-fx-text-fill: red;");
            donationerrors.set(3,true);
        }else{
            erreurcvc.setText("");
            donationerrors.set(3,false);
        }
        if (!cvc.getText().matches("^[0-9]{3}$")) {
            erreurcvc.setText("Veuillez saisir un cvc valide !");
            erreurcvc.setStyle("-fx-text-fill: red;");
            donationerrors.set(3,true);
        }else{
            erreurcvc.setText("");
            donationerrors.set(3,false);
        }
        if(montant.getText().isEmpty() ){
            erreurmontant.setText("Veuillez saisir un montant supérieur a 500 ( centimes ) ");
            erreurmontant.setStyle("-fx-text-fill: red;");
            donationerrors.set(4,true);
        }else{
            erreurmontant.setText("");
            donationerrors.set(4,false);
        }
        if(Integer.parseInt(montant.getText())<500 ){
            erreurmontant.setText("Veuillez saisir un montant supérieur a 500 ( centimes ) ");
            erreurmontant.setStyle("-fx-text-fill: red;");
            donationerrors.set(4,true);
        }else{
            erreurmontant.setText("");
            donationerrors.set(4,false);
        }
        boolean falsee = donationerrors.stream().allMatch(value->value.equals(false));

        if(falsee){
            return true;
        }else{
            return false;
        }
    }

    public boolean makePayment () {
        try{
            if(isValidCardDetails()){
                Stripe.apiKey = "sk_test_51OoXzvFsr5ihxnA1cf9z8ztT6RxAqYe7NcS1QGrzHaxk4eVh1KktcsJO8cWu1qgD34OdLViJW6u1vzRlQq58KmFN00daQPDHKY";
                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount(Long.parseLong(montant.getText()))
                        .setCurrency("gbp")
                        .setPaymentMethod("pm_card_visa")
                        .build();
                PaymentIntent paymentIntent = PaymentIntent.create(params);
                PaymentIntent confirmedPaymentIntent = paymentIntent.confirm();
                return  true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @FXML
    void ajouterProduit(ActionEvent event) {
        List<Boolean> hasErrors = new ArrayList<>(List.of(false,false,false,false,false));
        try {
            String libProduit = libfield.getText();
            String description = descrfield.getText();
            if(libProduit.length() <  5){
                labelerror.setText("Veuillez saisir un libellé de taille  5 minimum");
                labelerror.setStyle("-fx-text-fill: red;");
                hasErrors.set(0, true);
            }
            else{
                labelerror.setText("");
                hasErrors.set(0, false);
            }

            if (description.length() <  10) {
                descrerror.setText("Veuillez saisir une description de taille  10 minimum");
                descrerror.setStyle("-fx-text-fill: red;");
                hasErrors.set(1, true);
            }
            else{
                descrerror.setText("");
                hasErrors.set(1, false);
            }


            if (categorieComboBox.getValue() == null){
                caterror.setText("Veuillez sélectionner une catégorie");
                caterror.setStyle("-fx-text-fill: red;");
                hasErrors.set(3, true);
            }
            else{
                caterror.setText("");
                hasErrors.set(3, false);
            }
            if (imageUrl == null){
                imgerror.setText("Veuillez choisir une image");
                imgerror.setStyle("-fx-text-fill: red;");
                hasErrors.set(4, true);
            }else{
                imgerror.setText("");
                hasErrors.set(4, false);
            }
            boolean allFalse = hasErrors.stream().allMatch(value->value.equals(false));
            if(allFalse){
                ProduitFini newProduct = new ProduitFini(1,(Artiste) SessionManager.getInstance().getCurrentUser(), libProduit, description, imageUrl,  0, categorie, new Date());
                servicesProduitsFinis.add(newProduct);
                emailSender.sendEmail("mohamedaziztheiri@gmail.com","Votre produit a été ajouté avec succès ! ",newProduct);
                ((Artiste) SessionManager.getInstance().getCurrentUser()).setPoints(((Artiste) SessionManager.getInstance().getCurrentUser()).getPoints() +  20);
                userService.updateArtiste((Artiste) SessionManager.getInstance().getCurrentUser());
                clearFormFields();
                refreshProductList();
                Artiste artistedumois = servicesRating.findArtistWithBestRating();
                if (artistedumois != null) {
                    nommois.setText(artistedumois.getNom());
                    prenommois.setText(artistedumois.getPrenom());
                    pointmois.setText(String.valueOf(artistedumois.getPoints()));
                    Image image = new Image(artistedumois.getImageUrl());
                    circle.setFill(new ImagePattern(image));
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void closeqr(ActionEvent event) {
        paneqr.setVisible(false);
    }
    public void showQrCode(ProduitFini product) {
        try {
            paneqr.setVisible(false);
            String content = "Product: " + product.getLibProduit()+ "\nTotalRate: " + product.getTotalRate() + "\nDescription: " + product.getDescription() + "\nImage: " + product.getImageUrl() + "\ncategorie: " + product.getCategorie() + "\nOwner: " + product.getArtiste().getNom() ;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 214, 214, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            byte[] qrCodeBytes = outputStream.toByteArray();
            javafx.scene.image.Image qrCodeImage = new javafx.scene.image.Image(new ByteArrayInputStream(qrCodeBytes));
            qrimage.setImage(qrCodeImage);
            paneqr.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void generatePDF(ProduitFini produitFini){
        Document doc = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\USER\\Desktop\\sample.pdf"));
            System.out.println("PDF created.");
            doc.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph title = new Paragraph("Product Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            doc.add(title);
            Paragraph details = new Paragraph();
            details.add(new Chunk("Product Name: ", normalFont));
            details.add(new Chunk(produitFini.getLibProduit(), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.add(new Chunk("\nCategory: ", normalFont));
            details.add(new Chunk(produitFini.getCategorie(), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.add(new Chunk("\nDescription: ", normalFont));
            details.add(new Chunk(produitFini.getDescription(), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.add(new Chunk("\nArtist Name: ", normalFont));
            details.add(new Chunk(produitFini.getArtiste().getNom(), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.setAlignment(Element.ALIGN_JUSTIFIED);
            doc.add(details);            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException d) {
            System.out.println(d.getMessage());
        }
    }
    @FXML
    public void fileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(btnfile.getScene().getWindow());
        if (selectedFile != null) {
            String baseUrl = "http://127.0.0.1/images/";
            String imageName = selectedFile.getName();
            imageUrl = baseUrl + imageName;
            imgView.setImage(new Image(imageUrl));
            // Send the imageUrl to your database using your database logic
            System.out.println("Image URL: " + imageUrl);
        } else {
            System.out.println("No file has been selected");
        }
    }
    @FXML
    void naviguerVersAccueil(MouseEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
            Parent root = loader.load();
            mainContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void routeToProfile(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
        public void routeToMateriel(ActionEvent event){
        AnimationController.setNomCaller("ProduitFini");

        try{
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
            Parent root=fl.load();
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
@FXML
    void remboursementClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("ProduitFini");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodePage.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }

    @FXML
    void coursClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("ProduitFini");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionCour/GestionCour.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @FXML
    void eventClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("ProduitFini");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/GestionEvenement/AfficherFront.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }
    @FXML
    public void naviguerVersBoutique() {
        try {
            AnimationController.setNomCaller("ProduitFini");
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/Boutique.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void disconnect(MouseEvent event) {


        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            materielContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }



    @FXML
    public void homeClicked(MouseEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
