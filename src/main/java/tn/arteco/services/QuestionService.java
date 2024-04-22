package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Question;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionService implements Iservice<Question> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public QuestionService(){
        con= MyDataBase.getInstance().getConnection();
    };
    public void add(Question Q){
        String requete="INSERT INTO `question`(`idQuestion`,`question`, `numQuiz`) VALUES (?,?,?)";
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,Q.getIdQuestion());
            pst.setString(2,Q.getQuestion());
            pst.setInt(3,Q.getIdQuiz());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void delete(int Q){
        String requete="DELETE FROM `question` WHERE `idQuestion`=?";
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,Q);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public void update(Question Q){
        String requete="UPDATE `question` SET `idQuestion`=?,`question`=?,`numQuiz`=? WHERE `idQuestion`=?";
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,Q.getIdQuestion());
            pst.setString(2,Q.getQuestion());
            pst.setInt(3,Q.getIdQuiz());
            pst.setInt(4,Q.getIdQuestion());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public ArrayList<Question> getAll(){
        String requete="SELECT * FROM `question`";
        ArrayList<Question> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                list.add(new Question(rs.getInt(1),rs.getString(2),rs.getInt(3)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public List<Question> Sort(List<Question> Q){
        return Q.stream().sorted((Q1,Q2)->
            Q1.getIdQuestion() - Q2.getIdQuestion()
        ).collect(Collectors.toList());
    }
    public Question getById(int questionId) {
        String requete = "SELECT * FROM `question` WHERE `idQuestion`=?";
        Question Q = null;
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, questionId);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Q = (new Question(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Q;
    }
    /*public Question getByQuestion(String question) {
        String requete = "SELECT * FROM `question` WHERE `question`=?";
        Question Q = null;
        try {
            pst = con.prepareStatement(requete);
            pst.setString(1, question);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Q = (new Question(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Q;
    }*/

    public List<Question> getQuestionByQuizId(int quizId) {
        String requete = "SELECT * FROM `question` WHERE `numQuiz`=?";
        List<Question> list = new ArrayList<>();
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, quizId);
            ste = con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Question(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getLastQuestionId() {
        String requete = "SELECT max(idQuestion) FROM `question`";
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

