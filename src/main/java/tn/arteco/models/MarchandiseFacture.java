package tn.arteco.models;

import java.util.Date;

public class MarchandiseFacture {
private int idFacture;
private Date dateF;
private Marchandise marchandise;
private NonArtiste nonArtiste;
private int quantite;
private int  netPayer;

    public MarchandiseFacture() {
    }

    public MarchandiseFacture(int idFacture, Date dateF, Marchandise marchandise, NonArtiste nonArtiste, int quantite, int netPayer) {
        this.idFacture = idFacture;
        this.dateF = dateF;
        this.marchandise = marchandise;
        this.nonArtiste = nonArtiste;
        this.quantite = quantite;
        this.netPayer = netPayer;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }

    public Marchandise getMarchandise() {
        return marchandise;
    }

    public void setMarchandise(Marchandise marchandise) {
        this.marchandise = marchandise;
    }

    public NonArtiste getNonArtiste() {
        return nonArtiste;
    }

    public void setNonArtiste(NonArtiste nonArtiste) {
        this.nonArtiste = nonArtiste;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getNetPayer() {
        return netPayer ;
    }

    public void setNetPayer(int netPayer) {
        this.netPayer = netPayer;
    }

    @Override
    public String toString() {
        return "MerchandiseFacture{" +
                "idFacture=" + idFacture +
                ", dateF=" + dateF +
                ", marchandise=" + marchandise +
                ", nonArtiste=" + nonArtiste +
                ", quantite=" + quantite +
                ", netPayer=" + netPayer +
                '}';
    }
}
