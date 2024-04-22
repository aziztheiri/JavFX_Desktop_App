package tn.arteco.services;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.arteco.models.ProduitFini;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
public class EmailSender {

    // Read the image file and convert it to a base64 encoded string
    public void sendEmail(String to, String description, ProduitFini newProduct) {
        String from = "sarah.ayari.09@gmail.com";
        String password = "syazcliissradtnx";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Confirmation d'ajout ");
            message.setText(description);
            String htmlContent = "<html><body>";
            htmlContent += "<div style='border:  1px solid #000; padding:  20px; background-color: #f9f9f9;'>";
            htmlContent += "<h2 style='color: #333; font-family: Arial, sans-serif;'>Votre produit a été ajouté avec succès !</h2>";
            htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>Libellé:</strong> " + newProduct.getLibProduit() + "</p>";
            htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>Catégorie:</strong> " + newProduct.getCategorie() + "</p>";
            htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>Description:</strong> " + newProduct.getDescription() + "</p>";
            htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>Artiste:</strong> " + newProduct.getArtiste().getNom() + "</p>";
            htmlContent += "</div>";
            htmlContent += "</body></html>";

            message.setContent(htmlContent, "text/html");

            new Thread(() -> {
                try {
                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendEmailEvent(String to, String descri, String subject) throws MessagingException {
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

        String htmlMsg = descri
                + "<img src='../images/arteco.png'>";

        message.setSubject(subject);
        message.setContent(htmlMsg, "text/html");
        message.setText("Dear Client, \n Thank you for you reservation , We hope you would enjoy your event ");

        // Send message
        Transport.send(message);
    }
    /*public static void l(String to, String description,String code) {
        String from = "sarah.ayari.09@gmail.com";
        String password = "syazcliissradtnx";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("CONFIRMATION PAR CODE " );
            String htmlContent = "<html><body>";
            htmlContent += "<div style='border:  1px solid #000; padding:  20px; background-color: #f9f9f9;'>";
            htmlContent += "<h2 style='color: #333; font-family: Arial, sans-serif;'>"+description+"!</h2>";
            htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>Code:</strong> " + code + "</p>";
            htmlContent += "</div>";
            htmlContent += "</body></html>";
            message.setContent(htmlContent, "text/html");
            Transport.send(message);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/
    public static void sendEmail(String recipientEmail,String description, String authenticationCode) throws MailjetException {

        MailjetClient client;
        MailjetRequest request;
        JSONObject message;
        JSONArray recipients;

        // Create Mailjet client
        ClientOptions clientOptions = ClientOptions.builder()
                .apiKey("d045660d3186756658ce774efd5c739d")
                .apiSecretKey("549c0af53497f9d1fa4bd832add8eab9")
                .build();
        client = new MailjetClient(clientOptions);
        String htmlContent = "<html><body>";
        htmlContent += "<div style='border:  1px solid #000; padding:  20px; background-color: #f9f9f9;'>";
        htmlContent += "<h2 style='color: #333; font-family: Arial, sans-serif;'>"+description+"!</h2>";
        htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>Code:</strong> {{authenticationCode}}</p>";
        htmlContent += "</div>";
        htmlContent += "</body></html>";
        htmlContent = htmlContent.replace("{{authenticationCode}}", authenticationCode);
        // Create Mailjet request
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put("From", new JSONObject()
                                        .put("Email", "mohamedbenhadjlakhal@gmail.com")
                                        .put("Name", "mohamed"))
                                .put("To", new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", recipientEmail)))
                                .put("Subject", "Authentification code")
                                .put("HTMLPart", htmlContent)
                        ));
        client.post(request);
    }
    public static void sendCustumerContactEmail(String recipientEmail,String message1, String userEmail,String username) throws MailjetException{
        MailjetClient client;
        MailjetRequest request;
        JSONObject message;
        JSONArray recipients;

        // Create Mailjet client
        ClientOptions clientOptions = ClientOptions.builder()
                .apiKey("d045660d3186756658ce774efd5c739d")
                .apiSecretKey("549c0af53497f9d1fa4bd832add8eab9")
                .build();
        client = new MailjetClient(clientOptions);
        String htmlContent = "<html><body>";
        htmlContent += "<div style='border:  1px solid #000; padding:  20px; background-color: #f9f9f9;'>";
        htmlContent += "<h2 style='color: #333; font-family: Arial, sans-serif;'>Custumer Contact</h2>";
        htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>User email:</strong> {{userEmail}}</p>";
        htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>username:</strong> {{username}}</p>";
        htmlContent += "<p style='color: #666; font-family: Arial, sans-serif;'><strong>message:</strong> {{message}}</p>";
        htmlContent += "</div>";
        htmlContent += "</body></html>";
        htmlContent=htmlContent.replace("{{userEmail}}",userEmail);
        htmlContent=htmlContent.replace("{{username}}",username);
        htmlContent=htmlContent.replace("{{message}}",message1);
        // Create Mailjet request
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put("From", new JSONObject()
                                        .put("Email", "mohamedbenhadjlakhal@gmail.com")
                                        .put("Name", "mohamed"))
                                .put("To", new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", recipientEmail)))
                                .put("Subject", "Custumer Contact")
                                .put("HTMLPart", htmlContent)
                        ));
        client.post(request);
    }
}