package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CodeCorrectController {
    CodePageController cc;

    @FXML
    private Button ressayerButton;
    public void setParent(CodePageController cc)
    {
        this.cc=cc;
    }


    @FXML
    public void ressayer(ActionEvent event)
    {
        Stage stage=(Stage)ressayerButton.getScene().getWindow();

        stage.close();

        cc.resetBackgroundColor();
    }
}
