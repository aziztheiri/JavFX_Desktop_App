package tn.arteco.models;

import java.util.Date;

public class TransactionP {
    private int idTp;
    private Accomplissement accomplissement ;
    private Date dateTp ;
    private int points ;
    private boolean incoming ;

    public TransactionP() {
    }

    public TransactionP(int idTp,Accomplissement accomplissements, Date dateTp, int points, boolean incoming) {
        this.idTp = idTp;
        this.accomplissement = accomplissements;
        this.dateTp = dateTp;
        this.points = points;
        this.incoming = incoming;
    }

    public int getIdTp() {
        return idTp;
    }

    public void setIdTp(int idTp) {
        this.idTp = idTp;
    }

    public Accomplissement getAccomplissement() {
        return accomplissement;
    }

    public void setAccomplissement(Accomplissement accomplissement) {
        this.accomplissement = accomplissement;
    }

    public Date getDateTp() {
        return dateTp;
    }

    public void setDateTp(Date dateTp) {
        this.dateTp = dateTp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isIncoming() {
        return incoming ;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    @Override
    public String toString() {
        return "TransactionP{" +
                "idTp=" + idTp +
                ", accomplissements=" + accomplissement +
                ", dateTp=" + dateTp +
                ", points=" + points +
                ", incoming=" + incoming +
                '}';
    }
}
