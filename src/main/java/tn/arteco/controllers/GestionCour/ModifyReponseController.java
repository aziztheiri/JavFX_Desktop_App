package tn.arteco.controllers.GestionCour;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.arteco.models.Reponse;
import tn.arteco.services.ReponseService;

public class ModifyReponseController {
    @FXML
    private TextField Reponse;
    @FXML
    private CheckBox True;
    @FXML
    private Label idReponse;
    ModifyQuestionController MQC;
    int IdQuestion;
    String reponse;

    @FXML
    public Reponse GetAndSetReponseData(int Id) {
        this.reponse = this.Reponse.getText();
        return new Reponse(0, Id, this.Reponse.getText(), this.True.isSelected());
    }

    public Reponse getReponse() {
        return new Reponse(Integer.parseInt(idReponse.getText()), this.IdQuestion, this.Reponse.getText(), this.True.isSelected());
    }

    @FXML
    public void SetReponseData(Reponse R) {
        this.idReponse.setText(String.valueOf(R.getIdReponse()));
        this.Reponse.setText(R.getReponse());
        this.True.setSelected(R.isEtat());
    }

    @FXML
    public ModifyQuestionController getParent() {
        return this.MQC;
    }

    public ModifyReponseController getARC() {
        return this;
    }

    public void setParentARC(ModifyQuestionController mrc){
        this.MQC=mrc;
        this.IdQuestion= MQC.QuestionId;
        this.reponse="";
    }
}