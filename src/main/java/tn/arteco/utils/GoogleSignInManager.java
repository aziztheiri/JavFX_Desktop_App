package tn.arteco.utils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.oauth2.model.Userinfoplus;
import tn.arteco.test.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GoogleSignInManager {
    private static final String APPLICATION_NAME = "HyperNova/Arteco/1.0";

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/oauth2_sample");

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** OAuth 2.0 scopes. */
    private static final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email");

    private static Oauth2 oauth2;
    private static GoogleClientSecrets clientSecrets;

    public static Credential authorize() throws Exception {
        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(Main.class.getResourceAsStream("/client-secret.json")));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(
                dataStoreFactory).build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    private static void tokenInfo(String accessToken) throws IOException {
        Tokeninfo tokeninfo = oauth2.tokeninfo().setAccessToken(accessToken).execute();
        System.out.println(tokeninfo.toPrettyString());
        if (!tokeninfo.getAudience().equals(clientSecrets.getDetails().getClientId())) {
            System.err.println("ERROR: audience does not match our client ID!");
        }
    }
    public static void deleteFilesInsideDirectory(Path directoryPath) throws IOException {
        Files.walk(directoryPath)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.err.println("Error deleting file: " + file.toString());
                    }
                });
    }

    public static FileDataStoreFactory getDataStoreFactory() {
        return dataStoreFactory;
    }

    public static HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public static Oauth2 getOauth2() {
        return oauth2;
    }

    public static GoogleClientSecrets getClientSecrets() {
        return clientSecrets;
    }
    public static java.io.File getDataStoreDir(){
        return DATA_STORE_DIR;
    }
    public static JsonFactory getJsonFactory(){
        return JSON_FACTORY;
    }

    public static void setDataStoreFactory(FileDataStoreFactory dataStoreFactory) {
        GoogleSignInManager.dataStoreFactory = dataStoreFactory;
    }

    public static void setHttpTransport(HttpTransport httpTransport) {
        GoogleSignInManager.httpTransport = httpTransport;
    }

    public static void setOauth2(Oauth2 oauth2) {
        GoogleSignInManager.oauth2 = oauth2;
    }

    public static void setClientSecrets(GoogleClientSecrets clientSecrets) {
        GoogleSignInManager.clientSecrets = clientSecrets;
    }
}
