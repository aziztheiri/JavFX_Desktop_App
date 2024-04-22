package tn.arteco.controllers.GestionUtilisateur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.arteco.controllers.GestionUtilisateur.GestionUtilisateurController;
import tn.arteco.models.Roles;
import tn.arteco.models.User;
import tn.arteco.models.UserRequest;
import tn.arteco.services.UserRequestService;
import tn.arteco.services.UserService;

public class RequestCardController {
    @FXML
    Label type;
    @FXML
    Label etat;
    @FXML
    Label date;
    @FXML
    Label username;
    @FXML
    Button accept;
    @FXML
    Button reject;
    @FXML
    Button delete;
    @FXML
    Label id;
    GestionUtilisateurController gestionUtilisateurController;

    public void setGestionUtilisateurController(GestionUtilisateurController gestionUtilisateurController) {
        this.gestionUtilisateurController = gestionUtilisateurController;
    }
    @FXML
    public void initialize(){
       etat.textProperty().addListener((obs,oldvalue,newvalue)->{
           if(!newvalue.matches("[a-zA-Z: ]*pending")){
               accept.setManaged(false);
               reject.setManaged(false);
               accept.setVisible(false);
               reject.setVisible(false);
               delete.setVisible(true);
               delete.setVisible(true);
           }
           else {
               accept.setManaged(true);
               reject.setManaged(true);
               delete.setVisible(false);
               delete.setVisible(false);
           }
       });
       id.setManaged(false);
       id.setVisible(false);
    }
    public void setRequestData(UserRequest req){
        id.setText(String.valueOf(req.getIdRequest()));
        etat.setText("etat: "+req.getEtat());
        date.setText("date : " +req.getDateRequest().toString());
        type.setText("type : "+req.getTypeRequest());
        username.setText(req.getUser().getUsername());
    }
    @FXML
    public void accept(){
        UserRequestService userRequestService=new UserRequestService();
        UserService userService=new UserService();
        UserRequest type=userRequestService.getById(Integer.parseInt(id.getText()));
        User user=userService.getUserByUsername(username.getText());
        if(type.getTypeRequest().equals("activation")){
            user.setEtat(true);
            userService.update(user);
        }
        else if(type.getTypeRequest().equals("artist")){
            user.setRole(Roles.ARTISTE);
            userService.update(user);
        }
        UserRequest userRequest=userRequestService.getByUsername(username.getText());
        userRequest.setEtat("accepted");
        userRequestService.update(userRequest);
        gestionUtilisateurController.refreshReqs();
    }
    @FXML
    public void reject(){
        UserRequestService userRequestService=new UserRequestService();
        UserRequest userRequest=userRequestService.getByUsername(username.getText());
        userRequest.setEtat("rejected");
        userRequestService.update(userRequest);
        gestionUtilisateurController.refreshReqs();
    }
    @FXML
    public void delete(){
        UserRequestService userRequestService=new UserRequestService();
        System.out.println(Integer.parseInt(id.getText()));
        userRequestService.delete(Integer.parseInt(id.getText()));
        gestionUtilisateurController.refreshReqs();
    }
}
