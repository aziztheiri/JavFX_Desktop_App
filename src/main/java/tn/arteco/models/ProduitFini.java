package tn.arteco.models;

import java.util.Date;
import java.util.Objects;

public class ProduitFini {
    private int idProduit;
    private Artiste artiste ;
    private String libProduit;
    private String description;
    private String imageUrl;

    private int totalRate;
    private String categorie ;
    private Date date ;

    public ProduitFini(int idProduit, Artiste artiste, String libProduit, String description, String imageUrl, int totalRate, String categorie,Date date) {
        this.idProduit = idProduit;
        this.artiste = artiste;
        this.libProduit = libProduit;
        this.description = description;
        this.imageUrl = imageUrl;
        this.totalRate = totalRate;
        this.categorie=categorie;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProduitFini(){};
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }
    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public String getLibProduit() {
        return libProduit;
    }

    public void setLibProduit(String libProduit) {
        this.libProduit = libProduit;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    @Override
    public String toString() {
        return "ProduitFini{" +
                "idProduit=" + idProduit +
                ", artiste=" + artiste +
                ", libProduit='" + libProduit + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", totalRate=" + totalRate +
                ", categorie='" + categorie + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProduitFini that = (ProduitFini) object;
        return idProduit == that.idProduit && totalRate == that.totalRate && Objects.equals(artiste, that.artiste) && Objects.equals(libProduit, that.libProduit) && Objects.equals(description, that.description) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(categorie, that.categorie) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit, artiste, libProduit, description, imageUrl, totalRate, categorie, date);
    }
}
