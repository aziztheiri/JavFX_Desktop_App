package tn.arteco.controllers.GestionMateriel;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.arteco.models.Code;
import tn.arteco.models.Materiel;
import tn.arteco.models.NonArtiste;
import tn.arteco.services.ServiceCode;
import tn.arteco.services.ServiceMateriel;
import tn.arteco.services.SessionManager;

import java.util.List;
import java.util.Random;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class GestionMaterielController {

    private String username= SessionManager.getInstance().getCurrentUser().getUsername();
    @FXML
    private Label user;
    @FXML
    private ImageView userPhoto;
    @FXML
    private Text points;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private GridPane materielGrid;

    int rows = 1;
    int columns;

    @FXML
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private ComboBox<String> triParComboBox;

    @FXML
    private Label nbrReservation;
    AnimationController ac;

    @FXML
    private Button produitFiniText;
    @FXML
    private ImageView produitFiniIcon;


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
    private ScrollPane scrollPane;
    @FXML
    private ImageView getCode;

    private MaterielController mc;

    private String allIds="";

    private Preferences pre;
    int qte;
    int etatCode;
    private int etatMat,etatProduitFini,etatCours,etatEvent,etatBoutique,etatRemboursement;

    @FXML
    private MenuItem recupererCodeItem;
    @FXML
    private Circle notificationDot;


    public void setMaterielGrid(List<Materiel>lm)
    {

        for (Materiel m:lm)
        {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gestionMateriel/materiel.fxml"));

            Pane vbox= null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mc = fxmlLoader.getController();
            mc.setParentMC(this);

            mc.setMaterielData(m);
            if(columns>2)
            {
                columns=0;
                rows++;
            }
            materielGrid.add(vbox,columns++,rows);
            GridPane.setMargin(vbox,new Insets(20));
        }
    }

    public void resetRowsColumns()
    {
        rows=1;
        columns=0;
    }

    public void setAllIds(String id)
    {
        allIds=allIds+id;
        pre.put(username,allIds);
    }

    public void setQte(int qte)
    {
        this.qte+=qte;
        pre.putInt("NumberReservation"+username,this.qte);
    }

    @FXML
    public void testClicked(ActionEvent event)
    {
        System.out.println(allIds);
    }

    public void verifierCadeau()
    {
        ServiceCode sc=new ServiceCode();
        System.out.println(sc.getMatValide(1));
        if(sc.getMatValide(SessionManager.getInstance().getCurrentUser().getUserId())>=5) {

            ac.animateButton(getCode);
            etatCode=1;
        }
        else {
            ac.stopButtonAnimation(getCode);
            etatCode=0;
        }
    }
    @FXML
    public void initialize() throws IOException, BackingStoreException {
        ac = new AnimationController();

        user.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a) {
            points.setText(String.valueOf(a.getPoints()));
        }

        pre = Preferences.userRoot().node("/tn/arteco/controllers");
        //pre.clear();
        qte = pre.getInt("NumberReservation" + username, 0);

        allIds = pre.get(username, "");
        nbrReservation.setText(String.valueOf(qte));
        verifierCadeau();
        if (etatCode == 1)
            ac.animateButton(getCode);

        ServiceMateriel sm = new ServiceMateriel();
        ArrayList<Materiel> lm = sm.getAll();
        setMaterielGrid(lm);
        resetRowsColumns();
        triParComboBox.setItems(FXCollections.observableArrayList("Nom Matériel", "Adresse", "Quantité", "Aucun"));

        if(AnimationController.getNomCaller().equals("stop"))
        {
            materielText.setLayoutX(materielText.getLayoutX() + 30);
            materielIcon.setLayoutX(materielIcon.getLayoutX() + 30);
        }
        else {
            ac.moveNavBar(materielText, materielIcon);
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
            } else if (AnimationController.getNomCaller().equals("Boutique")) {
                boutiqueIcon.setLayoutX(boutiqueIcon.getLayoutX() + 30);
                boutiqueText.setLayoutX(boutiqueText.getLayoutX() + 30);
                ac.moveBackNavBar(boutiqueIcon, boutiqueText);

            }
        }
    }

    public void incrementerReservation(int qte)
    {
        int res=Integer.parseInt(this.nbrReservation.getText());
        res+=qte;
        this.nbrReservation.setText(String.valueOf(res));
        pre.putInt("NumberReservation"+username,res);
    }

    @FXML
    void getTriType(ActionEvent event) {
        ServiceMateriel sm=new ServiceMateriel();
        List<Materiel> lm=sm.getAll();
        String type=triParComboBox.getValue();
        if(type.equals("Nom Matériel"))
        {
            lm=sm.trierListParLibelle(lm);
        }
        else if (type.equals("Adresse"))
            lm=sm.trierListParAdresse(lm);
        else if(type.equals("Quantité"))
            lm=sm.trierListParQuantite(lm);
        setMaterielGrid(lm);
        resetRowsColumns();
    }

    @FXML
    void remboursementClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Materiel");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodePage.fxml"));
            Parent root=fl.load();
            CodePageController cc=fl.getController();
            cc.setCaller("materiel");

            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }




    @FXML
    void ajouterProduit(MouseEvent event) throws IOException {
        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/AjouterProduit.fxml"));
        Parent root=fl.load();
        Stage ajouterMatStage=new Stage();
        ajouterMatStage.setScene(new Scene(root));
        AjouterProduit ap=fl.getController();
        ap.setParent(this);
        ajouterMatStage.show();
    }


    @FXML
    void panierClicked(MouseEvent event) throws IOException {

        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/PanierMateriel.fxml"));
        Parent root=fl.load();
        triParComboBox.getScene().setRoot(root);
    }

    void getCodeClicked() {
        if (etatCode == 1) {
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/gestionMateriel/Code.fxml"));
            notificationDot.setVisible(true);
            try {
                Parent root = fl.load();
                Stage getCodeStage = new Stage();
                getCodeStage.setScene(new Scene(root));
                CodeController cc = fl.getController();
                LocalDate date = LocalDate.now().plusDays(5);
                Date dateValide = java.sql.Date.valueOf(date);

                String myCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                String finalCode = "";
                Random random = new Random();
                for (int i = 0; i < 6; i++) {
                    int index = random.nextInt(myCode.length());
                    finalCode += myCode.charAt(index);
                }
                Random randmPoints = new Random();
                int randomP = randmPoints.nextInt((300 - 50 + 1) + 50);
                ServiceCode sc = new ServiceCode();
                Code c = new Code(1, finalCode, dateValide, "nonUtilise", randomP, SessionManager.getInstance().getCurrentUser().getUserId());
                cc.setCode(finalCode, randomP);
                sc.add(c);
                verifierCadeau();
                cc.setParent(this);
                Lighting lighting = new Lighting();
                lighting.setDiffuseConstant(0.67);
                mainContainer.setEffect(lighting);
                getCodeStage.initStyle(StageStyle.TRANSPARENT);
                getCodeStage.initModality(Modality.APPLICATION_MODAL);

                getCodeStage.show();
                getCodeStage.setOnHiding(e -> mainContainer.setEffect(null));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    void recupererCodeClicked(MouseEvent event) {
        if(etatCode==1)
        {
            notificationDot.setVisible(true);
            recupererCodeItem.setStyle("-fx-text-fill:red");
            recupererCodeItem.setStyle("-fx-font-family:Poppins Meduim");
            recupererCodeItem.setDisable(false);

        }
        else {
            notificationDot.setVisible(false);
            //recupererCodeItem.setStyle("-fx-text-fill:#898989");
            recupererCodeItem.setDisable(true);
            recupererCodeItem.setStyle("-fx-background:none");

        }
    }

    @FXML
    void recupererCodeItem(ActionEvent event) {

        getCodeClicked();
    }

    @FXML
    void getListCode(ActionEvent event) {

        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/ListCode.fxml"));
        Parent root= null;
        try {
            root = fl.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        Stage afficherListCode=new Stage();
        ListCodeController lc=fl.getController();
        lc.setParent(this);
        afficherListCode.setScene(new Scene(root));
        afficherListCode.show();
    }


    @FXML
    void produitFiniClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Materiel");
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
        AnimationController.setNomCaller("Materiel");
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
        AnimationController.setNomCaller("Materiel");
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
            AnimationController.setNomCaller("Materiel");

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
