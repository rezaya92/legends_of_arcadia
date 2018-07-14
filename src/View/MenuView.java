package View;

import Controller.LegendsOfArcadia;
import Controller.Main;
import Model.Stuff;
import Model.TypeOfStuffToBuyAndSell;
import View.GameView.ConsoleView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static Controller.Main.human;

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

    public static void showStuffShop(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, List<? extends Stuff> shopStuff, List<? extends Stuff> playerStuff){
        showStuffShop(typeOfStuffToBuyAndSell, shopStuff, playerStuff, null);
    }

    public static void showStuffShop(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, List<? extends Stuff> shopStuff, List<? extends Stuff> playerStuff, String transactionMessage){
        Group cardShopGroup = new Group();
        Scene scene = new Scene(cardShopGroup);
        ArrayList<Button> shopButtons = new ArrayList<>();
        ArrayList<Button> playerButtons = new ArrayList<>();
        TextArea textArea = new TextArea("welcome to the shop!\nhere you can buy the stuff you want and sell the stuff that you no longer need.");
        Text gilText = new Text("Remaining Gil: " + human.getGil());
        TextArea transactionResult = new TextArea(transactionMessage);
        //TODO add return button

        ConsoleView.setConsole(transactionResult);

        gilText.relocate(100, 20);

        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());

        //------------sort shopStuff and playerStuff by name-----------------
        shopStuff.sort(new Comparator<Stuff>() {
            @Override
            public int compare(Stuff o1, Stuff o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        playerStuff.sort(new Comparator<Stuff>() {
            @Override
            public int compare(Stuff o1, Stuff o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        //-------------------------------------------------------------------

        for(Stuff stuff : shopStuff){
            Button itemButton = new Button(stuff.getName());
            //itemButton.setMinHeight(50);
            itemButton.setOnMouseEntered(event -> {
                textArea.setText(stuff.toString());
                textArea.appendText("\nPrice: " + stuff.getPrice() + " gil");
            });
            itemButton.setOnMouseClicked(event -> {
                try {
                    transactionResult.clear();
                    Main.buyThingsProcessor(typeOfStuffToBuyAndSell, "buy " + stuff.getName() + " - 1");
                    switch (typeOfStuffToBuyAndSell){
                        case CARD:
                            showStuffShop(TypeOfStuffToBuyAndSell.CARD, human.getShop().getCards(), human.getInventoryCards(), transactionResult.getText());
                            break;
                        case ITEM:
                            showStuffShop(TypeOfStuffToBuyAndSell.ITEM, human.getShop().getItems(), human.getItems(), transactionResult.getText());
                            break;
                        case AMULET:
                            MenuView.showStuffShop(TypeOfStuffToBuyAndSell.AMULET, human.getShop().getAmulets(), human.getAmulets(), transactionResult.getText());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            shopButtons.add(itemButton);
        }

        for(Stuff stuff : playerStuff){
            Button itemButton = new Button(stuff.getName());
            //itemButton.setPrefHeight(50);
            itemButton.setOnMouseEntered(event -> {
                textArea.setText(stuff.toString());
                textArea.appendText("\nMoney gained from selling: " + stuff.getPrice()/2 + " gil");
                if(typeOfStuffToBuyAndSell == TypeOfStuffToBuyAndSell.CARD) {
                    textArea.appendText("\n\n**number in deck: " + Stuff.numberOfStuffInList(stuff, human.getDeckCards()));
                }
            });
            itemButton.setOnMouseClicked(event -> {
                try {
                    transactionResult.clear();
                    Main.sellThingsProcessor(typeOfStuffToBuyAndSell, "sell " + stuff.getName() + " - 1");
                    switch (typeOfStuffToBuyAndSell){
                        case CARD:
                            showStuffShop(TypeOfStuffToBuyAndSell.CARD, human.getShop().getCards(), human.getInventoryCards(), transactionResult.getText());
                            break;
                        case ITEM:
                            MenuView.showStuffShop(TypeOfStuffToBuyAndSell.ITEM, human.getShop().getItems(), human.getItems(), transactionResult.getText());
                            break;
                        case AMULET:
                            MenuView.showStuffShop(TypeOfStuffToBuyAndSell.AMULET, human.getShop().getAmulets(), human.getAmulets(), transactionResult.getText());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            playerButtons.add(itemButton);
        }

//        ScrollBar scrollBar = new ScrollBar();
//        scrollBar.setMin(0);
//        scrollBar.setOrientation(Orientation.VERTICAL);
//        scrollBar.setPrefHeight(600);
//        scrollBar.setLayoutX(100);
//        scrollBar.setLayoutY(100);
//        scrollBar.setMax(playerStuff.size() * 30);
//
//        VBox vBox = makeVBox(100.0, 100.0, buttonPrefWidth, 10.0, shopButtons.toArray(new Node[shopButtons.size()]));
//        vBox.setStyle("-fx-border-width: 2;\n-fx-border-color: black;");
//
//        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
//            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
//                //vBox.setLayoutY(100-new_val.doubleValue());
//                for(Button button : shopButtons){
//                    button.setLayoutY(100 - new_val.doubleValue());
//                }
//            }
//        });

        textArea.relocate(600, 180);
        textArea.setEditable(false);
        textArea.setPrefSize(300, 300);

        transactionResult.relocate(600, 525);
        transactionResult.setEditable(false);
        transactionResult.setPrefSize(300, 50);

        ListView<Button> shopItemsListView = new ListView<>(FXCollections.observableArrayList(shopButtons));
        //shopItemsListView.setFixedCellSize(60);
        shopItemsListView.relocate(150, 100);
        shopItemsListView.setPrefSize(330, 500);

        ListView<Button> playerItemsListView = new ListView<>(FXCollections.observableArrayList(playerButtons));
        playerItemsListView.relocate(1000, 100);
        playerItemsListView.setPrefSize(330, 500);



        cardShopGroup.getChildren().addAll(shopItemsListView, playerItemsListView, textArea, gilText, transactionResult);
        //cardShopGroup.getChildren().addAll(vBox, scrollBar);
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
        //vBox.setMaxHeight(400);
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
