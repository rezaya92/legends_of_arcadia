package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuView {
    private static final double buttonPrefWidth = 300;
    private static Button singlePlayerButton = new Button("Single Player");
    private static Button customGameButton = new Button("Custom Game");
    private static Button multiPlayerButton = new Button("Multi Player");
    private static Button settingsButton = new Button("Settings");
    private static Button exitButton = new Button("Exit");

    private static Button cardShopButton = new Button("Card Shop");
    private static Button itemShopButton = new Button("Item Shop");
    private static Button amuletShopButton = new Button("Amulet Shop");
    private static VBox vBox;

    public static void showMainMenu(Stage primaryStage){
        Group mainMenuGroup = new Group();
        Scene scene = new Scene(mainMenuGroup);
        primaryStage.setScene(scene);

        VBox vBox = makeVBox(primaryStage, singlePlayerButton, customGameButton, multiPlayerButton, settingsButton, exitButton);

        mainMenuGroup.getChildren().addAll(vBox);
    }

    public static void showShop(Stage primaryStage){
        Group shopGroup = new Group();
        Scene scene = new Scene(shopGroup);
        primaryStage.setScene(scene);
        VBox vBox = makeVBox(primaryStage, cardShopButton, itemShopButton, amuletShopButton);
        shopGroup.getChildren().add(vBox);
    }

    private static VBox makeVBox(Stage primaryStage, Button... buttons){
        for(Button button : buttons){
            button.setPrefWidth(buttonPrefWidth);
        }
        vBox = new VBox(primaryStage.getHeight()/28, buttons);
        vBox.setPrefWidth(buttonPrefWidth);
        vBox.setPrefHeight(primaryStage.getHeight()/2);
        vBox.setLayoutX(primaryStage.getWidth()/2 - vBox.getPrefWidth()/2);
        vBox.setLayoutY(primaryStage.getHeight()/2 - vBox.getPrefHeight()/2);

        return vBox;
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
}
