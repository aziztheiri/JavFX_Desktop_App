package tn.arteco.controllers.gestionRecompense;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.arteco.controllers.GestionMateriel.AnimationController;
import tn.arteco.game.GameFrame;
import tn.arteco.game.GamePanel;
import tn.arteco.game.SnakeGame;
import tn.arteco.models.Marchandise;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.services.MarchandiseFactureService;
import tn.arteco.services.MarchandiseService;
import tn.arteco.services.SessionManager;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BoutiqueController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
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
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private AnchorPane navbarHolder;

    @FXML
    private Text points;

    @FXML
    private ImageView produitFiniIcon;

    @FXML
    private Button produitFiniText;

    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button t;

    @FXML
    private ImageView userPhoto;

    @FXML
    private Label username;
    @FXML
    private GridPane marchandiseGrid;
    @FXML
    private ComboBox<String> triBox;
    @FXML
    private ImageView mostImg;

    @FXML
    private Button remoboursementText;
    @FXML
    private Label mostLebelle;
    @FXML
    private TextField tfResearch;
    private ArrayList<Marchandise>researchList=new ArrayList<>();
    private User user=new User();
    private MarchandiseService mS=new MarchandiseService();
    private MarchandiseFactureService mFS =new MarchandiseFactureService();
    private UserService uS=new UserService();
    ArrayList<Marchandise> listMarchandise=new ArrayList<>();
    private int most;
    private Marchandise marchandise;
    private int userId;

    @FXML
    void onResearch(KeyEvent event) {
    loadList();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }

        this.user=uS.getById(SessionManager.getInstance().getCurrentUser().getUserId());
        this.most= mFS.getMostPopular();
        if (this.most!=-1) {
            this.marchandise = mS.getById(most);
            this.mostImg.setImage(new Image(marchandise.getImageUrl()));
            this.mostLebelle.setText(marchandise.getLibelle());
        }else {
        this.mostLebelle.setText("Pas Disponible");
        }
        triBox.setItems(FXCollections.observableArrayList("Trier par Libelle A-z", "Trier par Prix","par defaut"));
        triBox.setValue("par defaut");
        this.listMarchandise=this.mS.getAll();
        int rows=1;
        int columns=1;
        try {
            for (Marchandise m : this.listMarchandise) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseCard.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                MarchandiseCardController marchandiseCardController = fxmlLoader.getController();
                marchandiseCardController.setData(m);
                if (columns==5){
                    columns=1;
                    ++rows;
                }
                this.marchandiseGrid.add(anchorPane,columns++,rows);
                GridPane.setMargin(anchorPane,new Insets(7));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        AnimationController ac=new AnimationController();
        ac.moveNavBar(boutiqueText, boutiqueIcon);
        if (AnimationController.getNomCaller().equals("ProduitFini")) {
            produitFiniIcon.setLayoutX(produitFiniIcon.getLayoutX() + 30);
            produitFiniText.setLayoutX(produitFiniText.getLayoutX() + 30);
            ac.moveBackNavBar(produitFiniIcon, produitFiniText);
        } else if (AnimationController.getNomCaller().equals("Cours")) {
            coursIcon.setLayoutX(coursIcon.getLayoutX() + 30);
            coursText.setLayoutX(coursText.getLayoutX() + 30);
            ac.moveBackNavBar(coursIcon, coursText);
        } else if (AnimationController.getNomCaller().equals("Code")) {
            remoboursementIcon.setLayoutX(remoboursementIcon.getLayoutX() + 30);
            remoboursementText.setLayoutX(remoboursementText.getLayoutX() + 30);
            ac.moveBackNavBar(remoboursementIcon, remoboursementText);

        } else if (AnimationController.getNomCaller().equals("Event")) {
            eventIcon.setLayoutX(eventIcon.getLayoutX() + 30);
            eventText.setLayoutX(eventText.getLayoutX() + 30);
            ac.moveBackNavBar(eventIcon, eventText);
        } else if (AnimationController.getNomCaller().equals("Materiel")) {
            materielIcon.setLayoutX(materielIcon.getLayoutX() + 30);
            materielText.setLayoutX(materielText.getLayoutX() + 30);
            ac.moveBackNavBar(materielIcon, materielText);

        }

    }

    @FXML
    void onTri(ActionEvent event) {
        if (this.triBox.getValue().equals("Trier par Libelle A-z"))
        {
           this.listMarchandise= mS.sortBylibelle(this.listMarchandise);
            this.loadList();
        }
        else if (this.triBox.getValue().equals("Trier par Prix"))
        {
            this.listMarchandise= mS.sortByPrix(this.listMarchandise);
            this.loadList();
        } else if (this.triBox.getValue().equals("par defaut")) {
            this.listMarchandise=mS.getAll();
            this.loadList();
        }
    }
    public void loadList(){
        boolean action=true;
        if(this.tfResearch.getText().matches("\\s*")){
            this.researchList=this.listMarchandise;
            action=false;
            this.tfResearch.setStyle("-fx-background-color: transparent");
        }else {
            this.tfResearch.setStyle("-fx-background-color: #a6ceff;-fx-background-radius: 30");

            this.researchList = this.listMarchandise.stream().
                    filter(e -> MarchandiseService.containAnyWord(this.tfResearch.getText().trim(), e.getDescription()) ||
                            MarchandiseService.containAnyWord(this.tfResearch.getText(), e.getLibelle()) ||
                            MarchandiseService.containAnyWord(this.tfResearch.getText(), e.getTitreObtenu())).
                    collect(Collectors.toCollection(ArrayList::new));
        }
        this.marchandiseGrid.getChildren().clear();
        int rows=1;
        int columns=1;
        try {
            for (Marchandise m : this.researchList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseCard.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                MarchandiseCardController marchandiseCardController = fxmlLoader.getController();
                marchandiseCardController.setData(m);
                if (columns==5){
                    columns=1;
                    ++rows;
                }
                this.marchandiseGrid.add(anchorPane,columns++,rows);
                GridPane.setMargin(anchorPane,new Insets(7));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public void resetPoints(int p)
    {
            int oldPoints= Integer.parseInt(this.points.getText());
            this.points.setText(String.valueOf(p+oldPoints));

    }
    @FXML
    void goToPointStore(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/gestionRecomponse/PointStore.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

            stage.show();
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void openGame(MouseEvent event) {
        SnakeGame sg=new SnakeGame();
        sg.setParent(this);
        sg.startGame(user.getUserId());


    }
    @FXML
    void produitFiniClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Boutique");
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));

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
        AnimationController.setNomCaller("Boutique");
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
        AnimationController.setNomCaller("Boutique");
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
    public void routeToMateriel(ActionEvent event){
        AnimationController.setNomCaller("Boutique");

        try{
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void remboursementClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Boutique");
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
    public void disconnect(MouseEvent event) {


        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void routeToProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
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
