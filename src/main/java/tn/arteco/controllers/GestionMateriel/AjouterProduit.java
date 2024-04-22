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
import tn.arteco.models.NonArtiste;
import tn.arteco.models.Roles;
import tn.arteco.models.User;
import tn.arteco.services.ServiceMateriel;
import tn.arteco.services.SessionManager;


import java.io.File;
import java.io.IOException;


public class AjouterProduit {

    int idUser= SessionManager.getInstance().getCurrentUser().getUserId();
    User currentUser=SessionManager.getInstance().getCurrentUser();
    String imageName;
    String  imageUrl="";

    GestionMaterielController gmc;
    @FXML

    private TextField adresseMateriel;

    @FXML
    private Button ajouterMateriel;

    @FXML
    private Button chooseFileButton;

    @FXML
    private TextArea descriptionMateriel;

    @FXML
    private ImageView imageSelected;

    @FXML
    private TextField nomMateriel;

    @FXML
    private TextField quantiteMateriel;

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

    public void setParent(GestionMaterielController gmc)
    {
        this.gmc=gmc;
    }
    @FXML
    void ajouterMateriel(ActionEvent event) {
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
            if (currentUser instanceof NonArtiste na) {
                Materiel mat = new Materiel(1, nomMateriel.getText(), descriptionMateriel.getText(), adresseMateriel.getText(), Integer.parseInt(quantiteMateriel.getText()), imageUrl, na);
                ServiceMateriel sm = new ServiceMateriel();
                sm.add(mat);
                nomMateriel.clear();
                descriptionMateriel.clear();
                adresseMateriel.clear();
                quantiteMateriel.clear();
                gmc.resetRowsColumns();
                gmc.setMaterielGrid(sm.getAll());

                gmc.verifierCadeau();
            }
        }

    }

    @FXML
    public void initialize()
    {

        /*MapController mc=new MapController();
        mc.setParent(this);*/

          /*  WebEngine we=mapsView.getEngine();
        we.load(getClass().getResource("/gestionMateriel/maps.html").toExternalForm());

            //URL htmlFileUrl=getClass().getResource("/gestionMateriel/maps.html");
        we.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Script can be executed after the page has loaded
                we.executeScript("window.controller = new AjouterProduit();");
            }
        });
            JSObject window = (JSObject) we.executeScript("window");
            window.setMember("javaConnector", this);
            we.executeScript("let map;\n" +
                    "\n" +
                    "async function initMap(callback) {\n" +
                    "    const { Map } = await google.maps.importLibrary(\"maps\");\n" +
                    "    var myLatlng = new google.maps.LatLng(36.80766601939333,10.180356455967974);\n" +
                    "\n" +
                    "    map = new Map(document.getElementById(\"map\"), {\n" +
                    "        center: { lat: 36.80766601939333, lng: 10.180356455967974 },\n" +
                    "        zoom: 8,\n" +
                    "    });\n" +
                    "    var marker = new google.maps.Marker({\n" +
                    "        position: myLatlng,\n" +
                    "        map: map,\n" +
                    "        draggable:true,\n" +
                    "        title:\"Drag me!\"\n" +
                    "    });\n" +
                    "    google.maps.event.addListener(map, 'click', function (event) {\n" +
                    "        // Change the marker position on click\n" +
                    "        marker.setPosition(event.latLng);\n" +
                    "        javaConnector.updateMarker(event.latLng.lat(),event.latLng.lng());\n" +
                    "    });\n" +
                    "}\n" +
                    "\n" +
                    "initMap();");
        //we.setJavaScriptEnabled(true); // Enable JavaScript debugging
        //we.setOnConsoleMessage(consoleMessage -> System.out.println("Console: " + consoleMessage.getMessage()));
    });*/
    }
    @FXML
    void choisirAdresse(ActionEvent event) {

        FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/Map.fxml"));
        try {
            Parent root=fl.load();
            MapController mc=fl.getController();
            mc.setParent(this);
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
