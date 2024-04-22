package tn.arteco.models;

import java.util.ArrayList;
import java.util.List;

public class Accomplissement {
    private int idAccomp ;
    private NonArtiste nonArtiste;
    private String titre;

    private List<TransactionP> transactionsDePoints ;
    public Accomplissement(){
        this.transactionsDePoints =  new ArrayList<TransactionP>();
    }
    public Accomplissement(int idAccomp, NonArtiste nonArtiste, String titre) {
        this();
        this.idAccomp = idAccomp;
        this.nonArtiste = nonArtiste;
        this.titre = titre;



    }

    public int getIdAccomp() {
        return idAccomp;
    }

    public void setIdAccomp(int idAccomp) {
        this.idAccomp = idAccomp;
    }

    public NonArtiste getNonArtiste() {
        return nonArtiste;
    }

    public void setNonArtiste(NonArtiste nonArtiste) {
        this.nonArtiste = nonArtiste;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }



    public List<TransactionP> getTransactionsDePoints() {
        return transactionsDePoints;
    }

    public void setTransactionsDePoints(List<TransactionP> transactionsDePoints) {
        this.transactionsDePoints = transactionsDePoints;
    }

    @Override
    public String toString() {
        return "Accomplissements{" +
                "idAccomp=" + idAccomp +
                ", nonArtiste=" + nonArtiste +
                ", titre='" + titre + '\'' +
                ", transactionsDePoints=" + transactionsDePoints +
                '}' ;
    }
}
