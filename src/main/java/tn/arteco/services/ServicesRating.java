package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.*;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesRating implements Iservice<Rate> {
    private Connection connection ;
    private Statement ste ;
    private PreparedStatement pst;
    public ServicesRating(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void add(Rate rate) {
        String req = "INSERT INTO rate(rate,date,produitFini,iduser) VALUES (?,?,?,?)";
        try{
            pst = connection.prepareStatement(req);
            pst.setInt(1,rate.getRate());
            java.util.Date utilDate = rate.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pst.setDate(2,sqlDate);
            pst.setInt(3,rate.getProduitFini().getIdProduit());
            pst.setInt(4,rate.getIduser());
            pst.executeUpdate();
            System.out.println("Rate added successfully!");
            updateTotalRate(rate.getProduitFini().getIdProduit());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateTotalRate(int idProduit) {
        try {
            String totalRateQuery = "SELECT COALESCE(SUM(rate), 0) AS totalRate FROM rate WHERE produitFini = ?";
            PreparedStatement totalRateStatement = connection.prepareStatement(totalRateQuery);
            totalRateStatement.setInt(1, idProduit);
            ResultSet totalRateResult = totalRateStatement.executeQuery();
            int totalRate = 0;
            while (totalRateResult.next()) {
                totalRate = totalRateResult.getInt("totalRate");
            }
            String updateTotalRateQuery = "UPDATE produitfini SET totalRate = ? WHERE idProduit = ?";
            PreparedStatement updateTotalRateStatement = connection.prepareStatement(updateTotalRateQuery);
            updateTotalRateStatement.setInt(1, totalRate);
            updateTotalRateStatement.setInt(2, idProduit);
            updateTotalRateStatement.executeUpdate();

            System.out.println("TotalRate updated successfully for produit with id " + idProduit);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<Rate> getAll() {
        String req = "SELECT r.*, p.*, a.* " +
                "FROM rate AS r " +
                "INNER JOIN produitfini AS p ON r.produitFini = p.idProduit " +
                "INNER JOIN user AS a ON p.artiste = a.userId";
        ArrayList<Rate> r = new ArrayList<>();
        try{
            ste=connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                String artistImageUrl = rs.getString("a.imageUrl");
                Artiste artiste = new Artiste(rs.getInt("userId"),rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, artistImageUrl, rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                ProduitFini produitFini = new ProduitFini(rs.getInt("idProduit"),artiste,rs.getString("libProduit"),rs.getString("description"),rs.getString("imageUrl"),rs.getInt("totalRate"),rs.getString("categorie"),rs.getDate("date"));
                r.add(new Rate(rs.getInt(1),rs.getInt(2),rs.getDate(3),produitFini,rs.getInt(4)));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public Rate getRateByUserId(int userid,int produitid) {
        String req = "SELECT r.*, p.*, a.* " +
                "FROM rate AS r " +
                "INNER JOIN produitfini AS p ON r.produitFini = p.idProduit " +
                "INNER JOIN user AS a ON p.artiste = a.userId " +
                "WHERE r.iduser = ? and p.idProduit=?";
        Rate rate = null;
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, userid);
            pst.setInt(2,produitid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String artistImageUrl = rs.getString("a.imageUrl");
                Artiste artiste = new Artiste(rs.getInt("userId"), rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, artistImageUrl, rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                ProduitFini produitFini = new ProduitFini(rs.getInt("idProduit"), artiste, rs.getString("libProduit"), rs.getString("description"), rs.getString("imageUrl"), rs.getInt("totalRate"), rs.getString("categorie"), rs.getDate("date"));
                rate = new Rate(rs.getInt(1), rs.getInt(2), rs.getDate(3), produitFini,rs.getInt(4));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rate;
    }
    public List<Rate> getAllRatingsForCurrentMonth(){
        String req = "SELECT r.*, p.*, a.* " +
                "FROM rate AS r " +
                "INNER JOIN produitfini AS p ON r.produitFini = p.idProduit " +
                "INNER JOIN user AS a ON p.artiste = a.userId " +
                "WHERE MONTH(r.date) = MONTH(CURRENT_DATE()) AND YEAR(r.date) = YEAR(CURRENT_DATE())";
        ArrayList<Rate> r = new ArrayList<>();
        try{
            ste=connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                String artistImageUrl = rs.getString("a.imageUrl");
                Artiste artiste = new Artiste(rs.getInt("userId"),rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, artistImageUrl, rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                ProduitFini produitFini = new ProduitFini(rs.getInt("idProduit"),artiste,rs.getString("libProduit"),rs.getString("description"),rs.getString("imageUrl"),rs.getInt("totalRate"),rs.getString("categorie"),rs.getDate("date"));
                r.add(new Rate(rs.getInt(1),rs.getInt(2),rs.getDate(3),produitFini,rs.getInt(4)));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }
    @Override
    public void update(Rate rate) {
        String req = "UPDATE rate set rate=?,date=?,produitFini=? where rateId=?";
        try{
            pst = connection.prepareStatement(req);
            pst.setInt(1,rate.getRate());
            java.util.Date utilDate = rate.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pst.setDate(2,sqlDate);
            pst.setInt(3,rate.getProduitFini().getIdProduit());
            pst.setInt(4,rate.getRateId());
            pst.executeUpdate();
            System.out.println("Rate updated successfully!");
            updateTotalRate(rate.getProduitFini().getIdProduit());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRate0(Rate rate) {
        String req = "UPDATE rate set rate=? where rateId=?";
        try{
            pst = connection.prepareStatement(req);
            pst.setInt(1,0);
            pst.setInt(2,rate.getRateId());
            pst.executeUpdate();
            System.out.println("Rate updated successfully!");
            updateTotalRate(rate.getProduitFini().getIdProduit());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM rate WHERE rateId=?";
        try{
            pst = connection.prepareStatement(req);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("Rate deleted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Rate getById(int id) {
        String req = "SELECT r.*, p.*, a.* " +
                "FROM rate AS r " +
                "INNER JOIN produitfini AS p ON r.produitFini = p.idProduit " +
                "INNER JOIN user AS a ON p.artiste = a.userId WHERE r.rateId='"+id+"'";

        Rate r  = null ;
        try{
            ste=connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                String artistImageUrl = rs.getString("a.imageUrl");

                Artiste artiste = new Artiste(rs.getInt("userId"),rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, artistImageUrl, rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                ProduitFini produitFini = new ProduitFini(rs.getInt("idProduit"),artiste,rs.getString("libProduit"),rs.getString("description"),rs.getString("imageUrl"),rs.getInt("totalRate"),rs.getString("categorie"),rs.getDate("date"));
                r = new Rate(rs.getInt(1),rs.getInt(2),rs.getDate(3),produitFini,rs.getInt(4));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }
    public Artiste findArtistWithBestRating() {
        List<Rate> allRatings = getAllRatingsForCurrentMonth();
        Map<Artiste, Integer> artistRatings = new HashMap<>();
        for (Rate rating : allRatings) {
            Artiste artist = rating.getProduitFini().getArtiste();
            int sum = 0;
            sum += rating.getRate();
            artistRatings.put(artist, sum);
        }
        Artiste bestArtist = null;
        int maxRating = Integer.MIN_VALUE;
        for (Map.Entry<Artiste, Integer> entry : artistRatings.entrySet()) {
            if (entry.getValue() > maxRating) {
                maxRating = entry.getValue();
                bestArtist = entry.getKey();
            }
        }
        return bestArtist;
    }

}
