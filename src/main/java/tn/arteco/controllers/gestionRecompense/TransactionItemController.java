package tn.arteco.controllers.gestionRecompense;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.arteco.models.MarchandiseFacture;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.TransactionP;
import tn.arteco.models.User;
import tn.arteco.services.AccomplissementService;
import tn.arteco.services.UserService;


public class TransactionItemController  {

   private AccomplissementService aS=new AccomplissementService();
   private UserService uS=new UserService();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label idClient;

    @FXML
    private Label idDate;

    @FXML
    private Label idSensT;

    @FXML
    private Label idPoints;

    @FXML
    private ImageView itemImg;

    @FXML
    private Rectangle rec;




    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSuprimer;

    private TransactionP transactionP;
    private Image image;





    public void setData(TransactionP tp,boolean b){
        if(b)
        {
            this.rec.setStyle("-fx-fill:#c8edf9;-fx-arc-height:20;-fx-arc-width:20");
        }
        this.setData(tp);
    }
    public void setData(TransactionP tp){
        int i=aS.getUserIdByAccomplisementId(tp.getAccomplissement().getIdAccomp());
        User user =new User();
       user=uS.getById(i);

        this.transactionP=tp;
        this.idPoints.setText(String.valueOf(tp.getPoints()));
        if(tp.isIncoming())
        this.idSensT.setText("Entrant");
        else
            this.idSensT.setText("Sortant");
        this.idClient.setText(user.getUsername());
        //this.idClient.setTooltip(new Tooltip(f.getNonArtiste().toString()));
        this.idDate.setText(tp.getDateTp().toString());

    }

}
