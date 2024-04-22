package tn.arteco.models;

import java.util.ArrayList;
import java.util.List;

public class ReservationEv {

    private int idReserv;
    private int idEvent;
    private String DateReserv;
    private int nbrPersonnes;

    private String etatReserv;
    private String emailClient;


    public ReservationEv() {}

    public ReservationEv(int idEvent, String dateReserv, int nbrPersonnes, String etatReserv, String emailClient) {
        this.idEvent = idEvent;
        DateReserv = dateReserv;
        this.nbrPersonnes = nbrPersonnes;
        this.etatReserv = etatReserv;
        this.emailClient = emailClient;
    }

    public ReservationEv(int idReserv, int idEvent, String dateReserv, int nbrPersonnes, String etatReserv, String emailClient) {
        this.idReserv = idReserv;
        this.idEvent = idEvent;
        this.DateReserv = dateReserv;
        this.nbrPersonnes = nbrPersonnes;
        this.etatReserv = etatReserv;
        this.emailClient = emailClient;
    }



    public int getIdReserv() {
        return idReserv;
    }

    public void setIdReserv(int idReserv) {
        this.idReserv = idReserv;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getDateReserv() {
        return DateReserv;
    }

    public void setDateReserv(String dateReserv) {
        DateReserv = dateReserv;
    }

    public int getNbrPersonnes() {
        return nbrPersonnes;
    }

    public void setNbrPersonnes(int nbrPersonnes) {
        this.nbrPersonnes = nbrPersonnes;
    }

    public String getEtatReserv() {
        return etatReserv;
    }

    public void setEtatReserv(String etatReserv) {
        this.etatReserv = etatReserv;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public String toString() {
        return "ReservationEv{" +
                "idReserv=" + idReserv +
                ", idEvent=" + idEvent +
                ", DateReserv='" + DateReserv + '\'' +
                ", nbrPersonnes=" + nbrPersonnes +
                ", etatReserv='" + etatReserv + '\'' +
                ", emailClient='" + emailClient + '\'' +
                '}';
    }
}
