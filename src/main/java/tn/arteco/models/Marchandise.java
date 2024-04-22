package tn.arteco.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Marchandise {
    private int idMerch;
    private String libelle ;
    private Date dateSortie;
    private Date dateLimit;
    private int quantiteDispo;
    private String description ;
    private int prix ;

    private String titreObtenu;
    private String imageUrl ;
    private List<MarchandiseFacture> listFactures;

    public Marchandise() {
        this.listFactures=new ArrayList<>();
    }

    public Marchandise(int idMerch, String libelle, Date dateSortie, Date dateLimit, int quantiteDispo
            , String description, int prix, String titreObtenu, String imageUrl) {
        this();
        this.idMerch = idMerch;
        this.libelle=libelle;
        this.dateSortie = dateSortie;
        this.dateLimit = dateLimit;
        this.quantiteDispo = quantiteDispo;
        this.description = description;
        this.prix = prix;
        this.titreObtenu = titreObtenu;
        this.imageUrl = imageUrl;

    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MarchandiseFacture> getListFactures() {
        return listFactures;
    }

    public void addFacture (MarchandiseFacture f){
        this.listFactures.add(f);
    }

    public int getIdMerch() {
        return idMerch;
    }

    public void setIdMerch(int idMerch) {
        this.idMerch = idMerch;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Date getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(Date dateLimit) {
        this.dateLimit = dateLimit;
    }

    public int getQuantiteDispo() {
        return quantiteDispo;
    }

    public void setQuantiteDispo(int quantiteDispo) {
        this.quantiteDispo = quantiteDispo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getTitreObtenu() {
        return titreObtenu;
    }

    public void setTitreObtenu(String titreObtenu) {
        this.titreObtenu = titreObtenu;
    }

    @Override
    public String toString() {
        return "Marchandise{" +
                "idMerch=" + idMerch +
                ", libelle='" + libelle + '\'' +
                ", dateSortie=" + dateSortie +
                ", dateLimit=" + dateLimit +
                ", quantiteDispo=" + quantiteDispo +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", titreObtenu='" + titreObtenu + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", listFactures=" + listFactures +
                '}';
    }
}
