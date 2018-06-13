package Controller;

import Model.Player;
import View.GameView;
import javafx.application.Application;
import javafx.stage.Stage;
import View.MenuView;

public class LegendsOfArcadia extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        primaryStage.setTitle("Legends of Arcardia");
        //primaryStage.setFullScreen(true);
        primaryStage.show();
        GameView.ShowGame(primaryStage, new Player("1",1000), new Player("2",1000));
        //MenuView.ShowMainMenu(primaryStage);
        //Main.startOfOperations();
    }
}
