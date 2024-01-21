package userInterface.controllers;

import classifications.DnDClass;
import handlers.SpellOutputHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import handlers.FeedbackHandler;
import userInterface.TableTop;
import userInterface.creation.CharacterCreationBox;
//import userInterface.creation.SpellCreationBox;
import userInterface.outputs.SpellOutputBox;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    private FeedbackHandler feedbackHandler;
    @FXML
    private Text feedbackText;

    @FXML
    private BorderPane rootPane;

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
        SpellOutputHandler spellOutput = new SpellOutputHandler();

        // Create a SpellOutputBox with the SpellOutput
        SpellOutputBox spellOutputBox = new SpellOutputBox(spellOutput);

        // Create the scene and set it to the search stage
        Scene searchScene = new Scene(spellOutputBox.getLayoutPane(), 600, 400);
        searchStage.setScene(searchScene);
        searchStage.show();
    }

    public void createSpell() {
        Stage spellCreationStage = new Stage();
        spellCreationStage.setTitle("Create Spell");
        feedbackHandler = new FeedbackHandler(feedbackText);
        feedbackText = feedbackHandler.getFeedbackText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpellCreation.fxml"));
        Parent layoutPane = null;
        try {
            layoutPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SpellCreationController controller = loader.getController();
        controller.setFeedbackHandler(feedbackHandler);

        Scene spellCreationScene = new Scene(layoutPane, 500, 500);
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.setMinWidth(500);
        spellCreationStage.setMinHeight(500);
        spellCreationStage.show();

        BorderPane.setAlignment(feedbackText, Pos.CENTER);
        assert layoutPane != null;
        ((BorderPane) layoutPane).setCenter(feedbackText);
        spellCreationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/spellcreation.css")).toExternalForm());
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.show();
        spellCreationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/spellcreation.css")).toExternalForm());
    }
}