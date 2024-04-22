package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.*;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;


public class MarchandiseFactureService  implements Iservice<MarchandiseFacture> {
    private Connection conx;
    private Statement ste;
    private PreparedStatement pst;
    TransactionPService tPs=new TransactionPService();
    MarchandiseService mS=new MarchandiseService();
    UserService uS=new UserService();
    AccomplissementService aS=new AccomplissementService();
    public MarchandiseFactureService() {
        this.conx = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(MarchandiseFacture marchandiseFacture) {
        Accomplissement accomplissement=new Accomplissement();
        Marchandise marchandise=new Marchandise();
        User user=new User();
    String req="INSERT INTO `marchendisefacture`( `dateF`, `quantite`, `netPayer`, `userId`, `marchendiseId`) " +
            "VALUES (?,?,?,?,?)";
        try{

            user=marchandiseFacture.getNonArtiste();
            accomplissement=aS.getByNonArtistId(user.getUserId());
            uS.updateUserPoints(user.getUserId(),((NonArtiste)user).getPoints()-marchandiseFacture.getNetPayer());
            marchandise=marchandiseFacture.getMarchandise();
            marchandise.setQuantiteDispo(marchandiseFacture.getMarchandise().getQuantiteDispo()-marchandiseFacture.getQuantite());
            mS.update(marchandise);
            tPs.add(new TransactionP(0,accomplissement,new java.util.Date(),marchandiseFacture.getNetPayer(),false));


            pst=conx.prepareStatement(req);
            pst.setDate(1,new java.sql.Date(marchandiseFacture.getDateF().getTime()));
            pst.setInt(2,marchandiseFacture.getQuantite());
            pst.setInt(3,marchandiseFacture.getNetPayer());
            pst.setInt(4,marchandiseFacture.getNonArtiste().getUserId());
            pst.setInt(5,marchandiseFacture.getMarchandise().getIdMerch());
            pst.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public  MarchandiseFacture getById(int id ){
        MarchandiseFacture mf =new MarchandiseFacture();
        String req="SELECT * FROM `marchendisefacture` WHERE idFacture=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {   NonArtiste na =new NonArtiste();
                  na.setUserId(rs.getInt(5));
                  Marchandise m=new Marchandise();
                  m.setIdMerch(rs.getInt(6));
                mf.setIdFacture(rs.getInt(1));
                mf.setMarchandise(m);
                mf.setNonArtiste(na);
                mf.setQuantite(rs.getInt(3));
                mf.setDateF(rs.getDate(2));
                mf.setNetPayer(rs.getInt(4));
            }
        }catch (SQLException e){
            System.out.println("+++"+e.getMessage()+"+++");
        }

        return mf;
    }


    public ArrayList<MarchandiseFacture> getByNonArtistId(int id) {
        String req ="SELECT * FROM `marchendisefacture`  where userId=? ";
        ArrayList<MarchandiseFacture>lmf =new ArrayList<MarchandiseFacture>();
        try{
            pst= conx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            Marchandise m =new Marchandise();
            NonArtiste na=new NonArtiste();
            while(rs.next()) {
                m.setIdMerch(rs.getInt(6));
                na.setUserId(rs.getInt(5));
                lmf.add(new MarchandiseFacture(rs.getInt(1), rs.getDate(2), m
                        , na, rs.getInt(3), rs.getInt(4)));
                System.out.println("----");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lmf ;
    }
    @Override
    public ArrayList<MarchandiseFacture> getAll() {
        String req ="SELECT * FROM `marchendisefacture`";
        ArrayList<MarchandiseFacture>lmf =new ArrayList<MarchandiseFacture>();
        try{
            ste= conx.createStatement();
            ResultSet rs=ste.executeQuery(req);


            while(rs.next()) {
                NonArtiste na=new NonArtiste();
                Marchandise m =new Marchandise();
                System.out.println(rs.getInt(6));
                m.setIdMerch(rs.getInt(6));
                na.setUserId(rs.getInt(5));
                lmf.add(new MarchandiseFacture(rs.getInt(1), new java.util.Date(rs.getDate(2).getTime()), m
                        , na, rs.getInt(3), rs.getInt(4)));
                System.out.println("----");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lmf ;
    }



    @Override
    public void update(MarchandiseFacture marchandiseFacture) {
        String req="UPDATE `marchendisefacture` SET `dateF`=?,`quantite`=?," +
                "`netPayer`=?,`userId`=?,`marchendiseId`=?" +
                " WHERE idFacture=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setDate(1,new java.sql.Date(marchandiseFacture.getDateF().getTime()));
            pst.setInt(2,marchandiseFacture.getQuantite());
            pst.setInt(3,marchandiseFacture.getNetPayer());
            pst.setInt(4,marchandiseFacture.getNonArtiste().getUserId());
            pst.setInt(5,marchandiseFacture.getMarchandise().getIdMerch());
            pst.setInt(6,marchandiseFacture.getIdFacture());
            pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
    String req="DELETE FROM `marchendisefacture` WHERE idFacture=?";
    try{
        pst=conx.prepareStatement(req);
        pst.setInt(1,id);
        pst.executeUpdate();
    }catch (SQLException e){
        System.out.println(e.getMessage());
    }
    }
    public int getMostPopular(){
        int i=0;
        String req="SELECT marchendiseId, SUM(quantite) AS totalQuantite FROM marchendisefacture GROUP BY marchendiseId ORDER BY totalQuantite DESC LIMIT 1;";
        try{
            ste= conx.createStatement();
            ResultSet rs=ste.executeQuery(req);
            if (rs.next()) {
                 i =rs.getInt(1);

            } else {
                System.out.println("No data found.");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return i;
    }
}
