package Controller;

import Model.Amulet;
import Model.Spell.GeneralizedSpell;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import View.MenuView;

public class LegendsOfArcadia extends Application {
    static Stage pStage;
    private static Map map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        PreProcess.preProcess();//TODO delete duplicate in Main



//        System.out.println(Main.mysticHourGlass);

        Game game = Game.getCopyOfCurrentGame();
        game.setName("Main Game");
        Main.customGames.add(game);

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
        //MenuView.showMainMenu();
        //Main.editInventory();
        //Main.joinGame("127.0.0.1",656);
        Main.hostGame(656);
        primaryStage.show();
    }

    private void preProcessEventHandling(Stage primaryStage){
        MenuView.getSinglePlayerButton().setOnMouseClicked(event -> {
            try {
                Main.afterMatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
            map = new Map(primaryStage, 1);

        });

        MenuView.getExitButton().setOnMouseClicked(event -> {
            primaryStage.close();//correct? (can change to system.exit(0))
        });
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    public static Map getMap() {return map;}
}
