package View;

import Controller.*;
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

import static View.MenuView.makeVBox;

public class CustomGameView {
    private static Stage primaryStage = LegendsOfArcadia.getPrimaryStage();
    private static Game newCustomGame;

    public static void showMainEntrance(){
        Group root = new Group();
        Scene entranceScene = new Scene(root);
        entranceScene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(entranceScene);
        primaryStage.setTitle("Custom Game");

        ArrayList<HBox> hBoxes = new ArrayList<>();
        for(int i = 0; i< LegendsOfArcadia.customGames.size(); i++) {
            Button gameButton = new Button(LegendsOfArcadia.customGames.get(i).getName());

            Button deleteButton = new Button();
            deleteButton.setId("smallButton");

            ImageView imageView = new ImageView(new Image("file:delete.png"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            deleteButton.setGraphic(imageView);

            //load game
            final int ind = i;
            gameButton.setOnMouseClicked(event -> {
                Game.setCurrentGame(LegendsOfArcadia.customGames.get(ind));
                //Main.afterMatch();
                LegendsOfArcadia.setMap(new Map(primaryStage, 1));
            });

            //---delete game---
            deleteButton.setOnMouseClicked(event -> {
                LegendsOfArcadia.customGames.remove(ind);
                showMainEntrance();
            });

            HBox hBox = new HBox(1, gameButton, deleteButton);
            if(i == 0)
                hBox = new HBox(gameButton);
            hBoxes.add(hBox);
        }

        Button newGameButton = new Button("+new game");
        newGameButton.setId("newGameButton");
        newGameButton.setOnMouseClicked(event -> {
            showNewGameTemplate();
        });
        hBoxes.add(new HBox(newGameButton));

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            MenuView.showMainMenu();
        });

        ListView<HBox> listView = new ListView<>(FXCollections.observableArrayList(hBoxes));
        listView.setPrefSize(330, 550);
        listView.relocate(primaryStage.getWidth()/2 - listView.getPrefWidth()/2, 150);

        root.getChildren().addAll(listView, returnButton);
    }

    public static void showNewGameTemplate(){
        Group root = new Group();
        Scene entranceScene = new Scene(root);
        entranceScene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(entranceScene);
        primaryStage.setTitle("Select Game Template");

        ArrayList<HBox> hBoxes = new ArrayList<>();
        for(int i = 0; i< LegendsOfArcadia.customGames.size(); i++) {
            Button gameButton = new Button(LegendsOfArcadia.customGames.get(i).getName());

            //load game
            final int ind = i;
            gameButton.setOnMouseClicked(event -> {
                newCustomGame = (Game)DeepCopy.copy(LegendsOfArcadia.customGames.get(ind));
                showEditPart();
            });

            HBox hBox = new HBox(gameButton);
            hBoxes.add(hBox);
        }

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            MenuView.showMainMenu();
        });

        ListView<HBox> listView = new ListView<>(FXCollections.observableArrayList(hBoxes));
        listView.setPrefSize(330, 550);
        listView.relocate(primaryStage.getWidth()/2 - listView.getPrefWidth()/2, 150);

        root.getChildren().addAll(listView, returnButton);
    }

    public static void showEditPart(){
        Group root = new Group();
        Scene entranceScene = new Scene(root);
        entranceScene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());//TODO css
        primaryStage.setScene(entranceScene);
        primaryStage.setTitle("Customize Game");

        Button createSpellButton = new Button("Create Spell");
        Button createGeneralizedSpellButton = new Button("Create Generalized Spell");
        Button editCardsButton = new Button("Edit Cards");
        Button editItemsButton = new Button("Edit Items");//TODO create or edit?
        Button editAmuletsButton = new Button("Edit Amulets");
        Button editShopButton = new Button("Edit Shop");
        Button editDecksButton = new Button("Edit Decks");

        VBox vBox = makeVBox(createSpellButton, createGeneralizedSpellButton, editCardsButton, editItemsButton, editAmuletsButton, editShopButton, editDecksButton);

        root.getChildren().addAll(vBox);
    }

    public static void setStatusOfReturnButton(Button returnButton, double x, double y){
        ImageView imageView = new ImageView(new Image("file:return-icon3.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        returnButton.setGraphic(imageView);
        returnButton.setMaxWidth(50);
        //returnButton.setStyle("-fx-background-color: rgba(20, 100, 40, 0.7);");
        returnButton.relocate(x, y);
    }
}
