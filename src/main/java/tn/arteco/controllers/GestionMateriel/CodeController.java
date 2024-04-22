package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CodeController {

    @FXML
    private Text code;

    @FXML
    private Text points;

    private GestionMaterielController gmc;

    /*@FXML
    public void initialize()
    {

    }*/

    public void setParent(GestionMaterielController gmc){
    this.gmc=gmc;
    }
    public void setCode(String code,int points)
    {
        this.code.setText(code);
        this.points.setText(String.valueOf(points)+" Points");
    }
    @FXML
    void redeemClicked(ActionEvent event) {
        Stage stage=(Stage)code.getScene().getWindow();
        stage.close();
       gmc.remboursementClicked(event);


    }

    @FXML
    void closeClicked(MouseEvent event) {
        Stage stage=(Stage)code.getScene().getWindow();
        stage.close();
    }

}
