package tn.arteco.controllers.GestionUtilisateur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.arteco.controllers.GestionUtilisateur.GestionUtilisateurController;
import tn.arteco.models.Artiste;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.services.UserService;


public class UserCardController {
    @FXML
    ImageView userPic;
    @FXML
    Label username;
    @FXML
    Label points;
    @FXML
    Label fullName;
    @FXML
    Label email;
    @FXML
    Label role;
    @FXML
    Label mats;
    @FXML
    Label prods;
    @FXML
    Label achievs;
    @FXML
    Label factures;
    @FXML
    Label quizes;
    @FXML
    Button activate;

    public UserCardController(){}
    GestionUtilisateurController gestionUtilisateurController;

    public void setGestionUtilisateurController(GestionUtilisateurController gestionUtilisateurController) {
        this.gestionUtilisateurController = gestionUtilisateurController;
    }
    public void setUserData(User user){

        if(!user.getImageUrl().isEmpty()){
            userPic.setImage(new Image(user.getImageUrl()));
        }

        username.setText(user.getUsername());
        fullName.setText(user.getNom() + " " + user.getPrenom());
        email.setText(user.getEmail());
        email.setTooltip(new Tooltip(user.getEmail()));
        activate.setText(user.isEtat() ? "deactivate" : "activate");
        if(user instanceof Artiste a){
            prods.setText("produits:"+a.getListeProduitFini().size());
        }
        if(user instanceof NonArtiste na) {
            points.setText(String.valueOf(na.getPoints()));
            if(na.getAccomplissment()!=null)
                achievs.setText("accomlissement: "+na.getAccomplissment().getTitre());

            mats.setText("Materiels: "+na.getListeMateriel().size());
            factures.setText("Facture: "+na.getListeFacture().size());
            quizes.setText("quizes: "+na.getListeResultat().size());
        }
        role.setText(user.getRole().toString().toLowerCase());
    }
    @FXML
    public void activate(){
        UserService userService=new UserService();
        User user=userService.getUserByUsername(username.getText());
        user.setEtat(!user.isEtat());
        userService.update(user);
        gestionUtilisateurController.refresh();
    }
}
