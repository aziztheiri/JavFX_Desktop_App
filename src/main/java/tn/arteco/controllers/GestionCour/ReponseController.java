package tn.arteco.controllers.GestionCour;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import javafx.scene.control.Label;
import tn.arteco.models.Reponse;
import tn.arteco.models.ResultatReponse;


public class ReponseController {
    @FXML
    private CheckBox theReponse;
    @FXML
    private Label idReponse;
    QuestionController QC;
    int IdQuestion;
    @FXML
    public void setReponseData(Reponse R){

        this.idReponse.setText(String.valueOf(R.getIdReponse()));
        this.theReponse.setText(R.getReponse());
    }
    public void setParentRC(QuestionController QC){
        this.QC=QC;
        this.IdQuestion=QC.IdQuestion;
        System.out.println("id Question de reponse =" + this.IdQuestion);
    }
    @FXML
    public void CheckedorNot(String id){
        ResultatReponse R = new ResultatReponse();
        if(this.idReponse.getText().equals(id)){
            R.prefs.putBoolean(id,this.theReponse.isSelected());
        }}
}
