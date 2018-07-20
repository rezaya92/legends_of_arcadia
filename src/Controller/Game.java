package Controller;

import Model.Player;
import Model.Stuff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Game implements Serializable{
    private String name;
    private Player human;// = new Player("human", 10000);
    private Player goblinChieftain;// = new Player("Goblin Chieftain", 100);
    private Player ogreWarlord;// = new Player("Ogre Warlord", 10000);
    private Player vampireLord;// = new Player("Vampire Lord", 10000);
    private Player lucifer;// = new Player("Lucifer", 10000);
    private int mysticHourGlass;// = 3;
    private ArrayList<Player> opponents;// = new ArrayList<>(Arrays.asList(goblinChieftain,ogreWarlord,vampireLord,lucifer));
    private ArrayList<Stuff> allStuff;// = new ArrayList<>();
    private Map map;

//    public Game(){
//
//    }

    public static Game getCopyOfCurrentGame(){
        Game game = new Game();
        game.human = Main.human;
        game.goblinChieftain = Main.goblinChieftain;
        game.ogreWarlord = Main.ogreWarlord;
        game.vampireLord = Main.vampireLord;
        game.lucifer = Main.lucifer;
        game.mysticHourGlass = Main.mysticHourGlass;
        game.opponents = new ArrayList<>(Arrays.asList(Main.goblinChieftain,Main.ogreWarlord,Main.vampireLord,Main.lucifer));
        game.allStuff = Main.allStuff;
        //TODO make sure nothing got left

        Game copyGame = (Game)DeepCopy.copy(game);
        return copyGame;
    }

    public static void setCurrentGame(Game game){
        Game copyGame = (Game)DeepCopy.copy(game);

        Main.human = copyGame.human;
        Main.goblinChieftain = copyGame.goblinChieftain;
        Main.ogreWarlord = copyGame.ogreWarlord;
        Main.vampireLord = copyGame.vampireLord;
        Main.lucifer = copyGame.lucifer;
        Main.mysticHourGlass = copyGame.mysticHourGlass;
        Main.opponents = copyGame.opponents;
        Main.allStuff = copyGame.allStuff;
    }

    public Player getHuman() {
        return human;
    }

    public Player getGoblinChieftain() {
        return goblinChieftain;
    }

    public Player getOgreWarlord() {
        return ogreWarlord;
    }

    public Player getVampireLord() {
        return vampireLord;
    }

    public Player getLucifer() {
        return lucifer;
    }

    public int getMysticHourGlass() {
        return mysticHourGlass;
    }

    public ArrayList<Player> getOpponents() {
        return opponents;
    }

    public ArrayList<Stuff> getAllStuff() {
        return allStuff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
