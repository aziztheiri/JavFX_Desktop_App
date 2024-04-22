package tn.arteco.controllers.gestionRecompense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.arteco.controllers.GestionMateriel.AnimationController;
import tn.arteco.models.Marchandise;
import tn.arteco.models.MarchandiseFacture;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.services.MarchandiseFactureService;
import tn.arteco.services.SessionManager;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MarchandiseDetailController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView boutiqueIcon;

    @FXML
    private Button boutiqueText;

    @FXML
    private Button btnAcheter;

    @FXML
    private ImageView coursIcon;

    @FXML
    private Button coursText;

    @FXML
    private Label dateLimite;

    @FXML
    private Label dateSortie;

    @FXML
    private Text description;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button eventText;

    @FXML
    private Label libelle;

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
    private TextField quantite;

    @FXML
    private Label quantiteDiso;

    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button t;

    @FXML
    private Label titreObtenu;

    @FXML
    private ImageView userPhoto;

    @FXML
    private Label username;
    @FXML
    private ImageView imgMarchandise;
    @FXML
    private Label netPayer;
    @FXML
    private Label prix;
    private Marchandise marchandise;
    private User user = SessionManager.getInstance().getCurrentUser();
    private UserService uS=new UserService();
    private MarchandiseFactureService mFS=new MarchandiseFactureService();

   //&& event.getText().matches("\\s*")
    //this.quantite.getText().indexOf(event.getText())
    @FXML
    void testQuantite(KeyEvent event) {

        boolean arrows=event.getCode().isArrowKey();
//        boolean arrows=event.getCode().isLetterKey();
        System.out.println(arrows);
        String ch=this.quantite.getText();
        System.out.println(ch.length());
        System.out.println(ch);
//        if(){
        if (!event.getText().matches("[0-9]")&&!event.getText().isEmpty()) {
                int index=this.quantite.getText().indexOf(event.getText());
            while (index!= -1) {
                this.quantite.setText(this.quantite.getText().substring(0,index)+this.quantite.getText().substring(index+1));
                this.quantite.positionCaret(this.quantite.getLength());
                index=this.quantite.getText().indexOf(event.getText());
            }

            System.out.println("el position ="+this.quantite.getCaretPosition());
                System.out.println(this.quantite.getText());

            }else if(arrows && (this.quantite.getCaretPosition()!=this.quantite.getText().length()))
            {
                System.out.println("---" + this.quantite.getLength() + "---");
            this.quantite.positionCaret(this.quantite.getLength());

            }

        if(this.quantite.getText().isEmpty()){
            this.quantite.setText("1");


        }
        else if(Integer.valueOf(this.quantite.getText())>this.marchandise.getQuantiteDispo())
        {this.quantite.setText(String.valueOf(this.marchandise.getQuantiteDispo()));

        }
        else if(Integer.valueOf(this.quantite.getText())*marchandise.getPrix()>((NonArtiste)this.user).getPoints())
        {
            this.quantite.setText(String.valueOf(((NonArtiste)this.user).getPoints()/this.marchandise.getPrix()));

        }
        this.netPayer.setText(String.valueOf(marchandise.getPrix()*Integer.valueOf(this.quantite.getText())));
    }

    @FXML
    void acheter(ActionEvent event) {
        MarchandiseFacture marchandiseFacture=new MarchandiseFacture();
        marchandiseFacture.setMarchandise(this.marchandise);
        marchandiseFacture.setNetPayer(Integer.valueOf(this.netPayer.getText()));
        marchandiseFacture.setDateF(new Date());
        marchandiseFacture.setNonArtiste((NonArtiste) user);
        marchandiseFacture.setQuantite(Integer.valueOf(this.quantite.getText()));
        if(SessionManager.getInstance().getCurrentUser() instanceof NonArtiste na)
        {
            int points=na.getPoints()-Integer.parseInt(netPayer.getText());
            na.setPoints(points);
        }
        mFS.add(marchandiseFacture);
        try {
            root = FXMLLoader.load(getClass().getResource("/gestionRecomponse/Boutique.fxml"));
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
    void goToBoutique(MouseEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/Boutique.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();

        scene =new Scene(root);
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

        stage.show();
    }
    public void setData(Marchandise m){
       this.marchandise=m;
       this.imgMarchandise.setImage(new Image(m.getImageUrl()));
       this.libelle.setText(m.getLibelle());
       this.description.setText(m.getDescription());
       this.titreObtenu.setText(m.getTitreObtenu());
       this.dateSortie.setText(m.getDateSortie().toString());
       this.prix.setText(String.valueOf(m.getPrix()));
        this.netPayer.setText(String.valueOf(m.getPrix()));
       if(m.getDateLimit()!=null)
           this.dateLimite.setText(m.getDateLimit().toString());
       else this.dateLimite.setText("Illimite");
       this.quantiteDiso.setText(String.valueOf(m.getQuantiteDispo()));
       if (m.getQuantiteDispo()==0){
           this.btnAcheter.setDisable(true);
            this.quantite.setDisable(true);
            this.quantiteDiso.setStyle("-fx-text-fill:red");}
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }

    if(((NonArtiste)user).getPoints()<=0)
        this.btnAcheter.setDisable(true);
    this.quantite.setText("1");

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




}
