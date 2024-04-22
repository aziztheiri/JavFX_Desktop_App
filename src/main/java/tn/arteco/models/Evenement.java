package tn.arteco.models;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

public class Evenement {
    private int idEvenement;
    private String nomEvenement;
    private String adresseEvenement;

    private String descriptionEvenement;
    private Date dateEvenement ;
   //private List<Participation> listParticipation ;
    private  List<User> listParticipants ;


    public Evenement() {listParticipants =new ArrayList<>();}


    public Evenement(int idEvenement, String nomEvenement, String adresseEvenement, String descriptionEvenement, Date dateEvenement , List<User> listParticipants) {
        this.idEvenement = idEvenement;
        this.nomEvenement = nomEvenement;
        this.adresseEvenement = adresseEvenement;
        this.descriptionEvenement = descriptionEvenement;
        this.dateEvenement = dateEvenement;
        this.listParticipants = listParticipants;
    }

    public Evenement(int idEvenement, String nomEvenement, String adresseEvenement, String descriptionEvenement, Date dateEvenement ) {
        this.idEvenement = idEvenement;
        this.nomEvenement = nomEvenement;
        this.adresseEvenement = adresseEvenement;
        this.descriptionEvenement = descriptionEvenement;
        this.dateEvenement = dateEvenement;
    }

    public Evenement(String nomEvenement, String adresseEvenement, String descriptionEvenement, Date dateEvenement) {
        this.nomEvenement = nomEvenement;
        this.adresseEvenement = adresseEvenement;
        this.descriptionEvenement = descriptionEvenement;
        this.dateEvenement = dateEvenement;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getAdresseEvenement() {
        return adresseEvenement;
    }

    public void setAdresseEvenement(String adresseEvenement) {
        this.adresseEvenement = adresseEvenement;
    }

    public String getDescriptionEvenement() {
        return descriptionEvenement;
    }

    public void setDescriptionEvenement(String descriptionEvenement) {
        this.descriptionEvenement = descriptionEvenement;
    }

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    /*public List<Participation> getListParticipation() {
        return listParticipation;
    }

    public void setListParticipation(List<Participation> listParticipation) {
        this.listParticipation = listParticipation;
    }*/
    public List<User> getListParticipants() {
        return listParticipants;
    }

    public void setListParticipation(List<User> listParticipants) {
        this.listParticipants = listParticipants;

    }

    @Override
    public String toString() {
        return "Evenement{" +
                "idEvenement=" + idEvenement +
                ", nomEvenement='" + nomEvenement + '\'' +
                ", adresseEvenement='" + adresseEvenement + '\'' +
                ", descriptionEvenement='" + descriptionEvenement + '\'' +
                ", dateEvenement=" + dateEvenement +
                ", listParticipants=" + listParticipants +
                '}';
    }
}








