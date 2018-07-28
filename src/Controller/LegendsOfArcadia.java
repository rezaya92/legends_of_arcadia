package Controller;

import Model.Shop;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import View.MenuView;

import java.util.ArrayList;

import static Controller.Main.human;

public class LegendsOfArcadia extends Application {
    public static ArrayList<Game> customGames = new ArrayList<>();
    static Stage pStage;
    private static Map map;

    public static void main(String[] args) {
        String path = LegendsOfArcadia.class.getResource("Suspicion.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        PreProcess.preProcess();//TODO delete duplicate in Main


        Game game = Game.getCopyOfCurrentGame();
        game.setName("Main Game");
        customGames.add(game);

//        System.out.println(game.getMysticHourGlass());
//        game.getAllStuff().remove(0);
//        new Amulet(new GeneralizedSpell(null, "ss", "sq"), 10);
//        //game.getAllStuff().add(new GeneralizedSpell(null, "ss", "sq"), 10);
//        System.out.println(Main.allStuff);
//        System.out.println(Main.allStuff.size());
//        System.out.println(game.getAllStuff().size());


        pStage = primaryStage;
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        primaryStage.setTitle("Legends of Arcadia");
        primaryStage.getIcons().add(new Image(LegendsOfArcadia.class.getResourceAsStream("icon.png")));

        preProcessEventHandling(primaryStage);
        MenuView.showMainMenu();
        //Main.editInventory();
        //Main.joinGame("127.0.0.1",656);
        //Main.hostGame(656);
        primaryStage.show();
    }

    private void preProcessEventHandling(Stage primaryStage){
        MenuView.getSinglePlayerButton().setOnMouseClicked(event -> {
            Game.setCurrentGame(LegendsOfArcadia.customGames.get(0));
            beforeStartNewAdventurePreProcess();
            map = new Map(primaryStage, 1);
        });

        MenuView.getMultiPlayerButton().setOnMouseClicked(event -> MenuView.showMultiplayerMenu());

        MenuView.getExitButton().setOnMouseClicked(event -> {
            primaryStage.close();//correct? (can change to system.exit(0))
        });
    }

    public static void beforeStartNewAdventurePreProcess(){
        Battle.humanDefaultDeckCardBeforeCustomization = new ArrayList<>(human.getDefaultDeckCards());
        Battle.humanDeckCardBeforeCustomization = new ArrayList<>(human.getDeckCards());
        Battle.humanItemsBeforeCustomization = new ArrayList<>(human.getItems());
        try {
            Battle.shopBeforeCustomization = (Shop) human.getShop().clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        Battle.humanInventoryBeforeCustomization = new ArrayList<>(human.getInventoryCards());
        Battle.humanGilBeforeCustomization = human.getGil();
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    public static Map getMap() {return map;}

    public static void setMap(Map map) {
        LegendsOfArcadia.map = map;
    }
}
