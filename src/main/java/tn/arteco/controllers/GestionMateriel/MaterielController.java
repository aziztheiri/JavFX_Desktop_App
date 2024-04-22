package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import tn.arteco.models.Materiel;
import tn.arteco.services.SessionManager;

import java.util.Arrays;
import java.util.prefs.Preferences;

public class MaterielController {

    private String username= SessionManager.getInstance().getCurrentUser().getUsername();
    @FXML
    private Pane containerPane;
    @FXML
    private Label MaterielName;

    @FXML
    private Label description;

    @FXML
    private Label disponibilite;

    @FXML
    private ImageView imageMateriel;

    @FXML
    private Label place;
    @FXML
    private Label quantite;

    @FXML
    private Label matId;

    @FXML
    private Button reservationMat;


    GestionMaterielController gmc;

    PanierController pc;
    @FXML
    private Label test;
    String allIds;

    Preferences pre;

    public void setMaterielData(Materiel mat) {
        Image image = new Image(mat.getImageUrl());
        imageMateriel.setImage(image);
        /*imageMateriel.setFitWidth(170);
        imageMateriel.setFitHeight(160);
        imageMateriel.setLayoutX(32);
        imageMateriel.setLayoutY(17);*/
        MaterielName.setText(mat.getLibMateriel());
        MaterielName.setTooltip(new Tooltip(mat.getLibMateriel()));
        description.setText(mat.getDescription());
        description.setTooltip(new Tooltip(description.getText()));
        disponibilite.setText(String.valueOf(mat.getQuantite()));
        place.setText(String.valueOf(mat.getAdresse()));
        place.setTooltip(new Tooltip(place.getText()));
        matId.setText(String.valueOf(mat.getidMateriel()));
        if(mat.getQuantite()==0)
            reservationMat.setDisable(true);
        pre=Preferences.userRoot().node("/tn/arteco/controllers");
        if(verifierReservationDispo()==0)
            reservationMat.setDisable(true);
    }

    public void setParentMC(GestionMaterielController gmc) {
        this.gmc = gmc;
    }
    @FXML
    public void initialize()
    {
        pre=Preferences.userRoot().node("/tn/arteco/controllers");
        if(verifierReservationDispo()==0)
            reservationMat.setDisable(true);
        else
            reservationMat.setDisable(false);
    }

    public int verifierReservationDispo()
    {
        int qte = Integer.parseInt(quantite.getText());
        String[] ids=pre.get(username,"").split(",");
        int qteReserver=0;
        for (String q : ids) {
            if (q.equals(matId.getText()))
                qteReserver++;
        }
        if(qte+qteReserver<Integer.parseInt(disponibilite.getText())) {
            return 1;
        }
        else if (qte+qteReserver==Integer.parseInt(disponibilite.getText())) {
            return 2;
        }
        else if (qteReserver>Integer.parseInt(disponibilite.getText()));
        return 0;
    }

    @FXML
    void reserverClicked(ActionEvent event) {
        int qte = Integer.parseInt(quantite.getText());

        int verif=verifierReservationDispo();
        if(verif==1) {
            String mot = "";
            for (int i = 0; i < qte; i++)
                mot = mot + matId.getText() + ",";

            //gmc.doAnimerPanier();

            gmc.incrementerReservation(qte);
            gmc.setAllIds(mot);
            gmc.setQte(qte);
            quantite.setText(String.valueOf(1));

        } else if (verif==2) {
            String mot = "";
            for (int i = 0; i < qte; i++)
                mot = mot + matId.getText() + ",";

            //gmc.doAnimerPanier();

            gmc.incrementerReservation(qte);
            gmc.setAllIds(mot);
            gmc.setQte(qte);
            quantite.setText(String.valueOf(1));
            reservationMat.setDisable(true);
            quantite.setTooltip(new Tooltip("Verifier Votre Panier Nombre de réservation max est atteint pour ce produit"));

        }



    }
    @FXML
    void plusClicked(MouseEvent event)
    {
        int qte = Integer.parseInt(quantite.getText());
        int qteDispo=Integer.parseInt(disponibilite.getText());
        if(qte+1<=qteDispo) {
            qte += 1;
            quantite.setText(String.valueOf(qte));
        }

    }

    @FXML
    void minusClicked(MouseEvent event)
    {
        int qte = Integer.parseInt(quantite.getText());
        if(qte>1)
            qte-=1;
        quantite.setText(String.valueOf(qte));
    }



}
