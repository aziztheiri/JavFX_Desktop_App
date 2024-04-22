package tn.arteco.controllers.GestionEvenement;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.arteco.services.ServiceStat;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class statistique implements Initializable {

    @FXML
    private PieChart pieChart;
    @FXML
    private Button retourstat;


   /* public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceStat serviceStat = new ServiceStat();
        List<String> listeNoms = serviceStat.listenomevent();
        List<Integer> listeReseConf = serviceStat.listeConfirme();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(

                        for(int i = 0; i < listeNoms.size(); i++) {
                            new PieChart.Data(listeNoms.get(i), listeReseConf.get(i));
                        }
                        )

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
    }*/
   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceStat serviceStat = new ServiceStat();
        List<String> listeNoms = serviceStat.listenomevent();
        List<Integer> listeReseConf = serviceStat.listeConfirme();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(int i = 0; i < listeNoms.size(); i++) {
            pieChartData.add(new PieChart.Data(listeNoms.get(i), listeReseConf.get(i)));
        }

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", (data.pieValueProperty().getValue() * 10)/100
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
    }

    @FXML
    // le bouton retour
    public void retourstat(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            ajouterevenement ajouterevenement = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Close the current window
            retourstat.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
}
