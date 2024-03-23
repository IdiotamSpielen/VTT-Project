package userInterface.controllers;

import classifications.Spell;
import handlers.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpellSearchController {
    @FXML private TextField searchInput;
    @FXML private Text spellName;
    @FXML private Text range;
    @FXML private Text castingTime;
    @FXML private Text components;
    @FXML private Text duration;
    @FXML private  Text ingredients;
    @FXML private Text descArea;
    @FXML private Text level;
    @FXML private Text school;
    @FXML private Text ritual;
    @FXML private Text concentration;
    @FXML private ListView<String> spellListView;
    @FXML private GridPane gridPane;

    private final Map<String, Spell> spellMap = new HashMap<>();
    private final FileHandler<Spell> fileHandler = new FileHandler<Spell>(Spell.class, "src/library/data/spells");

    public void initialize(){
        gridPane.setVisible(false);
        ritual.setVisible(false);
        concentration.setVisible(false);
    }
    @FXML
    public void handleSearch(){
        String query = searchInput.getText();
        List<Spell> spells = fileHandler.search(query);
        displaySpells(spells);
    }
    private void displaySpells(List<Spell> spells){
        spellListView.getItems().clear();
        spellMap.clear();
        for (Spell spell : spells) {
            spellListView.getItems().add(spell.getName());
            spellMap.put(spell.getName(), spell);
        }
        gridPane.setVisible(false);
        spellListView.setVisible(true);
    }
    @FXML
    public void handleSpellSelection(){
        String selectedSpellName = spellListView.getSelectionModel().getSelectedItem();
        Spell selectedSpell = spellMap.get(selectedSpellName);
        if (selectedSpell != null) {
            spellName.setText(selectedSpell.getName());
            range.setText(selectedSpell.getRange());
            castingTime.setText(selectedSpell.getTime());
            components.setText(selectedSpell.getComponents());
            duration.setText(selectedSpell.getDuration());
            ingredients.setText(selectedSpell.getIngredients());
            descArea.setText(selectedSpell.getDescription());
            level.setText(String.valueOf(selectedSpell.getLevel()));
            school.setText(selectedSpell.getSchool());
            if(selectedSpell.isRitual()){ritual.setText("ritual");}
            if(selectedSpell.isConcentration()){concentration.setText("concentration");}
            // Hide the ListView
            spellListView.setVisible(false);
            // Make the text elements visible
            if(selectedSpell.isRitual()){ritual.setVisible(true);}
            if(selectedSpell.isConcentration()){concentration.setVisible(true);}
            gridPane.setVisible(true);
            gridPane.requestFocus();
        }
        else{
            // Hide the text elements
            gridPane.setVisible(false);
            // Show the ListView
            spellListView.setVisible(true);
        }
    }
}
