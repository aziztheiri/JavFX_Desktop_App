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
import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    @FXML
    private Label Question;
    @FXML
    private GridPane ReponseGrid;
    GestionQuizController GQC;
    @FXML
    private Label idQuestion;
     int IdQuestion;
    int rows = 1;
    int columns;
    public List<ReponseController> setQuestionData(Question Q){
        List <ReponseController> LRC = new ArrayList<>();
        IdQuestion=Q.getIdQuestion();
        System.out.println("ID Question de question = " + IdQuestion);
        this.Question.setText(Q.getQuestion());
        this.idQuestion.setText(String.valueOf(Q.getIdQuestion()));
        ReponseService RS=new ReponseService();
        int id=Integer.parseInt(idQuestion.getText());
        LRC = setReponseGrid(RS.getReponseByIdQuestion(id));
        resetRowsColumns();
        return LRC;
    }

    public void setParentQC(GestionQuizController GQC){
        this.GQC=GQC;
    }

    @FXML
    public List<ReponseController> setReponseGrid(List<Reponse> LR){
        List<ReponseController> LRC= new ArrayList<>();
        for (Reponse R:LR)  {

            Pane vbox = null;
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/gestionCour/Reponse.fxml"));
            try {

                vbox = fl.load();

            }catch (IOException e){
                throw new RuntimeException(e);
            }
            ReponseController RC = fl.getController();
            RC.setParentRC(this);
            RC.setReponseData(R);
            LRC.add(RC);
            if (columns > 0) {
                columns = 0;
                rows++;
            };
            ReponseGrid.add(vbox, columns++, rows);
            GridPane.setMargin(vbox, new Insets(10));

        }
        return LRC;
    }
    public void resetRowsColumns() {
        rows = 1;
        columns = 0;
    }


}
