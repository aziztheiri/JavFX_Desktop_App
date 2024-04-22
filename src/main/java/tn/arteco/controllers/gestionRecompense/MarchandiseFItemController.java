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
import tn.arteco.models.User;
import tn.arteco.services.UserService;


public class  MarchandiseFItemController{
   private MarchandiseListController marchandiseListController=new MarchandiseListController();
   private UserService uS=new UserService();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label idClient;

    @FXML
    private Label idDate;

    @FXML
    private Label idMarchandise;

    @FXML
    private Label idNetPayer;

    @FXML
    private Label idQuantite;

    @FXML
    private ImageView itemImg;

    @FXML
    private Rectangle rec;




    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSuprimer;

    private MarchandiseFacture marchandiseFacture ;
    private Image image;
    private User client =new User();





    public void setData(MarchandiseFacture f,boolean b){
        if(b)
        {
            this.rec.setStyle("-fx-fill:#c8edf9;-fx-arc-height:20;-fx-arc-width:20");
        }
        this.setData(f);
    }
    public void setData(MarchandiseFacture f){
    this.client=uS.getById(f.getNonArtiste().getUserId());
        this.marchandiseFacture=f;
        this.idQuantite.setText(String.valueOf(f.getQuantite()));
        this.idNetPayer.setText(String.valueOf(f.getNetPayer()));
        this.idMarchandise.setText(f.getMarchandise().getLibelle());
        this.idMarchandise.setTooltip(new Tooltip("libelle: "+f.getMarchandise().getLibelle()+" description: "+f.getMarchandise().getDescription()+" titre obteni: "+f.getMarchandise().getTitreObtenu()));
        this.idClient.setText((client.getUsername()));
        this.idClient.setTooltip(new Tooltip(f.getNonArtiste().toString()));
        this.idDate.setText(f.getDateF().toString());

    }



}
