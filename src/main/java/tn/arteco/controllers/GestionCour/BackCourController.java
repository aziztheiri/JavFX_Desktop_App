package tn.arteco.controllers.GestionCour;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.arteco.models.Quiz;
import tn.arteco.services.QuizService;

import java.io.IOException;

public class BackCourController {
    @FXML
    private Button DeleteButton;
    @FXML
    private Button ModifyButton;
    @FXML
    private Label LabelTitreQuiz;
    @FXML
    private Label LabelPoints;
    @FXML
    private Label QuizId;
    BackGestionCourController BCC;
    boolean test=false;
    public void setQuizData(Quiz Quiz) {
        this.LabelTitreQuiz.setText(Quiz.getTitreQuiz());
        this.LabelPoints.setText(String.valueOf(Quiz.getPointQuiz()));
        this.QuizId.setText(String.valueOf(Quiz.getIdQuiz()));
    }
    @FXML
    public void onDeleteButtonClicked(ActionEvent e){
        QuizService QS = new QuizService();
        QS.delete(Integer.parseInt(this.QuizId.getText()));
        this.BCC.clearQuizGrid();
        try {
            this.BCC.initialize();
        }catch (IOException e1){
            throw new RuntimeException(e1);
        }
    }
    @FXML
    public void onModifyButtonClicked(MouseEvent e){
        QuizService QS = new QuizService();
        Quiz Q = QS.getById(Integer.parseInt(this.QuizId.getText()));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionCour/ModifyQuiz.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Stage QuizStage=new Stage();
            QuizStage.setScene(new Scene(root));
            ModifyQuizController MQC = fxmlLoader.getController();
            MQC.setParentAQC(this);
            QuizStage.show();
            MQC.setDataMQC(Q);
        }catch (IOException e1){
            throw new RuntimeException(e1);}
    }
    public void setParentBCC(BackGestionCourController bcc){
        this.BCC=bcc;

    }
    public void resetBack()
    {
        try {
            BCC.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
