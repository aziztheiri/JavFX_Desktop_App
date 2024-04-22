package tn.arteco.controllers.GestionCour;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.arteco.models.Quiz;
import tn.arteco.services.QuizService;
import tn.arteco.services.SessionManager;
import tn.arteco.test.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class BackGestionCourController {

    @FXML
    private GridPane QuizGrid;
    @FXML
    private TextField FilterByTitle;
    @FXML
    private TextField FilterById;
    @FXML
    private AnchorPane MainContainer;
    int rows = 1;
    int columns;
    public void setQuizGrid(List<Quiz> Q) {
        for (Quiz Quiz : Q) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gestionCour/BackCour.fxml"));
            Pane vbox = null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BackCourController BCC = fxmlLoader.getController();
            BCC.setParentBCC(this);
            BCC.setQuizData(Quiz);
            if (columns > 3) {
                columns = 0;
                rows++;
            }
            QuizGrid.add(vbox, columns++, rows);
            GridPane.setMargin(vbox, new Insets(20));
        }

    }
    @FXML
    public void clearQuizGrid(){
        this.QuizGrid.getChildren().clear();
    }
    public void resetRowsColumns() {
        rows = 1;
        columns = 0;
    }
    @FXML
    public void onAddClicked(ActionEvent e){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionCour/AddQuiz.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Stage QuizStage=new Stage();
            QuizStage.setScene(new Scene(root));
            AddQuizController AQC = fxmlLoader.getController();
            AQC.setParentAQC(this);
            QuizStage.show();
        }catch (IOException e1){
            throw new RuntimeException(e1);}
    }
    @FXML
    public void initialize() throws IOException {
        QuizService QS = new QuizService();
        List<Quiz> LQS = QS.getAll();
        setQuizGrid(LQS);
        resetRowsColumns();
    }
    @FXML
    public void FilterByTitle(MouseEvent event) throws IOException {
        FilterByTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                QuizGrid.getChildren().clear();
                QuizService QS = new QuizService();
                List<Quiz> QL = QS.getAll();
                QL = QS.FilterByTitle(QL, FilterByTitle.getText());
                setQuizGrid(QL);
                resetRowsColumns();
            }  else{
                QuizGrid.getChildren().clear();
                QuizService QS = new QuizService();
                List<Quiz> QL = QS.getAll();
                setQuizGrid(QL);
                resetRowsColumns();
            }
        });}
    @FXML
    public void FilterById(MouseEvent event) throws IOException {
        FilterById.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                QuizGrid.getChildren().clear();
                QuizService QS = new QuizService();
                List<Quiz> QL = new ArrayList<>();
                QL.add(QS.getById(Integer.parseInt(FilterById.getText())));
                setQuizGrid(QL);
                resetRowsColumns();
            }  else{
                QuizGrid.getChildren().clear();
                QuizService QS = new QuizService();
                List<Quiz> QL = QS.getAll();
                setQuizGrid(QL);
                resetRowsColumns();
            }
        });}
    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        try{
            Parent root=FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            MainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
    @FXML
    void naviguerVersUsers(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/GestionUtilisateur.fxml"));
            MainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void naviguerVersDashboard(ActionEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashBoardBack.fxml"));
            Parent root = loader.load();
            MainContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void materielBackClicked(ActionEvent event) {

        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
            MainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            MainContainer.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            MainContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            MainContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
