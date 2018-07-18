package Controller;

import Model.Player;
import View.GameView.GameView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import View.MenuView;

import java.util.Random;

import static Controller.Main.human;
import static Controller.Main.lucifer;

public class LegendsOfArcadia extends Application {
    static Stage pStage;
    private static Map map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        PreProcess.preProcess();//TODO delete duplicate in Main
        pStage = primaryStage;
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        primaryStage.setTitle("Legends of Arcadia");
        primaryStage.getIcons().add(new Image(LegendsOfArcadia.class.getResourceAsStream("icon.png")));

        preProcessEventHandling(primaryStage);
        MenuView.showMainMenu();
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
