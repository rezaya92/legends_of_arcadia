package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuView {
    private static Button singlePlayerButton = new Button("Single Player");
    public static void ShowMainMenu(Stage primaryStage){
        singlePlayerButton.setLayoutX(100);
        singlePlayerButton.setLayoutY(100);
        Group mainMenuGroup = new Group();
        Scene scene = new Scene(mainMenuGroup);

        primaryStage.setScene(scene);

        mainMenuGroup.getChildren().add(singlePlayerButton);
        primaryStage.show();
    }
}
