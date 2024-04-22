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

public class ModifyQuizController {
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
    int idQuiz;

    int reponse;
    BackCourController BCC;
    List<ModifyQuestionController> LMQC;
    int rows = 1;
    int QN=1;
    int columns;

    public void onAddQuizClicked(ActionEvent e){
        ReponseService RS = new ReponseService();
        QuestionService QS=new QuestionService();
        QuizService quizService=new QuizService();
        quizService.update(new Quiz(idQuiz,this.Title.getText(),Integer.parseInt(this.Points.getText())));
        for (ModifyQuestionController MQC: this.LMQC) {
            if(QN==1 && this.LMQC.iterator().hasNext()){
                int Qid =  MQC.QuestionId;
                QS.update(MQC.getQuestion(Qid));;
            }
            else if (QN>1 && (this.LMQC.indexOf(MQC)==this.LMQC.size()-1)){
                MQC.QuestionId=QS.getLastQuestionId()+1;
                QS.add(new Question(MQC.QuestionId,MQC.question,idQuiz));}
            for (ModifyReponseController MRC: MQC.LMRC) {

                if(MRC!=null){
                    if(MQC.RN>1){
                        if(RS.getByReponse(MRC.GetAndSetReponseData(MQC.QuestionId).getReponse())==null)
                            RS.add(MRC.GetAndSetReponseData(MQC.QuestionId));}
                    else {
                        //MQC.setQuestionData(QS.getById(MQC.QuestionId));
                        //addModifiedReponse();
                        RS.update(MRC.getReponse());
                    }
                }
            }}

        this.BCC.resetBack();
        Stage stage=(Stage)Confirm.getScene().getWindow();
        stage.close();
    }

    public void setParentAQC(BackCourController bcc){
        this.BCC=bcc;
        this.LMQC= new ArrayList<>();
    }
    @FXML
    public void setQuestionGrid(Quiz Q){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gestionCour/ModifyQuestion.fxml"));
        Pane vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModifyQuestionController MQC = fxmlLoader.getController();
        MQC.setParentAQC(this);
        MQC.addQuestionData(Q);
        this.LMQC.add(MQC);
        columns = 0;
        rows++;
        QuestionGrid.add(vbox, columns++, rows);
        GridPane.setMargin(vbox, new Insets(20));
    }

    @FXML
    public void setQuestionGridData(Quiz Q1,Question Q){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gestionCour/ModifyQuestion.fxml"));
        Pane vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModifyQuestionController MQC = fxmlLoader.getController();
        MQC.setParentAQC(this);
        MQC.addQuestionData(Q1);

        this.LMQC.add(MQC);
        MQC.setQuestionData(Q);
        columns = 0;
        rows++;
        QuestionGrid.add(vbox, columns++, rows);
        GridPane.setMargin(vbox, new Insets(20));
    }
    @FXML
    public void setDataMQC(Quiz Q){
        QuestionService QS = new QuestionService();
        List<Question> LQ = QS.getQuestionByQuizId(Q.getIdQuiz());
        LQ.listIterator();
        for (Question Q1: LQ)
            setQuestionGridData(Q,Q1);
        this.Title.setText(Q.getTitreQuiz());
        this.Points.setText(String.valueOf(Q.getPointQuiz()));
        this.idQuiz=Q.getIdQuiz();

    }
    public void onAddQuestionClicked(ActionEvent e){
        if(!this.Title.getText().isEmpty() && !this.Points.getText().isEmpty()){
            this.QN++;
            this.Alert.setVisible(false);
            Quiz Q = new Quiz(this.idQuiz,this.Title.getText(),Integer.parseInt(this.Points.getText()));
            QuizService QS = new QuizService();
            QuestionService QS1 = new QuestionService();
            this.setQuestionGrid(Q);

        }
        else
            this.Alert.setVisible(true);
    }



}
