package View;

import Model.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GameView {

    private static Stage primaryStage;
    private static Player player;
    private static Player opponent;
    private static TextArea details;
    private static TextArea console;
    private static Group informationGroup;
    private static Group playFieldGroup;
    private static Group menusGroup;

    public static void ShowGame(Stage primaryStage, Player player, Player opponent){
        GameView.primaryStage = primaryStage;
        GameView.opponent = opponent;
        GameView.player = player;
        Group root = new Group();
        informationGroup = new Group();
        playFieldGroup = new Group();
        menusGroup = new Group();
        root.setLayoutX(0);
        root.setLayoutY(0);
        prepareInformationGroup();
        preparePlayFieldGroup();
        prepareMenusGroup();
        root.getChildren().addAll(informationGroup,playFieldGroup,menusGroup);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    private static void prepareInformationGroup(){
        informationGroup.setLayoutX(0);
        informationGroup.setLayoutY(0);
        console = new TextArea();
        details = new TextArea();
        console.setEditable(false);
        details.setEditable(false);
        details.relocate(15, 10);
        details.setPrefSize(primaryStage.getWidth() / 3 - 100, primaryStage.getHeight() / 3 * 2 - 35);
        console.relocate(15,primaryStage.getHeight() / 3 * 2 - 10);
        console.setPrefSize(primaryStage.getWidth() / 3 - 100, primaryStage.getHeight() / 3 - 45);
        informationGroup.getChildren().addAll(console,details);
    }

    private static void preparePlayFieldGroup(){
        playFieldGroup.setLayoutX(primaryStage.getWidth()/3);
        playFieldGroup.setLayoutY(0);
    }

    private static void prepareMenusGroup(){
        menusGroup.setLayoutX(primaryStage.getWidth()/3 * 2);
        menusGroup.setLayoutY(0);
    }
}
