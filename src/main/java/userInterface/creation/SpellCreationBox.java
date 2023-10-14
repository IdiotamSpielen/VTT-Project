/*package userInterface.creation;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import handlers.FeedbackHandler;
import handlers.SpellFileHandler;
import creators.SpellCreator;
import org.jetbrains.annotations.NotNull;
import userInterface.controllers.SpellCreationController;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class SpellCreationBox {

    private FeedbackHandler feedbackHandler;
    private Text feedbackText;
    private final Map<String, TextField> textFields = new HashMap<>();
    private final Map<String, TextArea> textAreas = new HashMap<>();
    private final Map<String, CheckBox> checkBoxes = new HashMap<>();


    public SpellCreationBox(FeedbackHandler feedbackHandler) {
        this.feedbackHandler = feedbackHandler;

    }
        layoutPane = new BorderPane();
        layoutPane.setPrefSize(450, 450);
        layoutPane.getStyleClass().add("spell-creation-box");
        createUI(feedbackHandler);
    }

    public void createUI() {

        GridPane gridPane = new GridPane();
        BorderPane.setAlignment(gridPane, Pos.TOP_CENTER);
        layoutPane.setTop(gridPane);
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(col1, col2);

        //We start in row one because the UI looks so much better that way
        // Row 1 (name)
        StackPane namePane = createTFRow("Spell Name");
        gridPane.addRow(1, namePane);
        GridPane.setColumnSpan(namePane, 2);


        // Row 2 (casting time and range)
        StackPane timePane = createTFRow("Casting Time");
        StackPane rangePane = createTFRow("Range");
        gridPane.addRow(2, timePane, rangePane);

        // Row 3 (components and duration)
        StackPane compPane = createTFRow("Components");
        StackPane durationPane = createTFRow("Duration");
        gridPane.addRow(3, compPane, durationPane);

        // Row 4 (ingredients)
        StackPane ingredientPane = createTFRow("Ingredients");
        gridPane.addRow(4, ingredientPane);
        GridPane.setColumnSpan(ingredientPane, 2);

        // Rows 5-6 (Description)

        StackPane descPane = createTARow("Description");
        GridPane.setRowSpan(descPane, 2);
        gridPane.addRow(5, descPane);
        GridPane.setColumnSpan(descPane, 2);

        // Row 7 (Level and School)
        StackPane lvPane = createTFRow("Level");
        StackPane schoolPane = createTFRow("School");
        gridPane.addRow(7, lvPane, schoolPane);

        // Row 8 (Checkboxes for ritual and concentration)
        CheckBox ritualCB = createCheckBox("Ritual");
        CheckBox concentrationCB = createCheckBox("Concentration");
        gridPane.addRow(8, ritualCB, concentrationCB);

        //initialize the feedback text field
        feedbackText = new Text();
        layoutPane.setCenter(feedbackText);

    // Create a button to submit the spell creation
    Button createButton = new Button("create");
    StackPane buttonPane = new StackPane(createButton);
    buttonPane.setAlignment(Pos.BOTTOM_CENTER);
    buttonPane.setPadding(new Insets(10));
    layoutPane.setBottom(buttonPane);
    createButton.setOnAction(event -> handleCreateButtonAction(feedbackHandler));
    }

    public void handleCreateButtonAction(FeedbackHandler feedbackHandler) {
        String spellName = textFields.get("Spell Name").getText();
        String range = textFields.get("Range").getText();
        String castingTime = textFields.get("Casting Time").getText();
        String description = textAreas.get("Description").getText();
        String ingredients = textFields.get("Ingredients").getText();
        boolean isRitual = checkBoxes.get("Ritual").isSelected();
        boolean isConcentration = checkBoxes.get("Concentration").isSelected();
        String components = textFields.get("Components").getText();
        String levelString = textFields.get("Level").getText();
        String school = textFields.get("School").getText();
        String duration = textFields.get("Duration").getText();

        // establish handlers
        SpellFileHandler spellFileHandler = new SpellFileHandler();

        // Handle the spell creation using the retrieved values
        SpellCreator spellCreator = new SpellCreator(feedbackHandler, spellFileHandler);
        spellCreator.create(spellName, range, castingTime, description, ingredients, school, duration, isRitual, isConcentration, components, levelString);
        boolean isSpellCreated = spellCreator.isSpellCreated();

        if (isSpellCreated) {
            // Save the spell and handle the result
            boolean isSpellSaved = spellFileHandler.isSaved();
            // Create feedback based on the result of spell creation and saving
            if(isSpellSaved) feedbackHandler.displaySuccess(spellName);
            else feedbackHandler.displayError();
        } else if(!spellCreator.validInputs(spellName, range, castingTime, description, levelString, components))
            feedbackHandler.pointOutIncompetence();
    }

    private void establishTFListeners(@NotNull TextField tf, Label l){
        tf.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            l.setVisible(newValue.isEmpty());
        });
        l.addEventFilter(MouseEvent.ANY, event -> {
            tf.fireEvent(event);
            event.consume();
        });
    }

    private void establishTAListeners(@NotNull TextArea ta, Label l){
        ta.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            l.setVisible(newValue.isEmpty());
        });
        l.addEventFilter(MouseEvent.ANY, event -> {
            ta.fireEvent(event);
            event.consume();
        });
    }

    private StackPane createTFRow(String promptText) {
        TextField textField = new TextField();
        textFields.put(promptText, textField);  // Store the TextField in the map
        Label label = createPromptLabel(promptText);

        StackPane.setAlignment(label, Pos.CENTER_LEFT);
        StackPane.setMargin(label, new Insets(0, 0, 0, 8));

        StackPane pane = new StackPane(textField, label);

        establishTFListeners(textField, label);

        return pane;
    }

    private StackPane createTARow(String promptText) {
        TextArea textArea = new TextArea();
        textAreas.put(promptText, textArea);  // Store the TextArea in the map
        Label label = createPromptLabel(promptText);

        StackPane.setAlignment(label, Pos.TOP_LEFT);
        StackPane.setMargin(label, new Insets(4, 0, 0, 8));

        StackPane pane = new StackPane(textArea, label);

        establishTAListeners(textArea, label);

        return pane;
    }

    private CheckBox createCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBoxes.put(text, checkBox);  // Store the CheckBox in the map
        StackPane.setAlignment(checkBox, Pos.BASELINE_CENTER);
        return checkBox;
    }

    //Miscellaneous commands

    public BorderPane getLayoutPane() {
    return layoutPane;
    }
    private Label createPromptLabel(String text) {
    Label label = new Label(text);
    label.setStyle("-fx-text-fill: grey;");
    return label;
    }
}
*/