package tn.arteco.controllers.GestionCour;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tn.arteco.models.Question;
import tn.arteco.models.Quiz;
import tn.arteco.models.Reponse;
import tn.arteco.services.QuestionService;
import tn.arteco.services.ReponseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModifyQuestionController {
    @FXML
    private TextField Question;
    @FXML
    private Label Alert;
    @FXML
    private GridPane ReponseGrid;
    @FXML
    private Button AddReponse;
    @FXML
    private Label idQuestion;
    int QuizId;
    int QuestionId;
    String question="";
    ModifyQuizController MQC;
    int rows = 1;
    int RN=1;
    int columns;
    List<ModifyReponseController> LMRC;
    public void setParentAQC(ModifyQuizController MQC){
        this.MQC=MQC;

    }
    @FXML
    public void onAddReponseClicked(MouseEvent e) {
        if (!this.Question.getText().isEmpty()) {
            if (this.RN < 4) {
                this.RN++;
                this.question = this.Question.getText();
                this.Alert.setVisible(false);
                setReponseGrid();
            }
        }
        else
            this.Alert.setVisible(true);

    }

    @FXML
    public void setReponseGrid(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gestionCour/ModifyReponse.fxml"));
        Pane vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModifyReponseController MRC = fxmlLoader.getController();
        MRC.setParentARC(this);
        MRC.GetAndSetReponseData(this.QuestionId);
        this.LMRC.add(MRC);
        columns = 0;
        rows++;
        ReponseGrid.add(vbox, columns, rows);
        GridPane.setMargin(vbox, new Insets(20));
    }
    @FXML
    public void setReponseGridData(Reponse R){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gestionCour/ModifyReponse.fxml"));
        Pane vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModifyReponseController MRC = fxmlLoader.getController();
        MRC.setParentARC(this);
        MRC.GetAndSetReponseData(this.QuestionId);
        MRC.SetReponseData(R);
        this.LMRC.add(MRC);
        columns = 0;
        rows++;
        ReponseGrid.add(vbox, columns, rows);
        GridPane.setMargin(vbox, new Insets(20));
    }

    public void addQuestionData(Quiz Q){
        this.QuizId=Q.getIdQuiz();
        this.LMRC= new ArrayList<>();

    }
    public void setQuestionData(Question Q){
        this.idQuestion.setText(String.valueOf(Q.getIdQuestion()));
        QuestionId=Integer.parseInt(idQuestion.getText());
        ReponseService RS = new ReponseService();
        List<Reponse> LR = RS.getReponseByIdQuestion(Q.getIdQuestion());
        for (Reponse R: LR) {
            setReponseGridData(R);
        }
        this.Question.setText(Q.getQuestion());
    }
    public Question getQuestion(int Id){
        return new Question(Id,this.Question.getText(),this.QuizId);
    }
}
