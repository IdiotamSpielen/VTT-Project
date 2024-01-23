package userInterface.controllers;

import creators.SpellCreator;
import handlers.FeedbackHandler;
import handlers.SpellFileHandler;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

public class SpellCreationController {

    @FXML private TextField spellNameTF;
    @FXML private Label spellNameL;
    @FXML private TextField castingTimeTF;
    @FXML private Label castingTimeL;
    @FXML private TextField rangeTF;
    @FXML private Label rangeL;
    @FXML private  TextField ComponentTF;
    @FXML private Label ComponentL;
    @FXML private TextField DurationTF;
    @FXML private Label DurationL;
    @FXML private TextField IngredientTF;
    @FXML private Label IngredientL;
    @FXML private TextArea DescArea;
    @FXML private Label DescL;
    @FXML private TextField LvTF;
    @FXML private Label LvL;
    @FXML private TextField SchoolTF;
    @FXML private Label SchoolL;
    @FXML private CheckBox RitualCB;
    @FXML private CheckBox ConcentrationCB;
    private FeedbackHandler feedbackHandler;

    public void initialize() {
        establishTFListeners(spellNameTF, spellNameL);
        establishTFListeners(castingTimeTF, castingTimeL);
        establishTFListeners(rangeTF, rangeL);
        establishTFListeners(ComponentTF, ComponentL);
        establishTFListeners(DurationTF, DurationL);
        establishTFListeners(IngredientTF, IngredientL);
        establishTAListeners(DescArea, DescL);
        establishTFListeners(LvTF, LvL);
        establishTFListeners(SchoolTF, SchoolL);
    }

    private void establishTFListeners(@org.jetbrains.annotations.NotNull TextField tf, Label l) {
        tf.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            l.setVisible(newValue.isEmpty());
        });
        l.addEventFilter(MouseEvent.ANY, event -> {
            tf.fireEvent(event);
            event.consume();
        });
    }

    private void establishTAListeners(@NotNull TextArea ta, Label l) {
        ta.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            l.setVisible(newValue.isEmpty());
        });
        l.addEventFilter(MouseEvent.ANY, event -> {
            ta.fireEvent(event);
            event.consume();
        });
    }

    public void setFeedbackHandler(FeedbackHandler feedbackHandler) {
        this.feedbackHandler = feedbackHandler;
    }



    public void handleButtonPress(){
        String spellName = spellNameTF.getText();
        String range = rangeTF.getText();
        String castingTime = castingTimeTF.getText();
        String description = DescArea.getText();
        String ingredients = IngredientTF.getText();
        boolean isRitual = RitualCB.isSelected();
        boolean isConcentration = ConcentrationCB.isSelected();
        String components = ComponentTF.getText();
        String levelString = LvTF.getText();
        String school = SchoolTF.getText();
        String duration = DurationTF.getText();


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
            if (isSpellSaved) feedbackHandler.displaySuccess(spellName);
            else feedbackHandler.displayError();
        } else if (!spellCreator.validInputs(spellName, range, castingTime, description, levelString, components))
            feedbackHandler.pointOutIncompetence();
    }
}

