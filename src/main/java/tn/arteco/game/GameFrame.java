package tn.arteco.game;

import tn.arteco.controllers.gestionRecompense.BoutiqueController;
import tn.arteco.models.Accomplissement;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.TransactionP;
import tn.arteco.models.User;
import tn.arteco.services.AccomplissementService;
import tn.arteco.services.SessionManager;
import tn.arteco.services.TransactionPService;
import tn.arteco.services.UserService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameFrame extends JFrame {
    UserService uS=new UserService();
    TransactionPService tPS=new TransactionPService();
    AccomplissementService aS=new AccomplissementService();
    BoutiqueController bc;
     User player;
     SnakeGame sn;

    private Clip clip;  // Add a field to store the Clip object

    GameFrame(int id) {
        this.player=this.uS.getById(id);
        String filepath = "gameMusic.wav";
        playMusic(filepath);
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        setLocationRelativeTo(null);

        // Add a window listener to stop the music when the frame is closed
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                stopMusic();
                uS.updateUserPoints(player.getUserId(),((NonArtiste)(player)).getPoints()+SnakeGame.gameResult);

                if(player instanceof NonArtiste na)
                {
                    int points=na.getPoints();
                    na.setPoints(SnakeGame.gameResult+points);
                }
                Accomplissement a = aS.getByNonArtistId(player.getUserId());
                TransactionP t=new TransactionP(0,a,new Date(),SnakeGame.gameResult,true);
                tPS.add(t);
                sn.bc.resetPoints(SnakeGame.gameResult);
            }
        });

    }

    public void playMusic(String location) {
        try {
            File musicPath = new File(location);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find file: " + musicPath.getPath());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
public void setParent(SnakeGame sg)
{
this.sn=sg;
}
}
