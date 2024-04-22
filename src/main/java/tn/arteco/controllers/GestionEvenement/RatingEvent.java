package tn.arteco.controllers.GestionEvenement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.Rating;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.arteco.models.Evenement;
import tn.arteco.services.ServiceEvenement;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/*import java.awt.*;
import java.awt.event.ActionEvent;*/
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class RatingEvent  {

    @FXML
    private Label msg;

    @FXML
    private Rating rating;

    @FXML
    private Button buttonR;



    @FXML
    public void handleButtonAction(ActionEvent event) {
        System.out.println(" Rating given by users:" +rating.getRating());
    }


}
