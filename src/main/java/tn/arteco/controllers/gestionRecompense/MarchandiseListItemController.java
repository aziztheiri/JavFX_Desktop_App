package tn.arteco.controllers.gestionRecompense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.arteco.models.Marchandise;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class MarchandiseListItemController {
    MarchandiseListController marchandiseListController=new MarchandiseListController();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label IdDateSortie;

    @FXML
    private Label idDateLimit;

    @FXML
    private Label idDescription;

    @FXML
    private Label idLibelle;

    @FXML
    private Label idPrix;

    @FXML
    private Label idQuantiteDispo;

    @FXML
    private Label idTitreObtenu;

    @FXML
    private ImageView itemImg;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSuprimer;

    private Marchandise marchandise ;
    private Image image;
    @FXML
    private Rectangle rec;

    @FXML
    void onClickModifier(ActionEvent event) throws IOException {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/gestionRecomponse/ModifierMarchandise.fxml"));
        root =loader.load();
        ModifierMarchandiseController modifierMarchandiseController=loader.getController();
        modifierMarchandiseController.setData(this.marchandise);
        //  root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/ModifierMarchandise.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onClickSuprimer(ActionEvent event) throws IOException {
        this.marchandiseListController.delete(this.marchandise.getIdMerch());
        System.out.println("from delete");
        root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/MarchandiseList.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void setData(Marchandise m,boolean b){
        if(b)
        {
            this.rec.setStyle("-fx-fill:#c8edf9;-fx-arc-height:20;-fx-arc-width:20");
        }
        this.setData(m);
    }
    public void setData(Marchandise m){
        this.marchandise=m;
        this.itemImg.setImage(new Image(m.getImageUrl()));
        this.idLibelle.setText(m.getLibelle());
    this.idDescription.setText(m.getDescription());
    this.idDescription.setTooltip(new Tooltip(m.getDescription()));
    this.IdDateSortie.setText(m.getDateSortie().toString());
    if(m.getDateLimit()==null)
        this.idDateLimit.setText("illimitée");
        else {
        this.idDateLimit.setText(m.getDateLimit().toString());
        if (m.getDateLimit().getTime() < new Date().getTime())
            this.idDateLimit.setStyle("-fx-text-fill: red;");
        }
    this.idTitreObtenu.setText(m.getTitreObtenu());
    this.idQuantiteDispo.setText(String.valueOf(m.getQuantiteDispo()));
    if(m.getQuantiteDispo()==0)
        this.idQuantiteDispo.setStyle("-fx-text-fill: red;");
    this.idPrix.setText(String.valueOf(m.getPrix()));

    }



}
