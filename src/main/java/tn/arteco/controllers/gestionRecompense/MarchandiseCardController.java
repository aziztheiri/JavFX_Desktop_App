package tn.arteco.controllers.gestionRecompense;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.arteco.models.Marchandise;

import java.io.IOException;

public class MarchandiseCardController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label Tdisponibilite;

    @FXML
    private Label Tlibelle;

    @FXML
    private Label Tprix;

    @FXML
    private ImageView imgMerch;
    @FXML
    private ImageView imgLimited;
    private Marchandise marchandise;

    public void setData(Marchandise m){

        this.marchandise=m;
        this.imgMerch.setImage(new Image(m.getImageUrl()));
        this.Tlibelle.setText(m.getLibelle());
        this.Tprix.setText(String.valueOf(m.getPrix())+" P/DT");
        if(m.getDateLimit()==null){
            this.imgLimited.setVisible(false);
            }else {
            Tooltip tooltip=new Tooltip(m.getDateLimit().toString());
            Tooltip.install(this.imgLimited,tooltip);
        }

        if(m.getQuantiteDispo()>0)
        {
            this.Tdisponibilite.setText("En Stock");
            this.Tdisponibilite.setStyle("-fx-text-fill: green");
        }
        else {
            this.Tdisponibilite.setText("Indisponible");
            this.Tdisponibilite.setStyle("-fx-text-fill: red");
        }

    }
    @FXML
    void goToMarchandiseDtail(MouseEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/gestionRecomponse/MarchandiseDetail.fxml"));
        root =loader.load();
        MarchandiseDetailController marchandiseDetailController=loader.getController();
        marchandiseDetailController.setData(this.marchandise);

        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

        stage.show();
    }


}
