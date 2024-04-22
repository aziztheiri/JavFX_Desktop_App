package tn.arteco.controllers.gestionRecompense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.arteco.models.Marchandise;
import tn.arteco.services.MarchandiseService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterMarchandiseController implements Initializable {
    private MarchandiseService mS=new MarchandiseService();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnImageUp;

    @FXML
    private CheckBox cbDate;

    @FXML
    private DatePicker dpDateLimite;

    @FXML
    private TextArea tfDescri;

    @FXML
    private TextField tfLibelle;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfQuantite;

    @FXML
    private TextField tfTitre;

    @FXML
    private Label username;

    private String imageUrl;

    @FXML
    private ImageView marchendiseImage;
     boolean libellleTest=true;
     boolean descripTest=true;
     boolean titreObtenuTest=true;
     boolean quantiteTest=true;
     boolean prixTest=true;
     boolean imageTest=true;
    boolean  dateLimiteTest=true;
     public static boolean isNumeric(String s){
         if (s==null)
                 return false;
        return s.matches("\\s*\\d+");
     }
    public boolean inputTest(){
        if(this.cbDate.isSelected()&&this.dpDateLimite.getValue().isBefore(java.time.LocalDate.now()))
        {
            this.dateLimiteTest=false;
            this.dpDateLimite.setStyle("-fx-border-color: red;");
        } else {
            this.dateLimiteTest=true;
            this.dpDateLimite.setStyle("-fx-border-color: null;");
        }
        if (this.marchendiseImage.getImage()==null)
        { imageTest=false;
        this.btnImageUp.setStyle("-fx-border-color: red;");
        }else {
            imageTest=true;
            this.btnImageUp.setStyle("-fx-border-color: null;");
        }
        if(this.tfLibelle.getText().equals("")){
            this.libellleTest=false;
            this.tfLibelle.setStyle("-fx-border-color: red;");
        }
        else {
            libellleTest=true;
            this.tfLibelle.setStyle("-fx-border-color: null;");
        }
        if(this.tfDescri.getText().equals("")){
            this.descripTest=false;
            this.tfDescri.setStyle("-fx-border-color: red;");
        }else {
            this.descripTest=true;
            this.tfDescri.setStyle("-fx-border-color: null;");
        }
        if(this.tfTitre.getText().equals("")){
            this.titreObtenuTest=false;
            this.tfTitre.setStyle("-fx-border-color: red;");
        }else {
            titreObtenuTest=true;
            this.tfTitre.setStyle("-fx-border-color: null;");
        }
        if(!isNumeric(this.tfQuantite.getText())){
            this.quantiteTest=false;
            this.tfQuantite.setStyle("-fx-border-color: red;");
        }
        else if (0>=Integer.valueOf(this.tfQuantite.getText())){
            this.quantiteTest=false;
            this.tfQuantite.setStyle("-fx-border-color: red;");
            System.out.println(quantiteTest);
        }
        else {
            quantiteTest=true;
            this.tfQuantite.setStyle("-fx-border-color: null;");
        }
        if(!isNumeric(this.tfPrix.getText())){
            System.out.println(this.tfPrix.toString());
            this.prixTest=false;
            this.tfPrix.setStyle("-fx-border-color: red;");
            System.out.println(prixTest);

        }
        else if (0>=Integer.valueOf(this.tfPrix.getText())){
            this.prixTest=false;
            this.tfPrix.setStyle("-fx-border-color: red;");
            System.out.println(prixTest);
        }
        else {
            prixTest=true;

            this.tfPrix.setStyle("-fx-border-color: null;");
        }
        return this.libellleTest &&this.imageTest &&this.descripTest&&
                this.titreObtenuTest&&this.quantiteTest&&this.prixTest&&this.dateLimiteTest;
    }
    @FXML
    void fermer(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/MarchandiseList.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void nettoyer(ActionEvent event) {
    tfLibelle.clear();
    tfDescri.clear();
    tfPrix.clear();
    tfTitre.clear();
    tfQuantite.clear();
    cbDate.setSelected(false);
    dpDateLimite.setVisible(false);
    dpDateLimite.setManaged(false);
    marchendiseImage.setImage(new Image("."));
        System.out.println(new Image(".").getUrl());
    }

    @FXML
    void submit(ActionEvent event) {
         if(inputTest()) {
             Marchandise m = new Marchandise();
             m.setLibelle(tfLibelle.getText());
             m.setDescription(tfDescri.getText());
             m.setTitreObtenu(tfTitre.getText());
             m.setQuantiteDispo(Integer.valueOf(tfQuantite.getText()));
             if (this.cbDate.isSelected())
                 m.setDateLimit(Date.from(dpDateLimite.getValue().atStartOfDay(ZoneId.of("Africa/Tunis")).toInstant()));
             else m.setDateLimit(new Date("09/11/2001"));
             m.setDateSortie(new Date());
             m.setImageUrl(imageUrl);
             m.setPrix(Integer.parseInt(tfPrix.getText()));
             mS.add(m);
             System.out.println("jawik behi");
             this.nettoyer(event);
         }
         }

    @FXML
    void checked(ActionEvent event) {
        dpDateLimite.setVisible(cbDate.isSelected());
        dpDateLimite.setManaged(cbDate.isSelected());

    }
    @FXML
    void uploadImage(ActionEvent event) {
//        JFileChooser fileChooser =new JFileChooser();
        FileChooser fileChooser =new FileChooser();
        fileChooser.setTitle("selectioner image");
        fileChooser.setInitialDirectory(new File("C:\\xampp\\htdocs\\images"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File selectedFile =fileChooser.showOpenDialog(stage);
        if(selectedFile!=null){
             imageUrl="http://127.0.0.1/images/"+selectedFile.getName();

            System.out.println(selectedFile.getPath());
        marchendiseImage.setImage(new Image(imageUrl));
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dpDateLimite.setVisible(false);
        dpDateLimite.setManaged(false);
    }
}
