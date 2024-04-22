package tn.arteco.controllers.ProduitsFinis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.arteco.models.ProduitFini;
import tn.arteco.utils.ChatGPTComparisonAPI;

import java.util.*;

public class CompareProductController {

    @FXML
    private ImageView imagev1;

    @FXML
    private ImageView imagev11;

    @FXML
    private Label showart1;

    @FXML
    private Label showart11;

    @FXML
    private Label showcat1;

    @FXML
    private Label showcat11;

    @FXML
    private TextArea showdescr1;

    @FXML
    private TextArea showdescr11;

    @FXML
    private Label showlibelle1;

    @FXML
    private Label showlibelle11;

    @FXML
    private Label showrate1;
    @FXML
    private Label descrrrr;
    @FXML
    private Label showrate11;
    @FXML
    private Label comparisonArtiste;
    @FXML
    private Label comparisonCat;
    @FXML
    private Label comparisonRate;
    @FXML
    private Label firstdesccomp;
    @FXML
    private Label seconddescrcomp;
    ChatGPTComparisonAPI chatGPTAPIExample = new ChatGPTComparisonAPI();
    ProduitFini firstElement;
    ProduitFini secondElement;

    public void setFirstCompareProduct(Set<ProduitFini> setproduit) {
        Iterator<ProduitFini> iterator = setproduit.iterator();
        if (iterator.hasNext()) {
            firstElement = iterator.next();
            showart1.setText(firstElement.getArtiste().getNom());
            showcat1.setText(firstElement.getCategorie());
            showdescr1.setText(firstElement.getDescription());
            showlibelle1.setText(firstElement.getLibProduit());
            showrate1.setText(String.valueOf(firstElement.getTotalRate()));
            Image image = new Image(firstElement.getImageUrl());
            imagev1.setImage(image);

            if (iterator.hasNext()) {
                secondElement = iterator.next();
                showart11.setText(secondElement.getArtiste().getNom());
                showcat11.setText(secondElement.getCategorie());
                showdescr11.setText(secondElement.getDescription());
                showlibelle11.setText(secondElement.getLibProduit());
                showrate11.setText(String.valueOf(secondElement.getTotalRate()));
                Image image1 = new Image(secondElement.getImageUrl());
                imagev11.setImage(image1);

            }

        }
      String response = chatGPTAPIExample.sendChatMessage(firstElement.toString() + secondElement.toString());
        String[] bulletPoints = response.split("\n");
        String bulletPoint1 = bulletPoints[0];
        String bulletPoint2 = bulletPoints[1];
        String bulletPoint3 = bulletPoints[2];
        String bulletPoint4 = bulletPoints[3];
        String bulletPoint5 = bulletPoints[4];
        String bulletPoint6 = bulletPoints[5];
        // Printing the extracted bullet points
            comparisonCat.setText(bulletPoint1);
          comparisonCat.setStyle("-fx-text-fill: #35374B; -fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;  -fx-border-radius: 10px;");
          comparisonRate.setText(bulletPoint2);
          comparisonRate.setStyle("-fx-text-fill: #35374B;-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;-fx-border-radius: 10px;");
          comparisonArtiste.setText(bulletPoint3);
          comparisonArtiste.setStyle("-fx-text-fill: #35374B;-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;-fx-border-radius: 10px;");
          firstdesccomp.setText(bulletPoint4);
        firstdesccomp.setStyle("-fx-text-fill: #35374B;-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;-fx-border-radius: 10px;");
        seconddescrcomp.setText(bulletPoint5);
        seconddescrcomp.setStyle("-fx-text-fill: #35374B;-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;-fx-border-radius: 10px;");
        descrrrr.setText(bulletPoint6);
        descrrrr.setStyle("-fx-text-fill: #35374B;-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;-fx-border-radius: 10px;");
    }
}
