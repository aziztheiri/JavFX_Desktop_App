package tn.arteco.controllers.ProduitsFinis;

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
import javafx.stage.FileChooser;
import tn.arteco.models.Artiste;
import tn.arteco.models.ProduitFini;
import tn.arteco.models.User;
import tn.arteco.services.ServicesProduitsFinis;
import tn.arteco.services.SessionManager;
import tn.arteco.services.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestionProduitFinisController implements Initializable {
private final ServicesProduitsFinis servicesProduitsFinis = new ServicesProduitsFinis();
private final UserService userService = new UserService();
private Map<String, User> artistMap = new HashMap<>();
    private ProduitController produitController;
    public void setProduitController(ProduitController produitController) {
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
    private Label imgerror;
    @FXML
    private Label description;
    @FXML
    private ImageView imgView;
    @FXML
    private Label libproduit;
    @FXML
    private ComboBox<String> tribox;
    @FXML
    private ComboBox<String> artistComboBox;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private TextArea descrfield;
    @FXML
    private TextField libfield;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private GridPane materielGrid;
    @FXML
    private ScrollPane materielContainer;
    @FXML
    private Button updateButton;
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

    public Label getArtisterror() {
        return artisterror;
    }

    @FXML
    private Label artisterror;
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
    public AnchorPane getFormpane(){
        return formpane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<ProduitFini> produitFinis = servicesProduitsFinis.getAll();
        setGridProduits(produitFinis);
        List<User> allUsers = userService.getArtists();
        for (User user : allUsers) {
            artistMap.put(user.getUsername(), user);
        }
        //form
        ObservableList<String> observableArtistNames = FXCollections.observableArrayList(artistsusernme);

        artistComboBox.setItems(observableArtistNames);
        artistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedArtist = artistMap.get(newValue);
        });
        ObservableList<String> observableCategories = FXCollections.observableArrayList(productcategories);
        categorieComboBox.setItems(observableCategories);
        categorieComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categorie = categorieComboBox.getValue();
        });
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
            } else {
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

    public void setGridProduits(List<ProduitFini> produitFinis){
        for (ProduitFini p :produitFinis){
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ProduitsFinisFXML/ProduitsBack.fxml"));
            VBox vbox= null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProduitController pr=fxmlLoader.getController();
            pr.setGestionProduitController(this);
            pr.setMaterielData(p);
            pr.deleteData(p);
            pr.setFields(descrfield, libfield,artistComboBox,categorieComboBox,imgView,idfield);
            updateButton.setOnAction(event -> {
                String libProduit = libfield.getText();
                String description = descrfield.getText();
                String imageUrl = imgView.getImage().getUrl();
                String artisteNom = artistComboBox.getValue();
                String categorie = categorieComboBox.getValue();
                String id = idfield.getText();
                User artiste = artistMap.get(artisteNom);
                List<Boolean> hasErrors = new ArrayList<>(List.of(false,false,false,false,false));
                try {
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

                    if (artistComboBox.getValue() == null) {
                        artisterror.setText("Veuillez sélectionner un artiste");
                        artisterror.setStyle("-fx-text-fill: red;");
                        hasErrors.set(2, true);
                    }
                    else{
                        artisterror.setText("");
                        hasErrors.set(2, false);
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
                        ProduitFini updatedProduit = new ProduitFini(
                                Integer.parseInt(id),
                                (Artiste) artiste,
                                libProduit,
                                description,
                                imageUrl,
                                p.getTotalRate(),
                                categorie,
                                new Date()
                        );
                        servicesProduitsFinis.update(updatedProduit);
                        clearFormFields();
                        refreshProductList();
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            });
            materielGrid.add(vbox,col++,row);
            if (col == 2){
                col=0;
                row ++ ;
            }
            GridPane.setMargin(vbox,new Insets(10));
        }
    }
    private void handleArtistSelection(boolean isSelected, String artistName) {
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.filtreParNomArtiste(artistName);
        setGridProduits(products);
        resetColumns();
    }
    private void handleCategorySelection(boolean isSelected, String category) {
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.filtreParCategorie(category);
        setGridProduits(products);
        resetColumns();
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
    public void top3product() {
        materielGrid.getChildren().clear();
        List<ProduitFini> products = servicesProduitsFinis.top3ProduitRate();
        setGridProduits(products);
        resetColumns();

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
        setGridProduits(produitFinis);
        resetColumns();

    }
    private void clearFormFields() {
        libfield.clear();
        descrfield.clear();
        imgView.setImage(null);
        imageUrl = null;
        artistComboBox.getSelectionModel().clearSelection();
        categorieComboBox.getSelectionModel().clearSelection();
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

            if (artistComboBox.getValue() == null) {
                artisterror.setText("Veuillez sélectionner un artiste");
                artisterror.setStyle("-fx-text-fill: red;");
                hasErrors.set(2, true);
            }
            else{
                artisterror.setText("");
                hasErrors.set(2, false);
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
                    ProduitFini newProduct = new ProduitFini(1, (Artiste) selectedArtist, libProduit, description, imageUrl,  0, categorie, new Date());
                    servicesProduitsFinis.add(newProduct);
                    clearFormFields();
                    refreshProductList();
                }

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    void naviguerVersDashboard(ActionEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardBack.fxml"));
            Parent root = loader.load();
            mainContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void materielBackClicked(ActionEvent event) {

        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
            mainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
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
