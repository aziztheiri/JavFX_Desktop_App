package tn.arteco.controllers.gestionRecompense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.arteco.models.Marchandise;
import tn.arteco.services.MarchandiseService;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MarchandiseListController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    MarchandiseService mr = new MarchandiseService();
    @FXML
    private Label nbrMarchandise;
    @FXML
    private Label nbrHorStock;
    @FXML
    private Label nbrOffer;

    @FXML
    private Label nbrNonOffer;

    @FXML
    private Label username;

    @FXML
    private VBox idvb;
    @FXML
    private VBox vbox;
    @FXML
    private TextField tfResearch;



    private ArrayList<Marchandise> listMarchandise = new ArrayList<>();
    private ArrayList<Marchandise>researchList=new ArrayList<>();




    @FXML
    void showAll(ActionEvent event) {
        ArrayList<Marchandise> am = mr.getAll();
        for (Marchandise m : am) {
            Label label = new Label(m.getLibelle());
            Label description = new Label(m.getDescription());
            Label titreOb = new Label(m.getTitreObtenu());
            Label prix = new Label(String.valueOf(m.getPrix()));
            HBox hBox = new HBox(label, description, titreOb, prix);
            hBox.setAlignment(Pos.CENTER);

            idvb.getChildren().add(hBox);

        }

    }

    public void loadContent(){
        this.listMarchandise = this.mr.getAll();
        int nMarchandise = listMarchandise.size();
        System.out.println(listMarchandise);
        int nOffer = this.mr.getLimitedOfer().size();
        int nHorStock = this.mr.getHorStock().size();
        int nNonOffer = this.mr.getNonActiveLimitedOfer().size();

        this.nbrMarchandise.setText(String.valueOf(nMarchandise));
        this.nbrOffer.setText(String.valueOf(nOffer));
        this.nbrHorStock.setText(String.valueOf(nHorStock));
        this.nbrNonOffer.setText(String.valueOf(nNonOffer));
        System.out.println("jawna behi ness el koll ");

        try {
            for (Marchandise m : this.listMarchandise) {
                FXMLLoader fxmlLoader =new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseListItem.fxml"));
                AnchorPane anchorPane =fxmlLoader.load();

                MarchandiseListItemController marchandiseListItemController=fxmlLoader.getController();
                marchandiseListItemController.setData(m);
                this.vbox.getChildren().add(anchorPane);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id){
        this.mr.delete(id);
        System.out.println("delete");



    }

    @FXML
    void goToAjouterMarchandise(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/gestionRecomponse/AjouterMarchandise.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadContent();
    }
    @FXML
    public void onResearch(KeyEvent event) {
        boolean action=true;
        System.out.println("hello from Research text is "+this.tfResearch.getText());
        if(this.tfResearch.getText().matches("\\s*")){
            this.researchList=this.listMarchandise;
            action=false;
        }else {
            this.researchList = this.listMarchandise.stream().
                    filter(e -> MarchandiseService.containAnyWord(this.tfResearch.getText().trim(), e.getDescription()) ||
                            MarchandiseService.containAnyWord(this.tfResearch.getText(), e.getLibelle()) ||
                            MarchandiseService.containAnyWord(this.tfResearch.getText(), e.getTitreObtenu())).
                    collect(Collectors.toCollection(ArrayList::new));
        }
            this.vbox.getChildren().clear();
            try {
                for (Marchandise m : this.researchList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseListItem.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    MarchandiseListItemController marchandiseListItemController = fxmlLoader.getController();
                    marchandiseListItemController.setData(m,action);
                    this.vbox.getChildren().add(anchorPane);

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


    }
    @FXML
    void materielBackClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void naviguerVersDashboard(ActionEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashBoardBack.fxml"));
            Parent root = loader.load();
            nbrNonOffer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            nbrNonOffer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }


}
