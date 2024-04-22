package tn.arteco.services;

import com.mailjet.client.errors.MailjetException;

import javax.mail.MessagingException;
import java.util.Random;
import java.util.prefs.Preferences;

public class TFAFirstLogin {
    private static final Preferences preferences=Preferences.userRoot().node("/tn/arteco/services");
    public static void storeUsername(String username,boolean isFirstTimeLogin){
        preferences.putBoolean(username,isFirstTimeLogin);
    }
    public static boolean isFirstTimeLogin(String username){
        return preferences.getBoolean(username,true);
    }
    public static String getRandomNumberString(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
    public static void sendAuthEmail(String email,String description,String randomNumber){
            try{
                EmailSender.sendEmail(email,description,randomNumber);
            }catch (MailjetException e){
                System.err.println(e.getMessage());
            }
    }
}
