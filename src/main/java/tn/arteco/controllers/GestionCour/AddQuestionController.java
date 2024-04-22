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
import tn.arteco.services.QuestionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddQuestionController {
    @FXML
    private TextField Question;
    @FXML
    private Label Alert;
    @FXML
    private GridPane ReponseGrid;
    @FXML
    private Button AddReponse;
    int QuizId;
    int QuestionId;
    AddQuizController AQC;
    int rows = 1;
    int columns;
    int reponses=1;
    String question="";
    List<AddReponseController> LARC;
    public void setParentAQC(AddQuizController AQC){
        this.AQC=AQC;

    }
    @FXML
    public void onAddReponseClicked(MouseEvent e) {
        AddReponseController ARC= new AddReponseController();
        if (reponses < 5) {
            if (!this.Question.getText().isEmpty()) {

                /*if (this.rows == 1) {
                    QS.add(new Question(1, this.Question.getText(), this.QuizId));
                }*/
                //Question Q = QS.getByQuestion(this.Question.getText());
                this.Alert.setVisible(false);
                ARC = setReponseGrid();
                reponses++;
            } else
                this.Alert.setVisible(true);
        }
        if(reponses==rows-1){
            this.LARC.add(ARC);
        }

        question=Question.getText();
    }

    @FXML
    public AddReponseController setReponseGrid(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gestionCour/AddReponse.fxml"));
        Pane vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddReponseController ARC = fxmlLoader.getController();
        ARC.setParentARC(this);
        ARC.GetAndSetReponseData(this.QuestionId);
        this.LARC.add(ARC);
        columns = 0;
        rows++;
        ReponseGrid.add(vbox, columns, rows);
        GridPane.setMargin(vbox, new Insets(20));
        return ARC.getARC();
    }



    public void addQuestionData(Quiz Q){
        this.QuizId=Q.getIdQuiz();
        this.LARC= new ArrayList<>();
        QuestionService QS = new QuestionService();
        this.QuestionId = QS.getLastQuestionId()+1;


    }
}
