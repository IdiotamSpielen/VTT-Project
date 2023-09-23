package userInterface.outputs;

import classifications.Spell;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import handlers.SpellOutputHandler;
import java.util.List;

public class SpellOutputBox {
    private final BorderPane layoutPane;

    public SpellOutputBox(SpellOutputHandler spellOutput) {
        layoutPane = new BorderPane();
        layoutPane.setPrefSize(600, 400);

        Text spellOutputText = new Text();
        spellOutputText.getStyleClass().add("spell-output-text");

        TextField searchInput = new TextField();
        searchInput.setPromptText("Enter spell name to search");
        searchInput.setOnAction(event -> {
            String query = searchInput.getText();
            List<Spell> spells = spellOutput.searchSpell(query);
            displaySpells(spells, spellOutputText);
        });



        Button displaySpellsButton = new Button("Display Spells");
        displaySpellsButton.setOnAction(event -> {
            String query = searchInput.getText();
            List<Spell> spells = spellOutput.searchSpell(query);
            displaySpells(spells, spellOutputText);
        });

        layoutPane.setTop(searchInput);
        layoutPane.setCenter(spellOutputText);
        layoutPane.setBottom(displaySpellsButton);
    }

    private void displaySpells(List<Spell> spells, Text spellOutputText) {
        StringBuilder spellInfo = new StringBuilder();

        for (Spell spell : spells) {
            spellInfo.append("Spell Name: ").append(spell.getName()).append("\n");
            spellInfo.append("Range: ").append(spell.getRange()).append("\n");
            spellInfo.append("Casting Time: ").append(spell.getTime()).append("\n");
            spellInfo.append("Description: ").append(spell.getDescription()).append("\n");
            spellInfo.append("Ingredients: ").append(spell.getIngredients()).append("\n");
            spellInfo.append("Ritual: ").append(spell.isRitual()).append("\n");
            spellInfo.append("Concentration: ").append(spell.isConcentration()).append("\n");
            spellInfo.append("Components: ").append(spell.getComponents()).append("\n");
            spellInfo.append("school: ").append(spell.getSchool()).append("\n");
            spellInfo.append("duration: ").append(spell.getDuration()).append("\n");
            spellInfo.append("Level: ").append(spell.getLevel()).append("\n");
            spellInfo.append("---------------\n");
        }

        spellOutputText.setText(spellInfo.toString());
    }

    public BorderPane getLayoutPane() {
        return layoutPane;
    }
}
