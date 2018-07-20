package View;

import Controller.*;
import Model.Card.SpellCard;
import Model.Spell.GeneralizedSpell;
import Model.Spell.Spell;
import Model.Stuff;
import Model.TypeOfStuffToBuyAndSell;
import View.GameView.ConsoleView;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;

import static Controller.Main.human;
import static View.MenuView.makeVBox;

public class CustomGameView {
    private static Stage primaryStage = LegendsOfArcadia.getPrimaryStage();
    private static Game newCustomGame;
    private static ArrayList<Spell> customGameSpells = new ArrayList<>();
    private static ArrayList<Spell> playerCustomGameSpells = new ArrayList<>();
    private static ArrayList<GeneralizedSpell> generalizedGameSpells = new ArrayList<>();

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
                //newCustomGame = (Game)DeepCopy.copy(LegendsOfArcadia.customGames.get(ind));//TODO not necessary
                Game.setCurrentGame(LegendsOfArcadia.customGames.get(ind));
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

        createGeneralizedSpellButton.setOnMouseClicked(event -> {
            showGeneralizedSpellMakingMenu();
        });

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




    public static void showGeneralizedSpellMakingMenu(){
        Group cardShopGroup = new Group();
        Scene scene = new Scene(cardShopGroup);
        ArrayList<Button> shopButtons = new ArrayList<>();  //todo refactor rename
        ArrayList<Button> playerButtons = new ArrayList<>();
        TextArea textArea = new TextArea();
        //Text gilText = new Text("Remaining Gil: " + human.getGil());
        TextArea transactionResult = new TextArea("welcome to spell making!\nhere you can craft a custom spell of what you like.");
        Button returnButton = new Button();
        Button submitButton = new Button();

        StackPane stackPane = new StackPane();
        Rectangle headLineRectangle = new Rectangle(330, 30);
        headLineRectangle.setFill(Color.rgb(23, 187, 237));
        Text headLineText = new Text("Spells");
        stackPane.getChildren().addAll(headLineRectangle, headLineText);
        stackPane.relocate(150, 60);

        StackPane stackPane1 = new StackPane();
        Rectangle headLineRectangle1 = new Rectangle(330, 30);
        headLineRectangle1.setFill(Color.rgb(20, 184, 11));
        Text headLineText1 = new Text("Your selected spells");
        stackPane1.getChildren().addAll(headLineRectangle1, headLineText1);
        stackPane1.relocate(1000, 60);
        //headLine.setText

        ConsoleView.setConsole(transactionResult);

        //gilText.relocate(100, 20);

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
            showEditPart();
        });

        //------------------------submit button----------------------
        //submitButton.setMaxWidth(50);
        submitButton.relocate(600, 600);
        submitButton.setText("Craft Spell");
        submitButton.setOnMouseClicked(event -> {
            if (playerCustomGameSpells.size() == 0) {
                new Popup("crafted spell must contain at least 1 spell.").show();
                return;
            }
            Spell[] spells = new Spell[playerCustomGameSpells.size()];
            for (int i = 0; i < playerCustomGameSpells.size(); i++) {
                spells[i] = playerCustomGameSpells.get(i);
            }
            GeneralizedSpell generalizedSpell = new GeneralizedSpell(spells, "Custom Spell", "Custom Spell"); //todo consider name
            generalizedGameSpells.add(generalizedSpell);
        });

        //------------sort customGameSpells and playerStuff by name-----------------//todo correct
        customGameSpells.sort(new Comparator<Spell>() {
            @Override
            public int compare(Spell o1, Spell o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        playerCustomGameSpells.sort(new Comparator<Spell>() {
            @Override
            public int compare(Spell o1, Spell o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        //-------------------------------------------------------------------

        for(Spell customGameSpell : customGameSpells){
            Button itemButton = new Button(customGameSpell.getName());
            itemButton.setOnMouseEntered(event -> {
                textArea.setText(customGameSpell.toString());
            });
            itemButton.setOnMouseClicked(event -> {
                transactionResult.clear();
                customGameSpells.remove(customGameSpell);
                playerCustomGameSpells.add(customGameSpell);
                showGeneralizedSpellMakingMenu();
            });
            shopButtons.add(itemButton);
        }

        for(Spell playerCustomGameSpell : playerCustomGameSpells){
            Button itemButton = new Button(playerCustomGameSpell.getName());
            itemButton.setOnMouseEntered(event -> {
                textArea.setText(playerCustomGameSpell.toString());
            });
            itemButton.setOnMouseClicked(event -> {
                transactionResult.clear();
                playerCustomGameSpells.remove(playerCustomGameSpell);
                customGameSpells.add(playerCustomGameSpell);
                showGeneralizedSpellMakingMenu();
            });
            playerButtons.add(itemButton);
        }

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



        cardShopGroup.getChildren().addAll(shopItemsListView, playerItemsListView, textArea, transactionResult, returnButton, submitButton, stackPane, stackPane1);
        //cardShopGroup.getChildren().addAll(vBox, scrollBar);
    }
}
