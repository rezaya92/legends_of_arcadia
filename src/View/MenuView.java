package View;

import Controller.LegendsOfArcadia;
import Controller.Main;
import Controller.Popup;
import Model.Amulet;
import Model.Card.Card;
import Model.Stuff;
import Model.TypeOfStuffToBuyAndSell;
import View.GameView.ConsoleView;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static Controller.Main.human;

public class MenuView {
    private static Stage primaryStage = LegendsOfArcadia.getPrimaryStage();
    private static final double buttonPrefWidth = 500;
    private static Button singlePlayerButton = new Button("Single Player");
    private static Button customGameButton = new Button("Custom Game");
    private static Button multiPlayerButton = new Button("Multiplayer");
    private static Button settingsButton = new Button("Settings");
    private static Button exitButton = new Button("Exit");
    private static Button cardShopButton = new Button("Card Shop");
    private static Button itemShopButton = new Button("Item Shop");
    private static Button amuletShopButton = new Button("Amulet Shop");

    private static Button editDeckButton = new Button("Edit Deck");
    private static Button editAmuletButton = new Button("Edit Amulet");

    public static void showMainMenu(){
        Text title = new Text("Legends of Arcadia");
        title.setId("title");
        title.relocate(400,100);
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        Group mainMenuGroup = new Group();
        ImageView imageView = new ImageView(new Image(new File("epic-north-arcadia-redux.jpg").toURI().toString()));
        imageView.setFitWidth(1500);
        imageView.setFitHeight(800);
        mainMenuGroup.getChildren().addAll(imageView,title);
        //StackPane stackPane = new StackPane();
        //stackPane.setId("pane");
        Scene scene = new Scene(mainMenuGroup);
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        primaryStage.setScene(scene);

        //setPrefWidthOfButtons(buttonPrefWidth, cardShopButton, itemShopButton, amuletShopButton);
        VBox vBox = makeVBox(singlePlayerButton, customGameButton, multiPlayerButton, settingsButton, exitButton);
        //vBox.relocate(300, 300);

        mainMenuGroup.getChildren().addAll(vBox);
        //stackPane.getChildren().addAll(mainMenuGroup);

        customGameButton.setOnMouseClicked(event -> {
            CustomGameView.showMainEntrance();
        });
    }

    public static void showShop(){
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        Group shopGroup = new Group();
        ImageView imageView = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView.setFitWidth(1500);
        imageView.setFitHeight(800);
        shopGroup.getChildren().addAll(imageView);
        Scene scene = new Scene(shopGroup);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        Button returnButton = new Button("Return");

        cardShopButton.setOnMouseClicked(event -> {
            MenuView.showStuffShop(TypeOfStuffToBuyAndSell.CARD, human.getShop().getCards(), human.getInventoryCards());
        });

        itemShopButton.setOnMouseClicked(event -> {
            MenuView.showStuffShop(TypeOfStuffToBuyAndSell.ITEM, human.getShop().getItems(), human.getItems());
        });

        amuletShopButton.setOnMouseClicked(event -> {
            MenuView.showStuffShop(TypeOfStuffToBuyAndSell.AMULET, human.getShop().getAmulets(), human.getAmulets());
        });

        returnButton.setOnMouseClicked(event -> {
            LegendsOfArcadia.getMap().continueMap();
        });

        VBox vBox = makeVBox(cardShopButton, itemShopButton, amuletShopButton, returnButton);
        shopGroup.getChildren().add(vBox);
    }

    public static void showMultiplayerMenu(){
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        Group multiplayerMenuGroup = new Group();
        Scene scene = new Scene(multiplayerMenuGroup);
        primaryStage.setScene(scene);
        TextField textField = new TextField();
        textField.resize(100,50);
        Text nameText = new Text("Name:");
        nameText.setId("text");
        nameText.relocate(primaryStage.getWidth()/2 - 305,150);
        Button returnButton = new Button("Return");
        Button hostGameButton = new Button("Host Game");
        Button joinGameButton = new Button("Join Game");
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        VBox vBox = makeVBox(textField,hostGameButton,joinGameButton,returnButton);
        multiplayerMenuGroup.getChildren().addAll(imageView1,nameText,vBox);
        hostGameButton.setOnMouseClicked(event -> {
            if (!textField.getText().equals("")) {
                human.setName(textField.getText());
                showHostGameMenu();
            }
        });
        joinGameButton.setOnMouseClicked(event -> {
            if (!textField.getText().equals("")) {
                human.setName(textField.getText());
                showJoinGameMenu();
            }
        });
        returnButton.setOnMouseClicked(event -> showMainMenu());
    }

    private static void showHostGameMenu(){
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        Group hostGameMenuGroup = new Group();
        Scene scene = new Scene(hostGameMenuGroup);
        primaryStage.setScene(scene);
        Button returnButton = new Button("Return");
        Button hostGameButton = new Button("Host Game");
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        TextField textField = new TextField();
        textField.resize(100,50);
        Text portText = new Text("Port Number:");
        portText.setId("text");
        Text waitingText = new Text("waiting for guest to connect to one of the ip addresses below:\n");
        portText.relocate(primaryStage.getWidth()/2 - 340,150);
        waitingText.relocate(primaryStage.getWidth()/2 - 100,800);
        Iterator<NetworkInterface> iterator = null;
        try {
            iterator = NetworkInterface.getNetworkInterfaces().asIterator();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (iterator.hasNext()) {
            Iterator<InetAddress> iterator1 = iterator.next().getInetAddresses().asIterator();
            while (iterator1.hasNext()) {
                InetAddress inetAddress = iterator1.next();
                if (inetAddress.getHostAddress().contains("."))
                    waitingText.setText(waitingText.getText().concat(inetAddress.getHostAddress() + "\n"));
            }
        }
        VBox vBox = makeVBox(textField,hostGameButton,returnButton);
        hostGameMenuGroup.getChildren().addAll(imageView1,portText,vBox);
        returnButton.setOnMouseClicked(event -> showMultiplayerMenu());
        hostGameButton.setOnMouseClicked(event -> {
            if (!textField.getText().equals("")) {
                //new Popup(waitingText.getText()).show();
                hostGameMenuGroup.getChildren().add(waitingText);
                try {
                    Main.hostGame(Integer.parseInt(textField.getText()));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void showJoinGameMenu(){
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        Group joinGameMenuGroup = new Group();
        Scene scene = new Scene(joinGameMenuGroup);
        primaryStage.setScene(scene);
        Button returnButton = new Button("Return");
        Button joinGameButton = new Button("Join Game");
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        TextField portTextField = new TextField();
        portTextField.resize(100,50);
        TextField ipTextField = new TextField();
        ipTextField.resize(100,50);
        Text portText = new Text("Port Number:");
        portText.setId("text");
        Text ipText = new Text("IP Address:");
        ipText.setId("text");
        portText.relocate(primaryStage.getWidth()/2 - 340,205);
        ipText.relocate(primaryStage.getWidth()/2 - 330,150);
        VBox vBox = makeVBox(ipTextField,portTextField,joinGameButton,returnButton);
        joinGameMenuGroup.getChildren().addAll(imageView1,ipText,portText,vBox);
        returnButton.setOnMouseClicked(event -> showMultiplayerMenu());
        joinGameButton.setOnMouseClicked(event -> {
            if (!portTextField.getText().equals("") && !ipTextField.getText().equals("")) {
                try {
                    Main.joinGame(ipTextField.getText(),Integer.parseInt(portTextField.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showStuffShop(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, List<? extends Stuff> shopStuff, List<? extends Stuff> playerStuff){
        showStuffShop(typeOfStuffToBuyAndSell, shopStuff, playerStuff, null);
    }

    public static void showStuffShop(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, List<? extends Stuff> shopStuff, List<? extends Stuff> playerStuff, String transactionMessage){
        Group cardShopGroup = new Group();
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        cardShopGroup.getChildren().addAll(imageView1);
        Scene scene = new Scene(cardShopGroup);
        ArrayList<Button> shopButtons = new ArrayList<>();
        ArrayList<Button> playerButtons = new ArrayList<>();
        TextArea textArea = new TextArea();
        if(transactionMessage == null) {
            textArea.setText("welcome to the shop!\nhere you can buy the stuff you want and sell the stuff that you no longer need.");
        }
        Text gilText = new Text("Remaining Gil: " + human.getGil());
        TextArea transactionResult = new TextArea(transactionMessage);
        Button returnButton = new Button();

        StackPane stackPane = new StackPane();
        Rectangle headLineRectangle = new Rectangle(330, 30);
        headLineRectangle.setFill(Color.rgb(23, 187, 237,0.6));
        Text headLineText = new Text("Shop " + typeOfStuffToBuyAndSell.name().toLowerCase() + "s");
        stackPane.getChildren().addAll(headLineRectangle, headLineText);
        stackPane.relocate(150, 60);

        StackPane stackPane1 = new StackPane();
        Rectangle headLineRectangle1 = new Rectangle(330, 30);
        headLineRectangle1.setFill(Color.rgb(20, 184, 11));
        Text headLineText1 = new Text("Your " + typeOfStuffToBuyAndSell.name().toLowerCase() + "s");
        stackPane1.getChildren().addAll(headLineRectangle1, headLineText1);
        stackPane1.relocate(1000, 60);
        //headLine.setText

        ConsoleView.setConsole(transactionResult);

        gilText.relocate(100, 20);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());
        gilText.setId("text");
        //------------------------return button----------------------
        ImageView imageView = new ImageView(new Image("file:return-icon3.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        returnButton.setGraphic(imageView);
        returnButton.setMaxWidth(50);
        //returnButton.setStyle("-fx-background-color: rgba(20, 100, 40, 0.7);");
        returnButton.relocate(1150, 620);
        returnButton.setOnMouseClicked(event -> {
            Main.enterShop();
        });

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
                            showStuffShop(TypeOfStuffToBuyAndSell.AMULET, human.getShop().getAmulets(), human.getAmulets(), transactionResult.getText());
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
        gilText.setId("text");
        gilText.setFill(Color.rgb(255,255,255));


        cardShopGroup.getChildren().addAll(shopItemsListView, playerItemsListView, textArea, gilText, transactionResult, returnButton, stackPane, stackPane1);
        //cardShopGroup.getChildren().addAll(vBox, scrollBar);
    }

//    private static void setPrefWidthOfButtons(double value, Button... buttons){
//        for(Button button : buttons){
//            button.setPrefWidth(buttonPrefWidth);
//        }
//    }
//




    public static void showInventoryMenu(){
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        Group shopGroup = new Group();
        ImageView imageView = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView.setFitWidth(1500);
        imageView.setFitHeight(800);
        shopGroup.getChildren().addAll(imageView);
        Scene scene = new Scene(shopGroup);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("MenuStyle.css").toExternalForm());
        Button returnButton = new Button("Return");

        returnButton.setOnMouseClicked(event -> {
            if (human.getDeckCards().size() < 25) {
                Popup popup = new Popup("Deck must contain at least 25 cards!");
                popup.show();
            }
            else {
                LegendsOfArcadia.getMap().continueMap();
            }
        });

        editDeckButton.setOnMouseClicked(event -> {
            MenuView.showEditDeck(human.getInventoryMinusDeck(), human.getDefaultDeckCards(), null, false);
        });

        editAmuletButton.setOnMouseClicked(event -> {
            MenuView.showEditAmulet(human.getAmulets(), human.getEquippedAmulet(), null);
        });

        VBox vBox = makeVBox(editDeckButton, editAmuletButton, returnButton);
        shopGroup.getChildren().add(vBox);
    }











    public static void showEditDeck(ArrayList<Card> inventoryCardsMinusDeck, ArrayList<Card> deckCardsWithNull, String transferMessage, boolean nextIsBattle){
        Group editDeckGroup = new Group();
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        editDeckGroup.getChildren().addAll(imageView1);
        Scene scene = new Scene(editDeckGroup);
        ArrayList<Button> inventoryButtons = new ArrayList<>();
        ArrayList<Button> deckButtons = new ArrayList<>();
        TextArea textArea = new TextArea();
        if(transferMessage == null) {
            textArea.setText("Here you can edit your Deck.");
        }
        TextArea transferResult = new TextArea(transferMessage);
        Button returnButton = new Button();

        StackPane stackPane = new StackPane();
        Rectangle headLineRectangle = new Rectangle(330, 30);
        headLineRectangle.setFill(Color.rgb(23, 187, 237));
        Text headLineText = new Text("Inventory");
        stackPane.getChildren().addAll(headLineRectangle, headLineText);
        stackPane.relocate(150, 60);

        StackPane stackPane1 = new StackPane();
        Rectangle headLineRectangle1 = new Rectangle(330, 30);
        headLineRectangle1.setFill(Color.rgb(20, 184, 11));
        Text headLineText1 = new Text("Deck");
        stackPane1.getChildren().addAll(headLineRectangle1, headLineText1);
        stackPane1.relocate(1000, 60);
        //headLine.setText

        ConsoleView.setConsole(transferResult);

        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());

        //------------------------return button----------------------
        ImageView imageView = new ImageView(new Image("file:return-icon3.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        returnButton.setGraphic(imageView);
        returnButton.setMaxWidth(50);
        //returnButton.setStyle("-fx-background-color: rgba(20, 100, 40, 0.7);");
        returnButton.relocate(1150, 620);
        returnButton.setOnMouseClicked(event -> {
            try {
                MenuView.showInventoryMenu();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        //------------sort shopStuff and playerStuff by name-----------------
        inventoryCardsMinusDeck.sort(new Comparator<Stuff>() {
            @Override
            public int compare(Stuff o1, Stuff o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        //-------------------------------------------------------------------

        for(Stuff stuff : inventoryCardsMinusDeck){
            Button itemButton = new Button(stuff.getName());
            //itemButton.setMinHeight(50);
            itemButton.setOnMouseEntered(event -> {
                textArea.setText(stuff.toString());
            });
            itemButton.setOnMouseClicked(event -> {
                try {
                    transferResult.clear();
                    for(int i=0; i<30; i++){
                        if(deckCardsWithNull.get(i) == null){
                            Main.editDeck("add " + stuff.getName() + " " + (i+1), nextIsBattle);
                            MenuView.showEditDeck(human.getInventoryMinusDeck(), human.getDefaultDeckCards(), transferResult.getText(), nextIsBattle);
                            return;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            inventoryButtons.add(itemButton);
        }

        for(int i=0; i<30; i++){
            Button itemButton = new Button(deckCardsWithNull.get(i) == null ? "empty" : deckCardsWithNull.get(i).getName());
            if(deckCardsWithNull.get(i) == null){
                itemButton.setDisable(true);
            }else {
                final Stuff stuff = deckCardsWithNull.get(i);
                itemButton.setOnMouseEntered(event -> {
                    textArea.setText(stuff.toString());
                });
                final int ind = i+1;
                itemButton.setOnMouseClicked(event -> {
                    try {
                        transferResult.clear();
                        Main.editDeck("remove " + ind, nextIsBattle);
                        MenuView.showEditDeck(human.getInventoryMinusDeck(), human.getDefaultDeckCards(), transferResult.getText(), nextIsBattle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            deckButtons.add(itemButton);
        }

        textArea.relocate(600, 180);
        textArea.setEditable(false);
        textArea.setPrefSize(300, 300);

        transferResult.relocate(600, 525);
        transferResult.setEditable(false);
        transferResult.setPrefSize(300, 50);

        ListView<Button> shopItemsListView = new ListView<>(FXCollections.observableArrayList(inventoryButtons));
        //shopItemsListView.setFixedCellSize(60);
        shopItemsListView.relocate(150, 100);
        shopItemsListView.setPrefSize(330, 500);

        ListView<Button> playerItemsListView = new ListView<>(FXCollections.observableArrayList(deckButtons));
        playerItemsListView.relocate(1000, 100);
        playerItemsListView.setPrefSize(330, 500);


        editDeckGroup.getChildren().addAll(shopItemsListView, playerItemsListView, textArea, transferResult, returnButton, stackPane, stackPane1);
    }






    public static void showEditAmulet(ArrayList<Amulet> amulets, Amulet equippedAmulet, String transferMessage){
        Group editAmuletGroup = new Group();
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        editAmuletGroup.getChildren().addAll(imageView1);
        Scene scene = new Scene(editAmuletGroup);
        ArrayList<Button> amuletsButtons = new ArrayList<>();
        ArrayList<Button> equippedAmuletButtons = new ArrayList<>();
        TextArea textArea = new TextArea();
        if(transferMessage == null) {
            textArea.setText("Here you can pick your desired Amulet.");
        }
        TextArea transferResult = new TextArea(transferMessage);
        Button returnButton = new Button();

        StackPane stackPane = new StackPane();
        Rectangle headLineRectangle = new Rectangle(330, 30);
        headLineRectangle.setFill(Color.rgb(23, 187, 237));
        Text headLineText = new Text("Amulets");
        stackPane.getChildren().addAll(headLineRectangle, headLineText);
        stackPane.relocate(150, 60);

        StackPane stackPane1 = new StackPane();
        Rectangle headLineRectangle1 = new Rectangle(330, 30);
        headLineRectangle1.setFill(Color.rgb(20, 184, 11));
        Text headLineText1 = new Text("Equipped");
        stackPane1.getChildren().addAll(headLineRectangle1, headLineText1);
        stackPane1.relocate(1000, 60);
        //headLine.setText

        ConsoleView.setConsole(transferResult);

        primaryStage.setScene(scene);
        scene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());

        //------------------------return button----------------------
        ImageView imageView = new ImageView(new Image("file:return-icon3.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        returnButton.setGraphic(imageView);
        returnButton.setMaxWidth(50);
        //returnButton.setStyle("-fx-background-color: rgba(20, 100, 40, 0.7);");
        returnButton.relocate(1150, 620);
        returnButton.setOnMouseClicked(event -> {
            try {
                MenuView.showInventoryMenu();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        //------------sort shopStuff and playerStuff by name-----------------
        amulets.sort(new Comparator<Stuff>() {
            @Override
            public int compare(Stuff o1, Stuff o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        //-------------------------------------------------------------------

        for(Stuff stuff : amulets){
            if(equippedAmulet != null && stuff.getName().equalsIgnoreCase(equippedAmulet.getName()))
                continue;
            Button itemButton = new Button(stuff.getName());
            //itemButton.setMinHeight(50);
            itemButton.setOnMouseEntered(event -> {
                textArea.setText(stuff.toString());
            });
            itemButton.setOnMouseClicked(event -> {
                transferResult.clear();
                Main.editAmulet("equip " + stuff.getName());
                showEditAmulet(human.getAmulets(), human.getEquippedAmulet(), transferResult.getText());
            });
            amuletsButtons.add(itemButton);
        }


        if(equippedAmulet != null) {
            Button equippedAmuletButton = new Button(equippedAmulet.getName());
            //final Stuff stuff = deckCardsWithNull.get(i);
            equippedAmuletButton.setOnMouseEntered(event -> {
                textArea.setText(equippedAmulet.toString());
            });
            //final int ind = i + 1;
            equippedAmuletButton.setOnMouseClicked(event -> {
                transferResult.clear();
                Main.editAmulet("remove amulet");
                showEditAmulet(human.getAmulets(), human.getEquippedAmulet(), transferResult.getText());
            });
            equippedAmuletButtons.add(equippedAmuletButton);
        }


        textArea.relocate(600, 180);
        textArea.setEditable(false);
        textArea.setPrefSize(300, 300);

        transferResult.relocate(600, 525);
        transferResult.setEditable(false);
        transferResult.setPrefSize(300, 50);

        ListView<Button> shopItemsListView = new ListView<>(FXCollections.observableArrayList(amuletsButtons));
        //shopItemsListView.setFixedCellSize(60);
        shopItemsListView.relocate(150, 100);
        shopItemsListView.setPrefSize(330, 500);

        ListView<Button> playerItemsListView = new ListView<>(FXCollections.observableArrayList(equippedAmuletButtons));
        playerItemsListView.relocate(1000, 100);
        playerItemsListView.setPrefSize(330, 500);


        editAmuletGroup.getChildren().addAll(shopItemsListView, playerItemsListView, textArea, transferResult, returnButton, stackPane, stackPane1);
    }








    public static VBox makeVBox(double x, double y, double prefWidth, double spacing, Node... nodes){
        VBox vBox = new VBox(spacing, nodes);
        vBox.setPrefWidth(prefWidth);//TODO css VBox?
        vBox.setPrefHeight(primaryStage.getHeight()/2);
        //vBox.setMaxHeight(400);
        vBox.setLayoutX(x);
        vBox.setLayoutY(y);

        return vBox;
    }

    public static VBox makeVBox(Node... nodes){
//        for(Node button : buttons){
//            button.setPrefWidth(buttonPrefWidth);
//        }
        return makeVBox(primaryStage.getWidth()/2 - buttonPrefWidth/2, primaryStage.getHeight()/2 - buttonPrefWidth/2, buttonPrefWidth, primaryStage.getHeight()/28, nodes);
    }

    public static VBox makeVBox(List<? extends Node> nodes){
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
