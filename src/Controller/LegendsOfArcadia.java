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
        primaryStage.setTitle("Legends of Arcadia");

        preProcessEventHandling(primaryStage);
        //primaryStage.setFullScreen(true);
        MenuView.showMainMenu(primaryStage);
        //primaryStage.setFullScreen(true);
        //GameView.ShowGame(primaryStage, new Player("1",1000), new Player("2",1000));
        //MenuView.ShowMainMenu(primaryStage);

        primaryStage.show();
        //Main.startOfOperations();
    }

    private void preProcessEventHandling(Stage primaryStage){
        MenuView.getSinglePlayerButton().setOnMouseClicked(event -> {
            try {
                Main.afterMatch(primaryStage);
                //TODO change to Map.enterMap(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        MenuView.getExitButton().setOnMouseClicked(event -> {
            primaryStage.close();//correct? (can change to system.exit(0))
        });
    }
}
