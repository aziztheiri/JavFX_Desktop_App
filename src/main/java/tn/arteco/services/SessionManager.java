package tn.arteco.services;

import tn.arteco.models.NonArtiste;
import tn.arteco.models.User;
import tn.arteco.utils.GoogleSignInManager;
import tn.arteco.utils.HashPassword;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SessionManager {
    private static SessionManager instance;
    private static User currentUser;
    private final UserService userService=new UserService();
    private SessionManager(){}
    public static SessionManager getInstance(){
        if(instance==null)
            instance=new SessionManager();
        return instance;
    }
    public boolean login(String username,String password){
        User user = userService.getUserByUsername(username);

        if(user == null){
            System.out.println("unknown account exception");
            return false;
        }
        if(!user.isEtat())
            return false;
        if(user.getPassword().equals(password)){
            currentUser=user;
            return true;
        }
        return false;
    }
    public User getCurrentUser(){return currentUser;}
    public void logout(){
        currentUser=null;
        RememberMeManager.clearCredentials();
        Path directoryPath = Paths.get(System.getProperty("user.home"), ".store/oauth2_sample");

        try {
            GoogleSignInManager.deleteFilesInsideDirectory(directoryPath);
            System.out.println("Files inside directory deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error deleting files inside directory: " + e.getMessage());
        }
    }
    public void deactivateAccount(){
        currentUser.setEtat(false);
        userService.update(currentUser);
        logout();
    }
    public boolean isAuthenticated(){
        return currentUser!=null;
    }
}
