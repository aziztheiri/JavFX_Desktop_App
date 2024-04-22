package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Question;
import tn.arteco.models.Reponse;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReponseService implements Iservice<Reponse> {
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public ReponseService(){
        con= MyDataBase.getInstance().getConnection();
    };
    public void add(Reponse R){
        String requete="INSERT INTO `reponse`(`numQuestion`, `reponse`, `etat`) VALUES  (?,?,?)";
        try {
            pst=con.prepareStatement(requete);
            pst.setString(2,R.getReponse());
            pst.setInt(1,R.getIdQuestion());
            pst.setBoolean(3,R.isEtat());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void delete(int R){
        String requete="DELETE FROM `reponse` WHERE `idReponse`=?";
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,R);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public void update(Reponse R){
        String requete="UPDATE `reponse` SET `numQuestion`=?,`reponse`=?,`etat`=? WHERE `idReponse`=?";
        try {
            pst=con.prepareStatement(requete);
            pst.setString(2,R.getReponse());
            pst.setInt(1,R.getIdQuestion());
            pst.setBoolean(3,R.isEtat());
            pst.setInt(4,R.getIdReponse());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public ArrayList<Reponse> getAll(){
        String requete="SELECT * FROM `reponse`";
        ArrayList<Reponse> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                list.add(new Reponse(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getBoolean(4)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public Reponse getById(int reponseId){
        String requete="SELECT * FROM `reponse` WHERE `idReponse`=?";
        Reponse R = null;
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,reponseId);
            ste=con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                R = (new Reponse(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getBoolean(4)));
            }  } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return R;
    }
    public Reponse getByReponse(String reponse){
        String requete="SELECT * FROM `reponse` WHERE `reponse`=?";
        Reponse R = null;
        try {
            pst=con.prepareStatement(requete);
            pst.setString(1,reponse);
            ste=con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                R = (new Reponse(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getBoolean(4)));
            }  } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return R;
    }
    public ArrayList<Reponse> getReponseByIdQuestion(int questionId){
        String requete="SELECT * FROM `reponse` WHERE `numQuestion`=?";
        ArrayList<Reponse> list = new ArrayList<>();
        try {
            pst=con.prepareStatement(requete);
            pst.setInt(1,questionId);
            ste=con.createStatement();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Reponse(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getBoolean(4)));
            }  } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Reponse> Sort(List<Reponse> R){
        return R.stream().sorted((R1,R2)->
                R1.getReponse().compareTo(R2.getReponse())
        ).collect(Collectors.toList());
    }
    public ArrayList<Reponse> getAllCorrectReponse(){
        String requete="SELECT * FROM `reponse` WHERE `etat`=?";
        ArrayList<Reponse> list = new ArrayList<>();
        try {
            pst=con.prepareStatement(requete);
            pst.setBoolean(1,true);
            ste=con.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Reponse(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getBoolean(4)));
            }  } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
