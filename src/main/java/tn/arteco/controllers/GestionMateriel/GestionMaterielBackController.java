package tn.arteco.controllers.GestionMateriel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tn.arteco.models.Materiel;
import tn.arteco.services.ServiceMateriel;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GestionMaterielBackController {
    private int rows=1;
    List<Materiel> lm;
    private String adresse="";
    @FXML
    private TextField searchField;

    @FXML
    private Pane waitingpane;

    @FXML
    private GridPane materielContainer;

    @FXML
    private Text matStock;

    @FXML
    private Text nbTotalMat;

    @FXML
    private Text quantiteTotal;
    public void setMaterielGrid(List<Materiel> lm)
    {

        for (Materiel m:lm)
        {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gestionMateriel/MaterielBackCart.fxml"));

            Pane hBox= null;
            try {
                hBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MaterielBackCartController mc = fxmlLoader.getController();
            mc.setParent(this);

            mc.setMaterielData(m);

            materielContainer.add(hBox,0,rows++);
            GridPane.setMargin(hBox,new Insets(20));
        }
    }

   public void setUpperStats()
    {
        ServiceMateriel sm=new ServiceMateriel();
        List<Materiel> lm=sm.getAll();
        nbTotalMat.setText(String.valueOf(lm.stream().count()));
        matStock.setText(String.valueOf(lm.stream().filter(m->m.getQuantite()>0).count()));
        quantiteTotal.setText(String.valueOf(lm.stream().mapToInt(Materiel::getQuantite).sum()));
    }
    public void resetRowsColumns()
    {
        rows=1;

    }

    public void resetGrid()
    {
        materielContainer.getChildren().clear();
        ServiceMateriel sm=new ServiceMateriel();
        List<Materiel> lm=new ArrayList<>();
        setMaterielGrid(sm.getAll());
    }

    @FXML
    public void initialize()
    {
        ServiceMateriel sm=new ServiceMateriel();
        lm=new ArrayList<>();
        lm=sm.getAll();
        setMaterielGrid(lm);
        resetRowsColumns();
        setUpperStats();
    }

    @FXML
    void Searching(KeyEvent event) {
        ServiceMateriel sm=new ServiceMateriel();
        if(!adresse.equals(searchField.getText()))
        {
            adresse=searchField.getText();

            materielContainer.getChildren().clear();
            setMaterielGrid(sm.rechercheAdr(adresse,lm));
        }
        if(adresse.isEmpty())
            resetGrid();

        //sm.rechercherListParAdresse()
    }

    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        waitingpane.setVisible(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            new Thread(() -> {
                try {
                    Parent root = loader.load();
                    Platform.runLater(() -> {
                        waitingpane.getScene().setRoot(root);
                        waitingpane.setVisible(false);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            materielContainer.getScene().setRoot(root);
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
            materielContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            materielContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            materielContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            materielContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            materielContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
