package tn.arteco.models;

import java.util.Date;

public class Rate {

    private int rateId;
    private int rate;
    private Date date;

    private ProduitFini produitFini;
    private int iduser;
    public Rate(){};

    public Rate(int rateId, int rate, Date date, ProduitFini produitFini,int iduser) {
        this.rateId = rateId;
        this.rate = rate;
        this.date = date;
        this.produitFini = produitFini;
        this.iduser=iduser;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProduitFini getProduitFini() {
        return produitFini;
    }

    public void setProduitFini(ProduitFini produitFini) {
        this.produitFini = produitFini;
    }


    @Override
    public String toString() {
        return "Rate{" +
                "rateId=" + rateId +
                ", rate=" + rate +
                ", date=" + date +
                ", produitFini=" + produitFini +
                '}';
    }


}
