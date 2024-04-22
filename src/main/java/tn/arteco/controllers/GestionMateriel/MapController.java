package tn.arteco.controllers.GestionMateriel;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MapController {
    @FXML
    private WebView mapView;

AjouterProduit ap;
ModifierMateriel ma;


public void setParent(AjouterProduit ap)
{
this.ap=ap;
}
public void setParentModifier(ModifierMateriel ma)
    {
        this.ma=ma;
    }
    @FXML
    public void initialize() {
        WebEngine we = mapView.getEngine();
        we.load(getClass().getResource("/gestionMateriel/maps.html").toExternalForm());
        we.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            JSObject window = (JSObject) we.executeScript("window");
            window.setMember("javaConnector", this);
        });
    }


    public void updateMarker(String location)
    {
        System.out.println("Marker position updated to:"+ location);
        if(ap!=null) {
            ap.setAdresseMateriel(location);
        }
        else if(ma!=null)
            ma.setAdresseMateriel(location);


    }
}
