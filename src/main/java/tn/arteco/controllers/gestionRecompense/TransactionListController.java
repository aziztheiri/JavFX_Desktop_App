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
import tn.arteco.models.*;
import tn.arteco.services.AccomplissementService;
import tn.arteco.services.SessionManager;
import tn.arteco.services.TransactionPService;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TransactionListController implements Initializable {


    TransactionPService tS=new TransactionPService();
    AccomplissementService aS=new AccomplissementService();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label nbrTransaction;
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



    private ArrayList<TransactionP> listTransaction = new ArrayList<>();
    private ArrayList<TransactionP>filterList=new ArrayList<>();

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
            for (TransactionP tp : this.listTransaction) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/TransactionItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                TransactionItemController transactionItemController = fxmlLoader.getController();
                transactionItemController.setData(tp);
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

        System.out.println("hello from filter");

        this.filterList = this.listTransaction.stream().
                filter(e->isDateInRange(e.getDateTp())).collect(Collectors.toCollection(ArrayList::new));


        this.vbox.getChildren().clear();
        try {
            for (TransactionP tp : this.filterList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/TransactionItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                TransactionItemController transactionItemController = fxmlLoader.getController();
                transactionItemController.setData(tp,true);
                this.vbox.getChildren().add(anchorPane);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public void loadContent(){
        this.listTransaction = this.tS.getAll();
        int nTransaction = listTransaction.size();
        System.out.println(listTransaction);

        long nIn24h = this.listTransaction.stream().
                filter((e)-> e.getDateTp().getDay()==new Date().getDay()&&
                        e.getDateTp().getMonth()==new Date().getMonth()&&
                        e.getDateTp().getYear()==new Date().getYear()).count();
        long  nIn7j = this.listTransaction.stream().
                filter((e)-> Duration.between(e.getDateTp().toInstant(), new Date().toInstant()).toDays()<=7).count();
        long nIn30j=this.listTransaction.stream().
                filter((e)->Duration.between(e.getDateTp().toInstant(), new Date().toInstant()).toDays()<=30).count();
//        int nNonOffer = this.mr.getNonActiveLimitedOfer().size();

        this.nbrTransaction.setText(String.valueOf(nTransaction));
        this.nbrIn30j.setText(String.valueOf(nIn30j));
        this.nbrIn24h.setText(String.valueOf(nIn24h));
        this.nbrIn7j.setText(String.valueOf(nIn7j));
        NonArtiste na=new NonArtiste();
        na.setPrenom("aymen");
        na.setNom("kammoun");
        na.setUsername("aymen_kamoun");
        try {
            for (TransactionP tp : this.listTransaction) {
                System.out.println(tp);
                tp.setDateTp(new java.sql.Date(tp.getDateTp().getTime()));
                //tabdila nahne ba3d ma tzid el partie user ( menesh mbadlin )
              //  na=this.aS.getById(tp.getAccomplissement().getIdAccomp());
                if(na!=null)
                    tp.getAccomplissement().setNonArtiste(na);
                FXMLLoader fxmlLoader =new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/TransactionItem.fxml"));
                AnchorPane anchorPane =fxmlLoader.load();
                TransactionItemController transactionItemController=fxmlLoader.getController();
                transactionItemController.setData(tp);
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
