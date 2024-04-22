package tn.arteco.controllers.GestionCour;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.arteco.models.Question;
import tn.arteco.models.Quiz;
import tn.arteco.services.QuestionService;
import tn.arteco.services.QuizService;
import tn.arteco.services.ReponseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddQuizController {
    @FXML
    private Button AddQuestion;
    @FXML
    private Label Alert;
    @FXML
    private Button Confirm;
    @FXML
    private TextField Points;
    @FXML
    private TextField Title;
    @FXML
    private GridPane QuestionGrid;
    BackGestionCourController BGCC;
    List<AddQuestionController> LAQC;
    int QN;
     int questionId;
    Quiz Q;
int reponse;
    int rows = 1;
    int columns;

    @FXML
    public void onAddQuizClicked(ActionEvent e) throws IOException {

        ReponseService RS = new ReponseService();
        QuizService QS=new QuizService();
        QuestionService questionService=new QuestionService();
        QS.add(Q);
        for (AddQuestionController AQC: this.LAQC) {
            questionService.add(new Question(questionService.getLastQuestionId()+1, AQC.question,Q.getIdQuiz()));
            for (AddReponseController ARC: AQC.LARC) {
                if(ARC!=null){
                    RS.add(ARC.GetAndSetReponseData(questionService.getLastQuestionId()));
                }}}

        BGCC.initialize();
        Stage stage=(Stage)Confirm.getScene().getWindow();
        stage.close();
    }

    public void setParentAQC(BackGestionCourController bgcc){
        this.BGCC=bgcc;
        this.LAQC= new ArrayList<>();
    }
    @FXML
    public void setQuestionGrid(Quiz Q){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gestionCour/AddQuestion.fxml"));
        Pane vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddQuestionController AQC = fxmlLoader.getController();
        AQC.setParentAQC(this);

        AQC.addQuestionData(Q);
        this.LAQC.add(AQC);
        columns = 0;
        rows++;
        QuestionGrid.add(vbox, columns++, rows);
        GridPane.setMargin(vbox, new Insets(20));
    }


    public void onAddQuestionClicked(ActionEvent e){
        if(!this.Title.getText().isEmpty() && !this.Points.getText().isEmpty()){
            QN++;
            this.Alert.setVisible(false);
            QuizService QS = new QuizService();
            int id=QS.getLastId()+1;
            //System.out.println(id);
            Q = new Quiz(id,this.Title.getText(),Integer.parseInt(this.Points.getText()));
            // System.out.println(rows);
            /*if(rows==1)
            QS.add(Q);*/

            this.setQuestionGrid(Q);}
        else
            this.Alert.setVisible(true);
    }

}
