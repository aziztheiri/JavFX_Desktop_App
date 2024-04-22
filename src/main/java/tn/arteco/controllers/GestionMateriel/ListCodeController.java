package tn.arteco.controllers.GestionMateriel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.arteco.models.Code;
import tn.arteco.services.ServiceCode;

import java.io.IOException;
import java.util.List;

public class ListCodeController {

    @FXML
    private GridPane codeGrid;
    int rows=0;
    GestionMaterielController gmc;
    public void setCodeList(List<Code> lc)
    {
        for (Code c:lc)
        {
            FXMLLoader fl=new FXMLLoader(getClass().getResource("/gestionMateriel/CodeCart.fxml"));
            AnchorPane aPane=null;
            try {

                 aPane=fl.load();
                 CodeCartController ccc=fl.getController();
                 ccc.setData(c);
                 ccc.setParent(this);
                 codeGrid.add(aPane,0,rows++);

                GridPane.setMargin(aPane,new Insets(10));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void initialize()
    {
        ServiceCode sc=new ServiceCode();
        setCodeList(sc.getCodeByUserId(1));
    }

    public void setParent(GestionMaterielController gmc)
    {
        this.gmc=gmc;
    }

    public GestionMaterielController getParent()
    {
        return this.gmc;
    }
}
