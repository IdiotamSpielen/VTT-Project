package userInterface;

import javafx.beans.value.ChangeListener;
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

    ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
        double tableTopWidth = rootPane.getWidth() * 0.765;
        double tableTopHeight = rootPane.getHeight() * 0.77;
        tableTop.setDimensions(tableTopWidth, tableTopHeight);
    };

    rootPane.widthProperty().addListener(sizeChangeListener);
    rootPane.heightProperty().addListener(sizeChangeListener);

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
    }
}