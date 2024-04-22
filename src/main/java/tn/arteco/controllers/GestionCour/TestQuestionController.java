package tn.arteco.controllers.GestionCour;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tn.arteco.models.Question;
import tn.arteco.models.Reponse;
import tn.arteco.services.ReponseService;

import java.io.IOException;
import java.util.List;

public class TestQuestionController {
    @FXML
    private Label Question;
    @FXML
    private GridPane ReponseGrid;
    TestQuizController TQC;
    @FXML
    private Label idQuestion;
    int rows = 1;
    int columns;
    public void setTestQuestionData(Question Q){
        this.Question.setText(Q.getQuestion());
        this.idQuestion.setText(String.valueOf(Q.getIdQuestion()));
        ReponseService RS=new ReponseService();
        int id=Integer.parseInt(idQuestion.getText());
        setTestReponseGrid(RS.getReponseByIdQuestion(id));
        resetRowsColumns();
    }

    public void setParentTQC(TestQuizController TQC){
        this.TQC=TQC;
    }

    @FXML
    public void setTestReponseGrid(List<Reponse> LR){
        for (Reponse R:LR)  {

            Pane vbox = null;
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/gestionCour/TestReponse.fxml"));
            try {

                vbox = fl.load();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
            TestReponseController TRC = fl.getController();
            TRC.setParentTRC(this);
            TRC.setReponseData(R);
            TRC.setAnswer(String.valueOf(R.getIdReponse()));
            if (columns > 0) {
                columns = 0;
                rows++;
            };
            ReponseGrid.add(vbox, columns++, rows);
            GridPane.setMargin(vbox, new Insets(10));

        }

    }
    public void resetRowsColumns() {
        rows = 1;
        columns = 0;
    }


}

