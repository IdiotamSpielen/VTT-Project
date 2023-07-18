package userInterface;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import handlers.FeedbackHandler;
import creators.SpellCreator;

public class Controller {

    private FeedbackHandler feedbackHandler;
    @FXML
    private Text feedbackText;
    private SpellCreator spellCreator;
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

    tableTop.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.8));
    tableTop.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.8));

    ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
        double tableTopWidth = rootPane.getWidth() * 0.8;
        double tableTopHeight = rootPane.getHeight() * 0.8;
        tableTop.setDimensions(tableTopWidth, tableTopHeight);

    createSpellButton.setOnAction(event -> {
        // Handle button click event
        Stage spellCreationStage = new Stage();
        spellCreationStage.setTitle("Spell Creation");
        feedbackHandler = new FeedbackHandler(feedbackText);
        feedbackText = feedbackHandler.getFeedbackText();
        SpellCreationBox spellCreationBox = new SpellCreationBox(feedbackHandler);
        BorderPane layoutPane = spellCreationBox.getLayoutPane();

   

        // Create the scene and set it to the spell creation stage
        Scene spellCreationScene = new Scene(layoutPane, 500, 500);
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.setMinWidth(500);
        spellCreationStage.setMinHeight(500);
        spellCreationStage.show();

        BorderPane.setAlignment(feedbackText, Pos.CENTER);
        layoutPane.setCenter(feedbackText);
        });
    };
    }
}