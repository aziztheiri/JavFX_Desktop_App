package tn.arteco.controllers.gestionRecompense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.arteco.models.Marchandise;
import tn.arteco.models.MarchandiseFacture;
import tn.arteco.services.MarchandiseFactureService;
import tn.arteco.services.MarchandiseService;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MarchandiseFactureListController implements Initializable {
    MarchandiseFactureService mF=new MarchandiseFactureService();
    MarchandiseService mS=new MarchandiseService();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label nbrFacture;
    @FXML
    private Label nbrIn24h;
    @FXML
    private Label nbrIn30j;

    @FXML
    private Label nbrIn7j;

    @FXML
    private Label username;

    @FXML
    private VBox idvb;
    @FXML
    private VBox vbox;
    @FXML
    private DatePicker endDate;
    @FXML
    private DatePicker startDate;
    private boolean dsValueIn ;
    private boolean deValueIn;
    private boolean clearAct;



    private ArrayList<MarchandiseFacture> listFacture = new ArrayList<>();
    private ArrayList<MarchandiseFacture>filterList=new ArrayList<>();

    @FXML
    void filterClear(ActionEvent event) {
        this.clearAct=true;
    this.dsValueIn=false;
    this.startDate.setStyle(null);
    this.endDate.setStyle(null);
    this.startDate.setValue(null);
    this.endDate.setValue(null);
    this.endDate.setDisable(true);
    this.startDate.setDisable(false);
    this.vbox.getChildren().clear();
        try {
            for (MarchandiseFacture mf : this.listFacture) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseFactureItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                MarchandiseFItemController marchandiseFItemController = fxmlLoader.getController();
                marchandiseFItemController.setData(mf);
                this.vbox.getChildren().add(anchorPane);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    this.clearAct=false;
    }
    @FXML
    void setEndDate(ActionEvent event) {
        if(!this.clearAct){
            if(this.endDate.getValue().isAfter(this.startDate.getValue())){
                this.deValueIn=true;
                this.endDate.setStyle("-fx-border-color:#00f600");
                this.onfilter();
            }else {
                this.endDate.setStyle("-fx-border-color:red");
            }
        }
    }

    @FXML
    void setStarDate(ActionEvent event) {
        if (!this.clearAct)
        {
            this.startDate.setStyle("-fx-border-color: #90f367");
            this.endDate.setDisable(false);
            this.dsValueIn=true;
            this.startDate.setDisable(true);
        }
    }

    private boolean isDateInRange(Date targetDate) {
        // Convert DatePicker values to java.util.Date
        Date startDate = this.convertToDate(this.startDate);
        Date endDate = this.convertToDate(this.endDate);

        // Check if targetDate is between startDate and endDate
        return targetDate.compareTo(startDate) >= 0 && targetDate.compareTo(endDate) <= 0;
    }

    private Date convertToDate(javafx.scene.control.DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = datePicker.getValue().toString();
                return sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void onfilter() {


            this.filterList = this.listFacture.stream().
                    filter(e->isDateInRange(e.getDateF())).collect(Collectors.toCollection(ArrayList::new));


        this.vbox.getChildren().clear();
        try {
            for (MarchandiseFacture mf : this.filterList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseFactureItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                MarchandiseFItemController marchandiseFItemController = fxmlLoader.getController();
                marchandiseFItemController.setData(mf,true);
                this.vbox.getChildren().add(anchorPane);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public void loadContent(){
        this.listFacture = this.mF.getAll();
        int nMarchandise = listFacture.size();


        long nIn24h = this.listFacture.stream().
                filter((e)-> e.getDateF().getDay()==new Date().getDay()&&
                        e.getDateF().getMonth()==new Date().getMonth()&&
                        e.getDateF().getYear()==new Date().getYear()).count();
        long  nIn7j = this.listFacture.stream().
                filter((e)->Duration.between(e.getDateF().toInstant(), new Date().toInstant()).toDays()<=7).count();
        long nIn30j=this.listFacture.stream().
                filter((e)->Duration.between(e.getDateF().toInstant(), new Date().toInstant()).toDays()<=30).count();
//        int nNonOffer = this.mr.getNonActiveLimitedOfer().size();
        this.nbrFacture.setText(String.valueOf(nMarchandise));
        this.nbrIn30j.setText(String.valueOf(nIn30j));
        this.nbrIn24h.setText(String.valueOf(nIn24h));
        this.nbrIn7j.setText(String.valueOf(nIn7j));
        Marchandise m =new Marchandise();
        try {
            for (MarchandiseFacture mf : this.listFacture) {
                mf.setDateF(new java.sql.Date(mf.getDateF().getTime()));
                m=this.mS.getById(mf.getMarchandise().getIdMerch());
                if(m!=null)
                    mf.setMarchandise(m);
                FXMLLoader fxmlLoader =new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/MarchandiseFactureItem.fxml"));
                AnchorPane anchorPane =fxmlLoader.load();
                MarchandiseFItemController marchandiseFItemController=fxmlLoader.getController();
                marchandiseFItemController.setData(mf);
                this.vbox.getChildren().add(anchorPane);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.endDate.setDisable(true);
        this.loadContent();
    }
    @FXML
    void materielBackClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            nbrIn7j.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            nbrIn7j.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            nbrIn7j.getScene().setRoot(root);
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
            nbrIn7j.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            nbrIn7j.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            nbrIn7j.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            nbrIn7j.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            nbrIn7j.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
