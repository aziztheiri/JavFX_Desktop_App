package tn.arteco.models;
//modification n
public class Cours {

    private int idCours;
    private String libelleCours;

    private String description;
    private String imageUrl;
    private String contenuCours;

    public Cours(){};
    public Cours(int idCours, String libelleCours, String description, String imageUrl, String contenuCours) {
        this.idCours = idCours;
        this.libelleCours = libelleCours;
        this.description = description;
        this.imageUrl = imageUrl;
        this.contenuCours = contenuCours;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getLibelleCours() {
        return libelleCours;
    }

    public void setLibelleCours(String libelleCours) {
        this.libelleCours = libelleCours;
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

    public String getContenuCours() {
        return contenuCours;
    }

    public void setContenuCours(String contenuCours) {
        this.contenuCours = contenuCours;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "idCours=" + idCours +
                ", libelleCours='" + libelleCours + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", contenuCours='" + contenuCours + '\'' +
                '}';
    }
}