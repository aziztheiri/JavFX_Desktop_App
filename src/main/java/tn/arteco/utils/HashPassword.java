package tn.arteco.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
    public static String hashPassword(String password){
        StringBuilder hexString=new StringBuilder();
        try {
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            byte[] hash=md.digest(password.getBytes());

            for(byte b:hash){
                hexString.append(String.format("%02x",b));
            }
        }catch (NoSuchAlgorithmException e){
            System.err.println(e.getMessage());
        }
        return hexString.toString();
    }
}
