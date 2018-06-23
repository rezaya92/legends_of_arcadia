package View;

import Controller.LegendsOfArcadia;
import Model.Stuff;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MenuView {
    private static Stage primaryStage = LegendsOfArcadia.getPrimaryStage();
    private static final double buttonPrefWidth = 300;
    private static Button singlePlayerButton = new Button("Single Player");
    private static Button customGameButton = new Button("Custom Game");
    private static Button multiPlayerButton = new Button("Multi Player");
    private static Button settingsButton = new Button("Settings");
    private static Button exitButton = new Button("Exit");

    private static Button cardShopButton = new Button("Card Shop");
    private static Button itemShopButton = new Button("Item Shop");
    private static Button amuletShopButton = new Button("Amulet Shop");

    public static void showMainMenu(){
        Group mainMenuGroup = new Group();
        Scene scene = new Scene(mainMenuGroup);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());

        //scene.getStylesheets().add(MenuView.class.getResource("listStyle.css").toExternalForm());
        //setPrefWidthOfButtons(buttonPrefWidth, cardShopButton, itemShopButton, amuletShopButton);
        VBox vBox = makeVBox(singlePlayerButton, customGameButton, multiPlayerButton, settingsButton, exitButton);

        mainMenuGroup.getChildren().addAll(vBox);
    }

    public static void showShop(){
        Group shopGroup = new Group();
        Scene scene = new Scene(shopGroup);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        //setPrefWidthOfButtons(buttonPrefWidth, cardShopButton, itemShopButton, amuletShopButton);
        VBox vBox = makeVBox(cardShopButton, itemShopButton, amuletShopButton);
        shopGroup.getChildren().add(vBox);
    }

    public static void showCardShop(List<? extends Stuff> shopStuff, List<? extends Stuff> playerStuff){
        ArrayList<Button> shopButtons = new ArrayList<>();
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setMin(0);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(500);
        scrollBar.setMax(500);
        //ListView<Button> listView = new ListView<>((ObservableList)shopStuff);
        Group cardShopGroup = new Group();
        Scene scene = new Scene(cardShopGroup);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());
        for(Stuff stuff : shopStuff){
            shopButtons.add(new Button(stuff.getName()));

        }
        VBox vBox = makeVBox(100.0, 100.0, buttonPrefWidth, 10.0, shopButtons.toArray(new Node[shopButtons.size()]));

        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                vBox.setLayoutY(-new_val.doubleValue());
            }
        });

        cardShopGroup.getChildren().addAll(vBox, scrollBar);

    }

//    private static void setPrefWidthOfButtons(double value, Button... buttons){
//        for(Button button : buttons){
//            button.setPrefWidth(buttonPrefWidth);
//        }
//    }

    private static VBox makeVBox(double x, double y, double prefWidth, double spacing, Node... nodes){
        VBox vBox = new VBox(spacing, nodes);
        vBox.setPrefWidth(prefWidth);//TODO css VBox?
        vBox.setPrefHeight(primaryStage.getHeight()/2);
        vBox.setLayoutX(x);
        vBox.setLayoutY(y);

        return vBox;
    }

    private static VBox makeVBox(Node... nodes){
//        for(Node button : buttons){
//            button.setPrefWidth(buttonPrefWidth);
//        }
        return makeVBox(primaryStage.getWidth()/2 - buttonPrefWidth/2, primaryStage.getHeight()/2 - buttonPrefWidth/2, buttonPrefWidth, primaryStage.getHeight()/28, nodes);
    }

    private static VBox makeVBox(List<? extends Node> nodes){
        Node[] nodes1 = new Node[nodes.size()];
        for(int i=0; i<nodes.size(); i++){
            nodes1[i] = nodes.get(i);
        }
        return makeVBox(nodes1);
    }

    public static Button getSinglePlayerButton() {
        return singlePlayerButton;
    }

    public static Button getCustomGameButton() {
        return customGameButton;
    }

    public static Button getMultiPlayerButton() {
        return multiPlayerButton;
    }

    public static Button getSettingsButton() {
        return settingsButton;
    }

    public static Button getExitButton() {
        return exitButton;
    }

    public static Button getCardShopButton() {
        return cardShopButton;
    }

    public static Button getItemShopButton() {
        return itemShopButton;
    }

    public static Button getAmuletShopButton() {
        return amuletShopButton;
    }
}
