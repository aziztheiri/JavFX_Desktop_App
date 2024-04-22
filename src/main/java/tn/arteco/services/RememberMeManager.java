package tn.arteco.services;

import java.util.prefs.Preferences;

public class RememberMeManager {
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final Preferences preferences = Preferences.userRoot().node("/tn/arteco/services");
    public static void saveCredentials(String username,String password){
        preferences.put(USERNAME_KEY,username);
        preferences.put(PASSWORD_KEY,password);
    }
    public static String[] retrieveCredentials() {
        String username = preferences.get(USERNAME_KEY, null);
        String password = preferences.get(PASSWORD_KEY, null);
        if (username != null && password != null) {
            return new String[]{username, password};
        } else {
            return null;
        }
    }

    public static void clearCredentials() {
        preferences.remove(USERNAME_KEY);
        preferences.remove(PASSWORD_KEY);
    }

}
