package tn.arteco.iservices;

import tn.arteco.models.Evenement;
import tn.arteco.models.ReservationEv;
import tn.arteco.models.User;

import java.util.ArrayList;

public interface IReservationEv {
    void ajouter_Reservation(ReservationEv r);
    void supprimer_Reservation(int idReserv);
    ArrayList<ReservationEv> listerReservations();
    void modifier_Reservation(int idReserv,int idEvent,String dateReserv,int nbrPersonnes,String etatReserv,String emailClient);
    public ReservationEv getReservationById(int idReservation);


}
