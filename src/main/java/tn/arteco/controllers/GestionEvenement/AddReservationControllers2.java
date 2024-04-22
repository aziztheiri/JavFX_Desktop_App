package tn.arteco.controllers.GestionEvenement;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.arteco.models.Evenement;
import tn.arteco.models.ReservationEv;
import tn.arteco.services.EmailSender;
import tn.arteco.services.ServiceEvenement;
import tn.arteco.services.ServiceReservationEv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;

import javafx.stage.FileChooser;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;

import javax.mail.MessagingException;

public class AddReservationControllers2 {


    @FXML
    private TextField Emailclient;

    @FXML
    private TextField EtatReserv;

    @FXML
    private TextField dateReserv;

    @FXML
    private TextField idEventReserv;

    @FXML
    private TextField nbrPersonnes;

    @FXML
    private Button retour;
    @FXML
    private Stage stage;

    @FXML
    void ajouterReservation(ActionEvent event) throws MessagingException {
        String nom = idEventReserv.getText();
        int id = Integer.parseInt(idEventReserv.getText());
        int nbr = Integer.parseInt(nbrPersonnes.getText());
        ReservationEv reservationEv = new ReservationEv(id, dateReserv.getText(),nbr, EtatReserv.getText(), Emailclient.getText());

        EmailSender.sendEmailEvent(Emailclient.getText(),"Réservation ajouté avec succès !","Confirmation réservation");
        ServiceReservationEv serviceReservationEv =new ServiceReservationEv();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AddReservation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AddReservationControllers addReservationControllers= loader.getController();
            serviceReservationEv.ajouter_Reservation(reservationEv);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reservation a ete ajoutee");
            alert.show();
            // Pass any necessary data to the new interface
            //Stage stage = new Stage();
            //stage.setScene(new Scene(root));
            //stage.show();

            // Close the current window
            // btn1.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    public void retour(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AfficherFront.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            AfficherFront afficherFront = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

            stage.show();
            // Close the current window
            retour.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


    @FXML
    void generatePdf(ActionEvent event) {
        Document document = new Document();

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                // Title
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD ,BaseColor.GREEN);
                Paragraph title = new Paragraph("-- Reservation Pass --", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20); // Espacement après le titre
                document.add(title);

// Company details
                Font companyFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
                Paragraph companyDetails = new Paragraph();
                companyDetails.add(new Chunk("Company Name: ", companyFont));
                companyDetails.add(new Chunk("ARTECO", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE)));
                companyDetails.add(Chunk.NEWLINE);
                companyDetails.add(new Chunk("Email: ", companyFont));
                companyDetails.add(new Chunk("Arteco@gmail.com", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE)));
                companyDetails.add(Chunk.NEWLINE);
                companyDetails.add(new Chunk("Phone: ", companyFont));
                companyDetails.add(new Chunk("+21698456123", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE)));
                companyDetails.setAlignment(Element.ALIGN_CENTER);
                companyDetails.setSpacingAfter(20); // Espacement après les détails de l'entreprise
                document.add(companyDetails);


                // Ajouter l'image
                String imagePath = "/Icons/LOGO.png"; // Chemin vers l'image
                try {
                    Image logo = Image.getInstance(imagePath);
                    logo.setAlignment(Element.ALIGN_CENTER);
                    logo.scaleAbsolute(200, 200); // Dimension de l'image
                    document.add(logo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
// Reservation details note
                Font reservationNoteFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD,BaseColor.GREEN);
                Paragraph reservationNote = new Paragraph("-- Details Reservation --", reservationNoteFont);
                reservationNote.setAlignment(Element.ALIGN_CENTER);
                reservationNote.setSpacingAfter(20); // Espacement après la note
                document.add(reservationNote);
// Reservation details

                Font reservationFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
                Paragraph reservationDetails = new Paragraph();
                reservationDetails.add(new Chunk("Email client: ", reservationFont));
                reservationDetails.add(new Chunk(Emailclient.getText(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails.add(Chunk.NEWLINE);
                reservationDetails.add(new Chunk("État réservation: ", reservationFont));
                reservationDetails.add(new Chunk(EtatReserv.getText(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails.add(Chunk.NEWLINE);
                reservationDetails.add(new Chunk("Date réservation: ", reservationFont));
                reservationDetails.add(new Chunk(dateReserv.getText(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails.add(Chunk.NEWLINE);
                reservationDetails.add(new Chunk("Nombre de personnes: ", reservationFont));
                reservationDetails.add(new Chunk(nbrPersonnes.getText(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails.setAlignment(Element.ALIGN_CENTER);
                reservationDetails.setSpacingAfter(20); // Espacement après les détails de la réservation
                document.add(reservationDetails);

                // Notation "Details Evenement"
                Font eventNoteFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD,BaseColor.GREEN);
                Paragraph eventNote = new Paragraph("-- Details Evenement -- ", eventNoteFont);
                eventNote.setAlignment(Element.ALIGN_CENTER);
                eventNote.setSpacingAfter(20); // Espacement après la notation
                document.add(eventNote);
                // event

                 int id = Integer.parseInt(idEventReserv.getText());
                ServiceEvenement serviceEvenement = new ServiceEvenement();
                Evenement evenement = serviceEvenement.getEventById(id);
                Font reservationFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
                Paragraph reservationDetails2 = new Paragraph();
                reservationDetails2.add(new Chunk("Nom Evenement: ", reservationFont));
                reservationDetails2.add(new Chunk(evenement.getNomEvenement(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails2.add(Chunk.NEWLINE);
                reservationDetails2.add(new Chunk("Description Evenement: ", reservationFont));
                reservationDetails2.add(new Chunk(evenement.getDescriptionEvenement(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails2.add(Chunk.NEWLINE);
                reservationDetails2.add(new Chunk("Adresse de l'évenement: ", reservationFont));
                reservationDetails2.add(new Chunk(evenement.getAdresseEvenement(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLUE)));
                reservationDetails2.setAlignment(Element.ALIGN_CENTER);
                reservationDetails2.setSpacingAfter(20); // Espacement après les détails de la réservation
                document.add(reservationDetails2);


                document.close();
                System.out.println("PDF file generated successfully!");
            } else {
                System.out.println("PDF generation canceled.");
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
