package idiotamspielen.vttproject.controllers;

import idiotamspielen.vttproject.classifications.Ability;
import idiotamspielen.vttproject.classifications.Character;
import idiotamspielen.vttproject.classifications.CharacterClass;
import idiotamspielen.vttproject.classifications.CharacterRace;
import idiotamspielen.vttproject.handlers.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CharacterCreationController {

    private final FileHandler<CharacterRace> raceFileHandler = new FileHandler<>(CharacterRace.class, "races");
    private final FileHandler<CharacterClass> classFileHandler = new FileHandler<>(CharacterClass.class, "classes");

    @FXML
    private TextField nameField;
    private TextField backstoryField;
    private TextField bondsField;
    private TextField flawsField;
    private Label initiativeLabel;
    private ComboBox<String> alignmentBox;
    private ComboBox<String> raceBox;
    private ComboBox<String> classBox;
    private Spinner<Integer> strSpinner;
    private Spinner<Integer> dexSpinner;
    private Spinner<Integer> conSpinner;
    private Spinner<Integer> intSpinner;
    private Spinner<Integer> wisSpinner;
    private Spinner<Integer> chaSpinner;
    private TextField acField;
    private TextField msField;
    private TextField hpField;
    private TextField strField;
    private TextField dexField;
    // fields for other properties

    @FXML
    public void initialize() {
        // ...

        List<CharacterRace> allRaces = raceFileHandler.getSavedObjectInformation();
        for (CharacterRace race : allRaces) {
            raceBox.getItems().add(race.getName());
        }

        List<CharacterClass> allClasses = classFileHandler.getSavedObjectInformation();
        for (CharacterClass characterClass : allClasses) {
            classBox.getItems().add(characterClass.getName());
        }
    }

    public void createCharacter(ActionEvent event) {
        // get input values
        String name = nameField.getText();
        String backstory = backstoryField.getText();
        String bonds = bondsField.getText();
        String flaws = flawsField.getText();
        String alignment = alignmentBox.getValue();
        int armorClass = Integer.parseInt(acField.getText());
        int movementSpeed = Integer.parseInt(msField.getText());
        int hitPoints = Integer.parseInt(hpField.getText());
        // create EnumMap for the abilities
        Map<Ability, Integer> abilityScores = new EnumMap<>(Ability.class);
        // Populate the map
        abilityScores.put(Ability.STRENGTH, Integer.valueOf(strField.getText()));
        // continue with other abilities
        // etc. get others

        String characterRace = raceBox.getValue();
        String characterClass = classBox.getValue();

        // create character object
        Character character = new Character(
                name, backstory, bonds, flaws, alignment, armorClass,
                movementSpeed, hitPoints, characterRace, characterClass, abilityScores);

        // save, update list, etc.
    }
}
