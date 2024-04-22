package tn.arteco.models;

public class User {
    protected int userId;
    protected String username;
    protected String nom,prenom;
    protected String password;
    protected String email;
    protected Roles role;

    protected String imageUrl;

    protected boolean etat;

    public User(String username,String nom, String prenom, String password, String email, String imageUrl) {
        this.nom = nom;
        this.username=username;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;

    }

    public User(int userId,String username, String nom, String prenom, String password,String email, Roles role,String imageUrl,boolean etat) {
        this.userId = userId;
        this.username=username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.role = role;
        this.imageUrl=imageUrl;
        this.email=email;
        this.etat=etat;

    }

    public User(){}

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRole() {
        return role;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", imageUrl='" + imageUrl + '\'' +
                ", etat=" + etat +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}