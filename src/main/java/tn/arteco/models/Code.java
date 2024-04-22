package tn.arteco.models;

import java.sql.Date;

public class Code {
    private int idCode;
    private String code;
    private Date dateValide;
    private String etat;
    private int nbPoints;

    private int idNonArtiste;

    public Code(){};
    public Code(int idCode, String code, Date dateValide, String etat, int nbPoints,int idNonArtiste) {
        this.idCode = idCode;
        this.code = code;
        this.dateValide = dateValide;
        this.etat = etat;
        this.nbPoints = nbPoints;
        this.idNonArtiste=idNonArtiste;
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateValide() {
        return dateValide;
    }

    public void setDateValide(Date dateValide) {
        this.dateValide = dateValide;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getIdNonArtiste() {
        return idNonArtiste;
    }

    public void setIdNonArtiste(int idNonArtiste) {
        this.idNonArtiste = idNonArtiste;
    }

    @Override
    public String toString() {
        return "Code{" +
                "idCode=" + idCode +
                ", code='" + code + '\'' +
                ", dateValide=" + dateValide +
                ", etat='" + etat + '\'' +
                ", nbPoints=" + nbPoints +
                ", idNonArtiste=" + idNonArtiste +
                '}';
    }
}
