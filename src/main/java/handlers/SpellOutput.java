package handlers;

import classifications.Spell;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellOutput {

    private Gson gson = new Gson();

    public List<Spell> getSavedSpellInformation() {
        List<Spell> spells = new ArrayList<>();

        SpellFileHandler spellFileHandler = new SpellFileHandler();
        File spellDirectory = new File("src/Library/data/spells");
        File[] spellFiles = spellDirectory.listFiles();

        if (spellFiles != null) {
            for (File spellFile : spellFiles) {
                if (spellFile.isFile() && spellFile.getName().endsWith(".json")) { // Check for JSON files
                    try (FileReader fileReader = new FileReader(spellFile)) {
                        // Deserialize the JSON into a Spell object
                        Spell spell = gson.fromJson(fileReader, Spell.class);
                        spells.add(spell);
                    } catch (IOException e) {
                        System.out.println("Failed to read spell file: " + spellFile.getName());
                        e.printStackTrace();
                    }
                }
            }
        }

        return spells;
    }

    public List<Spell> searchSpell(String query) {
        List<Spell> allSpells = getSavedSpellInformation();
        List<Spell> matchingSpells = new ArrayList<>();

        // Search for spells matching the query (case-insensitive)
        for (Spell spell : allSpells) {
            if (spell.getName().toLowerCase().contains(query.toLowerCase())) {
                matchingSpells.add(spell);
            }
        }

        return matchingSpells;
    }
}