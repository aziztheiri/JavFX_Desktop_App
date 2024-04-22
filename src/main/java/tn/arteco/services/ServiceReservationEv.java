package tn.arteco.services;

import tn.arteco.iservices.IReservationEv;
import tn.arteco.models.*;
import tn.arteco.utils.MyDataBase;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServiceReservationEv implements IReservationEv {

    public ServiceEvenement se ;

    public EmailSender emailSender;

    private Connection cnx;

    public ServiceReservationEv() {
        cnx = MyDataBase.getInstance().getConnection();
    }

    public  void sendEmail(String to, String descri) throws MessagingException {
        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Change to your SMTP server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        // Get Session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sarah.ayari.09@gmail.com", "syazcliissradtnx"); // Change to your email and password
            }
        });


        // Create message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("sarah.ayari.09@gmail.com")); // Change to your email
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));


        String subject = "ARTECO RESERVATION TEAM ";


        String htmlMsg = descri
                + "<img src='../images/arteco.png'>";

        message.setSubject(subject);
        message.setContent(htmlMsg, "text/html");
        //message.setText("Dear Client, \n Thank you for you reservation , We hope you would enjoy your event ");
        // Send message
        Transport.send(message);
    }





    public IReservationEv iReservationEv ;

    public void ajouter_Reservation(ReservationEv r) {


        try {
            String requete1 = "INSERT INTO reservationevent(idReserv,idEvent,dateReserv,nbrPersonnes,etatReserv,emailClient) VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete1);
            pst.setInt(1, r.getIdReserv());
            pst.setInt(2, r.getIdEvent());
            pst.setString(3, r.getDateReserv());
            pst.setInt(4, r.getNbrPersonnes());
            pst.setString(5, r.getEtatReserv());
            pst.setString(6, r.getEmailClient());

            if ( r.getEtatReserv().equals("CONFIRME")){
                String descri2 = "<h3> Dear Client, <br/> Thank you for your reservation , And your Reservation is <span style='color:green'> <b>Confirmed</b> </span>. <br/> We hope you would enjoy your event </h3>";
                sendEmail(r.getEmailClient(),descri2);
                System.out.println("Reservation ajouté 1.2!");
            } else if ( r.getEtatReserv().equals("NONCONFIRME")) {
                String descri3 = "<h3> Dear Client, <br/> Thank you for your reservation <span style='color:red'> but your Reservation is <b>NOT YET</b> Confirmed </span>. <br/> We hope you would enjoy your event </h3>";
                sendEmail(r.getEmailClient(),descri3);
                System.out.println("Reservation ajouté 1.3!");
            }
            System.out.println("Reservation ajouté 333!");

            pst.executeUpdate();
            System.out.println("Reservation ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



    public void supprimer_Reservation(int idReserv) {

        try {
            String requete2 = "DELETE FROM reservationevent WHERE idReserv=" + idReserv;
            Statement st = cnx.createStatement();

            st.executeUpdate(requete2);
            System.out.println("Reservation supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


    public void modifier_Reservation(int idReserv,int idEvent,String dateReserv,int nbrPersonnes,String etatReserv,String emailClient) {
        try {
            String requete3 = " UPDATE reservationevent SET " + "  idEvent= ?, dateReserv = ? , nbrPersonnes  = ?,etatReserv = ? ,emailClient = ? WHERE idReserv= "+ idReserv;
            PreparedStatement pst =cnx.prepareStatement(requete3);
            pst.setInt(1, idEvent);
            pst.setString(2, dateReserv);
            pst.setInt(3, nbrPersonnes);
            pst.setString(4, etatReserv);
            pst.setString(5, emailClient);
            pst.executeUpdate();
            System.out.println("Reservation modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }



    public ArrayList<ReservationEv> listerReservations() {
        ArrayList<ReservationEv> myList = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM reservationevent";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {

                ReservationEv R = new ReservationEv();
                R.setIdReserv(rs.getInt(1));
                R.setIdEvent(rs.getInt("idEvent"));
                R.setDateReserv(rs.getString("DateReserv"));
                R.setNbrPersonnes(rs.getInt("nbrPersonnes"));
                R.setEtatReserv(rs.getString("etatReserv"));
                R.setEmailClient(rs.getString("emailClient"));


                myList.add(R);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Affichage exécuté");
        return myList;
    }


    public ReservationEv getReservationById(int idReservation) {
        ReservationEv R = null;

        String requete5 = "SELECT \tidReserv, idEvent, DateReserv, nbrPersonnes, etatReserv, emailClient FROM reservationevent WHERE idReserv = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(requete5);
            ps.setInt(1, idReservation);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                R = new ReservationEv();
                R.setIdReserv(rs.getInt(1));
                R.setIdEvent(rs.getInt("idEvent"));
                R.setDateReserv(rs.getString("DateReserv"));
                R.setNbrPersonnes(rs.getInt("nbrPersonnes"));
                R.setEtatReserv(rs.getString("etatReserv"));
                R.setEmailClient(rs.getString("emailClient"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return R;
    }



}