package View;

import Controller.Game;
import Controller.LegendsOfArcadia;
import Controller.Main;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CustomGameView {
    private static Stage primaryStage = LegendsOfArcadia.getPrimaryStage();

    public static void showMainEntrance(){
        Group root = new Group();
        Scene entranceScene = new Scene(root);
        entranceScene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(entranceScene);

        ArrayList<HBox> hBoxes = new ArrayList<>();
        for(int i=0; i<Main.customGames.size(); i++) {
            Button gameButton = new Button(Main.customGames.get(i).getName());

            Button deleteButton = new Button();
            deleteButton.setId("smallButton");

            ImageView imageView = new ImageView(new Image("file:delete.png"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            deleteButton.setGraphic(imageView);

            HBox hBox = new HBox(1, gameButton, deleteButton);
            if(i == 0)
                hBox = new HBox(gameButton);
            hBoxes.add(hBox);
        }

        Button newGameButton = new Button("+new game");
        newGameButton.setId("newGameButton");
        hBoxes.add(new HBox(newGameButton));

        ListView<HBox> listView = new ListView<>(FXCollections.observableArrayList(hBoxes));
        listView.setPrefSize(330, 550);
        listView.relocate(primaryStage.getWidth()/2 - listView.getPrefWidth()/2, 150);

        //VBox vBox = MenuView.makeVBox(listView);

        root.getChildren().add(listView);
    }
}
