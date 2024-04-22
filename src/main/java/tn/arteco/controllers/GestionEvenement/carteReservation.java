package tn.arteco.controllers.GestionEvenement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class carteReservation {



    @FXML
    private Label IdEventLabel;

    @FXML
    private Label DateReservationLabel ;

    @FXML
    private Label NbrPersonnesLabel;

    @FXML
    private Label EtatReservationLabel;
    @FXML

    private Label EmailclientLabel;


    public void setIdEvent(String IdEvent) {
        IdEventLabel.setText(IdEvent);
    }
    public void setIDateReservation(String DateReservation) {
        DateReservationLabel.setText(DateReservation);
    }
    public void setNbrPersonnes(String NbrPersonnes) {NbrPersonnesLabel.setText(NbrPersonnes); }
    public void setEtatReservation(String EtatReservation) {EtatReservationLabel.setText(EtatReservation); }
    public void setEmailclient(String Emailclient) {EmailclientLabel.setText(Emailclient); }



}
