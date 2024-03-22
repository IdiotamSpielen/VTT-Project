package userInterface.controllers;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import handlers.FeedbackHandler;
import userInterface.TableTop;

import java.io.IOException;

public class MainController {

    @FXML
    private Text feedbackText;
    @FXML
    private BorderPane rootPane;
    @FXML
    private TableTop tableTop;
    @FXML
    private Pane tableTopPane;
    @FXML
    private ImageView imageView;


    public void initialize() {
    tableTop = new TableTop();
    ScrollPane scrollPane = new ScrollPane();
    tableTop.getChildren().add(scrollPane);
    tableTopPane.getChildren().add(tableTop);
    double minWidth = 400;
    double minHeight = 400;
    tableTop.setMinWidth(minWidth);
    tableTop.setMinHeight(minHeight);
    tableTop.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.765));
    tableTop.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.77));

    imageView = new ImageView();
    scrollPane.setContent(imageView);

    ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
        double tableTopWidth = rootPane.getWidth() * 0.765;
        double tableTopHeight = rootPane.getHeight() * 0.77;
        tableTop.setDimensions(tableTopWidth, tableTopHeight);
    };

    rootPane.widthProperty().addListener(sizeChangeListener);
    rootPane.heightProperty().addListener(sizeChangeListener);
    }

    public void searchSpell(ActionEvent event) {
        Stage searchStage = new Stage();
        searchStage.setTitle("Search Spell");

        // Create a SpellOutputBox with the SpellOutput
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpellSearch.fxml"));
        Parent layoutPane;
        try {
            layoutPane = loader.load();
        } catch (IOException e) {
            System.out.println("UI creation failed");
            return;
        }
        Scene searchScene = new Scene(layoutPane, 500, 500);
        searchStage.setScene(searchScene);
        searchStage.setMinWidth(500);
        searchStage.setMinHeight(500);
        searchStage.show();
        searchStage.setResizable(false);

    }

    public void createSpell() {
        Stage spellCreationStage = new Stage();
        spellCreationStage.setTitle("Create Spell");
        FeedbackHandler feedbackHandler = new FeedbackHandler(feedbackText);
        feedbackText = feedbackHandler.getFeedbackText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpellCreation.fxml"));
        Parent layoutPane = null;
        try {
            layoutPane = loader.load();
        } catch (IOException e) {
            System.out.println("UI creation failed");
        }

        SpellCreationController controller = loader.getController();
        controller.setFeedbackHandler(feedbackHandler);

        Scene spellCreationScene = new Scene(layoutPane, 500, 500);
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.setMinWidth(500);
        spellCreationStage.setMinHeight(500);
        spellCreationStage.show();
        spellCreationStage.setResizable(false);

        BorderPane.setAlignment(feedbackText, Pos.CENTER);
        assert layoutPane != null;
        ((BorderPane) layoutPane).setCenter(feedbackText);
    }
}