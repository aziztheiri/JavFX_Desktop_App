package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Accomplissement;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class AccomplissementService implements Iservice<Accomplissement> {
    private Connection conx ;
    private Statement ste;
    private PreparedStatement pst;


    public AccomplissementService() {
        this.conx = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Accomplissement accomplissement) {
    String req="INSERT INTO `accomplissement`(`titre`, `userId`) " +
            "VALUES (?,?)";
        try{
         pst=conx.prepareStatement(req);
         pst.setString(1,accomplissement.getTitre());
         pst.setInt(2,accomplissement.getNonArtiste().getUserId());
         pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
    }

    }

    @Override
    public ArrayList<Accomplissement> getAll() {
        String req="SELECT * FROM `accomplissement`";
        ArrayList<Accomplissement>al=new ArrayList<>();
        try{
            ste= conx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
               NonArtiste a =new NonArtiste();
               a.setUserId(rs.getInt(3));
                al.add(new Accomplissement(rs.getInt(1),a
                        ,rs.getString(2)));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return al;
    }

    public Accomplissement getByNonArtistId(int id){
        Accomplissement a1 =new Accomplissement();
        String req="SELECT * FROM `accomplissement` where userId=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                System.out.println("hi from inside ");
                NonArtiste na =new NonArtiste();
                na.setUserId(rs.getInt(3));
                a1.setIdAccomp(rs.getInt(1));
                a1.setTitre(rs.getString(2));
                a1.setNonArtiste(na);
            }
        }catch (SQLException e){
            System.out.println("+++"+e.getMessage()+"+++");
        }

        return a1;
    }

    public Accomplissement getById(int id){
        Accomplissement a1 =new Accomplissement();
        String req="SELECT * FROM `accomplissement` where idAccomp=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            { NonArtiste a =new NonArtiste();
                a.setUserId(rs.getInt(3));
                a1.setIdAccomp(rs.getInt(1));
                a1.setTitre(rs.getString(2));
                a1.setNonArtiste(a);
                }
        }catch (SQLException e){
            System.out.println("+++"+e.getMessage()+"+++");
        }

        return a1;
    }


    @Override
    public void update(Accomplissement accomplissement) {
        String req ="UPDATE `accomplissement` SET " +
                "`titre`=? WHERE idAccomp=?";
        try{
            System.out.println("update te5dem ");
            pst=conx.prepareStatement(req);
            pst.setString(1,accomplissement.getTitre());
            pst.setInt(2,accomplissement.getIdAccomp());
            pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        String req="DELETE FROM `accomplissement` WHERE idAccomp=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setInt(1,id);
            pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int  getUserIdByAccomplisementId(int id){
        String req="SELECT userId FROM `accomplissement` WHERE idAccomp=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }


}
