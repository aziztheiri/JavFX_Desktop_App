package tn.arteco.controllers.GestionUtilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import tn.arteco.models.Artiste;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.Roles;
import tn.arteco.models.UserRequest;
import tn.arteco.services.*;
import tn.arteco.utils.HashPassword;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UserProfileController {


    @FXML
    private ImageView home;

    @FXML
    BorderPane parentNode;
    @FXML
    BorderPane sideBar;
    @FXML
    ImageView userProfile;
    @FXML
    Label points;
    @FXML
    Label username;
    @FXML
    VBox routingWrapper;
    @FXML
    VBox resetUsernameWrapper;
    @FXML
    VBox userWrapper;
    @FXML
    VBox userWrapper1;
    @FXML
    Label resetErrorL;
    @FXML
    Label resetErrorL1;
    @FXML
    TextField resetField;

    @FXML
    VBox fieldWrapper;
    @FXML
    VBox fieldWrapper1;
    @FXML
    PasswordField passwordTF;
    @FXML
    PasswordField confirmPasswordTF;

    @FXML
    ScrollPane content;
    @FXML
    VBox wrapper;
    @FXML
    VBox resetPasswordWrapper;
    @FXML
    Label toggleInfo;
    @FXML
    Label updateError;
    @FXML
    TextField nameField;
    @FXML
    TextField lastnameField;
    @FXML
    TextField emailField;
    @FXML
    VBox updateWrapper;
    @FXML
    VBox update;
    @FXML
    VBox userProfileChange;
    boolean init = true;
    @FXML
    Label toggleB;
    @FXML
    VBox artist;
    @FXML
    Label artistL;
    @FXML
    VBox activationWrapper;
    @FXML
    HBox confirmWrapper;
    @FXML
    HBox dashboard;
    @FXML
    public void initialize() {


        confirmWrapper.setManaged(false);
        confirmWrapper.setManaged(false);
        dashboard.setVisible(false);
        dashboard.setManaged(false);
        userProfile.setImage(new Image( SessionManager.getInstance().getCurrentUser().getImageUrl()));
        Rectangle clip=new Rectangle(userProfile.getFitWidth(),userProfile.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        userProfile.setClip(clip);
        username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
        if (SessionManager.getInstance().getCurrentUser() instanceof NonArtiste)
            points.setText(String.valueOf(((NonArtiste) SessionManager.getInstance().getCurrentUser()).getPoints()));
        if (SessionManager.getInstance().getCurrentUser().getRole().equals(Roles.NONARTISTE)) {
            artist.setVisible(true);
            artist.setManaged(true);
        }
        else {
            artist.setVisible(false);
            artist.setManaged(false);
        }
        if ((SessionManager.getInstance().getCurrentUser().getRole().equals(Roles.ADMIN))) {
            home.setOnMouseClicked((e)->{});
            dashboard.setVisible(true);
            dashboard.setManaged(true);
        }
        routingWrapper.heightProperty().addListener((obs, oldval, newval) -> {
            routingWrapper.setSpacing(newval.doubleValue() / 15);
        });
        parentNode.widthProperty().addListener((obs, oldval, newval) -> {
            sideBar.setPrefWidth(newval.doubleValue() / 5);
            content.setPrefWidth(newval.doubleValue() * 4 / 5);
        });
        content.widthProperty().addListener((obs, oldval, newval) -> {
            wrapper.setPrefWidth(newval.doubleValue() - 10);
        });
        DropShadow dropShadow = new DropShadow(15, Color.GREY);
        resetUsernameWrapper.setEffect(dropShadow);
        resetPasswordWrapper.setEffect(dropShadow);
        update.setEffect(dropShadow);
        artist.setEffect(dropShadow);
        userProfileChange.setEffect(dropShadow);
        userWrapper.setVisible(false);
        userWrapper.setManaged(false);
        userWrapper1.setVisible(false);
        userWrapper1.setManaged(false);
        confirmWrapper.setManaged(false);
        confirmWrapper.setVisible(false);
        resetUsernameWrapper.setPrefHeight(45);
        resetPasswordWrapper.setPrefHeight(45);
        UserRequestService userRequestService = new UserRequestService();
        List<UserRequest> usernameResetList = userRequestService.getUsernameResetByusername(SessionManager.getInstance().getCurrentUser().getUsername());
        List<UserRequest> passwordResetList = userRequestService.getPasswordResetByUsername(SessionManager.getInstance().getCurrentUser().getUsername());
        if (!usernameResetList.isEmpty()) {
            LocalDate lastUsernameResetDate = userRequestService.sortBydate(usernameResetList).get(0).getDateRequest().toLocalDate();
            LocalDate currentDate = LocalDate.now();
            if (ChronoUnit.DAYS.between(lastUsernameResetDate, currentDate) > 15) {
                resetErrorL.setVisible(false);
                resetErrorL.setManaged(false);
                fieldWrapper.setManaged(true);
                fieldWrapper.setVisible(true);
            } else {
                resetErrorL.setVisible(true);
                resetErrorL.setManaged(true);
                resetErrorL.setText(resetErrorL.getText() + " " + String.valueOf(15 - ChronoUnit.DAYS.between(lastUsernameResetDate, currentDate)) + " days");
                fieldWrapper.setVisible(false);
                fieldWrapper.setManaged(false);
            }
        } else {
            resetErrorL.setVisible(false);
            resetErrorL.setManaged(false);
            fieldWrapper.setManaged(true);
            fieldWrapper.setVisible(true);
        }
        if (!passwordResetList.isEmpty()) {
            LocalDate lastPasswordResetDate = userRequestService.sortBydate(usernameResetList).get(0).getDateRequest().toLocalDate();
            LocalDate currentDate = LocalDate.now();
            if (ChronoUnit.DAYS.between(lastPasswordResetDate, currentDate) > 15) {
                resetErrorL1.setVisible(false);
                resetErrorL1.setManaged(false);
                fieldWrapper1.setManaged(true);
                fieldWrapper1.setVisible(true);
            } else {
                resetErrorL1.setVisible(true);
                resetErrorL1.setManaged(true);
                resetErrorL1.setText(resetErrorL1.getText() + " " + String.valueOf(15 - ChronoUnit.DAYS.between(lastPasswordResetDate, currentDate)) + " days");
                fieldWrapper1.setVisible(false);
                fieldWrapper1.setManaged(false);
            }
        } else {
            resetErrorL1.setVisible(false);
            resetErrorL1.setManaged(false);
            fieldWrapper1.setManaged(true);
            fieldWrapper1.setVisible(true);
        }
        updateWrapper.setManaged(false);
        updateWrapper.setVisible(false);
        updateError.setManaged(false);
        updateError.setVisible(false);
        nameField.setText(SessionManager.getInstance().getCurrentUser().getNom());
        lastnameField.setText(SessionManager.getInstance().getCurrentUser().getPrenom());
        emailField.setText(SessionManager.getInstance().getCurrentUser().getEmail());
        update.setPrefHeight(45);
        activationWrapper.setPrefHeight(45);
        if(userRequestService.pendingArtistRequestByUsername(SessionManager.getInstance().getCurrentUser().getUsername())){
            artistL.setText("Request to become artist is pending.");
            artistL.setOnMouseClicked((e)->{});
        }else{
            artistL.setOnMouseClicked((e)->{sendArtistRequest();});
            artistL.setText("Become an artist?");
        }
    }

    @FXML
    public void disconnect() {
        SessionManager.getInstance().logout();

    try{
        Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
        Scene scene = parentNode.getScene();
        if (scene != null) {
            scene.setRoot(root  );
        } else {
            System.err.println("Scene is null.");
        }
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
    }
    }

    @FXML
    public void routeToHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void showUsernameReset() {
        userWrapper.setManaged(!userWrapper.isManaged());
        userWrapper.setVisible(!userWrapper.isVisible());
        if (userWrapper.isManaged()) {
            resetUsernameWrapper.setPrefHeight(135);
        } else if (resetErrorL.isManaged() && userWrapper.isManaged()) {
            resetUsernameWrapper.setPrefHeight(60);
        } else {
            resetUsernameWrapper.setPrefHeight(45);
        }
    }

    public void showUpdate() {
        updateWrapper.setManaged(!updateWrapper.isManaged());
        updateWrapper.setVisible(!updateWrapper.isVisible());
        if (updateWrapper.isManaged()) {
            update.setPrefHeight(190);
        } else if (updateError.isManaged() && updateWrapper.isManaged()) {
            update.setPrefHeight(60);
        } else {
            update.setPrefHeight(45);
        }
    }

    @FXML
    public void showPasswordReset() {
        userWrapper1.setManaged(!userWrapper1.isManaged());
        userWrapper1.setVisible(!userWrapper1.isVisible());
        if (userWrapper1.isManaged()) {
            resetPasswordWrapper.setPrefHeight(145);
        } else if (resetErrorL1.isManaged() && userWrapper1.isManaged()) {
            resetPasswordWrapper.setPrefHeight(60);
        } else {
            resetPasswordWrapper.setPrefHeight(45);
        }
    }
    @FXML
    public void showDeactivate(){
        confirmWrapper.setManaged(!confirmWrapper.isManaged());
        confirmWrapper.setVisible(!confirmWrapper.isVisible());
        if(confirmWrapper.isManaged()){
            activationWrapper.setPrefHeight(105);
        }
        else{
            activationWrapper.setPrefHeight(45);
        }
    }

    @FXML
    public void resetUsername() {
        UserRequestService userRequestService = new UserRequestService();
        UserService userService = new UserService();

        if (userService.usernameExists(resetField.getText())) {
            resetErrorL.setManaged(true);
            resetErrorL.setVisible(true);
            resetErrorL.setText("username already exists");
        } else {
            userRequestService.add(new UserRequest(0, "usernameReset", "accepted", userService.getUserByUsername(SessionManager.getInstance().getCurrentUser().getUsername())));
            SessionManager.getInstance().getCurrentUser().setUsername(resetField.getText());
            username.setText(SessionManager.getInstance().getCurrentUser().getUsername());
            if (RememberMeManager.retrieveCredentials() != null) {
                RememberMeManager.clearCredentials();
                RememberMeManager.saveCredentials(SessionManager.getInstance().getCurrentUser().getUsername(), SessionManager.getInstance().getCurrentUser().getPassword());
            }
            userService.update(SessionManager.getInstance().getCurrentUser());
            resetErrorL.setText("Unable to reset username : wait for 15 days");
            resetErrorL.setVisible(true);
            resetErrorL.setManaged(true);
            fieldWrapper.setVisible(false);
            fieldWrapper.setManaged(false);

        }
    }

    @FXML
    public void resetPassword() {
        if (!passwordTF.getText().
                equals(confirmPasswordTF.getText()) || passwordTF.getText().isEmpty()) {
            resetErrorL1.setManaged(true);
            resetErrorL1.setVisible(true);
            resetErrorL1.setText("Passwords are not equal");
        } else {
            SessionManager.getInstance().getCurrentUser().setPassword(HashPassword.hashPassword(passwordTF.getText()));
            UserService userService = new UserService();
            userService.update(SessionManager.getInstance().getCurrentUser());
            UserRequestService userRequestService = new UserRequestService();
            userRequestService.add(new UserRequest(0, "passwordReset", "accepted", userService.getUserByUsername(SessionManager.getInstance().getCurrentUser().getUsername())));
            TFAFirstLogin.storeUsername(SessionManager.getInstance().getCurrentUser().getUsername(), false);
            SessionManager.getInstance().logout();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
                parentNode.getScene().setRoot(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void update() {
        if (nameField.getText().isEmpty() || lastnameField.getText().isEmpty()) {
            updateError.setText("name is empty");
            updateError.setManaged(true);
            updateError.setVisible(true);
        } else if (!emailField.getText().matches("[A-Za-z.]+@[A-Za-z]+.[a-zA-Z]{2,7}")) {
            updateError.setText("invalid email");
            updateError.setManaged(true);
            updateError.setVisible(true);
        } else if (emailField.getText().isEmpty()) {
            updateError.setText("email is empty");
            updateError.setManaged(true);
            updateError.setVisible(true);
        }
        else{
            UserService userService=new UserService();
            SessionManager.getInstance().getCurrentUser().setNom(nameField.getText());
            SessionManager.getInstance().getCurrentUser().setPrenom(lastnameField.getText());
            SessionManager.getInstance().getCurrentUser().setEmail(emailField.getText());
            userService.update(SessionManager.getInstance().getCurrentUser());
            updateError.setManaged(false);
            updateError.setVisible(false);
            showUpdate();
        }
    }
    @FXML
    public void sendArtistRequest(){
        UserRequestService userRequestService = new UserRequestService();
        userRequestService.add(new UserRequest(0,"artist","pending",SessionManager.getInstance().getCurrentUser()));
        artistL.setText("Request to become artist is pending.");
    }
    @FXML
    public void fileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG image", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG image", "*.JPG")
            );
        File selectedFile = fileChooser.showOpenDialog(username.getScene().getWindow());
        String imageUrl;
        if (selectedFile != null) {
            String baseUrl = "http://127.0.0.1/images/";
            String imageName = selectedFile.getName();
            imageUrl = baseUrl + imageName;

            try{
                //todo change to c:/ for inclusivity
                Files.copy(Paths.get(selectedFile.getAbsolutePath()),Paths.get("E:/xampp/htdocs/images/"+imageName));
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
            userProfile.setImage(new Image(imageUrl));
            UserService userService=new UserService();
            SessionManager.getInstance().getCurrentUser().setImageUrl(imageUrl);
            userService.update(SessionManager.getInstance().getCurrentUser());
            System.out.println("Image URL: " + imageUrl);
        } else {
            System.out.println("No file has been selected");
        }
    }
    @FXML
    public void deactivate(){
        SessionManager.getInstance().deactivateAccount();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    public void routeToDashboard(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DashboardBack.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
