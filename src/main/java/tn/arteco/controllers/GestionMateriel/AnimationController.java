package tn.arteco.controllers.GestionMateriel;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationController {

    static String nomCaller="none";
    int x=30;
    int y=-20;
    //int ms=350;
    int ms=500;
    public void moveNavBar(Node node1,Node node2)
    {

           TranslateTransition translate = new TranslateTransition();
           translate.setNode(node1);
           //translate.setNode(materielIcon);
           translate.setDuration(Duration.millis(ms));
           //translate.setFromX(0);
           translate.setToX(x);

           TranslateTransition iconTranslate = new TranslateTransition();
           iconTranslate.setNode(node2);
           iconTranslate.setDuration(Duration.millis(ms));
           iconTranslate.setToX(x);

           ParallelTransition pt = new ParallelTransition(translate, iconTranslate);
           pt.play();
       }

       public void moveBackNavBar(Node node1,Node node2)
       {

           TranslateTransition translate = new TranslateTransition();
           translate.setNode(node1);
           //translate.setNode(materielIcon);
           translate.setDuration(Duration.millis(ms));
           translate.setToX(-x);


           TranslateTransition iconTranslate = new TranslateTransition();
           iconTranslate.setNode(node2);
           iconTranslate.setDuration(Duration.millis(ms));
           iconTranslate.setToX(-x);

           ParallelTransition pt = new ParallelTransition(translate, iconTranslate);
           pt.play();
       }

       public int testMoveBack(int etat,Node node1,Node node2)
       {
           int res=0;
           if(etat==1) {
               moveBackNavBar(node1, node2);
           }
           return res;
       }

       public void animerPanier(Node node1)
       {
           TranslateTransition translateTransition=new TranslateTransition();
           translateTransition.setNode(node1);

           translateTransition.setDuration(Duration.millis(ms));
           translateTransition.setByY(y);
           translateTransition.setAutoReverse(true);
           translateTransition.play();
       }
       public void animateButton(Node node1)
       {
           RotateTransition rt=new RotateTransition();
           rt.setNode(node1);
           rt.setFromAngle(0);
           rt.setByAngle(10);
           rt.setDuration(Duration.millis(220));
           rt.setCycleCount(Transition.INDEFINITE);
           rt.setInterpolator(Interpolator.EASE_BOTH);
           rt.setAutoReverse(true);
           rt.play();
           /*TranslateTransition tt=new TranslateTransition();
           tt.setNode(node1);
           tt.setFromX(0);
           tt.setByX(10);
           tt.setDuration(Duration.millis(500));
           rt.setCycleCount(Transition.INDEFINITE);



PauseTransition pt=new PauseTransition(Duration.millis(1000));
           // Sequential transition combining rotate and translate
           SequentialTransition st = new SequentialTransition(rt, tt);
           st.setOnFinished(event -> pt.play());
            st.play();
           //ParallelTransition pt=new ParallelTransition(tt,tt1);
           //pt.play();*/

       }
       public void stopButtonAnimation(Node node)
       {
           RotateTransition rt=new RotateTransition();
           rt.setNode(node);
           rt.setByAngle(0);
           rt.setCycleCount(Transition.INDEFINITE);
           rt.play();
       }

       public static String getNomCaller()
       {
            return nomCaller;
       }

       public static void setNomCaller(String nom)
       {
            nomCaller=nom;
       }
}
