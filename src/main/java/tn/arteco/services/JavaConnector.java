package tn.arteco.services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JavaConnector {
    private final BooleanProperty captchaVerification = new SimpleBooleanProperty(false);

    public BooleanProperty captchaVerificationProperty() {
        return captchaVerification;
    }

    public boolean isCaptchaVerified() {
        return captchaVerification.get();
    }

    public void setCaptchaVerified(boolean value) {
        captchaVerification.set(value);
    }
    public void onRecaptchaCompleted(String response) {
        String responseToken = response;
        String secretKey = "6LfBeYgpAAAAAMmdHRjPo5JAiTe4fi_OzCcNPiFz";
        setCaptchaVerified(verifyRecaptcha(responseToken, secretKey));

    }
    public static boolean verifyRecaptcha(String responseToken, String secretKey) {
        try {
            String encodedResponse = URLEncoder.encode(responseToken, "UTF-8");
            String encodedSecret = URLEncoder.encode(secretKey, "UTF-8");
            String verificationUrl = "https://www.google.com/recaptcha/api/siteverify" +
                    "?secret=" + encodedSecret +
                    "&response=" + encodedResponse;
            URL url = new URL(verificationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String jsonResponse = response.toString();
            return jsonResponse.contains("\"success\": true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
