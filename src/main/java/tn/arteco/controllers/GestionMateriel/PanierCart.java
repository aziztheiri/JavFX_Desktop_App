package tn.arteco.controllers.GestionMateriel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.arteco.models.Materiel;
import tn.arteco.services.SessionManager;

import java.util.prefs.Preferences;

public class PanierCart {
    private String username= SessionManager.getInstance().getCurrentUser().getUsername();


    @FXML
    private Label descriptionMateriel;

    @FXML
    private ImageView materielImage;

    @FXML
    private Label nomMateriel;

    @FXML
    private Label quantiteMateriel;

    @FXML
    private Label idMateriel;

    PanierController panierController;
    Preferences pr;
    int qteTotal;


    public void setCart(Materiel m)
    {
        idMateriel.setText(String.valueOf(m.getidMateriel()));
        materielImage.setImage(new Image(m.getImageUrl()));
        nomMateriel.setText(m.getLibMateriel());
        descriptionMateriel.setText(m.getDescription());
        quantiteMateriel.setText(String.valueOf(m.getQuantiteReserver()));
        qteTotal=m.getQuantite();
    }

    public void setParent(PanierController pc)
    {
        panierController=pc;
    }
    @FXML
    public void initialize()
    {
        pr=Preferences.userRoot().node("/tn/arteco/controllers");
    }
    @FXML
    void plusClicked(MouseEvent event) {
        int qteReserver=Integer.parseInt(quantiteMateriel.getText());
        if(qteReserver+1<=qteTotal) {
            String ids = pr.get(username, "");
            ids += idMateriel.getText() + ",";
            pr.put(username, ids);
            int qte=pr.getInt("NumberReservation"+username,0);
            qte+=1;
            pr.putInt("NumberReservation"+username,qte);
            quantiteMateriel.setText(String.valueOf(Integer.parseInt(quantiteMateriel.getText()) + 1));
        }
    }
    @FXML
    void minusClicked(MouseEvent event)
    {
        if(Integer.parseInt(quantiteMateriel.getText())>1) {
            String ids = pr.get(username, "");
            ids = ids.replaceFirst(idMateriel.getText() + ",", "");
            pr.put(username, ids);
            int qte=pr.getInt("NumberReservation"+username,0);

            qte-=1;
            pr.putInt("NumberReservation"+username,qte);
            quantiteMateriel.setText(String.valueOf(Integer.parseInt(quantiteMateriel.getText()) - 1));
        }
    }

    @FXML
    void deleteClicked(MouseEvent event)
    {
        String ids = pr.get(username, "");
        ids = ids.replaceAll(idMateriel.getText() + ",", "");
        pr.put(username, ids);
        int qte=pr.getInt("NumberReservation"+username,0);
        qte-=Integer.parseInt(quantiteMateriel.getText());
        pr.putInt("NumberReservation"+username,qte);

        panierController.resetGrid();
        panierController.initialize();
    }

}
