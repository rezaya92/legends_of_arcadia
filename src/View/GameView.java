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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

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