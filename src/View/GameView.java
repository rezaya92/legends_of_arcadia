package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameView {
    public static void ShowGame(Stage primaryStage){
        Group root = new Group();
        Group informationGroup = new Group();
        Group playFieldGroup = new Group();
        Group menusGroup = new Group();
        root.setLayoutX(0);
        root.setLayoutY(0);
        informationGroup.setLayoutX(0);
        informationGroup.setLayoutY(0);
        playFieldGroup.setLayoutX(primaryStage.getWidth()/3);
        playFieldGroup.setLayoutY(0);
        menusGroup.setLayoutX(primaryStage.getWidth()/3 * 2);
        menusGroup.setLayoutY(0);
        root.getChildren().add(informationGroup);
        root.getChildren().add(playFieldGroup);
        root.getChildren().add(menusGroup);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }
}
