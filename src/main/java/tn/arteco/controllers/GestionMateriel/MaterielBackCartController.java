package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.arteco.models.Materiel;
import tn.arteco.services.ServiceMateriel;

import java.io.IOException;

public class MaterielBackCartController {

    @FXML
    private ComboBox<?> actions;

    @FXML
    private Label adresseMateriel;

    @FXML
    private Label descriptionMateriel;

    @FXML
    private Text nomMateriel;

    @FXML
    private Text quantiteMateriel;

    @FXML
    private Text utilisateur;
    @FXML
    private Text idMateriel;

    @FXML
    private ImageView materielImage;

    private GestionMaterielBackController gmc;
    private Materiel m;
    public void setMaterielData (Materiel m)
    {
        this.m=m;
        nomMateriel.setText(m.getLibMateriel());
        idMateriel.setText(String.valueOf(m.getidMateriel()));
        materielImage.setImage(new Image(m.getImageUrl()));
        descriptionMateriel.setText(m.getDescription());
        descriptionMateriel.setTooltip(new Tooltip(descriptionMateriel.getText()));
        quantiteMateriel.setText(String.valueOf(m.getQuantite()));
        adresseMateriel.setText(m.getAdresse());
        adresseMateriel.setTooltip(new Tooltip(adresseMateriel.getText()));
        utilisateur.setText(m.getNonArtiste().getUsername());
    }

    public void setParent(GestionMaterielBackController gmc)
    {
        this.gmc=gmc;
    }

    public GestionMaterielBackController getParent()
    {
        return gmc;
    }

    @FXML
    void updateClicked(ActionEvent event) {
        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/UpdateMateriel.fxml"));

        try {
            Parent root=fl.load();
            Stage updateStage=new Stage();
            updateStage.setScene(new Scene(root));
            ModifierMateriel mm=fl.getController();
            mm.setParent(this);
            //Materiel m =new Materiel(Integer.parseInt(idMateriel.getText()),nomMateriel.getText(),descriptionMateriel.getText(),materielImage);
            mm.setMaterielData(m);
            updateStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void deleteClicked(ActionEvent event)
    {
        ServiceMateriel sm=new ServiceMateriel();
        sm.delete(Integer.parseInt(idMateriel.getText()));
        gmc.resetGrid();
        gmc.setUpperStats();
    }



}