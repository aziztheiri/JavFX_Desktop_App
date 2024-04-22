package tn.arteco.controllers.GestionCour;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import tn.arteco.models.Quiz;

import java.io.IOException;

public class CourController {
    @FXML
    private Label LabelTitreQuiz;
    @FXML
    private Label LabelPoints;
    @FXML
    private Label QuizId;
    GestionCourController GCC;
    public void setQuizData(Quiz Quiz) {
        this.LabelTitreQuiz.setText(Quiz.getTitreQuiz());
        this.LabelPoints.setText(String.valueOf(Quiz.getPointQuiz()));
        this.QuizId.setText(String.valueOf(Quiz.getIdQuiz()));
}
    public void setParentCC(GestionCourController gcc) {
        this.GCC = gcc;
    }

    @FXML
    public void startButtonClicked(ActionEvent actionEvent) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionCour/GestionQuiz.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Stage QuizStage=new Stage();
            QuizStage.setScene(new Scene(root));
            GestionQuizController GQC = fxmlLoader.getController();
            GQC.setParentGQC(this);
            QuizStage.show();
            GQC.ShowQuiz(this.QuizId.getText());
        }catch (IOException e){
            throw new RuntimeException(e);}
    }
}
