package tn.arteco.controllers.GestionCour;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.arteco.models.*;
import tn.arteco.services.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestQuizController {
    @FXML
    private Label QuizTitle;
    @FXML
    private Label QuizId;
    @FXML
    private Label TotalPoints;
    @FXML
    private GridPane TestQuestionGrid;
    @FXML
    private Button ValidateButton;

    @FXML
    private Button exportPDF;
    private Set<Reponse> SR;
    GestionQuizController GQC;
    int rows = 1;
    int columns;
    @FXML
    public void ShowTestQuiz(String ID,List<List<ReponseController>> LLRC) throws IOException {
        int QuizID = Integer.parseInt(ID);
        ReponseService RS = new ReponseService();
        QuestionService QS1 = new QuestionService();
        List<Question> LQS = QS1.getQuestionByQuizId(Integer.parseInt(ID));
        setDataTQC(ID);
        for (Question Q: LQS) {
            this.SR.addAll(RS.getReponseByIdQuestion(Q.getIdQuestion()).stream().collect(Collectors.toSet()));}
        for (List<ReponseController> LRC: LLRC) {
            for (ReponseController RC: LRC) {
                for (Reponse Rep: this.SR)
                    RC.CheckedorNot(String.valueOf(Rep.getIdReponse()));
            }}
        setTestQuestionGrid(LQS);
        showTotalPointsAndAddResultat();
        resetRowsColumns();
    }
    public void setDataTQC(String id){
        int QuizID = Integer.parseInt(id);
        QuizService QS = new QuizService();
        Quiz Q = QS.getById(QuizID);
        this.QuizTitle.setText(Q.getTitreQuiz());
        this.QuizId.setText(String.valueOf(Q.getIdQuiz()));
    }
    @FXML
    public void ValidateButtonClicked(ActionEvent e){
        Stage stage=(Stage)ValidateButton.getScene().getWindow();
        stage.close();
    }
    public void setParentTQC(GestionQuizController gqc){
        this.GQC=gqc;
        this.SR= new HashSet<>();
    }
    public void showTotalPointsAndAddResultat(){
        ResultatQuizService RQS = new ResultatQuizService();
        QuizService QS = new QuizService();
        Quiz Q1 = QS.getById(Integer.parseInt(this.QuizId.getText()));
        ResultatQuiz RQ1 = RQS.getResultatQuizByQuizId(Q1.getIdQuiz());
        if(RQ1==null){
            ResultatQuiz RQ = new ResultatQuiz(1,RQS.FinalTest(this.SR), SessionManager.getInstance().getCurrentUser().getUserId(),Q1.getIdQuiz());
            RQS.add(RQ);
            this.TotalPoints.setText(String.valueOf(RQS.FinalTest(this.SR)));}
        else {
            this.TotalPoints.setText(String.valueOf(RQS.FinalTest(this.SR)));
            RQS.update(RQ1);
            if(SessionManager.getInstance().getCurrentUser() instanceof NonArtiste na)
                RQS.addPoints((int)(na.getPoints()+RQS.FinalTest(this.SR)),SessionManager.getInstance().getCurrentUser().getUserId());
        }

    }
    @FXML
    public void setTestQuestionGrid(List<Question> Questions){
        for (Question Q : Questions) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gestionCour/TestQuestion.fxml"));
            AnchorPane vbox = null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TestQuestionController TQC = fxmlLoader.getController();
            TQC.setParentTQC(this);
            TQC.setTestQuestionData(Q);
            if (columns > 0) {
                columns = 0;
                rows++;
            }
            this.TestQuestionGrid.add(vbox, columns++, rows);
            GridPane.setMargin(vbox, new Insets(30));

        }}

    public void resetRowsColumns() {
        rows = 1;
        columns = 0;
    }
    @FXML
    public void onExportPDFClicked(ActionEvent e){
        ResultatQuizService RQS = new ResultatQuizService();
        generatePDF(RQS.FinalTest(this.SR));
        Stage stage=(Stage)ValidateButton.getScene().getWindow();
        stage.close();
    }
    public void generatePDF(float Points){
        Document doc = new Document();
        UserService US = new UserService();
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\GHOFRANE\\Desktop\\sample1.pdf"));
            System.out.println("PDF created.");
            doc.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph title = new Paragraph("Product Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            doc.add(title);
            Paragraph details = new Paragraph();
            details.add(new Chunk("Points changed: ", normalFont));
            details.add(new Chunk(String.valueOf(Points), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.add(new Chunk("\nTitre Quiz: ", normalFont));
            details.add(new Chunk(this.QuizTitle.getText(), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.add(new Chunk("\nUser Name: ", normalFont));
            details.add(new Chunk(US.getById(SessionManager.getInstance().getCurrentUser().getUserId()).getNom(), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            details.setAlignment(Element.ALIGN_JUSTIFIED);
            doc.add(details);            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException d) {
            System.out.println(d.getMessage());
        }}

}
