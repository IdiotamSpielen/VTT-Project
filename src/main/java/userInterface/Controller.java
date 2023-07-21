package userInterface;

import handlers.SpellOutput;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import handlers.FeedbackHandler;

import java.util.Objects;

public class Controller {

    private FeedbackHandler feedbackHandler;
    @FXML
    private Text feedbackText;
    @FXML
    private Label label;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Button createSpellButton;

    @FXML
    private TableTop tableTop;

    @FXML
    private Pane tableTopPane;


    public void initialize() {
    tableTop = new TableTop();
    tableTopPane.getChildren().add(tableTop);
    double minWidth = 400;
    double minHeight = 400;
    tableTop.setMinWidth(minWidth);
    tableTop.setMinHeight(minHeight);
    tableTop.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.765));
    tableTop.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.77));

    feedbackHandler = new FeedbackHandler(feedbackText);

    ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
        double tableTopWidth = rootPane.getWidth() * 0.765;
        double tableTopHeight = rootPane.getHeight() * 0.77;
        tableTop.setDimensions(tableTopWidth, tableTopHeight);
    };

    rootPane.widthProperty().addListener(sizeChangeListener);
    rootPane.heightProperty().addListener(sizeChangeListener);
    }

    public void searchSpell(ActionEvent actionEvent) {
        Stage searchStage = new Stage();
        searchStage.setTitle("Search Spell");

        // Create a SpellOutput object for displaying search results
        SpellOutput spellOutput = new SpellOutput();

        // Create a SpellOutputBox with the SpellOutput
        SpellOutputBox spellOutputBox = new SpellOutputBox(spellOutput);

        // Create the scene and set it to the search stage
        Scene searchScene = new Scene(spellOutputBox.getLayoutPane(), 600, 400);
        searchStage.setScene(searchScene);
        searchStage.show();
    }

    public void createSpell(ActionEvent actionEvent) {
        Stage spellCreationStage = new Stage();
        spellCreationStage.setTitle("Spell Creation");
        feedbackHandler = new FeedbackHandler(feedbackText);
        feedbackText = feedbackHandler.getFeedbackText();
        SpellCreationBox spellCreationBox = new SpellCreationBox(feedbackHandler);
        BorderPane layoutPane = spellCreationBox.getLayoutPane();

        Scene spellCreationScene = new Scene(layoutPane, 500, 500);
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.setMinWidth(500);
        spellCreationStage.setMinHeight(500);
        spellCreationStage.show();

        BorderPane.setAlignment(feedbackText, Pos.CENTER);
        layoutPane.setCenter(feedbackText);
        spellCreationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/spellcreation.css")).toExternalForm());
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.show();
        spellCreationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/spellcreation.css")).toExternalForm());
    }

    public void createCharacter(ActionEvent actionEvent) {
        Stage characterCreationStage = new Stage();
        characterCreationStage.setTitle("Character Creation");

        // Create a CharacterCreationBox for character creation UI
        CharacterCreationBox characterCreationBox = new CharacterCreationBox();

        // Create the scene and set it to the character creation stage
        Scene characterCreationScene = new Scene(characterCreationBox.getLayoutPane(), 600, 400);
        characterCreationStage.setScene(characterCreationScene);
        characterCreationStage.show();
    }

    public void createCharacter(ActionEvent actionEvent) {
        Stage characterCreationStage = new Stage();
        characterCreationStage.setTitle("Character Creation");

        // Create a CharacterCreationBox for character creation UI
        CharacterCreationBox characterCreationBox = new CharacterCreationBox();

        // Create the scene and set it to the character creation stage
        Scene characterCreationScene = new Scene(characterCreationBox.getLayoutPane(), 600, 400);
        characterCreationStage.setScene(characterCreationScene);
        characterCreationStage.show();
    }
}