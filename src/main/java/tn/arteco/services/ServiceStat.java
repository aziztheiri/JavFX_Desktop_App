package tn.arteco.services;

import tn.arteco.models.Evenement;
import tn.arteco.models.RatingEvenement;
import tn.arteco.models.ReservationEv;

import java.util.ArrayList;
import java.util.List;

public class ServiceStat {
    ServiceEvenement se = new ServiceEvenement();
    ServiceReservationEv sr = new ServiceReservationEv();

    //Recupererles noms des evenements

    public List<String> listenomevent (){
        List<String> listeNoms = new ArrayList<>();
        for (Evenement evenement : se.listerEvenements()){
            listeNoms.add(evenement.getNomEvenement());
        }
        return listeNoms;

    }
//Recuperer le nombres des reservations confirmes par rapport a chaque evenement
    public List<Integer> listeConfirme(){
        List<Integer> listeconf = new ArrayList<>();
        for (Evenement evenement : se.listerEvenements()){
            int n = 0 ;
            for (ReservationEv reserv : sr.listerReservations()){
                if ( evenement.getIdEvenement()== reserv.getIdEvent() && reserv.getEtatReserv().equalsIgnoreCase("CONFIRME"))
                {
                    n++;
                }
            }
            listeconf.add(n);

        }
        return listeconf;
    }



}
