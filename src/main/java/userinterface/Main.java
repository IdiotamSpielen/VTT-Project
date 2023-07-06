package java.userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.handlers.FeedbackHandler;
import java.handlers.SpellFileHandler;
import java.creators.SpellCreator;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class Main extends Application {

    private FeedbackHandler feedbackHandler;
    private SpellFileHandler spellFileHandler;
    @Override
    public void start(Stage primaryStage) throws Exception {

        feedbackHandler = new FeedbackHandler(new Text(), new SpellCreator(new FeedbackHandler(new Text(), null), spellFileHandler));
        
        Parent root = FXMLLoader.load(getClass().getResource("/resources/uifx.fxml"));
        primaryStage.setTitle("VTT V0.0.1");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();

        Button createSpellButton = new Button("Create Spell");
        createSpellButton.setOnAction(event -> {
            // Handle button click event
            Stage spellCreationStage = new Stage();
            spellCreationStage.setTitle("Spell Creation");

            SpellCreationBox spellCreationBox = new SpellCreationBox(spellCreationStage, feedbackHandler);
            BorderPane layoutPane = spellCreationBox.getLayoutPane();

            // Create the scene and set it to the spell creation stage
            Scene spellCreationScene = new Scene(layoutPane, 400, 300);
            spellCreationScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            spellCreationStage.setScene(spellCreationScene);
            spellCreationStage.show();
            spellCreationScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}