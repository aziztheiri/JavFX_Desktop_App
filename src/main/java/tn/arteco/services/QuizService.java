package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Question;
import tn.arteco.models.Quiz;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuizService implements Iservice<Quiz> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public QuizService() {
        con = MyDataBase.getInstance().getConnection();
    }

    ;

    public void add(Quiz Q) {
        String requete = "INSERT INTO `quiz`(`idQuiz`,`titreQuiz`, `pointQuiz`) VALUES (?,?,?)";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, Q.getIdQuiz());
            pst.setString(2, Q.getTitreQuiz());
            pst.setInt(3, Q.getPointQuiz());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int Q) {
        String requete = "DELETE FROM `quiz` WHERE `idQuiz`=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, Q);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public void update(Quiz Q) {
        String requete = "UPDATE `quiz` SET `idQuiz`=?,`titreQuiz`=?,`pointQuiz`=? WHERE `idQuiz`=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, Q.getIdQuiz());
            pst.setString(2, Q.getTitreQuiz());
            pst.setInt(3, Q.getPointQuiz());
            pst.setInt(4, Q.getIdQuiz());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public ArrayList<Quiz> getAll() {
        String requete = "SELECT * FROM `quiz`";
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Quiz(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Quiz> Sort(List<Quiz> Q) {
        return Q.stream().sorted((Q1, Q2) ->
                Q1.getPointQuiz() - Q2.getPointQuiz()
        ).collect(Collectors.toList());
    }

    public List<Quiz> FilterByTitle(List<Quiz> Q, String Title) {
        return Q.stream().filter(
                K -> K.getTitreQuiz().contains(Title)
        ).collect(Collectors.toList());
    }

    public Quiz getById(int quizId) {
        String requete = "SELECT * FROM `quiz` WHERE `idQuiz`=?";
        Quiz Q = null;
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, quizId);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Q = (new Quiz(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Q;
    }

    public int getPoints(int questionsCount) {
        return questionsCount * 20;
    }

    public Quiz getQuizByTitle(String title) {
        String requete = "SELECT * FROM `quiz` WHERE `titreQuiz`=?";
        Quiz Q = null;
        try {
            pst = con.prepareStatement(requete);
            pst.setString(1, title);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Q = (new Quiz(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Q;
    }

    public int getLastId() {
        String requete = "SELECT max(idQuiz) FROM `quiz`";
        int id=0;
        try {
            pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id=rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}

