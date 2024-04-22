package tn.arteco.models;

public class Materiel {
   private int idMateriel;
   private String libMateriel;
   private String description;

   private String adresse;
   private int quantite;

   private  NonArtiste nonArtiste;
   private String imageUrl;

   private int quantiteReserver;


   public Materiel(){};
   public Materiel(int idMateriel, String libMateriel, String description, String adresse, int quantite,String imageUrl,NonArtiste nonArtiste) {
      this.idMateriel = idMateriel;
      this.libMateriel = libMateriel;
      this.description = description;
      this.adresse = adresse;
      this.quantite = quantite;
      this.nonArtiste = nonArtiste;
      this.imageUrl = imageUrl;
   }

   public Materiel(int idMateriel, String libMateriel, String description, String adresse, int quantite,String imageUrl) {
      this.idMateriel = idMateriel;
      this.libMateriel = libMateriel;
      this.description = description;
      this.adresse = adresse;
      this.quantite = quantite;
      this.imageUrl = imageUrl;
   }



   public int getidMateriel() {
      return idMateriel;
   }

   public String getLibMateriel() {
      return libMateriel;
   }

   public String getDescription() {
      return description;
   }

   public String getAdresse() {
      return adresse;
   }

   public int getQuantite() {
      return quantite;
   }

   public NonArtiste getNonArtiste() {
      return nonArtiste;
   }

   public void setidMateriel(int idMateriel) {
      this.idMateriel = idMateriel;
   }

   public void setLibMateriel(String libMateriel) {
      this.libMateriel = libMateriel;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setAdresse(String adresse) {
      this.adresse = adresse;
   }

   public void setQuantite(int quantite) {
      this.quantite = quantite;
   }


   public int getQuantiteReserver() {
      return quantiteReserver;
   }

   public void setQuantiteReserver(int quantiteReserver) {
      this.quantiteReserver = quantiteReserver;
   }

   @Override
   public String toString() {
      return "Materiel{" +
              "idMateriel=" + idMateriel +
              ", libMateriel='" + libMateriel + '\'' +
              ", description='" + description + '\'' +
              ", adresse='" + adresse + '\'' +
              ", quantite=" + quantite +
              ", NonArtiste=" + nonArtiste.getNom() +
              '}';
   }
   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public void setNonArtiste(NonArtiste NonArtiste) {
      this.nonArtiste = NonArtiste;
   }
}
