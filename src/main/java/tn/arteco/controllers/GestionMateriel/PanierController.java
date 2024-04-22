package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tn.arteco.models.Materiel;
import tn.arteco.models.NonArtiste;
import tn.arteco.services.ServiceMateriel;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.util.*;
import java.util.prefs.Preferences;

public class PanierController {

    private String username= SessionManager.getInstance().getCurrentUser().getUsername();

    @FXML
    private Label user;

    @FXML
    private ImageView userPhoto;
    @FXML
    private Text points;

    @FXML
    private GridPane panierContainer;
    @FXML
    private Text nombreMateriel;

    private Map<Integer,Integer> lm;
    List<Materiel>listMat;

    int rows=1;
    @FXML
    private Text panierVide;
    @FXML
    private Button confirmerReservation;

    @FXML
    private Text poursuivreNavigation;
    @FXML
    private Text reservationEffectue;
    public void setPanierContainer(List<Materiel> lm)
    {


        for (Materiel m:lm)
        {
            FXMLLoader fl=new FXMLLoader();
            fl.setLocation(getClass().getResource("/gestionMateriel/PanierCart.fxml"));
            Pane pn=null;
            try {
                pn=fl.load();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PanierCart pc=fl.getController();
            pc.setCart(m);
            pc.setParent(this);
            panierContainer.add(pn,0,rows);
            rows++;
            GridPane.setMargin(pn,new Insets(20));
        }

    }

    /*public List<Integer> getIdfromString(String mot)
    {
        List<Integer> numbers=new ArrayList<>();
        System.out.println(mot);
        String[] StringNumber=mot.split(",");
        for (String m:StringNumber)
            numbers.add(Integer.parseInt(m));
        return numbers;

    }*/
    public Map<Integer,Integer> getIdfromString(String mot) {
        Map<Integer,Integer> numbers = new HashMap<>();
        String[] stringNumber=mot.split(",");
        int qte=0;

        for (int i=0;i<stringNumber.length;i++)
        {
            int key=Integer.parseInt(stringNumber[i]);
            if(numbers.get(key)!= null)
            {
                qte=numbers.get(key)+1;
                numbers.put(key,qte);
            }
            else {
                qte = 1;
                numbers.put(key,qte);
            }
        }
        return numbers;
    }

    public void resetGrid()
    {
        panierContainer.getChildren().clear();
    }
    @FXML
    public void initialize() {

        user.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }

        rows=1;
        reservationEffectue.setVisible(false);
        poursuivreNavigation.setVisible(false);
        lm=new HashMap<>();
        listMat=new ArrayList<>();
        //System.out.println(listMat);
        Preferences pr=Preferences.userRoot().node("/tn/arteco/controllers");
        /*try {
            pr.clear();
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }*/
        //String result = pr.get(username,"none");
        ServiceMateriel sm=new ServiceMateriel();
        String mot=pr.get(username,"");
        System.out.println(mot);
        if(!mot.equals("")) {

            panierVide.setVisible(false);
            lm=getIdfromString(mot);
            for (Map.Entry<Integer,Integer> entry:lm.entrySet())
            {
                Materiel m=sm.getById(entry.getKey());

                m.setQuantiteReserver(entry.getValue());
                // System.out.println(m.getQuantite());
                listMat.add(m);
            }

            //List<Integer> numbers = getIdfromString(mot);
            /*for (Integer i : numbers) {
                //lm.add(sm.getById(i));
                //quantite=numbers.stream().count();

            }*/
            setPanierContainer(listMat);
        }
        else {
            panierVide.setVisible(true);
            confirmerReservation.setVisible(false);

        }


    }

    @FXML
    void poursuiveClicked(MouseEvent event) {
        AnimationController.setNomCaller("stop");
        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
        try {
            Parent root=fl.load();
            panierContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reserverClicked(ActionEvent event)
    {
        Preferences pr=Preferences.userRoot().node("/tn/arteco/controllers");
        String ids=pr.get(username,"");
        ids="";
        pr.put(username,ids);
        pr.putInt("NumberReservation"+username,0);
        ServiceMateriel sm=new ServiceMateriel();
        for (Materiel m:listMat) {
            sm.reserverMateriel(m);
        }
        panierContainer.getChildren().clear();
        reservationEffectue.setVisible(true);
        poursuivreNavigation.setVisible(true);
        confirmerReservation.setVisible(false);
        //panierVide.setVisible(false);
    }

    @FXML
    void produitFiniClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Materiel");
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));

            panierContainer.getScene().setRoot(root);
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
            panierContainer.getScene().setRoot(root);
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
            panierContainer.getScene().setRoot(root);
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
            panierContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event) {


        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            panierContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void routeToProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            panierContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void homeClicked(MouseEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            panierContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void remboursementClicked(ActionEvent event) {
        AnimationController.setNomCaller("Materiel");
        try {
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/gestionMateriel/CodePage.fxml"));
            Parent root = fl.load();
            CodePageController cc = fl.getController();
            cc.setCaller("materiel");

            panierContainer.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        } catch (IOException e) {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }
}
