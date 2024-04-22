package tn.arteco.controllers.GestionCour;


import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tn.arteco.controllers.GestionMateriel.AnimationController;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.Quiz;
import tn.arteco.services.QuizService;
import tn.arteco.services.SessionManager;

import java.io.IOException;
import java.util.List;

public class GestionCourController {
    @FXML
    private AnchorPane MainContainer;
    @FXML
    private GridPane QuizGrid;
    @FXML
    private Button trieButton;
    @FXML
    private ScrollPane QuizContainer;
    @FXML
    private TextField FilterByTitle;
    @FXML
    private Text points;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label username;
    int rows = 1;
    int columns;

    @FXML
    private Button produitFiniText;
    @FXML
    private ImageView produitFiniIcon;
    @FXML
    private ImageView materielIcon;

    @FXML
    private Button materielText;

    @FXML
    private ImageView boutiqueIcon;

    @FXML
    private Button boutiqueText;

    @FXML
    private ImageView coursIcon;

    @FXML
    private Button coursText;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button eventText;
    @FXML
    private ImageView remoboursementIcon;

    @FXML
    private Button remoboursementText;
    public void setQuizGrid(List<Quiz> Q) {
        for (Quiz Quiz : Q) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gestionCour/Cour.fxml"));
            Pane vbox = null;
            try {
                vbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CourController CC = fxmlLoader.getController();
            CC.setParentCC(this);
            CC.setQuizData(Quiz);
            if (columns > 2) {
                columns = 0;
                rows++;
            }
            QuizGrid.add(vbox, columns++, rows);
            GridPane.setMargin(vbox, new Insets(20));
        }

    }

    public void resetRowsColumns() {
        rows = 1;
        columns = 0;
    }

    @FXML
    public void initialize() throws IOException {
       AnimationController ac=new AnimationController();

        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        Image image1 = new Image(SessionManager.getInstance().getCurrentUser().getImageUrl());
        userPhoto.setImage(image1);
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste a){
            points.setText(String.valueOf(a.getPoints()));
        }
        QuizService QS = new QuizService();
        List<Quiz> LQS = QS.getAll();
        setQuizGrid(LQS);
        resetRowsColumns();

        ac.moveNavBar(coursIcon, coursText);
        if(AnimationController.getNomCaller().equals("ProduitFini")) {
            produitFiniIcon.setLayoutX(produitFiniIcon.getLayoutX()+30);
            produitFiniText.setLayoutX(produitFiniText.getLayoutX()+30);
            ac.moveBackNavBar(produitFiniIcon, produitFiniText);
        }

        else if(AnimationController.getNomCaller().equals("Materiel")) {
            materielIcon.setLayoutX(materielIcon.getLayoutX()+30);
            materielText.setLayoutX(materielText.getLayoutX()+30);
            ac.moveBackNavBar(materielIcon, materielText);
        }

        else if(AnimationController.getNomCaller().equals("Code")) {
            remoboursementIcon.setLayoutX(remoboursementIcon.getLayoutX()+30);
            remoboursementText.setLayoutX(remoboursementText.getLayoutX()+30);
            ac.moveBackNavBar(remoboursementIcon, remoboursementText);
        }

        else if(AnimationController.getNomCaller().equals("Event")) {
            eventIcon.setLayoutX(eventIcon.getLayoutX()+30);
            eventText.setLayoutX(eventText.getLayoutX()+30);
            ac.moveBackNavBar(eventIcon, eventText);
        }

        else if(AnimationController.getNomCaller().equals("Boutique")) {
            boutiqueIcon.setLayoutX(boutiqueIcon.getLayoutX()+30);
            boutiqueText.setLayoutX(boutiqueText.getLayoutX()+30);
            ac.moveBackNavBar(boutiqueIcon, boutiqueText);
        }


    }

    @FXML
    public void trieButtonClicked(MouseEvent event) throws IOException {
        QuizService QS = new QuizService();
        List<Quiz> QL = QS.getAll();
        QL = QS.Sort((QL));
        setQuizGrid(QL);
        resetRowsColumns();
    }

    @FXML
    public void ButtonClickedAndFilter(MouseEvent event) throws IOException {
        FilterByTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                QuizGrid.getChildren().clear();
                QuizService QS = new QuizService();
                List<Quiz> QL = QS.getAll();
                QL = QS.FilterByTitle(QL, FilterByTitle.getText());
                setQuizGrid(QL);
                resetRowsColumns();
                trieButton.setOnMouseClicked((event1) ->{
                        QuizGrid.getChildren().clear();
                        QuizService QS1 = new QuizService();
                        List<Quiz> QL1 = QS1.getAll();
                        QL1 = QS1.Sort((QL1));
                        QL1 = QS1.FilterByTitle(QL1, FilterByTitle.getText());
                        setQuizGrid(QL1);
                        resetRowsColumns();
                    });
                }  else{
                        QuizGrid.getChildren().clear();
                        QuizService QS = new QuizService();
                        List<Quiz> QL = QS.getAll();
                        setQuizGrid(QL);
                        resetRowsColumns();
                    }
                });}

    @FXML
    void produitFiniClicked(ActionEvent event) {
        AnimationController.setNomCaller("Cours");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));

            MainContainer.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        } catch (IOException e) {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @FXML
    public void routeToMateriel(ActionEvent event){
        AnimationController.setNomCaller("Cours");

        try{
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMateriel.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void remboursementClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Cours");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodePage.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }
    @FXML
    void eventClicked(ActionEvent event)
    {
        AnimationController.setNomCaller("Cours");
        try {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/GestionEvenement/AfficherFront.fxml"));
            Parent root=fl.load();
            materielText.getScene().setRoot(root);
            //ac.moveBackNavBar(materielText,materielIcon);

        }catch (IOException e)
        {
            //System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }
    @FXML
    public void naviguerVersBoutique() {
        try {
            AnimationController.setNomCaller("Cours");
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/Boutique.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void disconnect(MouseEvent event) {


        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            materielText.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void routeToProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void homeClicked(MouseEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            materielText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}