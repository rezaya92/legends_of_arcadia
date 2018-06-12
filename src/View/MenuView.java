package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuView {
    private static Button singlePlayerButton = new Button("Single Player");
    public static void ShowMainMenu(Stage primaryStage){
        singlePlayerButton.setLayoutX(primaryStage.getWidth()/2);
        singlePlayerButton.setLayoutY(primaryStage.getHeight()/2);
        Group mainMenuGroup = new Group();
        Scene scene = new Scene(mainMenuGroup);

        primaryStage.setScene(scene);

        mainMenuGroup.getChildren().add(singlePlayerButton);
    }
}
