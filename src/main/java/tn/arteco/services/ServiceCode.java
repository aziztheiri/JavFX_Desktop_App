package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Code;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCode implements Iservice<Code> {

    private Connection con;
    private Statement stm;
    private PreparedStatement pst;

    public ServiceCode() {
        con = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Code code) {
        String query = "Insert into code(code,dateValide,etat,nbPoints,nonArtiste) values(?,?,?,?,?)";


        try {
            pst = con.prepareStatement(query);
            pst.setString(1, code.getCode());
            pst.setDate(2, code.getDateValide());
            pst.setString(3, code.getEtat());
            pst.setInt(4, code.getNbPoints());
            pst.setInt(5, code.getIdNonArtiste());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Code> getAll() {
        String query = "Select * from code";
        ArrayList<Code> lc = new ArrayList<>();
        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                lc.add(new Code(rs.getInt(1), rs.getString("code"), rs.getDate("dateValide"), rs.getString("etat"), rs.getInt("nbpoints"), rs.getInt("nonArtiste")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lc;
    }

    @Override
    public void update(Code code) {

        String query = "update code set code=?,dateValide=?,etat=?,nbPoints=?";
        try {
            pst = con.prepareStatement(query);
            pst.setString(1, code.getCode());
            pst.setDate(2, code.getDateValide());
            pst.setString(3, code.getEtat());
            pst.setInt(4, code.getNbPoints());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int idCode) {
        String query = "delete from code where idCode = ?";
        try {
            pst = con.prepareStatement(query);
            pst.setInt(1, idCode);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Code getById(int idCode) {
        String query = "select * from code where idCode=?";
        Code c = null;
        try {
            pst = con.prepareStatement(query);
            pst.setInt(1, idCode);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                c = new Code();
                c.setIdCode(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setDateValide(rs.getDate(3));
                c.setEtat(rs.getString(4));
                c.setNbPoints(rs.getInt(5));
                c.setIdNonArtiste(rs.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;

    }

    public int getMatValide(int idUser) {
        ServiceMateriel sm = new ServiceMateriel();
        int resultatMat = sm.getMatByUserId(idUser).size();
        int resultatCode = 0;
        String query = "Select * from code where nonArtiste=?";
        try {
            pst = con.prepareStatement(query);
            pst.setInt(1, idUser);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                resultatCode++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultatMat - resultatCode * 5;
    }

    public int verifierCode(String code, int idNonArtiste) {
        int points = 0;
        try {
            String query = "Select * from code where code=? and nonArtiste=? and etat='nonUtilise'";
            pst = con.prepareStatement(query);
            pst.setString(1, code);
            pst.setInt(2, idNonArtiste);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
                points = Integer.parseInt(rs.getString("nbPoints"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return points;
    }

    public void updateEtatCode(String code) {
        String query = "UPDATE code set etat='utilise' where code=?";

        try {
            pst = con.prepareStatement(query);
            pst.setString(1, code);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Code> getCodeByUserId(int idUser) {
        List<Code> lc = new ArrayList<>();
        String query = "Select * from code where nonArtiste=?";
        try {
            pst = con.prepareStatement(query);

            pst.setInt(1, idUser);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Code c = new Code(rs.getInt("idCode"), rs.getString("code"), rs.getDate("dateValide"), rs.getString("etat"), rs.getInt("nbPoints"), rs.getInt("nonArtiste"));
                lc.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lc;
    }

    public void ajouterPoints(int points, int userId) {
        String query = "update user set points=? where userId=?";
        try {
            pst = con.prepareStatement(query);

            pst.setInt(1, points);
            pst.setInt(2, userId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}


