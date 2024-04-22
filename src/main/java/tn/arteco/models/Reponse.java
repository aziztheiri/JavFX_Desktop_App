package tn.arteco.models;

import java.util.Objects;

public class Reponse {

    private int idReponse;
    private int idQuestion;
    private String reponse;

    private boolean etat;

    public Reponse(){}
    public Reponse(int idReponse, int idquestion, String reponse, boolean etat) {
        this.idReponse = idReponse;
        this.idQuestion = idquestion;
        this.reponse = reponse;
        this.etat=etat;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "idReponse=" + idReponse +
                ", idQuestion=" + idQuestion +
                ", reponse='" + reponse + '\'' +
                ", etat=" + etat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reponse reponse1)) return false;
        return idReponse == reponse1.idReponse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReponse);
    }
}
