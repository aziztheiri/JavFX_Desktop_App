package tn.arteco.models;

import java.util.prefs.Preferences;

public class ResultatReponse{
    private String idReponse;
    private boolean etat;
    public Preferences prefs = Preferences.userRoot().node("/tn/arteco/ResultatReponse/idReponse");

    public ResultatReponse( boolean etat,String idReponse) {
        this.idReponse = idReponse;
        this.etat = etat;
    }

    public ResultatReponse(){}

    public String getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(String idReponse) {
        this.idReponse = idReponse;
    }

    public boolean isEtat() {
        return etat;
    }


    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "ResultatReponse{" +
                "idReponse='" + idReponse + '\'' +
                ", etat=" + etat +
                '}';
    }
}



