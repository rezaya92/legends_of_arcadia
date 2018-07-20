package Controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by msi-pc on 7/20/2018.
 */
public class Popup {
    private final Stage dialog = new Stage();

    public Popup(Stage ownerStage, String message) {
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ownerStage);
        StackPane root = new StackPane();
        Text text = new Text(message);
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);
        Scene dialogScene = new Scene(root, 400, 200);
        dialog.setScene(dialogScene);
    }

    public Popup(String message) {
        this(LegendsOfArcadia.pStage, message);
    }

    public void show() {
        dialog.show();
    }
}
