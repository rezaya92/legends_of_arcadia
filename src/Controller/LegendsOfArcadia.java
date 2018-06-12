package Controller;

import javafx.application.Application;
import javafx.stage.Stage;
import View.MenuView;

public class LegendsOfArcadia extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        MenuView.ShowMainMenu(primaryStage);
        //Main.startOfOperations();
    }
}
