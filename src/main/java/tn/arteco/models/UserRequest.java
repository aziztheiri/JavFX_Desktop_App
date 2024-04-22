package tn.arteco.models;

import java.sql.Date;
import java.time.LocalDate;

public class UserRequest {
    private int idRequest;
    private String typeRequest;
    private String etat;
    private Date dateRequest;
    private User user;

    public UserRequest(int idRequest, String typeRequest, String etat, Date dateRequest, User user) {
        this.idRequest = idRequest;
        this.typeRequest = typeRequest;
        this.etat = etat;
        this.dateRequest = dateRequest;
        this.user = user;
    }

    public UserRequest(int idRequest, String typeRequest, String etat, User user) {
        this.idRequest = idRequest;
        this.typeRequest = typeRequest;
        this.etat = etat;
        this.user = user;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "idRequest=" + idRequest +
                ", typeRequest='" + typeRequest + '\'' +
                ", etat='" + etat + '\'' +
                ", dateRequest=" + dateRequest +
                ", user=" + user +
                '}';
    }
}
