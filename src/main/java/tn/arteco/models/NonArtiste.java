package tn.arteco.models;

import tn.arteco.services.AccomplissementService;
import tn.arteco.services.MarchandiseFactureService;
import tn.arteco.services.ResultatQuizService;
import tn.arteco.services.ServiceMateriel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NonArtiste extends User{
    protected int points;
    protected List<Materiel> listeMateriel;
    protected List<ResultatQuiz> listeResultat;
    protected Accomplissement accomplissment;
    protected List<MarchandiseFacture> listeFacture;
    protected List<Participation> listeParticipation;

    public NonArtiste(int userId, String username, String nom, String prenom, String password, String email, Roles role, String imageUrl, boolean etat, List<Participation> listeParticipation) {
        super(userId, username, nom, prenom, password, email, role, imageUrl, etat);
        this.listeParticipation = listeParticipation;
    }

    public  NonArtiste(){
        listeMateriel=new ArrayList<>();
        listeResultat=new ArrayList<>();
        listeFacture=new ArrayList<>();
        listeParticipation=new ArrayList<>();
    }



    public NonArtiste(int userId, String username,String nom, String prenom, String password, String email, Roles role, String imageUrl, boolean etat, int points, Accomplissement accomplissment) {
        super(userId, username,nom, prenom, password, email, role, imageUrl, etat);
        listeMateriel=new ArrayList<>();
        listeResultat=new ArrayList<>();
        listeFacture=new ArrayList<>();
        listeParticipation=new ArrayList<>();
        this.points = points;
        this.accomplissment = accomplissment;
    }
    //Pour la creation des nouveaux non artistes
    public NonArtiste(String username,String nom, String prenom, String password, String email, String imageUrl) {
        super(username,nom, prenom, password, email, imageUrl);
        this.role=Roles.NONARTISTE;
        listeMateriel=new ArrayList<>();
        listeResultat=new ArrayList<>();
        listeFacture=new ArrayList<>();
        listeParticipation=new ArrayList<>();
    }
    //TODO : complete filling the other lists
    public NonArtiste(int userId, String username,String nom, String prenom, String password, String email, Roles role, String imageUrl, boolean etat, int points) {
        super(userId, username,nom, prenom, password, email, role, imageUrl, etat);
        this.role=Roles.NONARTISTE;
        this.points = points;
        MarchandiseFactureService marchandiseFactureService=new MarchandiseFactureService();
        ResultatQuizService resultatQuizService=new ResultatQuizService();
        AccomplissementService as=new AccomplissementService();
        ServiceMateriel serviceMateriel=new ServiceMateriel();
        listeMateriel=serviceMateriel.getMatByUserId(userId);
        listeResultat=resultatQuizService.getResultatQuizByUserId(userId);
        listeFacture=marchandiseFactureService.getByNonArtistId(userId);
        this.accomplissment=as.getByNonArtistId(userId);
        listeParticipation=new ArrayList<>();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Materiel> getListeMateriel() {
        return listeMateriel;
    }

    public void setListeMateriel(List<Materiel> listeMateriel) {
        this.listeMateriel = listeMateriel;
    }

    public List<ResultatQuiz> getListeResultat() {
        return listeResultat;
    }

    public void setListeResultat(List<ResultatQuiz> listeResultat) {
        this.listeResultat = listeResultat;
    }

    public Accomplissement getAccomplissment() {
        return accomplissment;
    }

    public void setAccomplissment(Accomplissement accomplissment) {
        this.accomplissment = accomplissment;
    }

    public List<MarchandiseFacture> getListeFacture() {
        return listeFacture;
    }

    public void setListeFacture(List<MarchandiseFacture> listeFacture) {
        this.listeFacture = listeFacture;
    }

    public List<Participation> getListeParticipation() {
        return listeParticipation;
    }

    public void setListeParticipation(List<Participation> listeParticipation) {
        this.listeParticipation = listeParticipation;
    }

    @Override
    public String toString() {

        return super.toString()+"NonArtiste{" +
                "points=" + points +
                ", listeMateriel=" + listeMateriel +
                ", listeResultat=" + listeResultat +
                ", accomplissment=" + accomplissment +
                ", listeFacture=" + listeFacture +
                ", listeParticipation=" + listeParticipation +
                '}';
    }
}
