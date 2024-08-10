package idiotamspielen.vttproject.controllers;

import idiotamspielen.vttproject.handlers.FileHandler;
import idiotamspielen.vttproject.classifications.Things;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import idiotamspielen.vttproject.userInterface.TableTop;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private BorderPane rootPane;
    @FXML
    private TableTop tableTop;
    @FXML
    private Pane tableTopPane;
    @FXML
    private ListView<String> recentFilesListView;
    @FXML
    private VBox recentFilesSidebar; // Sidebar VBox

    private final FileHandler<Things> fileHandler;

    public MainController() {
        // Initialize FileHandler with the appropriate directory and class type
        this.fileHandler = new FileHandler<>(Things.class, "spells");
    }

    public void initialize() {
        double minWidth = 400;
        double minHeight = 400;

        tableTop = new TableTop();
        tableTop.setDimensions(minWidth, minHeight);
        tableTopPane.getChildren().add(tableTop);

        loadRecentFiles();
        setupRecentFilesListView();
        // Set up a listener for the size changes to dynamically adjust the layout
        setupDynamicResizing();
    }

    private void loadRecentFiles() {
        // Fetch the list of recently opened files
        List<Things> recentFiles = fileHandler.getSavedObjectInformation();

        // Populate the ListView with the names of the recently opened files
        for (Things file : recentFiles) {
            recentFilesListView.getItems().add(file.getName());
        }
    }

    private void setupRecentFilesListView() {
        // Add click event handling to open files when clicked
        recentFilesListView.setOnMouseClicked(this::handleFileClick);
    }

    private void handleFileClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // Get the selected file name
            String selectedFile = recentFilesListView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                openFile(selectedFile);
            }
        }
    }

    private void openFile(String fileName) {
        // Implement your logic to open and display the file here
        System.out.println("Opening file: " + fileName);
        // Example: You might want to use fileHandler to load the file content
        Things fileContent = fileHandler.search(fileName).get(0); // Assume the search function retrieves the correct file

        // Further logic to display or process the opened file...
    }

    private void setupDynamicResizing() {
        // Listener for resizing the layout
        ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
            double tableTopWidth = rootPane.getWidth() * 0.765;
            double tableTopHeight = rootPane.getHeight() * 0.77;
            tableTop.setDimensions(tableTopWidth, tableTopHeight);

            // Calculate the remaining width for the sidebar
            double sidebarWidth = rootPane.getWidth() - tableTopWidth - 15;
            recentFilesSidebar.setPrefWidth(sidebarWidth);
            recentFilesSidebar.setMaxHeight(Double.MAX_VALUE); // Use the full height of the screen
        };

        // Apply the listener to both width and height properties
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