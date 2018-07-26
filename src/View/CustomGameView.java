package View;

import Controller.*;
import Model.*;
import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Card.SpellCardType;
import Model.Spell.*;
import View.GameView.ConsoleView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;

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
        ImageView imageView1 = new ImageView(new Image(new File("brown-background-waves.jpg").toURI().toString()));
        imageView1.setFitWidth(1500);
        imageView1.setFitHeight(800);
        root.getChildren().addAll(imageView1);
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
            showMainEntrance();
        });

        ListView<HBox> listView = new ListView<>(FXCollections.observableArrayList(hBoxes));
        listView.setPrefSize(330, 550);
        listView.relocate(primaryStage.getWidth()/2 - listView.getPrefWidth()/2, 150);

        root.getChildren().addAll(listView, returnButton);
    }














    public static void showEditPart(){
        Group root = new Group();
        Scene editPartScene = new Scene(root);
        editPartScene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());//TODO css
        primaryStage.setScene(editPartScene);
        primaryStage.setTitle("Customize Game");

        Button createSpellButton = new Button("Create Spell");
        Button createGeneralizedSpellButton = new Button("Create Generalized Spell");
        Button newCardsButton = new Button("Create New Cards");
        Button newItemsButton = new Button("Create New Items");//TODO create or edit?
        Button newAmuletsButton = new Button("Create New Amulets");
        Button editShopButton = new Button("Edit Shop");
        Button editDecksButton = new Button("Edit Decks");

        createSpellButton.setOnMouseClicked(event -> {
            showCreateSpell();
        });

        createGeneralizedSpellButton.setOnMouseClicked(event -> {
            showGeneralizedSpellMakingMenu();
        });

        newCardsButton.setOnMouseClicked(event -> {
            showNewCardMakingMenu();
        });

        newItemsButton.setOnMouseClicked(event -> {
            showItemAmuletMakingMenu(TypeOfStuffToBuyAndSell.ITEM);
        });

        newAmuletsButton.setOnMouseClicked(event -> {
            showItemAmuletMakingMenu(TypeOfStuffToBuyAndSell.AMULET);
        });

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            showNewGameTemplate();
        });

        TextField nameOfGameTextField = new TextField();
        Button saveButton = new Button("Save Game");

        saveButton.setOnMouseClicked(event -> {
            if(!areValidTextFields(nameOfGameTextField)){
                new Popup("Please enter the new game's name").show();
                return;
            }
            Game game = Game.getCopyOfCurrentGame();
            game.setName(nameOfGameTextField.getText());
            LegendsOfArcadia.customGames.add(game);
            showMainEntrance();
            new Popup(nameOfGameTextField.getText() + " created successfully").show();
        });
        nameOfGameTextField.relocate(860, 340);
        saveButton.relocate(805, 380);

        VBox vBox = makeVBox(createSpellButton, createGeneralizedSpellButton, newCardsButton, newItemsButton, newAmuletsButton, editShopButton, editDecksButton);

        root.getChildren().addAll(vBox, returnButton, nameOfGameTextField, saveButton);
    }
























    public static void showCreateSpell(){
        Group root = new Group();
        Scene createSpellScene = new Scene(root);
        createSpellScene.getStylesheets().add(MenuView.class.getResource("ShopStyle.css").toExternalForm());//TODO css
        primaryStage.setScene(createSpellScene);
        primaryStage.setTitle("Create Spell");

        ArrayList<HBox> hBoxes = new ArrayList<>();

        ChoiceBox<String> typeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("move spell", "hp spell", "ap spell"));
        ChoiceBox<SpellArea> targetPlaceChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(SpellArea.values()));
        ChoiceBox<String> targetCardTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Monster Card", "Spell Card", "Card"));
        ChoiceBox<SpellChoiceType> selectionMethodChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(SpellChoiceType.values()));
        ChoiceBox<SpellArea> destinationChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(SpellArea.values()));
        destinationChoiceBox.getItems().removeAll(SpellArea.FRIENDLY_PLAYER, SpellArea.ENEMY_PLAYER);
        TextField amountTextField = new TextField();

        hBoxes.add(new HBox(5, new Label("Type:"), typeChoiceBox));
        hBoxes.add(new HBox(5, new Label("Target Place:"), targetPlaceChoiceBox));
        hBoxes.add(new HBox(5, new Label("Target Card Type:"), targetCardTypeChoiceBox));
        hBoxes.add(new HBox(5, new Label("Selection Method:"), selectionMethodChoiceBox));
        HBox desHBox = new HBox(5, new Label("Destination:"), destinationChoiceBox);
        hBoxes.add(desHBox);
        HBox amountHBox = new HBox(5, new Label("Change Amount:"), amountTextField);
        amountHBox.setAlignment(Pos.CENTER);

        for(HBox hBox : hBoxes){
            hBox.setAlignment(Pos.CENTER);
        }
        VBox vBox = makeVBox(hBoxes);
        vBox.setAlignment(Pos.TOP_CENTER);

        typeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                targetPlaceChoiceBox.getItems().removeAll(SpellArea.FRIENDLY_PLAYER, SpellArea.ENEMY_PLAYER);
                if(newValue.equalsIgnoreCase("move spell")){
                    vBox.getChildren().set(4, desHBox);
                }else{
                    targetPlaceChoiceBox.getItems().addAll(SpellArea.FRIENDLY_PLAYER, SpellArea.ENEMY_PLAYER);
                    vBox.getChildren().set(4, amountHBox);
                }
            }
        });

        targetPlaceChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SpellArea>() {
            @Override
            public void changed(ObservableValue<? extends SpellArea> observable, SpellArea oldValue, SpellArea newValue) {
                if(newValue.name().endsWith("PLAYER")){
                    targetCardTypeChoiceBox.setDisable(true);
                    selectionMethodChoiceBox.setValue(SpellChoiceType.ALL);
                    selectionMethodChoiceBox.setDisable(true);
                }else{
                    targetCardTypeChoiceBox.setDisable(false);
                    selectionMethodChoiceBox.setDisable(false);
                }
            }
        });


        //--------------submit button----------------
        Button submitButton = new Button("Craft Spell");
        submitButton.setOnMouseClicked(event -> {
            if(!areValidChoiceBoxes(typeChoiceBox, targetPlaceChoiceBox, targetCardTypeChoiceBox, selectionMethodChoiceBox)){
                new Popup("you must specify the spell's details").show();
                return;
            }
            Class[] targetClasses = new Class[1];//{getClassByName(targetCardTypeChoiceBox.getValue())};
            if(targetPlaceChoiceBox.getValue().name().endsWith("PLAYER")){
                targetClasses[0] = PlayerHero.class;
            }else{
                targetClasses[0] = getClassByName(targetCardTypeChoiceBox.getValue());
            }

            Spell spell = null;

            if(typeChoiceBox.getValue().equalsIgnoreCase("move spell")){
                if(!areValidChoiceBoxes(destinationChoiceBox)){
                    new Popup("you must specify the spell's details").show();
                    return;
                }
                spell = new MoveSpell(EnumSet.of(targetPlaceChoiceBox.getValue()), targetClasses, selectionMethodChoiceBox.getValue(), destinationChoiceBox.getValue());
            }else{
                if(!areValidTextFields(amountTextField)){
                    new Popup("you must specify the spell's details").show();
                    return;
                }
                try {
                    if (typeChoiceBox.getValue().equalsIgnoreCase("hp spell")) {
                        spell = new HPSpell(EnumSet.of(targetPlaceChoiceBox.getValue()), targetClasses, selectionMethodChoiceBox.getValue(), Integer.parseInt(amountTextField.getText()));
                    } else if (typeChoiceBox.getValue().equalsIgnoreCase("ap spell")) {
                        spell = new APSpell(EnumSet.of(targetPlaceChoiceBox.getValue()), targetClasses, selectionMethodChoiceBox.getValue(), Integer.parseInt(amountTextField.getText()));
                    }
                }catch (Exception e){
                    new Popup("Invalid input").show();
                    return;
                }
            }
            spell.setName("Spell " + (customGameSpells.size()+playerCustomGameSpells.size()+1));
            customGameSpells.add(spell);
            new Popup("Spell Created!").show();
        });
        submitButton.relocate(600, 530);

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            showEditPart();
        });

        root.getChildren().addAll(vBox, submitButton, returnButton);
    }
















    public static Class getClassByName(String name){
        if(name.equalsIgnoreCase("Monster Card"))
            return MonsterCard.class;
        if(name.equalsIgnoreCase("Spell Card"))
            return SpellCard.class;
        if(name.equalsIgnoreCase("Card"))
            return Card.class;
        return Exception.class;
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

    public static boolean areValidChoiceBoxes(ChoiceBox... choiceBoxes){
        for(ChoiceBox choiceBox : choiceBoxes){
            if(!choiceBox.isDisable() && choiceBox.getValue() == null){
                return false;
            }
        }
        return true;
    }

    public static boolean areValidTextFields(TextField... textFields){
        for(TextField textField : textFields){
            if(textField.getText() == null || textField.getText().equalsIgnoreCase("")){
                return false;
            }
        }
        return true;
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
            GeneralizedSpell generalizedSpell = new GeneralizedSpell(spells, "Custom Spell", "Custom Spell " + (generalizedGameSpells.size() + 1)); //todo consider name
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


















    public static void showNewCardMakingMenu(){
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create New Card");

        Button spellCardMakingMenuButton = new Button("New Spell Card");
        Button monsterCardMakingMenuButton = new Button("New Monster Card");

        spellCardMakingMenuButton.setOnMouseClicked(event -> {
            showSpellCardMakingMenu();
        });

        monsterCardMakingMenuButton.setOnMouseClicked(event -> {
            showMonsterCardMakingMenu();
        });

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            showEditPart();
        });

        VBox vBox = makeVBox(spellCardMakingMenuButton, monsterCardMakingMenuButton);

        root.getChildren().addAll(vBox, returnButton);
    }
















    public static void showSpellCardMakingMenu(){
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create New Spell Card");

        ArrayList<HBox> hBoxes = new ArrayList<>();

        TextArea textArea = new TextArea();
        ChoiceBox<GeneralizedSpell> generalizedSpellChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(generalizedGameSpells));
        ChoiceBox<SpellCardType> spellCardTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(SpellCardType.values()));
        //new ChoiceBox<>()
        TextField manaTextField = new TextField();
        TextField priceTextField = new TextField();
        TextField nameTextField = new TextField();

        hBoxes.add(new HBox(5, new Label("Generalized Spell:"), generalizedSpellChoiceBox));
        hBoxes.add(new HBox(5, new Label("Spell Card Type:"), spellCardTypeChoiceBox));
        hBoxes.add(new HBox(5, new Label("Mana Cost:"), manaTextField));
        hBoxes.add(new HBox(5, new Label("Price:"), priceTextField));
        hBoxes.add(new HBox(5, new Label("Name:"), nameTextField));

        for(HBox hBox : hBoxes){
            hBox.setAlignment(Pos.CENTER);
        }
        VBox vBox = makeVBox(hBoxes);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.relocate(150, 200);

        generalizedSpellChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GeneralizedSpell>() {
            @Override
            public void changed(ObservableValue<? extends GeneralizedSpell> observable, GeneralizedSpell oldValue, GeneralizedSpell newValue) {
                textArea.setText(newValue.getDetail());
            }
        });

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            showNewCardMakingMenu();
        });

        //--------------submit button----------------
        Button submitButton = new Button("Craft Spell Card");
        submitButton.setOnMouseClicked(event -> {
            if(!areValidChoiceBoxes(generalizedSpellChoiceBox, spellCardTypeChoiceBox) || !areValidTextFields(manaTextField, priceTextField, nameTextField)){
                new Popup("you must specify the card's details").show();
                return;
            }
            try{
                new SpellCard(generalizedSpellChoiceBox.getValue(), Integer.parseInt(manaTextField.getText()), spellCardTypeChoiceBox.getValue(), Integer.parseInt(priceTextField.getText()), nameTextField.getText());
            }catch (Exception e){
                new Popup("Invalid input").show();
                return;
            }
            new Popup(nameTextField.getText() + " spell card created!").show();
        });
        submitButton.relocate(600, 530);

        //------------------text area----------------------
        textArea.relocate(600, 180);
        textArea.setEditable(false);
        textArea.setPrefSize(300, 300);

        root.getChildren().addAll(vBox, textArea, returnButton, submitButton);
    }



















    public static void showMonsterCardMakingMenu(){
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create New Monster Card");

        //TODO

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            showEditPart();
        });
    }





















    public static void showItemAmuletMakingMenu(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell){
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MenuView.class.getResource("CustomGameStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create New " + typeOfStuffToBuyAndSell.name().toLowerCase());

        ArrayList<HBox> hBoxes = new ArrayList<>();

        TextArea textArea = new TextArea();
        ChoiceBox<GeneralizedSpell> generalizedSpellChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(generalizedGameSpells));
        TextField priceTextField = new TextField();
        TextField nameTextField = new TextField();

        hBoxes.add(new HBox(5, new Label("Generalized Spell:"), generalizedSpellChoiceBox));
        hBoxes.add(new HBox(5, new Label("Price:"), priceTextField));
        hBoxes.add(new HBox(5, new Label("Name:"), nameTextField));

        for(HBox hBox : hBoxes){
            hBox.setAlignment(Pos.CENTER);
        }
        VBox vBox = makeVBox(hBoxes);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.relocate(150, 200);

        generalizedSpellChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GeneralizedSpell>() {
            @Override
            public void changed(ObservableValue<? extends GeneralizedSpell> observable, GeneralizedSpell oldValue, GeneralizedSpell newValue) {
                textArea.setText(newValue.getDetail());
            }
        });

        Button returnButton = new Button();
        setStatusOfReturnButton(returnButton, 950, 530);
        returnButton.setOnMouseClicked(event -> {
            showEditPart();
        });

        //--------------submit button----------------
        Button submitButton = new Button("Craft " + typeOfStuffToBuyAndSell.name().toLowerCase());
        submitButton.setOnMouseClicked(event -> {
            if(!areValidChoiceBoxes(generalizedSpellChoiceBox) || !areValidTextFields(priceTextField, nameTextField)){
                new Popup("you must specify the card's details").show();
                return;
            }
            try{
                if(typeOfStuffToBuyAndSell.equals(TypeOfStuffToBuyAndSell.ITEM))
                    new Item(generalizedSpellChoiceBox.getValue(), Integer.parseInt(priceTextField.getText()), nameTextField.getText());
                else if(typeOfStuffToBuyAndSell.equals(TypeOfStuffToBuyAndSell.AMULET))
                    new Amulet(generalizedSpellChoiceBox.getValue(), Integer.parseInt(priceTextField.getText()), nameTextField.getText());
            }catch (Exception e){
                new Popup("Invalid input").show();
                return;
            }
            new Popup(nameTextField.getText() + " " + typeOfStuffToBuyAndSell.name().toLowerCase() + " created!").show();
        });
        submitButton.relocate(600, 530);

        //------------------text area----------------------
        textArea.relocate(600, 180);
        textArea.setEditable(false);
        textArea.setPrefSize(300, 300);

        root.getChildren().addAll(vBox, textArea, returnButton, submitButton);
    }
}
