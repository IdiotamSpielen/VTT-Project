package userInterface;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import handlers.FeedbackHandler;
import creators.SpellCreator;

public class Controller {

    private FeedbackHandler feedbackHandler;
    private Text feedbackText;
    private SpellCreator spellCreator;
    @FXML
    private Label label;

    @FXML
    private Button createSpellButton;

    public void initialize() {
    createSpellButton.setOnAction(event -> {
        // Handle button click event
        Stage spellCreationStage = new Stage();
        spellCreationStage.setTitle("Spell Creation");
        feedbackHandler = new FeedbackHandler(feedbackText, spellCreator);
        SpellCreationBox spellCreationBox = new SpellCreationBox(spellCreationStage, feedbackHandler);
        BorderPane layoutPane = spellCreationBox.getLayoutPane();

   

        // Create the scene and set it to the spell creation stage
        Scene spellCreationScene = new Scene(layoutPane, 500, 500);
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.setMinWidth(500);
        spellCreationStage.setMinHeight(500);
        spellCreationStage.show();
        });
    }
}