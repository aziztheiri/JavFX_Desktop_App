package tn.arteco.test;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainFx extends Application {
    JavaConnector javaConnector=new JavaConnector();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
  //   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
     //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BackOffice.fxml"));
   //     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/GestionProduitsFinisFront.fxml"));
    // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashboardBack.fxml"));
         //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
      //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionUtilisateur/captcha.fxml"));
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement/AfficherFront.fxml"));
     //   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));

      //       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
     //          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionCour/GestionCour.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionRecomponse/BoutiqueGestion.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("/Icons/LOGO.png");
        scene.getStylesheets().add(getClass().getResource("/style/navbarStyle.css").toExternalForm());

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Arteco");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static class JavaConnector {

    }
}
