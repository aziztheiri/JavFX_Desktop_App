package tn.arteco.models;

import java.util.HashSet;
import java.util.Set;

public class Quiz {

    private int idQuiz;

    private String titreQuiz;
    private int pointQuiz;
    public Quiz(){};

    public Quiz(int idQuiz, String titreQuiz, int pointQuiz) {
        this.idQuiz = idQuiz;
        this.titreQuiz = titreQuiz;
        this.pointQuiz = pointQuiz;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }


    public String getTitreQuiz() {
        return titreQuiz;
    }

    public void setTitreQuiz(String titreQuiz) {
        this.titreQuiz = titreQuiz;
    }

    public int getPointQuiz() {
        return pointQuiz;
    }

    public void setPointQuiz(int pointQuiz) {
        this.pointQuiz = pointQuiz;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "idQuiz=" + idQuiz +
                ", titreQuiz='" + titreQuiz + '\'' +
                ", pointQuiz=" + pointQuiz +
                '}';
    }
}
