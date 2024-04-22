package tn.arteco.controllers.gestionRecompense;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.arteco.controllers.GestionMateriel.AnimationController;
import tn.arteco.models.*;
import tn.arteco.services.AccomplissementService;
import tn.arteco.services.SessionManager;
import tn.arteco.services.TransactionPService;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.*;

public class PointStoreController implements Initializable {
    private UserService uS=new UserService();
    public TransactionPService tPS=new TransactionPService();
    public AccomplissementService aS=new AccomplissementService();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView boutiqueIcon;

    @FXML
    private Button boutiqueText;

    @FXML
    private ImageView coursIcon;

    @FXML
    private Button coursText;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button eventText;

    @FXML
    private GridPane marchandiseGrid;

    @FXML
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private AnchorPane navbarHolder;

    @FXML
    private Text points;

    @FXML
    private ImageView produitFiniIcon;

    @FXML
    private Button produitFiniText;

    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button t;

    @FXML
    private ImageView userPhoto;

    @FXML
    private Label username;
    @FXML
    private VBox vBox;
    ArrayList <PointPack>listPointPack= new ArrayList<>();

    //++++++++++++++++++++++++++++++++++
    List<Boolean> donationerrors = new ArrayList<>(List.of(false,false,false,false,false));

    @FXML
    private ComboBox<Integer> anneeexpiration;

    @FXML
    private TextField creditcard;

    @FXML
    private TextField cvc;

    @FXML
    private Button donatebutton;

    @FXML
    private Pane donationpane;

    @FXML
    private Label erreurannee;

    @FXML
    private Label erreurcredit;

    @FXML
    private Label erreurcvc;

    @FXML
    private Label erreurmois;

    @FXML
    private Label erreurmontant;

    @FXML
    private Label erreurpoints;

    @FXML
    private TextField idfielddonation;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private ComboBox<Integer> moisexpiration;

    @FXML
    private TextField montant;
    public void clearDonationFields(){
        creditcard.clear();
        montant.clear();
        cvc.clear();
        moisexpiration.getSelectionModel().clearSelection();
        anneeexpiration.getSelectionModel().clearSelection();
    }
    @FXML
    void closedonation(ActionEvent event) {
        donationpane.setVisible(false);
        clearDonationFields();

    }
    //++++++++++++++++++++++++++++++++++

    @FXML
    void goToBoutique(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/gestionRecomponse/Boutique.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            /*scene = new Scene(root);
            stage.setScene(scene);
*/
            stage.setScene(new Scene(root));

            stage.show();
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void loadContent(){

        try {

            for (PointPack p : this.listPointPack) {
                FXMLLoader fxmlLoader =new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gestionRecomponse/PointPackCard.fxml"));
                AnchorPane anchorPane =fxmlLoader.load();

                 PointPackCardController pointPackCardController=fxmlLoader.getController();
                pointPackCardController.setPointStoreController(this);
                 pointPackCardController.setData(p);
                this.vBox.getChildren().add(anchorPane);
            }
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }

    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public boolean isValidCardDetails() {
        if (!creditcard.getText().matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
            erreurcredit.setText("Veuillez saisir une carte de crédit valide");
            erreurcredit.setStyle("-fx-text-fill: red;");
            donationerrors.set(0, true);
        } else{
            erreurcredit.setText("");
            donationerrors.set(0, false);
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(anneeexpiration.getValue() == null ){
            erreurannee.setText("Veuillez saisir une date valide !");
            erreurannee.setStyle("-fx-text-fill: red;");
            donationerrors.set(1, true);

        }else{
            erreurannee.setText("");
            donationerrors.set(1, false);
        }
        if(moisexpiration.getValue() == null){
            erreurmois.setText("Veuillez saisir une date valide !");
            erreurmois.setStyle("-fx-text-fill: red;");
            donationerrors.set(2, true);
        }else{
            erreurmois.setText("");
            donationerrors.set(2,false);
        }
        if (anneeexpiration.getValue() < currentYear  || (anneeexpiration.getValue() == currentYear && moisexpiration.getValue() < Calendar.getInstance().get(Calendar.MONTH) + 1)) {
            erreurannee.setText("Veuillez saisir une date valide !");
            erreurannee.setStyle("-fx-text-fill: red;");
            erreurmois.setText("Veuillez saisir une date valide !");
            erreurmois.setStyle("-fx-text-fill: red;");
            donationerrors.set(1, true);
            donationerrors.set(2, true);
        }else{
            erreurannee.setText("");
            erreurmois.setText("");
            donationerrors.set(1, false);
            donationerrors.set(2, false);
        }
        if(cvc.getText().isEmpty()){
            erreurcvc.setText("Veuillez saisir un cvc valide !");
            erreurcvc.setStyle("-fx-text-fill: red;");
            donationerrors.set(3,true);
        }else{
            erreurcvc.setText("");
            donationerrors.set(3,false);
        }
        if (!cvc.getText().matches("^[0-9]{3}$")) {
            erreurcvc.setText("Veuillez saisir un cvc valide !");
            erreurcvc.setStyle("-fx-text-fill: red;");
            donationerrors.set(3,true);
        }else{
            erreurcvc.setText("");
            donationerrors.set(3,false);
        }
        if(montant.getText().isEmpty() ){
            erreurmontant.setText("Veuillez saisir un montant supérieur a 500 ( centimes ) ");
            erreurmontant.setStyle("-fx-text-fill: red;");
            donationerrors.set(4,true);
        }else{
            erreurmontant.setText("");
            donationerrors.set(4,false);
        }
        if(Integer.parseInt(montant.getText())<500 ){
            erreurmontant.setText("Veuillez saisir un montant supérieur a 500 ( centimes ) ");
            erreurmontant.setStyle("-fx-text-fill: red;");
            donationerrors.set(4,true);
        }else{
            erreurmontant.setText("");
            donationerrors.set(4,false);
        }
        boolean falsee = donationerrors.stream().allMatch(value->value.equals(false));

        if(falsee){
            return true;
        }else{
            return false;
        }
    }

    public boolean makePayment () {
        try{
            if(isValidCardDetails()){
                Stripe.apiKey = "sk_test_51OoXzvFsr5ihxnA1cf9z8ztT6RxAqYe7NcS1QGrzHaxk4eVh1KktcsJO8cWu1qgD34OdLViJW6u1vzRlQq58KmFN00daQPDHKY";
                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount(Long.parseLong(montant.getText()))
                        .setCurrency("gbp")
                        .setPaymentMethod("pm_card_visa")
                        .build();
                PaymentIntent paymentIntent = PaymentIntent.create(params);
                PaymentIntent confirmedPaymentIntent = paymentIntent.confirm();
                return  true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Pane getDonationpane() {
        return donationpane;
    }

    public TextField getIdfielddonation() {
        return idfielddonation;
    }

    public TextField getMontant() {
        return montant;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }

        User user=uS.getById(SessionManager.getInstance().getCurrentUser().getUserId());
        PointPack p1=new PointPack(100,12,"pack de 100 Points");
        PointPack p2=new PointPack(1000,100,"pack de 1000 Points ");
        PointPack p3=new PointPack(5000,499,"pack de 5000 Points ");
        listPointPack.add(p1);
        listPointPack.add(p2);
        listPointPack.add(p3);
        this.loadContent();


        ObservableList<Integer> moisList = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            moisList.add(i);
        }
        moisexpiration.setItems(moisList);
        ObservableList<Integer> anneeList = FXCollections.observableArrayList();
        int currentYear = Year.now().getValue();
        for (int i = currentYear; i <= currentYear + 10; i++) {
            anneeList.add(i);
        }
        anneeexpiration.setItems(anneeList);
        donatebutton.setOnAction(event->{
            if(makePayment()) {

                int p = ((NonArtiste) user).getPoints();

                this.uS.updateUserPoints(user.getUserId(), Integer.valueOf(idfielddonation.getText())+ p);
                if(SessionManager.getInstance().getCurrentUser() instanceof NonArtiste na)
                {
                    int points=na.getPoints();
                    na.setPoints(Integer.valueOf(idfielddonation.getText())+points);
                }
                Accomplissement a = aS.getByNonArtistId(user.getUserId());
                this.tPS.add(new TransactionP(0, a, new Date(),Integer.valueOf(idfielddonation.getText()), true));

                try {
                    root = FXMLLoader.load(getClass().getResource("/gestionRecomponse/PointStore.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);

                    stage.setScene(scene);
                    stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

                    stage.show();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                showAlert("Payment Succeeded", "Your payment was successful.");
            }
            else {
                showAlert("Payment faild", "Your payment was faild.");
            }
        });

    }

    @FXML
    public void routeToProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void homeClicked(MouseEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void produitFiniClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Boutique");
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));

            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void coursClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Boutique");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionCour/GestionCour.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @FXML
    void eventClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Boutique");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/GestionEvenement/AfficherFront.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }
    @FXML
    public void routeToMateriel(ActionEvent event){
        AnimationController.setNomCaller("Boutique");

        try{
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void remboursementClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Boutique");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodePage.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }

    @FXML
    public void disconnect(MouseEvent event) {


        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

}
