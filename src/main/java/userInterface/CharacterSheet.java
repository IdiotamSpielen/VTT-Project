package userInterface;

import classifications.Character;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class CharacterSheet {
    private final VBox layoutPane;
    private final Character character;

    public CharacterSheet(Character character){
        this.character = character;
        layoutPane = new VBox(10);
        layoutPane.setPadding(new Insets(10));
        layoutPane.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label("Name: " + character.getName());
        Label raceClassLabel = new Label
        ("Race: " + character.getRace().getName() + " Class: " + character.getDndClass().getName());
        Label alignmentLabel = new Label("Alignment" + character.getAlignment());

        VBox abilityScoresBox = new VBox(5);
        abilityScoresBox.setAlignment(Pos.CENTER_LEFT);
        String[] abilities = {"STR", "DEX", "CON", "INT", "WIS", "CHA"};
        for (int i = 0; i < 6; i++) {
            VBox abilityBox = new VBox(2);
            abilityBox.setAlignment(Pos.CENTER_LEFT);
            Label abilityScoreLabel = new Label(abilities[i] + ": " + character.getAbilityScore(i));
            Label modifierLabel = new Label("Mod: " + character.getAbilityModifier(i));
            abilityBox.getChildren().addAll(abilityScoreLabel, modifierLabel);
            abilityScoresBox.getChildren().add(abilityBox);
        }
    }
}

