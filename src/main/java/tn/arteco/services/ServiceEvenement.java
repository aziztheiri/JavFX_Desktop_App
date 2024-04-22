package tn.arteco.services;

import tn.arteco.iservices.IEvent;
import tn.arteco.models.Evenement;
import tn.arteco.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;

public class ServiceEvenement  implements IEvent<Evenement> {
    private Connection cnx;

    public ServiceEvenement() {
        cnx = MyDataBase.getInstance().getConnection();
    }


    public void ajouter_Evenement(Evenement e) {
        try {
            String requete1 = "INSERT INTO evenement(idEvenement,nomEvenement,adresseEvenement,descriptionEvenement,dateEvenement) VALUES(?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete1);
            pst.setInt(1, e.getIdEvenement());
            pst.setString(2, e.getNomEvenement());
            pst.setString(3, e.getAdresseEvenement());
            pst.setString(4, e.getDescriptionEvenement());
            pst.setDate(5 , (Date) e.getDateEvenement());
            pst.executeUpdate();
            System.out.println("Evenement ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public void supprimer_Evenement(int idEvenement) {

        try {
            String requete2 = "DELETE FROM evenement WHERE idEvenement=" + idEvenement;
            Statement st = cnx.createStatement();

            st.executeUpdate(requete2);
            System.out.println("Evenement supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


    public ArrayList<Evenement> listerEvenements() {
        ArrayList<Evenement> myList = new ArrayList<>();
        try {

            String requete3 = "Select * FROM evenement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                Evenement event = new Evenement();

                event.setIdEvenement(rs.getInt(1));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setAdresseEvenement(rs.getString("adresseEvenement"));
                event.setDescriptionEvenement(rs.getString("descriptionEvenement"));
                event.setDateEvenement(rs.getDate("dateEvenement"));

                myList.add(event);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return myList;


    }




    public void modifier_Evenement(int idEvenement,String nomEvenement,String adresseEvenement,String descriptionEvenement,Date dateEvenement) {
        try {
            String requete4 = " UPDATE evenement SET " + "  nomEvenement= ?, adresseEvenement = ? , descriptionEvenement  = ?,dateEvenement = ? WHERE idEvenement= "+ idEvenement;
            PreparedStatement pst =cnx.prepareStatement(requete4);
            pst.setString(1, nomEvenement);
            pst.setString(2, adresseEvenement);
            pst.setString(3, descriptionEvenement);
            pst.setDate(4, dateEvenement);
            pst.executeUpdate();
            System.out.println("Evenement modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }




    public Evenement getEventById(int idEvenement) {
        Evenement event = null;

        String requete5 = "SELECT idEvenement, nomEvenement, descriptionEvenement, adresseEvenement, dateEvenement FROM evenement WHERE idEvenement = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(requete5);
            ps.setInt(1, idEvenement);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                event = new Evenement();

                event.setIdEvenement(rs.getInt("idEvenement"));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setDescriptionEvenement(rs.getString("descriptionEvenement"));
                event.setAdresseEvenement(rs.getString("adresseEvenement"));
                event.setDateEvenement(rs.getDate("dateEvenement"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return event;
    }







}
