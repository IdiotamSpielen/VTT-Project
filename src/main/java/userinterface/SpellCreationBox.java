package java.userinterface;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
import java.handlers.FeedbackHandler;
import java.handlers.SpellFileHandler;
import java.creators.SpellCreator;


public class SpellCreationBox {
    private final BorderPane layoutPane;
    private Text feedbackText;
    private FeedbackHandler feedbackHandler;
    private SpellFileHandler spellFileHandler;

    public SpellCreationBox(Stage spellCreationStage, FeedbackHandler feedbackHandler) {
        layoutPane = new BorderPane();
        layoutPane.setPrefSize(450, 450);
        layoutPane.getStyleClass().add("spell-creation-box");

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
        TextField spellNameTextField = new TextField();
        Label spellNameLabel = createPromptLabel("Spell Name");
        StackPane.setAlignment(spellNameLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(spellNameLabel, new Insets(0, 0, 0, 8));
        StackPane namePane = new StackPane(spellNameTextField, spellNameLabel);
        gridPane.addRow(1, namePane);
        GridPane.setColumnSpan(namePane, 2);

        spellNameTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            // Hide the label if text is not empty
            spellNameLabel.setVisible(newValue.isEmpty());
        });
            spellNameLabel.addEventFilter(MouseEvent.ANY, event -> {
            spellNameTextField.fireEvent(event);
            event.consume();
        });


        // Row 2 (casting time and range)
        TextField castingTimeTextField = new TextField();
        Label castingTimeLabel = createPromptLabel("Casting Time");
        StackPane.setAlignment(castingTimeLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(castingTimeLabel, new Insets(0, 0, 0, 8));
        StackPane timePane = new StackPane(castingTimeTextField, castingTimeLabel);;
        TextField rangeTextField = new TextField();
        rangeTextField.setPromptText("Range");
        Label rangeLabel = createPromptLabel("Range");
        StackPane.setAlignment(rangeLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(rangeLabel, new Insets(0, 0, 0, 8));
        StackPane rangePane = new StackPane(rangeTextField, rangeLabel);
        gridPane.addRow(2, timePane, rangePane);

        castingTimeTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            // Hide the label if text is not empty
            castingTimeLabel.setVisible(newValue.isEmpty());
        });
            castingTimeLabel.addEventFilter(MouseEvent.ANY, event -> {
            castingTimeTextField.fireEvent(event);
            event.consume();
        });
        rangeTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            // Hide the label if text is not empty
            rangeLabel.setVisible(newValue.isEmpty());
        });
            rangeLabel.addEventFilter(MouseEvent.ANY, event -> {
            rangeTextField.fireEvent(event);
            event.consume();
        });


        // Row 3 (components and duration)
        TextField componentsTextField = new TextField();
        Label componentsLabel = createPromptLabel("Components");
        StackPane.setAlignment(componentsLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(componentsLabel, new Insets(0, 0, 0, 8));
        StackPane compPane = new StackPane(componentsTextField, componentsLabel);
        TextField durationTextField = new TextField();
         Label durationLabel = createPromptLabel("Duration");
        StackPane.setAlignment(durationLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(durationLabel, new Insets(0, 0, 0, 8));
        StackPane durationPane = new StackPane(durationTextField, durationLabel);
        gridPane.addRow(3, compPane, durationPane);

         componentsTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            // Hide the label if text is not empty
            componentsLabel.setVisible(newValue.isEmpty());
        });
            componentsLabel.addEventFilter(MouseEvent.ANY, event -> {
            componentsTextField.fireEvent(event);
            event.consume();
        });
        durationTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            // Hide the label if text is not empty
            durationLabel.setVisible(newValue.isEmpty());
        });
            durationLabel.addEventFilter(MouseEvent.ANY, event -> {
            durationTextField.fireEvent(event);
            event.consume();
        });

        // Row 4 (ingredients)
        TextField ingredientsTextField = new TextField();
         Label ingredientsLabel = createPromptLabel("Ingredients");
        StackPane.setAlignment(ingredientsLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(ingredientsLabel, new Insets(0, 0, 0, 8));
        StackPane ingredientsPane = new StackPane(ingredientsTextField, ingredientsLabel);
        gridPane.addRow(4, ingredientsPane);
        GridPane.setColumnSpan(ingredientsPane, 2);

        ingredientsTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            // Hide the label if text is not empty
            ingredientsLabel.setVisible(newValue.isEmpty());
        });
            ingredientsLabel.addEventFilter(MouseEvent.ANY, event -> {
            ingredientsTextField.fireEvent(event);
            event.consume();
        });

        // Rows 5-6 (Description)
        TextArea descriptionTextArea = new TextArea();
        Label descLabel = createPromptLabel("Description");
        StackPane.setAlignment(descLabel, Pos.TOP_LEFT);
        StackPane.setMargin(descLabel, new Insets(4, 0, 0, 8));
        StackPane descPane = new StackPane(descriptionTextArea, descLabel);
        GridPane.setRowSpan(descriptionTextArea, 2);
        gridPane.addRow(5, descPane);
        GridPane.setColumnSpan(descPane, 2);

        descriptionTextArea.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            descLabel.setVisible(newValue.isEmpty());
        });
            descLabel.addEventFilter(MouseEvent.ANY, event -> {
            descriptionTextArea.fireEvent(event);
            event.consume();
        });

        // Row 7 (Level and School)
        TextField levelTextField = new TextField();
        Label levelLabel = createPromptLabel("Level");
        StackPane.setAlignment(levelLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(levelLabel, new Insets(0, 0, 0, 8));
        StackPane lvPane = new StackPane(levelTextField, levelLabel);
        TextField schoolTextField = new TextField();
        Label schoolLabel = createPromptLabel("School");
        StackPane.setAlignment(schoolLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(schoolLabel, new Insets(0, 0, 0, 8));
        StackPane schoolPane = new StackPane(schoolTextField, schoolLabel);
        gridPane.addRow(7, lvPane, schoolPane);

        levelTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            levelLabel.setVisible(newValue.isEmpty());
        });
            levelLabel.addEventFilter(MouseEvent.ANY, event -> {
            levelTextField.fireEvent(event);
            event.consume();
        });
        schoolTextField.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            schoolLabel.setVisible(newValue.isEmpty());
        });
            schoolLabel.addEventFilter(MouseEvent.ANY, event -> {
            schoolTextField.fireEvent(event);
            event.consume();
        });

        // Row 8 (Ritual Checkbox)
        CheckBox ritualCheckBox = new CheckBox("Ritual");
        CheckBox concentrationCheckBox = new CheckBox("Concentration");
        gridPane.addRow(8, ritualCheckBox);
        gridPane.addRow(8, concentrationCheckBox);
        StackPane.setAlignment(ritualCheckBox, Pos.BASELINE_CENTER);
        StackPane.setAlignment(concentrationCheckBox, Pos.BASELINE_CENTER);

        layoutPane.setTop(gridPane); //add the GridPane to the top region of the layout

        //initialize the feedback text field
        feedbackText = new Text();
        layoutPane.setCenter(feedbackText);

    // Create a button to submit the spell creation
    Button createButton = new Button("create");
    StackPane buttonPane = new StackPane(createButton);
    buttonPane.setAlignment(Pos.BOTTOM_CENTER);
    buttonPane.setPadding(new Insets(10));
    layoutPane.setBottom(buttonPane);
    createButton.setOnAction(event -> {
        
        String spellName = spellNameTextField.getText();
        String range = rangeTextField.getText();
        String castingTime = castingTimeTextField.getText();
        String description = descriptionTextArea.getText();
        String ingredients = ingredientsTextField.getText();
        boolean ritual = ritualCheckBox.isSelected();
        boolean concentration = concentrationCheckBox.isSelected();
        String components = componentsTextField.getText();
        String levelString = levelTextField.getText();
        
        // establish handlers
        SpellFileHandler spellFileHandler = new SpellFileHandler();
        this.feedbackHandler = feedbackHandler;
        this.feedbackText = feedbackHandler.getFeedbackText();

        // Handle the spell creation using the retrieved values
        SpellCreator spellCreator = new SpellCreator(feedbackHandler, spellFileHandler);
        spellCreator.create(spellName, range, castingTime, description, ingredients, ritual, concentration, components, levelString);


        //create feedback on if the Spell was saved
        if (spellFileHandler.saved()) 
        {feedbackHandler.displaySuccess();}
        else if (!spellFileHandler.saved())
        {feedbackHandler.displayError();} 
        else {feedbackHandler.pointoutIncompetence();}
        });
    }

    //Miscellanious commands

    public BorderPane getLayoutPane() {
    return layoutPane;
    }
    private Label createPromptLabel(String text) {
    Label label = new Label(text);
    label.setStyle("-fx-text-fill: grey;");
    return label;
    }
}
