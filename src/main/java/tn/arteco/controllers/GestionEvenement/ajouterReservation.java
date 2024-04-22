package tn.arteco.controllers.GestionEvenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import tn.arteco.models.Evenement;
import tn.arteco.models.ReservationEv;
import tn.arteco.services.ServiceEvenement;
import tn.arteco.services.ServiceReservationEv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
public class ajouterReservation implements Initializable {

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private HBox carteRv;
    @FXML
    private Button button2;
    @FXML
    private TableView<ReservationEv> tableview;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            ServiceReservationEv serviceReservationEv = new ServiceReservationEv();
            int size = serviceReservationEv.listerReservations().size();
            List<ReservationEv> reservationEvs = serviceReservationEv.listerReservations();
            for (ReservationEv reservationEv : reservationEvs) {

                FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/GestionEvenement/carteReservation.fxml"));
                HBox carteArticleBox = carteArticleLoader.load();
                carteReservation carte = carteArticleLoader.getController();


                // You can interact with carteArticleController if needed

                // Add the loaded content to the cartev HBox
                carteRv.getChildren().add(carteArticleBox);
                Evenement evenement = serviceEvenement.getEventById(reservationEv.getIdEvent());
                carte.setIdEvent(evenement.getNomEvenement());
                carte.setIDateReservation(reservationEv.getDateReserv());
                carte.setNbrPersonnes(Integer.toString(reservationEv.getNbrPersonnes()));
                carte.setEtatReservation(reservationEv.getEtatReserv());
                carte.setEmailclient(reservationEv.getEmailClient());

            }
        } catch (IOException e) {
            System.err.println("Error loading carteReservation.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void add(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/AddReservation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AddReservationControllers addReservation = loader.getController();

            // Pass any necessary data to the new interface


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn4.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


    @FXML
    public void delete(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/deleteReservation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            deletereservation deletereservation = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn5.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }

    }



    public void update(javafx.event.ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/updateReservation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            updateReservation updateReservation = loader.getController();
            // Pass any necessary data to the new interface

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn6.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    @FXML
    // le bouton yhezek lel Evenement
    public void button2(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            ajouterevenement ajouterevenement = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

            stage.show();
            // Close the current window
            button2.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


    /*
    void generateExcel(ActionEvent event) {
        ServiceReservationEv serviceReservationEv = new ServiceReservationEv();

        // Get data from your tableview or any other source
        List<ReservationEv> recettes = serviceReservationEv.listerReservations();

        // Create a new Excel workbook (HSSFWorkbook for .xls format)
        Workbook workbook = new HSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet(" Les Réservations");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Réservation");
        headerRow.createCell(1).setCellValue("ID Evenement");
        headerRow.createCell(2).setCellValue("Date Réservation");
        headerRow.createCell(3).setCellValue("Nbr Personnes Assistées");
        headerRow.createCell(4).setCellValue("Etat Réservation");
        headerRow.createCell(5).setCellValue("Email Client");
        // Add more columns if needed

        // Populate data rows
        int rowNum = 1;
        for (ReservationEv recette : recettes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(recette.getIdReserv());
            row.createCell(1).setCellValue(recette.getIdEvent());
            row.createCell(2).setCellValue(recette.getDateReserv());
            row.createCell(3).setCellValue(recette.getNbrPersonnes());
            row.createCell(4).setCellValue(recette.getEtatReserv());
            row.createCell(5).setCellValue(recette.getEmailClient());
            // Add more cell data if needed
        }

        // Write the workbook content to a file
        try (FileOutputStream fileOut = new FileOutputStream("Reservations.xls")) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/


    @FXML
    void generateExcel(ActionEvent event) {
        ServiceReservationEv serviceReservationEv = new ServiceReservationEv();

        // Get data from your tableview or any other source
        List<ReservationEv> recettes = serviceReservationEv.listerReservations();

        // Create a new Excel workbook (HSSFWorkbook for .xls format)
        Workbook workbook = new HSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet(" Les Réservations");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Réservation");
        headerRow.createCell(1).setCellValue("ID Evenement");
        headerRow.createCell(2).setCellValue("Date Réservation");
        headerRow.createCell(3).setCellValue("Nbr Personnes Assistées");
        headerRow.createCell(4).setCellValue("Etat Réservation");
        headerRow.createCell(5).setCellValue("Email Client");
        // Add more columns if needed

        // Populate data rows
        int rowNum = 1;
        for (ReservationEv recette : recettes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(recette.getIdReserv());
            row.createCell(1).setCellValue(recette.getIdEvent());
            row.createCell(2).setCellValue(recette.getDateReserv());
            row.createCell(3).setCellValue(recette.getNbrPersonnes());
            row.createCell(4).setCellValue(recette.getEtatReserv());
            row.createCell(5).setCellValue(recette.getEmailClient());
            // Add more cell data if needed
        }

        // Use FileChooser to let the user choose where to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Reservations.xls");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls"));

        // Show save dialog
        File file = fileChooser.showSaveDialog(null);

        // Write the workbook content to the selected file
        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Excel file generated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
