package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Accomplissement;
import tn.arteco.models.Marchandise;
import tn.arteco.models.TransactionP;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class TransactionPService implements Iservice<TransactionP> {
    private Connection conx ;
    private Statement ste;
    private PreparedStatement pst;

    public TransactionPService() {
        this.conx = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(TransactionP transactionP) {

    String req="INSERT INTO `transactionp`(`idAccomplissement`, " +
            "`dateTp`, `points`, `incoming`) " +
            "VALUES (?,?,?,?)";
    try{
        pst=conx.prepareStatement(req);
        pst.setInt(1,transactionP.getAccomplissement().getIdAccomp());
        pst.setDate(2,new java.sql.Date(transactionP.getDateTp().getTime()));
        pst.setInt(3,transactionP.getPoints());
        pst.setBoolean(4,transactionP.isIncoming());
        pst.executeUpdate();

    }catch (SQLException e){
        System.out.println(e.getMessage());
    }
    }

    @Override
    public TransactionP getById(int id){
        TransactionP tp1 =new TransactionP();
        String req="SELECT * FROM `transactionp` WHERE idTp=?";
        try{
            pst=conx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {Accomplissement a=new Accomplissement();
                a.setIdAccomp(rs.getInt(2));
                tp1.setIdTp(rs.getInt(1));
                tp1.setAccomplissement(a);
                tp1.setDateTp(rs.getDate(3));
                tp1.setPoints(rs.getInt(4));
                tp1.setIncoming(rs.getBoolean(5));
                }
        }catch (SQLException e){
            System.out.println("+++"+e.getMessage()+"+++");
        }

        return tp1;
    }

    @Override
    public ArrayList<TransactionP> getAll() {
        String req="SELECT * FROM `transactionp` ";
        ArrayList<TransactionP> tl =new ArrayList<>();
        try{
            Statement ste =conx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                Accomplissement a1 = new Accomplissement();
                a1.setIdAccomp(rs.getInt(2));
                tl.add(new TransactionP(rs.getInt(1),a1,
                        new java.util.Date(rs.getDate(3).getTime()),rs.getInt(4),rs.getBoolean(5)));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tl;
    }

    @Override
    public void update(TransactionP transactionP) {
        String req="UPDATE `transactionp` SET `idAccomplissement`=?," +
                "`dateTp`=?,`points`=?," +
                "`incoming`=? WHERE idTp=?";
        try{
            Accomplissement a= new Accomplissement();
            a.setIdAccomp(transactionP.getAccomplissement().getIdAccomp());
            pst=conx.prepareStatement(req);
            pst.setInt(1,a.getIdAccomp());
            pst.setDate(2,new java.sql.Date(transactionP.getDateTp().getTime()));
            pst.setInt(3,transactionP.getPoints());
            pst.setBoolean(4,transactionP.isIncoming());
            pst.setInt(5,transactionP.getIdTp());
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
    String req="DELETE FROM `transactionp` WHERE idTp=?";
    try{
        pst=conx.prepareStatement(req);
        pst.setInt(1,id);
        pst.executeUpdate();
    }catch (SQLException e) {
        System.out.println(e.getMessage()); }
    }
}
