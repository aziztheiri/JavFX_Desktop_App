package tn.arteco.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import tn.arteco.models.Code;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CodeCartController {

    @FXML
    private Text code;
    @FXML
    private Text date;
    @FXML
    private Text etat;
    @FXML
    private Text points;

    @FXML
    private Button rembourser;

    ListCodeController lcc;
    int etatClick=0;


    public void setParent(ListCodeController lcc) {
        this.lcc = lcc;
    }

    public void setData(Code c) {

        code.setText(c.getCode());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String datestr = df.format(c.getDateValide());
        date.setText(datestr);
        String etatCode = c.getEtat();
        System.out.println(c.getEtat());
        if (etatCode.equals("utilise"))
            rembourser.setDisable(true);
        etat.setText(etatCode);
        points.setText(String.valueOf(c.getNbPoints()));
    }

    @FXML
    void redeemClicked(ActionEvent event) {
        if(etatClick==0) {
            lcc.getParent().remboursementClicked(event);
            etatClick=1;
        }
    }
}
