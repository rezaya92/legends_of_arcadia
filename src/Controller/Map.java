package Controller;

import View.MenuView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by msi-pc on 6/18/2018.
 */
public class Map {
    Stage primaryStage;
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private int level;
    private int height = 900;
    private int width = 1618;
    private Text message = new Text(width/2 - 100, height - 150, "move\tWASD\nrun\t\tSHIFT" + "\ninventory\t  I");
    private boolean shownMessage = false;
    private boolean firstKey = false;

    private boolean goNorth = false;
    private boolean goEast  = false;
    private boolean goSouth = false;
    private boolean goWest  = false;
    private boolean doAction= false;

    private int actionTime = 0;

    private Image mapImage;
    private ImageView mapImageView = new ImageView();

    private ImageView character = new ImageView();
    private ArrayList<Image> toRight = new ArrayList<>();
    private ArrayList<Image> toLeft = new ArrayList<>();
    private ArrayList<Image> toUp = new ArrayList<>();
    private ArrayList<Image> toDown = new ArrayList<>();

    private PixelReader mapLava, mapTerrain, mapTerrain2, mapRocks, mapBridge, mapShop, mapCastle, mapBlocks1, mapBlocks2, mapBlocks3, mapTransparent;

    private int upNumber = 0;
    private int downNumber = 0;
    private int leftNumber = 0;
    private int rightNumber = 0;

    private EventHandler<ActionEvent> moveEvent;
    private Timeline moveTimeline;
    private int moveSpeed = 2;
    private int durationTime = 30;

    private int footageX1 = 20, footageX2 = 44, footageY1 = 40, footageY2 = 64;

    public Map(Stage primaryStage, int level){
        this.primaryStage = primaryStage;
        this.level = level;

        stageProcess();

        imagesProcess();

        levelProcess();

        root.getChildren().add(mapImageView);
        root.getChildren().add(character);
        root.getChildren().add(message);

        createMoveEvent();

        createMoveTimer();

        scene.setOnKeyPressed(event -> {
            if (!firstKey) {
                root.getChildren().remove(message);
                firstKey = true;
            }
            switch (event.getCode()){
                case W:     goNorth = true; break;
                case D:     goEast  = true; break;
                case S:     goSouth = true; break;
                case A:     goWest  = true; break;
                case SHIFT: durationTime /= 2; createMoveTimer(); break;    //fps change(not good)
                case E:     doAction= true; break;
                case I:     MenuView.showInventoryMenu();
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()){
                case W:     goNorth = false; break;
                case D:     goEast  = false; break;
                case S:     goSouth = false; break;
                case A:     goWest  = false; break;
                case SHIFT: durationTime *= 2; createMoveTimer(); break;    //fps change(not good)
                case E:     doAction= false; break;
            }
        });
    }


    private void createMoveEvent() {
        moveEvent = event -> {
            double newX = character.getLayoutX();
            double newY = character.getLayoutY();
            if (goNorth){
                newY -= moveSpeed;
            }
            if (goSouth){
                newY += moveSpeed;
            }
            if (goEast){
                newX += moveSpeed;
            }
            if (goWest){
                newX -= moveSpeed;
            }

            if (!blockPoints(character.getLayoutX(), newY)) {
                if (goNorth) {
                    character.setImage(toUp.get(upNumber));
                    upNumber = (upNumber + 1) % toUp.size();


                    if (character.getLayoutY() > 0)
                        character.setLayoutY(character.getLayoutY() - moveSpeed);
                    if (mapImageView.getLayoutY() < 0 && character.getLayoutY() <= height / 2) {
                        mapImageView.setLayoutY(mapImageView.getLayoutY() + moveSpeed);
                        character.setLayoutY(character.getLayoutY() + moveSpeed);
                    }
                }
                if (goSouth) {
                    character.setImage(toDown.get(downNumber));
                    downNumber = (downNumber + 1) % toDown.size();

                    if (character.getLayoutY() < height - 115)
                        character.setLayoutY(character.getLayoutY() + moveSpeed);
                    if (mapImageView.getLayoutY() + mapImage.getHeight() + 46 > height && character.getLayoutY() > height / 2) {
                        mapImageView.setLayoutY(mapImageView.getLayoutY() - moveSpeed);
                        character.setLayoutY(character.getLayoutY() - moveSpeed);
                    }
                }
            }
            if (!blockPoints(newX, character.getLayoutY())) {
                if (goEast) {
                    character.setImage(toRight.get(rightNumber));
                    rightNumber = (rightNumber + 1) % toRight.size();

                    if (character.getLayoutX() < width - 70)
                        character.setLayoutX(character.getLayoutX() + moveSpeed);
                }
                if (goWest) {
                    character.setImage(toLeft.get(leftNumber));
                    leftNumber = (leftNumber + 1) % toLeft.size();

                    if (character.getLayoutX() > -10)
                        character.setLayoutX(character.getLayoutX() - moveSpeed);
                }
            }

            if (!isTransparent(mapShop, character.getLayoutX() + 32, character.getLayoutY() + 32 - mapImageView.getLayoutY())) {
                if (!shownMessage) {
                    message.setText("Hold E to enter Shop");
                    root.getChildren().add(message);
                    shownMessage = true;
                }
                if (doAction) {
                    actionTime += durationTime;
                    if (actionTime >= 1000){
                        moveTimeline.pause();
                        MenuView.showShop();
                    }
                }
                else {
                    actionTime = 0;
                }
            }
            else if (!isTransparent(mapCastle, character.getLayoutX() + 32, character.getLayoutY() + 32 - mapImageView.getLayoutY())){
                if (!shownMessage) {
                    message.setText("Hold E to Start Battle #" + level);
                    root.getChildren().add(message);
                    shownMessage = true;
                }
                if (doAction) {
                    actionTime += durationTime;
                    if (actionTime >= 1000){
                        moveTimeline.pause();
                        Battle.startGameAgainst(Main.opponents.get(level - 1),new Random().nextInt(2),false);
                    }
                }
                else {
                    actionTime = 0;
                }
            }
            else {
                actionTime = 0;
                if (firstKey) {
                    root.getChildren().remove(message);
                }
                shownMessage = false;
            }
        };
    }

    private boolean blockPoints(double x, double y) {
        double mapX = x - mapImageView.getLayoutX();
        double mapY = y - mapImageView.getLayoutY();

        for (int i = (int)(mapX + footageX1); i <= (int)(mapX + footageX2); i++) {
            if (blockPoint(i, mapY + footageY1) || blockPoint(i, mapY + footageY2))
                return true;
        }
        for (int j = (int)(mapY + footageY1); j <= (int)(mapY + footageY2); j++) {
            if (blockPoint(mapX + footageX1, j) || blockPoint(mapX + footageX2, j))
                return true;
        }
        return false;
    }

    private boolean blockPoint(double x, double y) {
        return !isTransparent(mapRocks, x, y) || !isTransparent(mapBlocks1, x, y) || !isTransparent(mapBlocks2, x, y) ||
                !isTransparent(mapBlocks3, x, y) || (isTransparent(mapTerrain, x, y) && isTransparent(mapBridge, x, y));
    }

    private boolean isTransparent(PixelReader pixelReader, double x, double y) {
        return (pixelReader.getArgb((int) x, (int) y) >> 24) == 0x00;
    }

    private void createMoveTimer() {
        if (moveTimeline != null)
            moveTimeline.stop();
        moveTimeline = new Timeline(new KeyFrame(Duration.millis(durationTime), moveEvent));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }


    private void stageProcess() {
        primaryStage.setScene(scene);
        primaryStage.setHeight(height - 11);
        primaryStage.setWidth(width - 12);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Legends of Arcadia - World map");
    }

    private void imagesProcess(){
        mapLava = new Image(new File("map resource/map_lava.png").toURI().toString()).getPixelReader();
        mapTerrain = new Image(new File("map resource/map_terrain.png").toURI().toString()).getPixelReader();
        mapTerrain2 = new Image(new File("map resource/map_terrain2.png").toURI().toString()).getPixelReader();
        mapRocks = new Image(new File("map resource/map_rocks-fence.png").toURI().toString()).getPixelReader();
        mapBridge = new Image(new File("map resource/map_bridge.png").toURI().toString()).getPixelReader();
        mapShop = new Image(new File("map resource/map_shop.png").toURI().toString()).getPixelReader();
        mapCastle = new Image(new File("map resource/map_castle" + level + ".png").toURI().toString()).getPixelReader();
        mapBlocks1 = new Image(new File("map resource/map_blocks1.png").toURI().toString()).getPixelReader();
        mapBlocks2 = new Image(new File("map resource/map_blocks2.png").toURI().toString()).getPixelReader();
        mapBlocks3 = new Image(new File("map resource/map_blocks3.png").toURI().toString()).getPixelReader();
        mapTransparent = new Image(new File("map resource/map_transparent.png").toURI().toString()).getPixelReader();

        for (int i = 1; i <= 9; i++) {
            toRight.add(new Image(new File("map resource/toRight" + i + ".png").toURI().toString()));
            toLeft.add(new Image(new File("map resource/toLeft" + i + ".png").toURI().toString()));
            toUp.add(new Image(new File("map resource/toUp" + i + ".png").toURI().toString()));
            toDown.add(new Image(new File("map resource/toDown" + i + ".png").toURI().toString()));
        }
        character.setImage(toRight.get(0));
    }

    private void levelProcess() {
        switch (level) {
            case 1: character.setLayoutX(100);
                    character.setLayoutY(height - 200);
                    break;
            case 2: character.setLayoutX(width/2 + 100);
                    character.setLayoutY(height/2 - 50);
                    mapBlocks1 = mapTransparent;
                    break;
            case 3: character.setLayoutX(width*3/4 - 100);
                    character.setLayoutY(440);
                    mapBlocks1 = mapBlocks2 = mapTransparent;
                    break;
            case 4: character.setLayoutX(width/2 - 190);
                    character.setLayoutY(300);
                    mapBlocks1 = mapBlocks2 = mapBlocks3 = mapTransparent;
                    break;
        }

        File mapFile = new File("map resource/map_level" + level + ".png");
        mapImage = new Image(mapFile.toURI().toString());
        mapImageView.setImage(mapImage);

        mapImageView.setLayoutX(0);     // extra here
        if (level <= 2)
            mapImageView.setLayoutY(-747);
        else
            mapImageView.setLayoutY(0);
    }

    public void continueMap() {
        actionTime = 0;
        goNorth = goSouth = goEast = goWest = doAction = false;
        durationTime = 30;
        moveTimeline.play();//
        //createMoveEvent();
        //imagesProcess();
        //levelProcess();
        stageProcess();
    }

    public void continueMap(int level) {
        boolean differLevel = (this.level != level);
        this.level = level;
        if (differLevel) {
            mapImageView.setImage(new Image(new File("map resource/map_level" + level + ".png").toURI().toString()));
            mapCastle = new Image(new File("map resource/map_castle" + level + ".png").toURI().toString()).getPixelReader();
            switch (level) {
                case 2:
                    mapBlocks1 = mapTransparent;
                    break;
                case 3:
                    mapBlocks1 = mapBlocks2 = mapTransparent;
                    break;
                case 4:
                    mapBlocks1 = mapBlocks2 = mapBlocks3 = mapTransparent;
                    break;
            }
            createMoveEvent();
            continueMap(); // createMoveTimer ?
        }
        else {
            levelProcess();
            continueMap();
        }
    }
}
