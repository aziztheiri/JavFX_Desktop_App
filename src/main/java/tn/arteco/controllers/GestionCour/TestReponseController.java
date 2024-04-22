package tn.arteco.controllers.GestionCour;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import tn.arteco.models.Reponse;
import tn.arteco.models.ResultatReponse;
import tn.arteco.services.ReponseService;


public class TestReponseController {
    @FXML
    private CheckBox theReponse;
    @FXML
    private Pane TruePane;
    @FXML
    private Pane FalsePane;
    @FXML
    private Label idReponse;
    @FXML
    private Label UsersAnswer;
    TestQuestionController TQC;
    public void setReponseData(Reponse R){
        this.theReponse.setText(R.getReponse());
        this.idReponse.setText(String.valueOf(R.getIdReponse()));
    }
    public void setParentTRC(TestQuestionController TQC){
        this.TQC=TQC;
    }

    public void setAnswer(String Id){
        ReponseService RS = new ReponseService();
        Reponse R = RS.getById(Integer.parseInt(this.idReponse.getText()));
        ResultatReponse RR = new ResultatReponse();
        if(this.idReponse.getText().equals(Id) && RR.prefs.getBoolean(this.idReponse.getText(),false))
            this.UsersAnswer.setVisible(true);
        if(R.isEtat()){
            this.TruePane.setVisible(true);}
        else
            this.FalsePane.setVisible(true);
    }


}
