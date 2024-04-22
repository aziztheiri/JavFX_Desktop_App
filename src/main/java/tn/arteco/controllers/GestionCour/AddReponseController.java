package tn.arteco.controllers.GestionCour;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import tn.arteco.models.Reponse;

public class AddReponseController {
    @FXML
    private TextField Reponse;
    @FXML
    private CheckBox True;
    AddQuestionController AQC;
    int IdQuestion;
    String reponse;
    public void setParentARC(AddQuestionController arc){
        this.AQC=arc;
        this.IdQuestion= AQC.QuestionId;
        this.reponse="";
    }
    @FXML
    public Reponse GetAndSetReponseData(int IdQuestion) {
        this.reponse = this.Reponse.getText();
        System.out.println("reponse= " + reponse);
        return new Reponse(1,IdQuestion,this.Reponse.getText(),this.True.isSelected());
    }
public AddReponseController getARC(){
    return this;
}}