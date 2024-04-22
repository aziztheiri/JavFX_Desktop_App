package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Materiel;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.Quiz;
import tn.arteco.models.Roles;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceMateriel implements Iservice<Materiel> {

    private Connection conn;
    private Statement stm;
    private PreparedStatement pst;

    public ServiceMateriel(){conn= MyDataBase.getInstance().getConnection();}

    @Override
    public void add(Materiel materiel) {
        String query="INSERT INTO materiel(libMateriel,description,adresse,quantite,imageUrl,nonArtiste) values(?,?,?,?,?,?)";

        try {
            pst=conn.prepareStatement(query);
            pst.setString(1,materiel.getLibMateriel());
            pst.setString(2,materiel.getDescription());
            pst.setString(3,materiel.getAdresse());
            pst.setInt(4,materiel.getQuantite());
            pst.setString(5,materiel.getImageUrl());
            pst.setInt(6,materiel.getNonArtiste().getUserId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Materiel> getAll() {

        ArrayList<Materiel> lm = new ArrayList<>();

        try {
            String query = "SELECT * from materiel m INNER JOIN user u on m.nonArtiste=u.userId";
            stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                NonArtiste na=new NonArtiste(res.getInt(1), res.getString("username"), res.getString("nom"),res.getString("prenom"),res.getString("password"),res.getString("email"), Roles.NONARTISTE,res.getString("imageUrl"),res.getBoolean("etat"),res.getInt("points"));
                lm.add(new Materiel(res.getInt(1), res.getString("libMateriel"), res.getString("description"), res.getString("adresse"), res.getInt("quantite"), res.getString("imageUrl"),na));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lm;
    }
    //NEW
    public Map<String, Integer> getCountByAddress() {
        Map<String, Integer> countByAddress = new HashMap<>();

        try {
            String query = "SELECT adresse, COUNT(*) AS nombre_materiel " +
                    "FROM materiel " +
                    "GROUP BY adresse";
            stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                String adresse = res.getString("adresse");
                int nombreMateriel = res.getInt("nombre_materiel");
                countByAddress.put(adresse, nombreMateriel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return countByAddress;
    }
    //NEW
    public HashMap<String, Integer> getCountByArtist() {
        HashMap<String, Integer> countByArtist = new HashMap<>();

        try {
            String query = "SELECT u.username AS artiste, COUNT(*) AS nombre_materiel " +
                    "FROM materiel m " +
                    "INNER JOIN user u ON m.nonArtiste = u.userId " +
                    "GROUP BY u.username";
            stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                String artiste = res.getString("artiste");
                int nombreMateriel = res.getInt("nombre_materiel");
                countByArtist.put(artiste, nombreMateriel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return countByArtist;
    }
    @Override
    public void update(Materiel materiel) {
        String query="update materiel set libMateriel=?, description=?,adresse=?,quantite=?,imageUrl=? where idMateriel=?";
        try {
            pst=conn.prepareStatement(query);
            pst.setString(1, materiel.getLibMateriel());
            pst.setString(2,materiel.getDescription());
            pst.setString(3,materiel.getAdresse());
            pst.setInt(4,materiel.getQuantite());
            pst.setString(5,materiel.getImageUrl());
            pst.setInt(6,materiel.getidMateriel());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int idMat) {
        String query="delete from materiel where idMateriel=?";
        try {
            pst=conn.prepareStatement(query);
            pst.setInt(1,idMat);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Materiel getById(int idMat) {

        Materiel m =null;
        String query="Select * from materiel m INNER JOIN user u ON m.nonArtiste=u.userId and m.idMateriel=?";
        try
        {
            pst=conn.prepareStatement(query);
            pst.setInt(1,idMat);
            ResultSet res=pst.executeQuery();
            while (res.next())
            {
                NonArtiste na=new NonArtiste(res.getInt(1), res.getString("username"), res.getString("nom"),res.getString("prenom"),res.getString("password"),res.getString("email"), Roles.NONARTISTE,res.getString("imageUrl"),res.getBoolean("etat"),res.getInt("points"));
                 m=new Materiel(res.getInt(1), res.getString("libMateriel"), res.getString("description"), res.getString("adresse"), res.getInt("quantite"), res.getString("imageUrl"),na);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return m;
    }

    public List<Materiel> getMatByUserId(int id)
    {
        String query="Select * from materiel m,user u where m.nonArtiste =u.userId and nonArtiste= ?";
        List<Materiel> lm=new ArrayList<>();
        try {
            pst=conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet res=pst.executeQuery();
            while (res.next())
            {
                NonArtiste na=new NonArtiste(res.getInt(1), res.getString("username"), res.getString(2),res.getString("prenom"),res.getString("password"),res.getString("email"), Roles.NONARTISTE,res.getString("imageUrl"),res.getBoolean("etat"),res.getInt("points"));
                lm.add(new Materiel(res.getInt(1), res.getString("libMateriel"), res.getString("description"), res.getString("adresse"), res.getInt("quantite"), res.getString("imageUrl"),na));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lm;
    }

    public List<Materiel> trierListParLibelle(List<Materiel> lm)
    {
        return lm.stream().sorted(Comparator.comparing(Materiel::getLibMateriel)).collect(Collectors.toList());

    }
    public List<Materiel> trierListParAdresse(List<Materiel> lm)
    {
        return lm.stream().sorted(Comparator.comparing(Materiel::getAdresse)).collect(Collectors.toList());

    }

    public List<Materiel> trierListParQuantite(List<Materiel> lm)
    {
        return lm.stream().sorted(Comparator.comparing(Materiel::getQuantite)).collect(Collectors.toList());

    }

    public List<Materiel> filterListParQuantite(List<Materiel> lm,int quantite )
    {
        return lm.stream().filter(m->m.getQuantite()>=quantite).collect(Collectors.toList());
    }

    public List<Materiel> filterListParAdresse(List<Materiel> lm,String adresse )
    {
        return lm.stream().filter(m->m.getAdresse().equals(adresse)).collect(Collectors.toList());
    }

    public List<Materiel> rechercherListParAdresse(String adresse)
    {
        List<Materiel> lm=new ArrayList<>();
        try {

            String query="Select * from materiel m INNER JOIN user u ON m.nonArtiste=u.userId and m.adresse=?";
            pst=conn.prepareStatement(query);
            pst.setString(1,adresse);
            ResultSet res=pst.executeQuery();
            while (res.next())
            {
                NonArtiste na=new NonArtiste(res.getInt(1), res.getString("username"), res.getString(2),res.getString("prenom"),res.getString("password"),res.getString("email"), Roles.NONARTISTE,res.getString("imageUrl"),res.getBoolean("etat"),res.getInt("points"));
                lm.add(new Materiel(res.getInt(1), res.getString("libMateriel"), res.getString("description"), res.getString("adresse"), res.getInt("quantite"), res.getString("imageUrl"),na));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lm;
    }

    public void reserverMateriel(Materiel m)
    {
    String query="update materiel set quantite=? where idMateriel=?";
            try{
            pst=conn.prepareStatement(query);
            pst.setInt(1,m.getQuantite()-m.getQuantiteReserver());
            pst.setInt(2,m.getidMateriel());
            pst.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException();
            }
    }
    public List<Materiel> rechercheAdr(String adr,List<Materiel> lm)
    {

        return lm.stream().filter(m->m.getAdresse().toUpperCase().contains(adr.toUpperCase())).collect(Collectors.toList());
    }
}



