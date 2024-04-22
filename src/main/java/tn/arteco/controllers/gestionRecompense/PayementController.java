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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.arteco.models.Accomplissement;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.TransactionP;
import tn.arteco.models.User;
import tn.arteco.services.AccomplissementService;
import tn.arteco.services.TransactionPService;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.*;

public class PayementController  implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
