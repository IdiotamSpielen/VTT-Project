package idiotamspielen.vttproject.controllers;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import idiotamspielen.vttproject.userInterface.TableTop;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane rootPane;
    @FXML
    private TableTop tableTop;
    @FXML
    private Pane tableTopPane;


    public void initialize() {
        double minWidth = 400;
        double minHeight = 400;

        tableTop = new TableTop();
        tableTop.setDimensions(minWidth, minHeight);
        tableTopPane.getChildren().add(tableTop);

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpellCreation.fxml"));
        Parent layoutPane = null;
        try {
            layoutPane = loader.load();
        } catch (IOException e) {
            System.err.println("UI creation failed");
        }

        Scene spellCreationScene = new Scene(layoutPane, 500, 500);
        spellCreationStage.setScene(spellCreationScene);
        spellCreationStage.setMinWidth(500);
        spellCreationStage.setMinHeight(500);
        spellCreationStage.setResizable(false);
        spellCreationStage.show();
    }

    public void createItem() {
        Stage itemCreationStage = new Stage();
        itemCreationStage.setTitle("Create Item");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemCreation.fxml"));
        Parent layoutPane = null;
        try {
            layoutPane = loader.load();
        }
        catch (IOException e) {
            System.err.println("UI creation failed");
        }

        Scene itemCreationScene = new Scene(layoutPane, 500, 500);
        itemCreationStage.setScene(itemCreationScene);
        itemCreationStage.setMinWidth(500);
        itemCreationStage.setMinHeight(500);
        itemCreationStage.setResizable(false);
        itemCreationStage.show();
    }
}