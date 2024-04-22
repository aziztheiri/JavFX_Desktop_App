package tn.arteco.iservices;


import tn.arteco.models.Evenement;

import java.sql.Date;
import java.util.ArrayList;

public interface IEvent <Event> {
    void ajouter_Evenement(Evenement e);
    void supprimer_Evenement(int idEvenement);
    ArrayList<Evenement> listerEvenements();
    void modifier_Evenement(int idEvenement, String nomEvenement, String adresseEvenement, String descriptionEvenement, Date dateEvenement);

    Evenement getEventById(int idEvenement);

}