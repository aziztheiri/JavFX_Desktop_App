package tn.arteco.models;

public class ResultatQuiz {

    private int idResultatQuiz;
    private float score;
    private int idNonArtiste;
    private int idQuiz;

    public ResultatQuiz(int idResultatQuiz, float score, int idNonArtiste,int idQuiz) {
        this.idResultatQuiz = idResultatQuiz;
        this.score = score;
        this.idQuiz=idQuiz;
        this.idNonArtiste = idNonArtiste;
    }

    public ResultatQuiz() {
    }

    public int getIdResultatQuiz() {
        return idResultatQuiz;
    }

    public void setIdResultatQuiz(int idResultatQuiz) {
        this.idResultatQuiz = idResultatQuiz;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getIdNonArtiste() {
        return idNonArtiste;
    }

    public void setIdNonArtiste(int idNonArtiste) {
        this.idNonArtiste = idNonArtiste;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    @Override
    public String toString() {
        return "ResultatQuiz{" +
                "idResultatQuiz=" + idResultatQuiz +
                ", score=" + score +
                ", idNonArtiste=" + idNonArtiste +
                ", idQuiz=" + idQuiz +
                '}';
    }
}
