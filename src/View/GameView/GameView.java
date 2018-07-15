package View.GameView;

import Controller.Battle;
import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.NormalCard;
import Model.Card.Tribe;
import Model.Player;
import Model.Spell.ListShowable;
import Model.SpellCastable;
import Model.Stuff;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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

import java.util.ArrayList;

import static Controller.Main.human;

public class GameView {

    private static Stage primaryStage;
    private static Player player;
    private static Player opponent;
    private static TextArea details;
    private static Group informationGroup;
    private static Group playFieldGroup;
    private static Group menusGroup;
    private static ListView<ListShowable> listView;
    private static Button showItemsButton = new Button("Show Items");
    private static Button showHandButton = new Button("Show Hand");
    private static Button endTurnButton = new Button("End Turn");
    private static Button playCardButton = new Button("PlayCard");
    private static Button useItemButton = new Button("Use Item");
    private static Button useSpellButton = new Button("Use Spell");
    private static Button attackButton = new Button("Attack");
    private static Button chooseButton = new Button("Choose");
    private static Button cancelButton = new Button("Cancel");
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
    private static Text playerText;
    private static Text opponentText;
    private static Text playerMana;
    private static Text playerMaxMana;
    private static Text playerHP;
    private static Text opponentMaxMana;
    private static Text opponentMana;
    private static Text opponentHP;

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
        informationGroup.getChildren().addAll(console,details);
    }

    private static void preparePlayFieldGroup(){
        playFieldGroup.relocate(primaryStage.getWidth()/3,0);
        playerMonsterField = new HBox(30,new Button(),new Button(),new Button(),new Button(),new Button());
        playerMonsterField.setPrefWidth(primaryStage.getWidth()/3);
        playerMonsterField.relocate(0, primaryStage.getHeight() / 2);
        playerMonsterField.setAlignment(Pos.TOP_CENTER);
        playerSpellField = new HBox(30,new Button(),new Button(),new Button());
        playerSpellField.relocate(0, primaryStage.getHeight() / 2 + 100);
        playerSpellField.setPrefWidth(primaryStage.getWidth()/3);
        playerSpellField.setAlignment(Pos.TOP_CENTER);
        opponentMonsterField = new HBox(30,new Button(),new Button(),new Button(),new Button(),new Button());
        opponentMonsterField.setPrefWidth(primaryStage.getWidth()/3);
        opponentMonsterField.relocate(0, 250);
        opponentMonsterField.setAlignment(Pos.TOP_CENTER);
        opponentSpellField = new HBox( 30,new Button(),new Button(),new Button());
        opponentSpellField.relocate(0, 150);
        opponentSpellField.setPrefWidth(primaryStage.getWidth()/3);
        opponentSpellField.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < 5; i++) {
            playerMonsterField.getChildren().get(i).setId("empty");
            ((Button)playerMonsterField.getChildren().get(i)).wrapTextProperty().setValue(true);
            ((Button)playerMonsterField.getChildren().get(i)).setText("Empty Slot");
            opponentMonsterField.getChildren().get(i).setId("empty");
            ((Button)opponentMonsterField.getChildren().get(i)).wrapTextProperty().setValue(true);
            ((Button)opponentMonsterField.getChildren().get(i)).setText("Empty Slot");
        }
        for (int i = 0; i < 3; i++) {
            ((Button)playerSpellField.getChildren().get(i)).wrapTextProperty().setValue(true);
            ((Button)playerSpellField.getChildren().get(i)).setText("Empty Slot");
            playerSpellField.getChildren().get(i).setId("empty");
            ((Button)opponentSpellField.getChildren().get(i)).wrapTextProperty().setValue(true);
            ((Button)opponentSpellField.getChildren().get(i)).setText("Empty Slot");
            opponentSpellField.getChildren().get(i).setId("empty");
        }
        playerButton = new Button();
        playerButton.setId("empty");
        playerButton.setStyle("-fx-background-color: white");
        playerButton.relocate(220,primaryStage.getHeight() - 150);
        playerButton.setGraphic(new ImageView(new Image(GameView.class.getResource("PlayerPortrait.jpg").toExternalForm(),60,60,true,true)));
        playerButton.setAlignment(Pos.TOP_CENTER);
        playerText = new Text("HP:\nMP:    /");
        playerText.setId("text");
        playerText.relocate(220,primaryStage.getHeight() - 93);
        opponentButton = new Button();
        opponentButton.setId("empty");
        opponentButton.setStyle("-fx-background-color: white");
        opponentButton.relocate(220,10);
        opponentText = new Text("HP:\nMP:    /");
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
        playerMana = new Text();
        playerMana.textProperty().bind(player.manaProperty().asString());
        playerMana.setId("text");
        playerMana.relocate(245,primaryStage.getHeight() - 77);
        playerMaxMana = new Text();
        playerMaxMana.textProperty().bind(player.maxManaProperty().asString());
        playerMaxMana.setId("text");
        playerMaxMana.relocate(262,primaryStage.getHeight() - 77);
        opponentMaxMana = new Text();
        opponentMaxMana.textProperty().bind(opponent.maxManaProperty().asString());
        opponentMaxMana.setId("text");
        opponentMaxMana.relocate(262,83);
        opponentMana = new Text();
        opponentMana.textProperty().bind(opponent.manaProperty().asString());
        opponentMana.setId("text");
        opponentMana.relocate(245,83);
        playerHP = new Text();
        playerHP.textProperty().bind(player.getPlayerHero().hpProperty().asString());
        playerHP.setId("text");
        playerHP.relocate(245,primaryStage.getHeight() - 92);
        opponentHP = new Text();
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
            public void updateItem(ListShowable item, boolean empty) {
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
        chooseButton.setPrefSize(100,30);
        chooseButton.relocate(300,primaryStage.getHeight() - 200);
        cancelButton.setPrefSize(100,30);
        cancelButton.relocate(120,primaryStage.getHeight() - 200);
        menusGroup.getChildren().addAll(chooseButton,cancelButton,showHandButton,endTurnButton,showItemsButton, useItemButton,attackButton,playCardButton,useSpellButton,listView);
    }

    public static void showIdleScene(){
        //information group
        details.clear();
        //playfield group
        updateFields();
        changePlayFieldVisibility(true);
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
        updateFields();
        playFieldGroup.requestFocus();
        playerDeckCardCount.setText(String.valueOf(player.getDeckCards().size()));
        opponentDeckCardCount.setText(String.valueOf(opponent.getDeckCards().size()));
        //menus group
        menusGroup.getChildren().clear();
    }

    public static void playerClicked(Player player) {
        menusGroup.getChildren().removeAll(playCardButton,attackButton,useItemButton,useSpellButton);
        if (playerButton.getId().equals("choice") || opponentButton.getId().equals("choice"))
            Battle.target = player.getPlayerHero();
        else
            menusGroup.getChildren().remove(listView);
        listView.getSelectionModel().clearSelection();
        getDetails().setText(player.getPlayerHero().toString());
    }

    public static void fieldSelected(HBox selectedFieldNode, ArrayList<Card> selectedField,int selectedSlot) {
        menusGroup.getChildren().removeAll(playCardButton,attackButton,useItemButton,useSpellButton);
        if (selectedField.get(selectedSlot) == null)
            details.setText("Empty Slot");
        else {
            details.setText(selectedField.get(selectedSlot).toString());
            if (selectedField.equals(human.getMonsterFieldCards()) && player.isHisTurn()){
                menusGroup.getChildren().add(attackButton);
                if (!selectedFieldNode.getChildren().get(selectedSlot).getId().equals("choice"))
                    Battle.targetNeedingAttacker = (MonsterCard)selectedField.get(selectedSlot);
                if (((MonsterCard)player.getMonsterFieldCards().get(selectedSlot)).hasGotSpell())
                    menusGroup.getChildren().add(useSpellButton);
            }
        }
        listView.getSelectionModel().clearSelection();
        if (selectedFieldNode.getChildren().get(selectedSlot).getId().equals("choice"))
            Battle.target = selectedField.get(selectedSlot);
        else
            menusGroup.getChildren().remove(listView);
    }

    public static void graveYardSelected(Player player){
        menusGroup.getChildren().removeAll(playCardButton,listView,attackButton,useItemButton,useSpellButton);
        listView.setItems(FXCollections.observableArrayList(player.getGraveyardCards()));
        menusGroup.getChildren().add(listView);
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

    private static void showChoices(ArrayList<SpellCastable> choices){
        changePlayFieldVisibility(false);
        for (SpellCastable choice: choices){
            if (choice.equals(player.getPlayerHero())) {
                playerButton.setId("choice");
                playerText.setVisible(true);
                playerMana.setVisible(true);
                playerMaxMana.setVisible(true);
                playerHP.setVisible(true);
                playerButton.setVisible(true);
            }
            else if (choice.equals(opponent.getPlayerHero())) {
                opponentButton.setId("choice");
                opponentButton.setId("choice");
                opponentText.setVisible(true);
                opponentMana.setVisible(true);
                opponentMaxMana.setVisible(true);
                opponentHP.setVisible(true);
                opponentButton.setVisible(true);
            }
            else if (((Card)choice).getCardPlace().equals(player.getMonsterFieldCards())) {
                playerMonsterField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setId("choice");
                playerMonsterField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setVisible(true);
            }
            else if (((Card)choice).getCardPlace().equals(player.getSpellFieldCards())) {
                playerSpellField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setId("choice");
                playerSpellField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setVisible(true);
            }
            else if (((Card)choice).getCardPlace().equals(opponent.getMonsterFieldCards())) {
                opponentMonsterField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setId("choice");
                opponentMonsterField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setVisible(true);
            }
            else if (((Card)choice).getCardPlace().equals(opponent.getSpellFieldCards())) {
                opponentSpellField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setId("choice");
                opponentSpellField.getChildren().get(((Card) choice).getCardPlace().indexOf(choice)).setVisible(true);
            }
            listView.setItems(FXCollections.observableArrayList(choices));
        }
    }

    private static void changePlayFieldVisibility(boolean value){
        for (Node node: playFieldGroup.getChildren())
            node.setVisible(value);
        for (Node node: playerMonsterField.getChildren())
            node.setVisible(value);
        for (Node node: playerSpellField.getChildren())
            node.setVisible(value);
        for (Node node: opponentMonsterField.getChildren())
            node.setVisible(value);
        for (Node node: opponentSpellField.getChildren())
            node.setVisible(value);
        playerMonsterField.setVisible(true);
        playerSpellField.setVisible(true);
        opponentMonsterField.setVisible(true);
        opponentSpellField.setVisible(true);
    }

    public static void showAttackScene(){
        ArrayList<SpellCastable> targets = new ArrayList<>();
        if (opponent.isDefenderPresent()){
            for (Card card: opponent.getMonsterFieldCards()) {
                if (((MonsterCard)card).isDefender()){
                    targets.add(card);
                }
            }
        }
        else {
            for (Card card: opponent.getMonsterFieldCards()) {
                if (card != null)
                    targets.add(card);
            }
            targets.add(opponent.getPlayerHero());
        }
        menusGroup.getChildren().clear();
        menusGroup.getChildren().addAll(chooseButton,cancelButton,listView);
        showChoices(targets);
    }

    public static SpellCastable getSelectedPlayFieldButton(){
        SpellCastable selected = null;
        if (playerButton.isFocused())
            selected = player.getPlayerHero();
        else if (opponentButton.isFocused())
            selected = opponent.getPlayerHero();
        else {
            for (int i = 0; i < 5; i++) {
                if (playerMonsterField.getChildren().get(i).isFocused())
                    selected = player.getMonsterFieldCards().get(i);
                if (opponentMonsterField.getChildren().get(i).isFocused())
                    selected = opponent.getMonsterFieldCards().get(i);
            }
            for (int i = 0; i < 3; i++) {
                if (opponentSpellField.getChildren().get(i).isFocused())
                    selected = opponent.getSpellFieldCards().get(i);
                if (playerSpellField.getChildren().get(i).isFocused())
                    selected = player.getSpellFieldCards().get(i);
            }
        }
        return selected;
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

    public static ListView<ListShowable> getListView() {
        return listView;
    }

    public static TextArea getDetails() {
        return details;
    }

    public static Button getCancelButton() {
        return cancelButton;
    }

    public static Button getChooseButton() {
        return chooseButton;
    }
}