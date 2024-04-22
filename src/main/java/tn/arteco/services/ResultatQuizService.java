package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.*;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResultatQuizService implements Iservice<ResultatQuiz> {
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public ResultatQuizService() {
        con = MyDataBase.getInstance().getConnection();
    }

    ;

    public void add(ResultatQuiz R) {
        String requete = "INSERT INTO `resultatquiz`(`score`, `idNonArtiste`, `idQuiz`) VALUES   (?,?,?)";
        try {
            pst = con.prepareStatement(requete);
            pst.setFloat(1, R.getScore());
            pst.setInt(2, R.getIdNonArtiste());
            pst.setInt(3, R.getIdQuiz());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int RQ) {
        String requete = "DELETE FROM `resultatquiz` WHERE `idResultatQuiz`=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, RQ);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public void update(ResultatQuiz R) {
        String requete = "UPDATE `resultatquiz` SET `idResultatQuiz`=?,`score`=?,`idNonArtiste`=?,`idQuiz`=? WHERE `idResultatQuiz`=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, R.getIdResultatQuiz());
            pst.setFloat(2, R.getScore());
            pst.setInt(3, R.getIdNonArtiste());
            pst.setInt(4, R.getIdQuiz());
            pst.setInt(5, R.getIdResultatQuiz());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public ArrayList<ResultatQuiz> getAll() {
        String requete = "SELECT * FROM `resultatquiz`";
        ArrayList<ResultatQuiz> list = new ArrayList<>();
        try {
            ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new ResultatQuiz(rs.getInt(1), rs.getFloat(2), rs.getInt(3), rs.getInt(4)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public ResultatQuiz getById(int ResultatQuiz) {
        String requete = "SELECT * FROM `resultatquiz` WHERE `idResultatQuiz`=?";
        ResultatQuiz RQ = null;
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, ResultatQuiz);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RQ = (new ResultatQuiz(rs.getInt(1), rs.getFloat(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return RQ;
    }

    public List<ResultatQuiz> FilterByTitle(List<ResultatQuiz> Q, float score) {
        return Q.stream().filter(
                K -> K.getScore() == score
        ).collect(Collectors.toList());
    }

    public ArrayList<ResultatQuiz> getResultatQuizByUserId(int UserId) {
        String requete = "SELECT * FROM `resultatquiz` WHERE `idNonArtiste`=?";
        ArrayList<ResultatQuiz> list = new ArrayList<>();
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, UserId);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new ResultatQuiz(rs.getInt(1), rs.getFloat(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ResultatQuiz getResultatQuizByQuizId(int QuizId) {
        String requete = "SELECT * FROM resultatquiz WHERE `idQuiz`=?";
        ResultatQuiz RQ = null;
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, QuizId);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RQ = new ResultatQuiz(rs.getInt(1), rs.getFloat(2), rs.getInt(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return RQ;
    }

    public List<ResultatQuiz> Sort(List<ResultatQuiz> RQ) {
        return RQ.stream().sorted((R1, R2) ->
                (int) R1.getScore() - (int) R2.getScore()
        ).collect(Collectors.toList());
    }


    public float FinalTest(Set<Reponse> LR) {
        float f = 0f;
        ResultatReponseService RRS = new ResultatReponseService();
        List<ResultatReponse> LRS = RRS.getAll();
        for (ResultatReponse RR : LRS) {
            for (Reponse R : LR) {
                System.out.println(R.getIdReponse());
                if (((R.getIdReponse() == Integer.parseInt(RR.getIdReponse())) && (R.isEtat() && RR.isEtat()))) {
                    f = f + 20;
                }


            }}
        return f;
    }

    public void addPoints(int points,int userId) {
        String requete="UPDATE user SET points`=? WHERE userId`=?";
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,points);
            pst.setFloat(2,userId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
