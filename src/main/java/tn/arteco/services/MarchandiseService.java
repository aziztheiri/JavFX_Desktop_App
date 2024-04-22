package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Marchandise;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class MarchandiseService implements Iservice<Marchandise> {
    private Connection conx ;
    private PreparedStatement pst ;
    private Statement ste;
    public MarchandiseService(){
        this.conx= MyDataBase.getInstance().getConnection();

    }
    @Override
    public void add(Marchandise marchandise) {
        String req="INSERT INTO `marchendise`(`titreObtenu`, `libelle`, `description`," +
                " `dateSortie`, `dateLimit`, `quantiteDispo`, `prix`, `imageUrl`) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try {
            pst=conx.prepareStatement(req);
            pst.setString(1,marchandise.getTitreObtenu());
            pst.setString(2,marchandise.getLibelle());
            pst.setString(3,marchandise.getDescription());
            pst.setDate(4,new java.sql.Date(marchandise.getDateSortie().getTime()));
            if(marchandise.getDateLimit().equals(new Date("09/11/2001")))
            pst.setDate(5,null);
            else
            pst.setDate(5,new java.sql.Date(marchandise.getDateLimit().getTime()));
            pst.setInt(6,marchandise.getQuantiteDispo());
            pst.setInt(7,marchandise.getPrix());
            pst.setString(8,marchandise.getImageUrl());
            pst.executeUpdate();
        }catch (SQLException e){
        System.out.println(e.getMessage());
        }

    }
    public ArrayList<Marchandise> getHorStock(){
        String req ="SELECT * FROM `marchendise` where quantiteDispo <=0 ";
        ArrayList<Marchandise>lm =new ArrayList<Marchandise>();
        try{
            ste= conx.createStatement();
            ResultSet rs=ste.executeQuery(req);
            while(rs.next())
                lm.add(new Marchandise(rs.getInt(1),rs.getString(3),rs.getDate(5)
                        ,rs.getDate(6),rs.getInt(7),rs.getString(4)
                        ,rs.getInt(8),rs.getString(2),rs.getString(9)));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lm ;
    }
    public HashMap<Marchandise,Long> getNonActiveLimitedOfer(){
        String req="SELECT * FROM `marchendise` WHERE sysdate() " +
                " not BETWEEN dateSortie AND dateLimit And dateLimit IS Not NULL ";
        HashMap<Marchandise,Long> h =new HashMap<>();
        try{
            ste= conx.createStatement();
            ResultSet rs=ste.executeQuery(req);

            while(rs.next()){
                Marchandise m =new Marchandise(rs.getInt(1), rs.getString(3),
                        rs.getDate(5),rs.getDate(6),rs.getInt(7),rs.getString(4),
                        rs.getInt(8),rs.getString(2),rs.getString(9));
                Long l=ChronoUnit.DAYS.between(rs.getDate(5).toLocalDate(),rs.getDate(6).toLocalDate());
                h.put(m,l);
            }

        }catch (SQLException e){
            System.out.println("+++"+e.getMessage()+"+++");
        }
        return h;
    }
    public HashMap<Marchandise,Long> getLimitedOfer(){
    String req="SELECT * FROM `marchendise` WHERE sysdate() " +
            "BETWEEN dateSortie AND dateLimit";
    HashMap<Marchandise,Long> h =new HashMap<>();
    try{
        ste= conx.createStatement();
        ResultSet rs=ste.executeQuery(req);

        while(rs.next()){
            Marchandise m =new Marchandise(rs.getInt(1), rs.getString(3),
                    rs.getDate(5),rs.getDate(6),rs.getInt(7),rs.getString(4),
                    rs.getInt(8),rs.getString(2),rs.getString(9));
            Long l=ChronoUnit.DAYS.between(rs.getDate(5).toLocalDate(),rs.getDate(6).toLocalDate());
                   h.put(m,l);
        }

    }catch (SQLException e){
        System.out.println("+++"+e.getMessage()+"+++");
    }
        return h;
    }
    @Override
    public ArrayList<Marchandise> getAll() {
        String req ="SELECT * FROM `marchendise`";
        ArrayList<Marchandise>lm =new ArrayList<Marchandise>();
        try{
            ste= conx.createStatement();
            ResultSet rs=ste.executeQuery(req);
            while(rs.next())
                lm.add(new Marchandise(rs.getInt(1),rs.getString(3),rs.getDate(5)
                ,rs.getDate(6),rs.getInt(7),rs.getString(4)
                ,rs.getInt(8),rs.getString(2),rs.getString(9)));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lm ;
    }
    public Marchandise getById(int id){
        Marchandise m1=new Marchandise();
        String req="SELECT * FROM `marchendise` where idMerch=?";
                try{
                    pst=conx.prepareStatement(req);
                    pst.setInt(1,id);
                    ResultSet rs=pst.executeQuery();
                    if(rs.next())
                        {
                        m1.setIdMerch(rs.getInt(1));
                        m1.setLibelle(rs.getString(3));
                        m1.setDateSortie(rs.getDate(5));
                        m1.setDateLimit(rs.getDate(6));
                        m1.setQuantiteDispo(rs.getInt(7));
                        m1.setDescription(rs.getString(4));
                        m1.setPrix(rs.getInt(8));
                        m1.setTitreObtenu(rs.getString(2));
                        m1.setImageUrl(rs.getString(9));}
                }catch (SQLException e){
                    System.out.println("+++"+e.getMessage()+"+++");
                }

        return m1;
    }

    @Override
    public void update(Marchandise marchandise) {
        System.out.println("hi from service im update id="+marchandise.getIdMerch());

        String req="UPDATE `marchendise` SET" +
                "`titreObtenu`=?,`libelle`=?," +
                "`description`=?,`dateSortie`=?," +
                "`dateLimit`=?,`quantiteDispo`=?," +
                "`prix`=?,`imageUrl`=? WHERE idMerch=?";
        try {
            pst=conx.prepareStatement(req);
            pst.setString(1,marchandise.getTitreObtenu());
            pst.setString(2,marchandise.getLibelle());
            pst.setString(3,marchandise.getDescription());
            pst.setDate(4,new java.sql.Date(marchandise.getDateSortie().getTime()));
            pst.setDate(5,new java.sql.Date(marchandise.getDateLimit().getTime()));
            pst.setInt(6,marchandise.getQuantiteDispo());
            pst.setInt(7,marchandise.getPrix());
            pst.setString(8,marchandise.getImageUrl());
            pst.setInt(9,marchandise.getIdMerch());
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
     String req="DELETE FROM `marchendise` WHERE idMerch=?";
     try{
         pst=conx.prepareStatement(req);
         pst.setInt(1,id);
         pst.executeUpdate();
     }catch(SQLException e){
         System.out.println(e.getMessage());
        }
    }

    public static boolean containAnyWord(String s1 ,String s2){
        String[] st1 = s1.split("\\s+");
        String[] st2=s2.split("\\s+");
        for (String word:st1){
            for (String second :st2)
            { if (second.toUpperCase().contains(word.toUpperCase()))
                return true;
            }
        }
        return false;
    }

    public ArrayList<Marchandise> sortBylibelle(ArrayList<Marchandise> l){
        return new ArrayList<>(l.stream().collect(Collectors.toCollection(()->new TreeSet<Marchandise>(((a,b)->(a.getLibelle().compareTo(b.getLibelle())))))));
    }
    public ArrayList<Marchandise> sortByPrix(ArrayList<Marchandise> l){
        return new ArrayList<>(l.stream().collect(Collectors.toCollection(()->new TreeSet<Marchandise>(((a,b)->(a.getPrix()-b.getPrix()))))));
    }

}
