package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import handlers.FeedbackHandler;
import handlers.SpellFileHandler;
import creators.SpellCreator;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import java.io.IOException;

public class Main extends Application {

    private FeedbackHandler feedbackHandler;
    private SpellFileHandler spellFileHandler;
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {

        feedbackHandler = new FeedbackHandler(new Text());

        scene= new Scene(loadFXML("uifx"), 1366, 768);
        primaryStage.setTitle("VTT V0.0.3");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button createSpellButton = new Button("Create Spell");
        createSpellButton.setOnAction(event -> {
            // Handle button click event
            Stage spellCreationStage = new Stage();
            spellCreationStage.setTitle("Spell Creation");

            SpellCreationBox spellCreationBox = new SpellCreationBox(feedbackHandler);
            BorderPane layoutPane = spellCreationBox.getLayoutPane();

            // Create the scene and set it to the spell creation stage
            Scene spellCreationScene = new Scene(layoutPane, 400, 300);
            spellCreationScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            spellCreationStage.setScene(spellCreationScene);
            spellCreationStage.show();
            spellCreationScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load(); //What is the Problem here?
    }

    public static void main(String[] args) {
        launch(args);
    }
}