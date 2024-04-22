package tn.arteco.models;

public class RatingEvenement {
    private int idratingE;

    private int idEventToRate;
    private int RateE;

    public RatingEvenement(int idratingE, int idEventToRate, int rateE) {
        this.idratingE = idratingE;
        this.idEventToRate = idEventToRate;
        RateE = rateE;
    }

    public RatingEvenement(int idEventToRate, int rateE) {
        this.idEventToRate = idEventToRate;
        RateE = rateE;
    }

    public int getIdratingE() {
        return idratingE;
    }

    public void setIdratingE(int idratingE) {
        this.idratingE = idratingE;
    }

    public int getIdEventToRate() {
        return idEventToRate;
    }

    public void setIdEventToRate(int idEventToRate) {
        this.idEventToRate = idEventToRate;
    }

    public int getRateE() {
        return RateE;
    }

    public void setRateE(int rateE) {
        RateE = rateE;
    }


    @Override
    public String toString() {
        return "RatingEvenement{" +
                "idratingE=" + idratingE +
                ", idEventToRate=" + idEventToRate +
                ", RateE=" + RateE +
                '}';
    }


}
