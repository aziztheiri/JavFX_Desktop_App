package tn.arteco.models;

import tn.arteco.services.ServicesProduitsFinis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Artiste extends NonArtiste {
    private List<ProduitFini> listeProduitFini;
    public Artiste(){
        super();
        listeProduitFini=new ArrayList<>();
    }

    public Artiste(int userId,String username ,String nom, String prenom, String password, String email, Roles role, String imageUrl, boolean etat, int points, Accomplissement accomplissment) {
        super(userId, username,nom, prenom, password, email, role, imageUrl, etat, points, accomplissment);
        listeProduitFini=new ArrayList<>();
    }
    //Pour la creation des neaveaux artistes
    public Artiste(String username,String nom, String prenom, String password, String email, String imageUrl) {
        super(username,nom, prenom, password, email, imageUrl);
        this.role=Roles.ARTISTE;
        listeProduitFini=new ArrayList<>();
    }

    public Artiste(int userId, String username,String nom, String prenom, String password, String email, Roles role, String imageUrl, boolean etat, int points) {
        super(userId,username ,nom, prenom, password, email, role, imageUrl, etat, points);
        this.role=Roles.ARTISTE;
        ServicesProduitsFinis servicesProduitsFinis =new ServicesProduitsFinis();
        listeProduitFini=servicesProduitsFinis.getProduitByUserId(userId);
    }

    public List<ProduitFini> getListeProduitFini() {
        return listeProduitFini;
    }

    public void setListeProduitFini(List<ProduitFini> listeProduitFini) {
        this.listeProduitFini = listeProduitFini;
    }

    @Override
    public String toString() {
        return "Artiste{" +
                "points=" + points +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
