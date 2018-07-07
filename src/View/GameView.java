package View;

import Model.Card.Card;
import Model.Card.NormalCard;
import Model.Card.Tribe;
import Model.Item;
import Model.Player;
import Model.Stuff;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.Objects;

public class GameView {

    private static Stage primaryStage;
    private static Player player;
    private static Player opponent;
    private static TextArea details;
    private static TextArea console;
    private static Group informationGroup;
    private static Group playFieldGroup;
    private static Group menusGroup;
    private static ListView<Stuff> listView;
    private static Button showItemsButton = new Button("Show Items");
    private static Button showHandButton = new Button("Show Hand");
    private static Button endTurnButton = new Button("End Turn");
    private static Button playCardButton = new Button("PlayCard");
    private static Button useItemButton = new Button("Use Item");
    private static Button useSpellButton = new Button("Use Spell");
    private static Button attackButton = new Button("Attack");

    public static void ShowGame(Stage primaryStage, Player player, Player opponent){
        GameView.primaryStage = primaryStage;
        GameView.opponent = opponent;
        GameView.player = player;
        Group root = new Group();
        informationGroup = new Group();
        playFieldGroup = new Group();
        menusGroup = new Group();
        root.relocate(0,0);
        prepareInformationGroup();
        preparePlayFieldGroup();
        prepareMenusGroup();
        root.getChildren().addAll(informationGroup,playFieldGroup,menusGroup);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(GameView.class.getResource("listStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        root.requestFocus();
    }

    private static void prepareInformationGroup(){
        informationGroup.relocate(0,0);
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
        playFieldGroup.relocate(primaryStage.getWidth()/3,0);
        HBox playerMonsterField = new HBox(30,new Button(),new Button(),new Button(),new Button(),new Button());
        playerMonsterField.getChildren().get(0).setId("card");
        playerMonsterField.getChildren().get(1).setId("card");
        playerMonsterField.getChildren().get(2).setId("card");
        playerMonsterField.getChildren().get(3).setId("card");
        playerMonsterField.getChildren().get(4).setId("card");
        playerMonsterField.setPrefWidth(primaryStage.getWidth()/3);
        playerMonsterField.relocate(0, primaryStage.getHeight() / 2);
        playerMonsterField.setAlignment(Pos.TOP_CENTER);
        ((Button)playerMonsterField.getChildren().get(0)).wrapTextProperty().setValue(true);
        ((Button)playerMonsterField.getChildren().get(0)).setText("Empty Slot");
        ((Button)playerMonsterField.getChildren().get(1)).wrapTextProperty().setValue(true);
        ((Button)playerMonsterField.getChildren().get(1)).setText("Empty Slot");
        ((Button)playerMonsterField.getChildren().get(2)).wrapTextProperty().setValue(true);
        ((Button)playerMonsterField.getChildren().get(2)).setText("Empty Slot");
        ((Button)playerMonsterField.getChildren().get(3)).wrapTextProperty().setValue(true);
        ((Button)playerMonsterField.getChildren().get(3)).setText("Empty Slot");
        ((Button)playerMonsterField.getChildren().get(4)).wrapTextProperty().setValue(true);
        ((Button)playerMonsterField.getChildren().get(4)).setText("Empty Slot");
        HBox playerSpellField = new HBox(30,new Button(),new Button(),new Button());
        playerSpellField.getChildren().get(0).setId("card");
        playerSpellField.getChildren().get(1).setId("card");
        playerSpellField.getChildren().get(2).setId("card");
        playerSpellField.relocate(0, primaryStage.getHeight() / 2 + 100);
        playerSpellField.setPrefWidth(primaryStage.getWidth()/3);
        playerSpellField.setAlignment(Pos.TOP_CENTER);
        ((Button)playerSpellField.getChildren().get(0)).wrapTextProperty().setValue(true);
        ((Button)playerSpellField.getChildren().get(0)).setText("Empty Slot");
        ((Button)playerSpellField.getChildren().get(1)).wrapTextProperty().setValue(true);
        ((Button)playerSpellField.getChildren().get(1)).setText("Empty Slot");
        ((Button)playerSpellField.getChildren().get(2)).wrapTextProperty().setValue(true);
        ((Button)playerSpellField.getChildren().get(2)).setText("Empty Slot");
        HBox opponentMonsterField = new HBox(30,new Button(),new Button(),new Button(),new Button(),new Button());
        opponentMonsterField.getChildren().get(0).setId("card");
        opponentMonsterField.getChildren().get(1).setId("card");
        opponentMonsterField.getChildren().get(2).setId("card");
        opponentMonsterField.getChildren().get(3).setId("card");
        opponentMonsterField.getChildren().get(4).setId("card");
        ((Button)opponentMonsterField.getChildren().get(0)).wrapTextProperty().setValue(true);
        ((Button)opponentMonsterField.getChildren().get(0)).setText("Empty Slot");
        ((Button)opponentMonsterField.getChildren().get(1)).wrapTextProperty().setValue(true);
        ((Button)opponentMonsterField.getChildren().get(1)).setText("Empty Slot");
        ((Button)opponentMonsterField.getChildren().get(2)).wrapTextProperty().setValue(true);
        ((Button)opponentMonsterField.getChildren().get(2)).setText("Empty Slot");
        ((Button)opponentMonsterField.getChildren().get(3)).wrapTextProperty().setValue(true);
        ((Button)opponentMonsterField.getChildren().get(3)).setText("Empty Slot");
        ((Button)opponentMonsterField.getChildren().get(4)).wrapTextProperty().setValue(true);
        ((Button)opponentMonsterField.getChildren().get(4)).setText("Empty Slot");
        opponentMonsterField.setPrefWidth(primaryStage.getWidth()/3);
        opponentMonsterField.relocate(0, 250);
        opponentMonsterField.setAlignment(Pos.TOP_CENTER);
        HBox opponentSpellField = new HBox( 30,new Button(),new Button(),new Button());
        ((Button)opponentSpellField.getChildren().get(0)).wrapTextProperty().setValue(true);
        ((Button)opponentSpellField.getChildren().get(0)).setText("Empty Slot");
        ((Button)opponentSpellField.getChildren().get(1)).wrapTextProperty().setValue(true);
        ((Button)opponentSpellField.getChildren().get(1)).setText("Empty Slot");
        ((Button)opponentSpellField.getChildren().get(2)).wrapTextProperty().setValue(true);
        ((Button)opponentSpellField.getChildren().get(2)).setText("Empty Slot");
        opponentSpellField.getChildren().get(0).setId("card");
        opponentSpellField.getChildren().get(1).setId("card");
        opponentSpellField.getChildren().get(2).setId("card");
        opponentSpellField.relocate(0, 150);
        opponentSpellField.setPrefWidth(primaryStage.getWidth()/3);
        opponentSpellField.setAlignment(Pos.TOP_CENTER);
        Button playerButton = new Button();
        playerButton.setId("card");
        playerButton.setStyle("-fx-background-color: white");
        playerButton.relocate(220,primaryStage.getHeight() - 150);
        Text playerText = new Text("HP:\nMP:    /");
        playerText.setId("text");
        playerText.relocate(220,primaryStage.getHeight() - 93);
        playerButton.setGraphic(new ImageView(new Image(GameView.class.getResource("PlayerPortrait.jpg").toExternalForm(),60,60,true,true)));
        playerButton.setAlignment(Pos.TOP_CENTER);
        Button opponentButton = new Button();
        opponentButton.setId("card");
        opponentButton.setStyle("-fx-background-color: white");
        opponentButton.relocate(220,10);
        Text opponentText = new Text("HP:\nMP:    /");
        opponentText.setId("text");
        opponentText.relocate(220,67);
        opponentButton.setGraphic(new ImageView(new Image(GameView.class.getResource("DemonPortrait.jpg").toExternalForm(),60,60,true,true)));
        opponentButton.setAlignment(Pos.TOP_CENTER);
        Button playerGraveYard = new Button();
        playerGraveYard.setId("card");
        playerGraveYard.relocate(0,primaryStage.getHeight() - 150);
        playerGraveYard.setGraphic(new ImageView(new Image(GameView.class.getResource("GraveYard.png").toExternalForm(),60,90,false,true)));
        Button enemyGraveYard = new Button();
        enemyGraveYard.setId("card");
        enemyGraveYard.relocate(10,10);
        enemyGraveYard.setGraphic(new ImageView(new Image(GameView.class.getResource("GraveYard.png").toExternalForm(),60,90,false,true)));
        ImageView playerDeck = new ImageView(new Image(GameView.class.getResource("Deck.png").toExternalForm(),100,110,false,true));
        playerDeck.relocate(400,primaryStage.getHeight() - 160);
        ImageView enemyDeck = new ImageView(new Image(GameView.class.getResource("Deck.png").toExternalForm(),100,110,false,true));
        enemyDeck.relocate(400,0);
        Text playerMana = new Text();
        playerMana.textProperty().bind(player.manaProperty().asString());
        playerMana.setId("text");
        playerMana.relocate(245,primaryStage.getHeight() - 77);
        Text playerMaxMana = new Text();
        playerMaxMana.textProperty().bind(player.maxManaProperty().asString());
        playerMaxMana.setId("text");
        playerMaxMana.relocate(260,primaryStage.getHeight() - 77);
        Text opponentMaxMana = new Text();
        opponentMaxMana.textProperty().bind(opponent.maxManaProperty().asString());
        opponentMaxMana.setId("text");
        opponentMaxMana.relocate(260,83);
        Text opponentMana = new Text();
        opponentMana.textProperty().bind(player.manaProperty().asString());
        opponentMana.setId("text");
        opponentMana.relocate(245,83);

        playFieldGroup.getChildren().addAll(enemyDeck,playerDeck,playerGraveYard,enemyGraveYard,playerMonsterField,opponentSpellField,playerSpellField,opponentMonsterField,playerButton,playerText,opponentButton,opponentText,playerMana,playerMaxMana,opponentMana,opponentMaxMana);
    }

    private static void prepareMenusGroup(){
        menusGroup.relocate(primaryStage.getWidth()/3 * 2,0);
        listView = new ListView<>(FXCollections.observableArrayList(new NormalCard(Tribe.ELVEN,"Elven Ranger",100,100,1,false,false)));
        listView.setPrefSize(primaryStage.getWidth() / 3 - 200, primaryStage.getHeight() / 3 * 2 - 35);
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Stuff item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                    setText("Empty");
                else
                    setText(item.getName());
            }
        });
        listView.relocate(120,20);
        showHandButton.setPrefSize(100,30);
        showHandButton.relocate(120,primaryStage.getHeight() - 150);
        endTurnButton.setPrefSize(80,30);
        endTurnButton.relocate(220,primaryStage.getHeight() - 100);
        showItemsButton.setPrefSize(100,30);
        showItemsButton.relocate(300,primaryStage.getHeight() - 150);
        useItemButton.setPrefSize(100,30);
        useItemButton.relocate(210,primaryStage.getHeight() - 200);
        playCardButton.setPrefSize(100, 30);
        playCardButton.relocate(210,primaryStage.getHeight() - 200);
        attackButton.setPrefSize(100,30);
        attackButton.relocate(300,primaryStage.getHeight() - 200);
        useSpellButton.setPrefSize(100,30);
        useSpellButton.relocate(120,primaryStage.getHeight() - 200);
        menusGroup.getChildren().addAll(showHandButton,endTurnButton,showItemsButton, useItemButton,attackButton,playCardButton,useSpellButton,listView);
    }
}