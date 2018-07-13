package View.GameView;

import Model.Card.MonsterCard;
import Model.Card.NormalCard;
import Model.Card.Tribe;
import Model.Player;
import Model.Stuff;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.naming.Binding;

import static Controller.Main.human;

public class GameView {

    private static Stage primaryStage;
    private static Player player;
    private static Player opponent;
    private static TextArea details;
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
    private static HBox playerMonsterField;
    private static HBox playerSpellField;
    private static HBox opponentMonsterField;
    private static HBox opponentSpellField;
    private static Button playerButton;
    private static Button opponentButton;
    private static Button playerGraveYard;
    private static Button opponentGraveYard;
    private static Text playerDeckCardCount;
    private static Text opponentDeckCardCount;

    public static void prepare(Stage primaryStage, Player player, Player opponent){
        GameView.primaryStage = primaryStage;
        GameView.opponent = opponent;
        GameView.player = player;
        Group root = new Group();
        informationGroup = new Group();
        playFieldGroup = new Group();
        menusGroup = new Group();
        root.relocate(0,0);
        preparePlayFieldGroup();
        prepareMenusGroup();
        prepareInformationGroup();
        root.getChildren().addAll(informationGroup,playFieldGroup,menusGroup);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(GameView.class.getResource("listStyle.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private static void prepareInformationGroup(){
        informationGroup.relocate(0,0);
        TextArea console = new TextArea();
        ConsoleView.setConsole(console);
        details = new TextArea();
        console.setEditable(false);
        details.setEditable(false);
        details.relocate(15, 10);
        details.setPrefSize(primaryStage.getWidth() / 3 - 100, primaryStage.getHeight() / 3 * 2 - 35);
        console.relocate(15,primaryStage.getHeight() / 3 * 2 - 10);
        console.setPrefSize(primaryStage.getWidth() / 3 - 100, primaryStage.getHeight() / 3 - 45);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                details.setText(newValue.toString());
        });
        informationGroup.getChildren().addAll(console,details);
    }

    private static void preparePlayFieldGroup(){
        playFieldGroup.relocate(primaryStage.getWidth()/3,0);
        playerMonsterField = new HBox(30,new Button(),new Button(),new Button(),new Button(),new Button());
        playerMonsterField.getChildren().get(0).setId("empty");
        playerMonsterField.getChildren().get(1).setId("empty");
        playerMonsterField.getChildren().get(2).setId("empty");
        playerMonsterField.getChildren().get(3).setId("empty");
        playerMonsterField.getChildren().get(4).setId("empty");
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
        playerSpellField = new HBox(30,new Button(),new Button(),new Button());
        playerSpellField.getChildren().get(0).setId("empty");
        playerSpellField.getChildren().get(1).setId("empty");
        playerSpellField.getChildren().get(2).setId("empty");
        playerSpellField.relocate(0, primaryStage.getHeight() / 2 + 100);
        playerSpellField.setPrefWidth(primaryStage.getWidth()/3);
        playerSpellField.setAlignment(Pos.TOP_CENTER);
        ((Button)playerSpellField.getChildren().get(0)).wrapTextProperty().setValue(true);
        ((Button)playerSpellField.getChildren().get(0)).setText("Empty Slot");
        ((Button)playerSpellField.getChildren().get(1)).wrapTextProperty().setValue(true);
        ((Button)playerSpellField.getChildren().get(1)).setText("Empty Slot");
        ((Button)playerSpellField.getChildren().get(2)).wrapTextProperty().setValue(true);
        ((Button)playerSpellField.getChildren().get(2)).setText("Empty Slot");
        opponentMonsterField = new HBox(30,new Button(),new Button(),new Button(),new Button(),new Button());
        opponentMonsterField.getChildren().get(0).setId("empty");
        opponentMonsterField.getChildren().get(1).setId("empty");
        opponentMonsterField.getChildren().get(2).setId("empty");
        opponentMonsterField.getChildren().get(3).setId("empty");
        opponentMonsterField.getChildren().get(4).setId("empty");
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
        opponentSpellField = new HBox( 30,new Button(),new Button(),new Button());
        ((Button)opponentSpellField.getChildren().get(0)).wrapTextProperty().setValue(true);
        ((Button)opponentSpellField.getChildren().get(0)).setText("Empty Slot");
        ((Button)opponentSpellField.getChildren().get(1)).wrapTextProperty().setValue(true);
        ((Button)opponentSpellField.getChildren().get(1)).setText("Empty Slot");
        ((Button)opponentSpellField.getChildren().get(2)).wrapTextProperty().setValue(true);
        ((Button)opponentSpellField.getChildren().get(2)).setText("Empty Slot");
        opponentSpellField.getChildren().get(0).setId("empty");
        opponentSpellField.getChildren().get(1).setId("empty");
        opponentSpellField.getChildren().get(2).setId("empty");
        opponentSpellField.relocate(0, 150);
        opponentSpellField.setPrefWidth(primaryStage.getWidth()/3);
        opponentSpellField.setAlignment(Pos.TOP_CENTER);
        playerButton = new Button();
        playerButton.setId("empty");
        playerButton.setStyle("-fx-background-color: white");
        playerButton.relocate(220,primaryStage.getHeight() - 150);
        playerButton.setGraphic(new ImageView(new Image(GameView.class.getResource("PlayerPortrait.jpg").toExternalForm(),60,60,true,true)));
        playerButton.setAlignment(Pos.TOP_CENTER);
        Text playerText = new Text("HP:\nMP:    /");
        playerText.setId("text");
        playerText.relocate(220,primaryStage.getHeight() - 93);
        opponentButton = new Button();
        opponentButton.setId("empty");
        opponentButton.setStyle("-fx-background-color: white");
        opponentButton.relocate(220,10);
        Text opponentText = new Text("HP:\nMP:    /");
        opponentText.setId("text");
        opponentText.relocate(220,67);
        opponentButton.setGraphic(new ImageView(new Image(GameView.class.getResource("DemonPortrait.jpg").toExternalForm(),60,60,true,true)));
        opponentButton.setAlignment(Pos.TOP_CENTER);
        playerGraveYard = new Button();
        playerGraveYard.setId("empty");
        playerGraveYard.relocate(0,primaryStage.getHeight() - 150);
        playerGraveYard.setGraphic(new ImageView(new Image(GameView.class.getResource("GraveYard.png").toExternalForm(),60,90,false,true)));
        opponentGraveYard = new Button();
        opponentGraveYard.setId("empty");
        opponentGraveYard.relocate(10,10);
        opponentGraveYard.setGraphic(new ImageView(new Image(GameView.class.getResource("GraveYard.png").toExternalForm(),60,90,false,true)));
        ImageView playerDeck = new ImageView(new Image(GameView.class.getResource("Deck.png").toExternalForm(),100,110,false,true));
        playerDeck.relocate(400,primaryStage.getHeight() - 160);
        ImageView opponentDeck = new ImageView(new Image(GameView.class.getResource("Deck.png").toExternalForm(),100,110,false,true));
        opponentDeck.relocate(400,0);
        Text playerMana = new Text();
        playerMana.textProperty().bind(player.manaProperty().asString());
        playerMana.setId("text");
        playerMana.relocate(245,primaryStage.getHeight() - 77);
        Text playerMaxMana = new Text();
        playerMaxMana.textProperty().bind(player.maxManaProperty().asString());
        playerMaxMana.setId("text");
        playerMaxMana.relocate(262,primaryStage.getHeight() - 77);
        Text opponentMaxMana = new Text();
        opponentMaxMana.textProperty().bind(opponent.maxManaProperty().asString());
        opponentMaxMana.setId("text");
        opponentMaxMana.relocate(262,83);
        Text opponentMana = new Text();
        opponentMana.textProperty().bind(opponent.manaProperty().asString());
        opponentMana.setId("text");
        opponentMana.relocate(245,83);
        Text playerHP = new Text();
        playerHP.textProperty().bind(player.getPlayerHero().hpProperty().asString());
        playerHP.setId("text");
        playerHP.relocate(245,primaryStage.getHeight() - 92);
        Text opponentHP = new Text();
        opponentHP.textProperty().bind(opponent.getPlayerHero().hpProperty().asString());
        opponentHP.setId("text");
        opponentHP.relocate(245,68);
        opponentDeckCardCount = new Text("30");
        opponentDeckCardCount.relocate(455  , 45);
        opponentDeckCardCount.setFont(new Font("Forte",20));
        playerDeckCardCount = new Text("30");
        playerDeckCardCount.relocate(455  ,primaryStage.getHeight() - 117);
        playerDeckCardCount.setFont(new Font("Forte",20));
        playFieldGroup.getChildren().addAll(playerDeckCardCount,opponentDeckCardCount,opponentDeck,playerDeck,playerGraveYard,opponentGraveYard,playerMonsterField,opponentSpellField,playerSpellField,opponentMonsterField,playerButton,playerText,opponentButton,opponentText,playerMana,playerMaxMana,opponentMana,opponentMaxMana,playerHP,opponentHP);
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

    public static void showIdleScene(){
        //information group
        details.clear();
        //playfield group
        playFieldGroup.requestFocus();
        playerDeckCardCount.setText(String.valueOf(player.getDeckCards().size()));
        opponentDeckCardCount.setText(String.valueOf(opponent.getDeckCards().size()));
        //menus group
        menusGroup.getChildren().clear();
        menusGroup.getChildren().addAll(showHandButton,endTurnButton,showItemsButton);
    }

    public static void showOpponentTurnScene(){
        //information group
        details.clear();
        //playfield group
        playFieldGroup.requestFocus();
        playerDeckCardCount.setText(String.valueOf(player.getDeckCards().size()));
        opponentDeckCardCount.setText(String.valueOf(opponent.getDeckCards().size()));
        //menus group
        menusGroup.getChildren().clear();
    }

    public static Button getShowItemsButton() {
        return showItemsButton;
    }

    public static Button getShowHandButton() {
        return showHandButton;
    }

    public static Button getEndTurnButton() {
        return endTurnButton;
    }

    public static Button getPlayCardButton() {
        return playCardButton;
    }

    public static Button getUseItemButton() {
        return useItemButton;
    }

    public static Button getUseSpellButton() {
        return useSpellButton;
    }

    public static Button getAttackButton() {
        return attackButton;
    }

    public static void showHand(){
        menusGroup.getChildren().clear();
        ConsoleView.showPlayerMana(player);
        ConsoleView.viewHand(player);
        listView.setItems(FXCollections.observableArrayList(player.getHandCards()));
        menusGroup.getChildren().addAll(playCardButton,listView,endTurnButton,showHandButton,showItemsButton);
    }

    public static void showItems(){
        menusGroup.getChildren().clear();
        ConsoleView.availableItems(player);
        playerDeckCardCount.setText(String.valueOf(player.getDeckCards().size()));
        listView.setItems(FXCollections.observableArrayList(player.getItems()));
        menusGroup.getChildren().addAll(showHandButton,listView,endTurnButton,showItemsButton,useItemButton);
    }

    public static void updateFields(){
        int i;
        if (human.getIsPlaying()) {
            for (i = 0; i < 5; ++i) {
                if (player.getMonsterFieldCards().get(i) != null) {
                    ((Button) playerMonsterField.getChildren().get(i)).textProperty().bind(Bindings.concat(player.getMonsterFieldCards().get(i).getName()).concat("\nHP:").concat(((MonsterCard) player.getMonsterFieldCards().get(i)).hpProperty()).concat("\nAP:").concat(((MonsterCard) player.getMonsterFieldCards().get(i)).apProperty()));
                    switch (((MonsterCard) player.getMonsterFieldCards().get(i)).getTribe()){
                        case ELVEN:
                            playerMonsterField.getChildren().get(i).setId("ELVEN");
                            break;
                        case ATLANTIAN:
                            playerMonsterField.getChildren().get(i).setId("ATLANTIAN");
                            break;
                        case DRAGONBREED:
                            playerMonsterField.getChildren().get(i).setId("DRAGONBREED");
                            break;
                        case DEMONIC:
                            playerMonsterField.getChildren().get(i).setId("DEMONIC");
                            break;
                    }
                }
                else {
                    ((Button) playerMonsterField.getChildren().get(i)).textProperty().unbind();
                    ((Button) playerMonsterField.getChildren().get(i)).setText("EmptySlot");
                    playerMonsterField.getChildren().get(i).setId("empty");
                }
            }
            for (i = 0; i < 5; ++i) {
                if (opponent.getMonsterFieldCards().get(i) != null) {
                    ((Button) opponentMonsterField.getChildren().get(i)).textProperty().bind(Bindings.concat(opponent.getMonsterFieldCards().get(i).getName()).concat("\nHP:").concat(((MonsterCard) opponent.getMonsterFieldCards().get(i)).hpProperty()).concat("\nAP:").concat(((MonsterCard) opponent.getMonsterFieldCards().get(i)).apProperty()));
                    switch (((MonsterCard) opponent.getMonsterFieldCards().get(i)).getTribe()) {
                        case ELVEN:
                            opponentMonsterField.getChildren().get(i).setId("ELVEN");
                            break;
                        case ATLANTIAN:
                            opponentMonsterField.getChildren().get(i).setId("ATLANTIAN");
                            break;
                        case DRAGONBREED:
                            opponentMonsterField.getChildren().get(i).setId("DRAGONBREED");
                            break;
                        case DEMONIC:
                            opponentMonsterField.getChildren().get(i).setId("DEMONIC");
                            break;
                    }
                }
                else {
                    ((Button) opponentMonsterField.getChildren().get(i)).textProperty().unbind();
                    ((Button) opponentMonsterField.getChildren().get(i)).setText("EmptySlot");
                    opponentMonsterField.getChildren().get(i).setId("empty");
                }
            }
            for (i = 0; i < 3; ++i) {
                if (player.getSpellFieldCards().get(i) != null) {
                    ((Button) playerSpellField.getChildren().get(i)).setText(player.getSpellFieldCards().get(i).getName());
                    playerSpellField.getChildren().get(i).setId("spell");
                }
                else {
                    ((Button) playerSpellField.getChildren().get(i)).setText("EmptySlot");
                    playerSpellField.getChildren().get(i).setId("empty");
                }
            }
            for (i = 0; i < 3; ++i) {
                if (opponent.getSpellFieldCards().get(i) != null) {
                    ((Button) opponentSpellField.getChildren().get(i)).setText(opponent.getSpellFieldCards().get(i).getName());
                    opponentSpellField.getChildren().get(i).setId("spell");
                }
                else {
                    ((Button) opponentSpellField.getChildren().get(i)).setText("EmptySlot");
                    opponentSpellField.getChildren().get(i).setId("empty");
                }
            }
        }
    }

    public static HBox getPlayerMonsterField() {
        return playerMonsterField;
    }

    public static HBox getPlayerSpellField() {
        return playerSpellField;
    }

    public static HBox getOpponentMonsterField() {
        return opponentMonsterField;
    }

    public static HBox getOpponentSpellField() {
        return opponentSpellField;
    }

    public static Button getPlayerButton() {
        return playerButton;
    }

    public static Button getOpponentButton() {
        return opponentButton;
    }

    public static Button getPlayerGraveYard() {
        return playerGraveYard;
    }

    public static Button getOpponentGraveYard() {
        return opponentGraveYard;
    }

    public static ListView<Stuff> getListView() {
        return listView;
    }
}