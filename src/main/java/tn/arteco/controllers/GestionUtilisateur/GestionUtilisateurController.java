package tn.arteco.controllers.GestionUtilisateur;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tn.arteco.models.User;
import tn.arteco.models.UserRequest;
import tn.arteco.services.SessionManager;
import tn.arteco.services.UserRequestService;
import tn.arteco.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GestionUtilisateurController {

    @FXML
    private Pane waitingpane;
    @FXML
    BorderPane parentNode;
    @FXML
    BorderPane sideBar;
    @FXML
    BorderPane midWrapper;
    @FXML
    VBox routingWrapper;
    @FXML
    FlowPane topWrapper;
    @FXML
    ScrollPane contentWrapper;
    @FXML
    ComboBox<String> combo;
    @FXML
    FlowPane flow;
   /* @FXML
    HBox but;*/
    @FXML
    ComboBox<String> reqCombo;
    @FXML
    HBox reqWrapper;
    @FXML
    HBox sortUserWrapper;
    @FXML
    ComboBox<String> sortUser;
    @FXML
    HBox reqSortWrapper;
    @FXML
    ComboBox<String> reqSort;
    @FXML
    TextField searchField;
    boolean init=true;
    @FXML
    public void initialize(){
        if(init){
            parentNode.setPrefWidth(parentNode.getWidth()+850);
            parentNode.setPrefHeight(parentNode.getHeight());
            init=false;
        }
        /*parentNode.widthProperty().addListener((obs,oldval,newval)->{
            sideBar.setPrefWidth(newval.doubleValue()/5);
        });*/

       /* routingWrapper.heightProperty().addListener((obs,oldval,newval)->{
            routingWrapper.setSpacing(newval.doubleValue()/15);
        });*/
        contentWrapper.widthProperty().addListener((obs,oldval,newval)->{
            flow.setPrefWidth(newval.doubleValue());

        });
        contentWrapper.heightProperty().addListener((obs,oldval,newval)->{
            flow.setPrefHeight(newval.doubleValue());
        });
        String[] list= new String[]{"all","active", "inactive", "artists", "non artists","requests"};
        String[] reqList=new String[]{"pending","accepted","rejected","activation","all","usernameReset","passwordReset","artistRequests"};
        String[] reqsort=new String[]{"date","username"};
        ObservableList<String> reqObs= FXCollections.observableArrayList(reqList);
        ObservableList<String> obs= FXCollections.observableArrayList(list);
        ObservableList<String> obs4= FXCollections.observableArrayList(reqsort);
        reqSort.setItems(obs4);
        reqSort.setValue("date");
        String[] list1= new String[]{"alphabetically","by mats", "by factures", "by quizes","by prods"};
        ObservableList<String> obs1= FXCollections.observableArrayList(list1);
        sortUser.setItems(obs1);
        sortUser.setValue("alphabetically");
        reqCombo.setValue("all");
        reqCombo.setItems(reqObs);
        combo.setItems(obs);
        combo.setValue("active");
        reqWrapper.setManaged(false);
        reqWrapper.setVisible(false);
        reqSortWrapper.setManaged(false);
        reqSortWrapper.setVisible(false);
        combo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("requests")){
                reqWrapper.setManaged(false);
                reqWrapper.setVisible(false);
                reqSortWrapper.setManaged(false);
                reqSortWrapper.setVisible(false);
                sortUserWrapper.setManaged(true);
                sortUserWrapper.setVisible(true);
            }
            else{
                reqWrapper.setManaged(true);
                reqWrapper.setVisible(true);
                reqSortWrapper.setManaged(true);
                reqSortWrapper.setVisible(true);
                sortUserWrapper.setManaged(false);
                sortUserWrapper.setVisible(false);
            }
            if(newValue.equals("non artists")){
                String[] list2= new String[]{"alphabetically","by mats", "by factures", "by quizes"};
                ObservableList<String> obs3= FXCollections.observableArrayList(list2);
                sortUser.setItems(obs3);
            }
            else{
                String[] list3= new String[]{"alphabetically","by mats", "by factures", "by quizes","by prods"};
                ObservableList<String> obs3= FXCollections.observableArrayList(list3);
                sortUser.setItems(obs3);
            }
            refresh();
        });
        reqCombo.valueProperty().addListener((obse,oldvalue,newvalue)->{
            refreshReqs();
        });
        sortUser.valueProperty().addListener((obss,oldvalue,newvalue)->{
            refresh();
        });
        reqSort.valueProperty().addListener((obss,oldvalue,newvalue)->{
            refreshReqs();
        });
        searchField.textProperty().addListener((obss,oldval,newval)->{
            if(combo.getValue().equals("requests")){
                refreshReqs();
            }else{
                refresh();
            }
        });
        UserService userService=new UserService();
        setGridUsers(userService.sortByUsername(userService.getActivate()));
    }
    public void setGridUsers(List<User> users){
        for (User user:users){
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/GestionUtilisateur/UserCard.fxml"));
            try {
                VBox vbox = fxmlLoader.load();
                vbox.setEffect(new DropShadow(10, Color.BLACK));
                UserCardController userCardController = fxmlLoader.getController();
                userCardController.setGestionUtilisateurController(this);
                userCardController.setUserData(user);
                flow.getChildren().add(vbox);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void setGridRequests(List<UserRequest> reqs){
        for (UserRequest req:reqs){
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/GestionUtilisateur/RequestCard.fxml"));
            try {
                VBox vbox = fxmlLoader.load();
                vbox.setEffect(new DropShadow(10, Color.BLACK));
                RequestCardController requestCardController = fxmlLoader.getController();
                requestCardController.setGestionUtilisateurController(this);
                requestCardController.setRequestData(req);
                flow.getChildren().add(vbox);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @FXML
    public void refresh(){
        List<User> list=null;
        List<UserRequest> reqList=null;
        UserService userService=new UserService();
        UserRequestService userRequestService=new UserRequestService();
        if(combo.getValue().equals("active")){
            list=userService.getActivate();
            filterUsers(list, userService);
        }
        if(combo.getValue().equals("inactive")){
            list=userService.getInactivate();
            filterUsers(list, userService);
        }
        if(combo.getValue().equals("artists")){
            list=userService.getArtists();
            filterUsers(list, userService);
        }
        if(combo.getValue().equals("non artists")){
            list=userService.getNonArtists();
            if("alphabetically".equals(sortUser.getValue())){
                list=userService.sortByUsername(list);
            }
            if("by mats".equals(sortUser.getValue())){
                list=userService.sortBymats(list);
            }
            if("by factures".equals(sortUser.getValue())){
                list=userService.sortByFact(list);
            }
            if("by quizes".equals(sortUser.getValue())){
                list=userService.sortByQuiz(list);
            }
            if("by participation".equals(sortUser.getValue())){
                list=userService.sortByParticipation(list);
            }
            list = list.stream()
                    .filter(user -> user.getUsername().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            user.getPrenom().toLowerCase().contains(searchField.getText().toLowerCase())||
                            user.getNom().toLowerCase().contains(searchField.getText().toLowerCase())||
                            user.getEmail().toLowerCase().contains(searchField.getText().toLowerCase())
                    )
                    .collect(Collectors.toList());

            flow.getChildren().clear();
            setGridUsers(list);
        }
        if(combo.getValue().equals("all")){
            list=userService.getAll();
            filterUsers(list, userService);
        }
        if(combo.getValue().equals("requests"))     {
            reqList=userRequestService.sortBydate(userRequestService.getAll());
            flow.getChildren().clear();
            setGridRequests(reqList);
        }

    }
    private void filterUsers(List<User> list, UserService userService) {
        list = list.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                        user.getPrenom().toLowerCase().contains(searchField.getText().toLowerCase())||
                        user.getNom().toLowerCase().contains(searchField.getText().toLowerCase())||
                        user.getEmail().toLowerCase().contains(searchField.getText().toLowerCase())
                )
                .collect(Collectors.toList());
        if("alphabetically".equals(sortUser.getValue())){
            list=userService.sortByUsername(list);
        }
        if("by mats".equals(sortUser.getValue())){
            list=userService.sortBymats(list);
        }
        if("by factures".equals(sortUser.getValue())){
            list=userService.sortByFact(list);
        }
        if("by quizes".equals(sortUser.getValue())){
            list=userService.sortByQuiz(list);
        }

        if("by prods".equals(sortUser.getValue())){
            list=userService.sortByProds(list);
        }
        flow.getChildren().clear();
        setGridUsers(list);
    }
    public void filterReqs(List<UserRequest> list,UserRequestService userRequestService){
        list = list.stream()
                .filter(req -> req.getUser().getUsername().toLowerCase().contains(searchField.getText().toLowerCase()))
                .collect(Collectors.toList());
        if("username".equals(reqSort.getValue())){
            list=userRequestService.sortByUsername(list);
        }
        if("date".equals(reqSort.getValue())){
            list=userRequestService.sortBydate(list);
        }
        flow.getChildren().clear();
        setGridRequests(list);
    }
    public void refreshReqs(){
        List<UserRequest> reqList=null;
        UserRequestService userRequestService=new UserRequestService();
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("pending")){
            reqList=userRequestService.getPending();
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("accepted")){
            reqList=userRequestService.getAccepeted();
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("activation")){
            reqList=userRequestService.getActivationRequests();
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("rejected")){
            reqList=userRequestService.getRejected();
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("all")){
            reqList=userRequestService.sortBydate(userRequestService.getAll());
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("usernameReset")){
            reqList=userRequestService.sortBydate(userRequestService.getUsernameReset());
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("passwordReset")){
            reqList=userRequestService.sortBydate(userRequestService.getPasswordReset());
            filterReqs(reqList,userRequestService);
        }
        if(reqCombo.managedProperty().getValue() && reqCombo.getValue().equals("artistRequests")){
            reqList=userRequestService.sortBydate(userRequestService.getArtistRequest());
            filterReqs(reqList,userRequestService);
        }
    }
    @FXML
    public void disconnect(MouseEvent event){
        SessionManager.getInstance().logout();
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/GestionUtilisateur/LoginForm.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }


    @FXML
    public void routeToProfile(){
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionUtilisateur/UserProfile.fxml"));
            Parent root = loader.load();
            parentNode.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void naviguerVersDashboard(ActionEvent event) {
        try  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashBoardBack.fxml"));
            Parent root = loader.load();
            parentNode.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void materielBackClicked(ActionEvent event) {

        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionMateriel/GestionMaterielBack.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersProduitsBack(ActionEvent event) {
        waitingpane.setVisible(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsFinisFXML/BackOffice.fxml"));
            new Thread(() -> {
                try {
                    Parent root = loader.load();
                    Platform.runLater(() -> {
                        waitingpane.getScene().setRoot(root);
                        waitingpane.setVisible(false);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void CoursClicked(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/gestionCour/BackGestionCour.fxml"));
            parentNode.getScene().setRoot(root);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void naviguerVersEventBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionEvenement/ajouterevenement.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void naviguerVersBoutique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionRecomponse/BoutiqueGestion.fxml"));
            parentNode.getScene().setRoot(root);
        } catch (IOException ex) {
            //System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
