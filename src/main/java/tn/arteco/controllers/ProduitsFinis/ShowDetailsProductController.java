package tn.arteco.controllers.ProduitsFinis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;
import tn.arteco.models.ProduitFini;

public class ShowDetailsProductController {

    @FXML
    private Label showart;

    @FXML
    private Label showcat;


    @FXML
    private TextArea showdescr;


    @FXML
    private Label showlibelle;

    @FXML
    private Label showrate;
    @FXML
    private ImageView imagev;


    public void setProductDetails(ProduitFini produitFini) {
        showart.setText(produitFini.getArtiste().getNom());
        showcat.setText(produitFini.getCategorie());
        showdescr.setText(produitFini.getDescription());
        showlibelle.setText(produitFini.getLibProduit());
        showrate.setText(String.valueOf(produitFini.getTotalRate()));
        Image image = new Image(produitFini.getImageUrl());
        imagev.setImage(image);
    }

}
