package tn.arteco.game;

import tn.arteco.controllers.gestionRecompense.BoutiqueController;
import tn.arteco.models.User;

public class SnakeGame {

    BoutiqueController bc;
    public static int gameResult=0;

        public void startGame(int id){
               GameFrame g= new GameFrame(id);
               g.setParent(this);
        }


        public void setParent(BoutiqueController bc)
        {
            this.bc=bc;
        }

}