package tn.arteco.controllers.gestionRecompense;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.arteco.models.*;
import tn.arteco.services.AccomplissementService;
import tn.arteco.services.SessionManager;
import tn.arteco.services.TransactionPService;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class PointPackCardController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private UserService uS=new UserService();
    public TransactionPService tPS=new TransactionPService();
    public AccomplissementService aS=new AccomplissementService();
    PointStoreController pointStoreController;
    @FXML
    private Label lprix;

    @FXML
    private Label lDescription;

    @FXML
    private Label lpoints;

    @FXML
    private Pane acheter;
    private PointPack pointPack;
    private User user;

    public static  int points;

    public  int getPoints() {
        return Integer.valueOf(lpoints.getText());
    }

    public void setPointStoreController(PointStoreController pointStoreController) {
        this.pointStoreController = pointStoreController;
    }

    public void setData(PointPack p){

        this.user=uS.getById(SessionManager.getInstance().getCurrentUser().getUserId());
        this.pointPack=p;
        this.lprix.setText(String.valueOf(p.getPrice()));
        this.lDescription.setText(p.getName());
        this.lpoints.setText(String.valueOf(p.getPoints()));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acheter.setOnMouseClicked(e->{
            pointStoreController.getDonationpane().setVisible(true);
            pointStoreController.getIdfielddonation().setText(lpoints.getText());
            pointStoreController.getMontant().setText(String.valueOf(Integer.valueOf(lprix.getText())*100));
        });
    }
}
