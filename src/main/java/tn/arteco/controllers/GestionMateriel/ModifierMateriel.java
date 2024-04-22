package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.arteco.models.Materiel;
import tn.arteco.services.ServiceMateriel;

import java.io.File;
import java.io.IOException;

public class ModifierMateriel {
    MaterielBackCartController gmc;

    int idMat;
    String imageName;
    String imageUrl;
    @FXML
    private TextField adresseMateriel;

    @FXML
    private Button chooseFileButton;

    @FXML
    private TextArea descriptionMateriel;

    @FXML
    private Button modifierMateriel;

    @FXML
    private TextField nomMateriel;

    @FXML
    private TextField quantiteMateriel;
    @FXML
    private ImageView imageSelected;

    @FXML
    private Text adresseVide;
    @FXML
    private Text descriptionVide;
    @FXML
    private Text imageVide;
    @FXML
    private Text nomVide;
    @FXML
    private Text quantiteVide;

    int etatNom=0,etatadresse,etatQuantite,etatDecription,etatimage;
    @FXML
    void modifierMateriel(ActionEvent event) {
        if(nomMateriel.getText().isEmpty())
        {
            nomVide.setVisible(true);
            etatNom=0;
        }
        else {
            etatNom = 1;
            nomVide.setVisible(false);
        }
        if(adresseMateriel.getText().isEmpty())
        {
            adresseVide.setVisible(true);
            etatadresse=0;

        }
        else {
            etatadresse = 1;
            adresseVide.setVisible(false);

        }
        if(quantiteMateriel.getText().isEmpty() || !quantiteMateriel.getText().matches("[0-9]+"))
        {
            quantiteVide.setVisible(true);
            etatQuantite=0;
        }
        else {
            etatQuantite = 1;
            quantiteVide.setVisible(false);

        }
        if(descriptionMateriel.getText().isEmpty())
        {
            descriptionVide.setVisible(true);
            etatDecription=0;

        }
        else {
            etatDecription = 1;
            descriptionVide.setVisible(false);
        }
        if(imageUrl.isEmpty())
        {
            imageVide.setVisible(true);
            etatimage=0;
        }
        else {
            etatimage = 1;
            imageVide.setVisible(false);

        }

        if(etatimage==1 && etatDecription==1 && etatNom==1 && etatQuantite==1 && etatadresse==1) {
            ServiceMateriel sm = new ServiceMateriel();
            Materiel m = new Materiel(idMat, nomMateriel.getText(), descriptionMateriel.getText(), adresseMateriel.getText(), Integer.parseInt(quantiteMateriel.getText()), imageUrl);
            sm.update(m);
            gmc.getParent().resetGrid();
            gmc.getParent().setUpperStats();
            nomMateriel.clear();
            quantiteMateriel.clear();
            nomMateriel.clear();
            descriptionMateriel.clear();
            adresseMateriel.clear();
        }
    }

    @FXML
    public void selectFile(ActionEvent event)
    {
        FileChooser fc=new FileChooser();
        fc.setInitialDirectory(new File("C:\\"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG image", "*.png"));
        File selectedFile = fc.showOpenDialog(chooseFileButton.getScene().getWindow());
        if(selectedFile!=null)
        {
            String url="http://127.0.0.1/images/";
            imageName=selectedFile.getName();
            imageUrl=url+imageName;

            imageSelected.setImage(new Image(imageUrl));


        }

    }

    public void setMaterielData(Materiel m)
    {
        idMat=m.getidMateriel();
        nomMateriel.setText(m.getLibMateriel());
        descriptionMateriel.setText(m.getDescription());
        adresseMateriel.setText(m.getAdresse());
        quantiteMateriel.setText(String.valueOf(m.getQuantite()));
        imageSelected.setImage(new Image(m.getImageUrl()));
        imageUrl=m.getImageUrl();
    }

    public void setParent(MaterielBackCartController gmc) {
this.gmc=gmc;
    }

    @FXML
    void choisirAdresse(ActionEvent event) {

        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/Map.fxml"));
        try {
            Parent root=fl.load();
            MapController mc=fl.getController();
            mc.setParentModifier(this);
            Stage mapStage=new Stage();
            mapStage.setScene(new Scene(root));
            mapStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void setAdresseMateriel(String adresse)
    {
        adresseMateriel.setText(adresse);
    }
}
