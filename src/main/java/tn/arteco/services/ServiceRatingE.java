package tn.arteco.services;


import tn.arteco.iservices.IRatingE;
import tn.arteco.models.RatingEvenement;
import tn.arteco.utils.MyDataBase;

import java.sql.*;

public class ServiceRatingE implements IRatingE<ServiceRatingE> {

    private Connection cnx;

    public ServiceRatingE() {
        cnx = MyDataBase.getInstance().getConnection();}

    public void ajouter_RateE(RatingEvenement e) {
        try {
            String requete1 = "INSERT INTO ratingevenement (idratingE,idEventToRate,RateE) VALUES(?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete1);
            pst.setInt(1, e.getIdratingE());
            pst.setInt(2,e.getIdEventToRate());
            pst.setInt(3,e.getRateE());
            pst.executeUpdate();
            System.out.println("Rate ajoutee !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public double getAverageRating(int idEventToRate) {
        double averageRating = 0;
        try {
            String query = "SELECT AVG(RateE) AS average_rating FROM ratingevenement WHERE idEventToRate = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, idEventToRate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                averageRating = resultSet.getDouble("average_rating");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return averageRating;
    }
    public double getNombreRates() {
        double nombre = 0;
        try {
            String query = "SELECT COUNT(*) AS nombre FROM ratingevenement";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nombre = resultSet.getDouble("nombre");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nombre;
    }



}
