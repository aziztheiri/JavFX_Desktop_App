package tn.arteco.controllers.GestionCour;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.arteco.models.Question;
import tn.arteco.services.QuestionService;
import tn.arteco.services.QuizService;
import tn.arteco.models.Quiz;

import java.io.IOException;
import java.util.*;

public class GestionQuizController {
    @FXML
    private Label QuizTitle;
    @FXML
    private GridPane QuestionGrid;
    @FXML
    private Button ValidateButton;
    private List<List<ReponseController>> LRC;
    int rows = 1;
    int columns;
    CourController CC;
    String id;
    @FXML
   public void ShowQuiz(String ID) throws IOException {
        int QuizID = Integer.parseInt(ID);
        this.id=ID;
        QuizService QS = new QuizService();
        QuestionService QS1 = new QuestionService();
        Quiz Q = QS.getById(QuizID);
        this.QuizTitle.setText(Q.getTitreQuiz());
        List<Question> LQS = QS1.getQuestionByQuizId(Integer.parseInt(ID));
        setQuestionGrid(LQS);
        resetRowsColumns();
    }
    public void setQuestionGrid(List<Question> Questions){
        for (Question Q : Questions) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gestionCour/Question.fxml"));
            AnchorPane vbox = null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        QuestionController QC = fxmlLoader.getController();
            QC.setParentQC(this);
            this.LRC.add(QC.setQuestionData(Q));
            if (columns > 0) {
                columns = 0;
                rows++;
            }
            QuestionGrid.add(vbox, columns++, rows);
            GridPane.setMargin(vbox, new Insets(30));
        }
    }
    @FXML
    public void ValidateButtonClicked(ActionEvent e){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionCour/TestQuiz.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Stage TestQuizStage=new Stage();
            TestQuizStage.setScene(new Scene(root));
            TestQuizController TQC = fxmlLoader.getController();
            TQC.setParentTQC(this);
            TestQuizStage.show();
            TQC.ShowTestQuiz(this.id,this.LRC);
        }catch (IOException e1){
            throw new RuntimeException(e1);}
    }
    public void setParentGQC(CourController cc) {
        this.CC = cc;
        this.LRC= new ArrayList<>();
    }
    public void resetRowsColumns() {
        rows = 1;
        columns = 0;
    }

}
