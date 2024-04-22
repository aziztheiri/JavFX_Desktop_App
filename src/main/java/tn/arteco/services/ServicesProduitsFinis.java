package tn.arteco.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import tn.arteco.iservices.Iservice;
import tn.arteco.models.Accomplissement;
import tn.arteco.models.Artiste;
import tn.arteco.models.ProduitFini;
import tn.arteco.models.Roles;
import tn.arteco.utils.MyDataBase;

import javax.print.Doc;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ServicesProduitsFinis implements Iservice<ProduitFini> {
    private Connection connection;
    private Statement ste;
    private PreparedStatement pst;
    public Set<ProduitFini> produitFinis = new HashSet<>();

    public ServicesProduitsFinis() {
        connection = MyDataBase.getInstance().getConnection();
    }
UserService userService = new UserService();
    @Override
    public void add(ProduitFini produitFini) {
        String req = "INSERT INTO produitfini(libProduit,description,imageUrl,artiste,categorie,date) VALUES (?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, produitFini.getLibProduit());
            pst.setString(2, produitFini.getDescription());
            pst.setString(3, produitFini.getImageUrl());
            pst.setInt(4, produitFini.getArtiste().getUserId());
            pst.setString(5, produitFini.getCategorie());
            java.util.Date utilDate = produitFini.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pst.setDate(6, sqlDate);
            pst.executeUpdate();
            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<ProduitFini> getAll() {
        String req = "SELECT p.*, a.* FROM produitfini p INNER JOIN user a ON p.artiste = a.userId";
        ArrayList<ProduitFini> p = new ArrayList<>();
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Artiste artiste = new Artiste(rs.getInt("userId"), rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, rs.getString("imageUrl"), rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                p.add(new ProduitFini(rs.getInt("idProduit"), artiste, rs.getString("libProduit"), rs.getString("description"), rs.getString("imageUrl"), rs.getInt("totalRate"), rs.getString("categorie"), rs.getDate("date")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public List<ProduitFini> getProduitByUserId(int id) {
        String req = "SELECT p.*, a.* FROM produitfini p INNER JOIN user a ON p.artiste = a.userId WHERE p.artiste = ?";
        List<ProduitFini> p = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Artiste artiste = new Artiste(rs.getInt("userId"), rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, rs.getString("imageUrl"), rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                p.add(new ProduitFini(rs.getInt("idProduit"), artiste, rs.getString("libProduit"), rs.getString("description"), rs.getString("imageUrl"), rs.getInt("totalRate"), rs.getString("categorie"), rs.getDate("date")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    @Override
    public void update(ProduitFini produitFini) {
        String req = "UPDATE produitfini set libProduit=?,description=?,imageUrl=?,artiste=?,categorie =? WHERE idProduit=?";
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, produitFini.getLibProduit());
            pst.setString(2, produitFini.getDescription());
            pst.setString(3, produitFini.getImageUrl());
            pst.setInt(4, produitFini.getArtiste().getUserId());
            pst.setString(5, produitFini.getCategorie());
            pst.setInt(6, produitFini.getIdProduit());

            pst.executeUpdate();
            System.out.println("Product updated successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM produitfini WHERE idProduit=?";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ProduitFini getById(int id) {
        String req = "SELECT p.*, a.* FROM produitfini p INNER JOIN user a ON p.artiste = a.userId WHERE p.idProduit='" + id + "'";
        ProduitFini p = null;
        try {
            //  pst = connection.prepareStatement(req);
            // pst.setInt(1, id);
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Artiste artiste = new Artiste(rs.getInt("userId"), rs.getString("username"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), rs.getString("email"), Roles.ARTISTE, rs.getString("imageUrl"), rs.getBoolean("etat"), rs.getInt("points"), new Accomplissement());
                p = new ProduitFini(rs.getInt("idProduit"), artiste, rs.getString("libProduit"), rs.getString("description"), rs.getString("imageUrl"), rs.getInt("totalRate"), rs.getString("categorie"), rs.getDate("date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    //métiers
    public List<ProduitFini> triProduitByRateDesc() {
        return getAll().stream().filter(p -> p.getTotalRate() > 0).
                sorted(((o1, o2) -> o2.getTotalRate() - o1.getTotalRate()))
                .collect(Collectors.toList());
    }

    public List<ProduitFini> triProduitByRateAsc() {
        return getAll().stream().filter(p -> p.getTotalRate() > 0).
                sorted(((o1, o2) -> o1.getTotalRate() - o2.getTotalRate()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreParCategorieEtTriParRateAsc(String categorie){
        return getAll().stream().filter(p -> p.getTotalRate() > 0 && p.getCategorie().equals(categorie)).
                sorted(((o1, o2) -> o1.getTotalRate() - o2.getTotalRate()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreParCategorieEtTriParRateDesc(String categorie){
        return getAll().stream().filter(p -> p.getTotalRate() > 0 && p.getCategorie().equals(categorie)).
                sorted(((o1, o2) -> o2.getTotalRate() - o1.getTotalRate()))
                .collect(Collectors.toList());
    }

    public List<ProduitFini> triProduitByLibelleAsc() {
        return getAll().stream().sorted((o1, o2) -> o1.getLibProduit().compareTo(o2.getLibProduit())).collect(Collectors.toList());
    }

    public List<ProduitFini> triProduitByLibelleDesc() {
        return getAll().stream().sorted((o1, o2) -> o2.getLibProduit().compareTo(o1.getLibProduit())).collect(Collectors.toList());
    }

    public List<ProduitFini> filtreParCategorie(String categorie) {
        return getAll().stream().filter(p -> p.getCategorie().equals(categorie)).collect(Collectors.toList());
    }
    public List<ProduitFini> filtreetTriRateAscParArtiste(String nom) {
        return getAll().stream().filter(p -> p.getArtiste().getNom().equals(nom) && p.getTotalRate() > 0 )
                .sorted(((o1, o2) -> o1.getTotalRate() - o2.getTotalRate()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreetTriRateDescParArtiste(String nom) {
        return getAll().stream().filter(p -> p.getArtiste().getNom().equals(nom) && p.getTotalRate() > 0 )
                .sorted(((o1, o2) -> o2.getTotalRate() - o1.getTotalRate()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreEtTriLibDescParCategorie(String categorie) {
        return getAll().stream()
                .filter(p -> p.getCategorie().equals(categorie))
                .sorted((o1, o2) -> o2.getLibProduit().compareTo(o1.getLibProduit()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreEtTriLibAscParCategorie(String categorie) {
        return getAll().stream()
                .filter(p -> p.getCategorie().equals(categorie))
                .sorted((o1, o2) -> o1.getLibProduit().compareTo(o2.getLibProduit()))
                .collect(Collectors.toList());
    }

    public List<ProduitFini> filtreParNomArtiste(String nom) {
        return getAll().stream().filter(p -> p.getArtiste().getNom().equals(nom)).collect(Collectors.toList());
    }
    public List<ProduitFini> filtreetTriLibAscParNomArtiste(String nom) {
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nom)).
                sorted((o1, o2) -> o1.getLibProduit().compareTo(o2.getLibProduit()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreetTriLibDescParNomArtiste(String nom) {
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nom)).
                sorted((o1, o2) -> o2.getLibProduit().compareTo(o1.getLibProduit()))
                .collect(Collectors.toList());
    }

    public List<ProduitFini> filtreParArtisteEtCategorie(String nomArtiste, String categorie) {
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nomArtiste) && p.getCategorie().equals(categorie))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreparArtisteCategorieTrilibAsc(String nomArtiste,String categorie){
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nomArtiste) && p.getCategorie().equals(categorie))
                .sorted((o1, o2) -> o1.getLibProduit().compareTo(o2.getLibProduit()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreparArtisteCategorieTrilibDesc(String nomArtiste,String categorie){
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nomArtiste) && p.getCategorie().equals(categorie))
                .sorted((o1, o2) -> o2.getLibProduit().compareTo(o1.getLibProduit()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreparArtisteCategorieTriRateAsc(String nomArtiste,String categorie){
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nomArtiste) && p.getCategorie().equals(categorie) && p.getTotalRate() > 0)
                .sorted(((o1, o2) -> o1.getTotalRate() - o2.getTotalRate()))
                .collect(Collectors.toList());
    }
    public List<ProduitFini> filtreparArtisteCategorieTriRateDesc(String nomArtiste,String categorie){
        return getAll().stream()
                .filter(p -> p.getArtiste().getNom().equals(nomArtiste) && p.getCategorie().equals(categorie) && p.getTotalRate() > 0)
                .sorted(((o1, o2) -> o2.getTotalRate() - o1.getTotalRate()))
                .collect(Collectors.toList());
    }



    public List<ProduitFini> top3ProduitRate() {
        return getAll().stream().
                sorted(((o1, o2) -> o2.getTotalRate() - o1.getTotalRate())).limit(3).collect(Collectors.toList());
    }

    public List<ProduitFini> rechercheProduit(String recherche) {
       return getAll().stream().filter(e->(e.getLibProduit().contains(recherche)) || (e.getArtiste().getNom().contains(recherche))).distinct().collect(Collectors.toList());
    }
    public int nbrProduits() {
        String req = "select count(*) as nbrproduits from produitfini";
        int nbrProduits = 0;
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                nbrProduits = rs.getInt("nbrproduits");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nbrProduits;
    }

    public Map<String, Integer> nbrProduitsParArtiste() {
        String req = "SELECT a.*, COUNT(p.idProduit) AS nbrProduits " +
                "FROM user a " +
                "Inner Join produitfini p ON a.userId = p.artiste " +
                "GROUP BY a.userId";
        Map<String, Integer> nbrProduitsParArtiste = new HashMap<>();
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Artiste artiste = new Artiste(rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("password"),
                        rs.getString("email"),
                        Roles.ARTISTE,
                        rs.getString("imageUrl"),
                        rs.getBoolean("etat"),
                        rs.getInt("points"),
                        new Accomplissement());
                int nbrProduits = rs.getInt("nbrProduits");
                nbrProduitsParArtiste.put(artiste.getNom(), nbrProduits);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nbrProduitsParArtiste;
    }

    public Map<String, Integer> nbrProduitsParCategorie() {
        String req = "SELECT categorie, COUNT(*) AS nbrProduits from produitfini group by categorie";
        Map<String, Integer> nbrProduitsParCategorie = new HashMap<>();
        try {
            ste = connection.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                int nbrProduits = rs.getInt("nbrProduits");
                String categorie = rs.getString("categorie");
                nbrProduitsParCategorie.put(categorie, nbrProduits);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nbrProduitsParCategorie;
    }

   

}
