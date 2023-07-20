package handlers;

//TODO Get this working!

import classifications.Spell;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SpellOutput {

    public List<Spell> getSavedSpellInformation() {
        List<Spell> spells = new ArrayList<>();

        SpellFileHandler spellFileHandler = new SpellFileHandler();
        File spellDirectory = new File("src/Library/data/spells");
        File[] spellFiles = spellDirectory.listFiles();

        if (spellFiles != null) {
            for (File spellFile : spellFiles) {
                if (spellFile.isFile()) {
                    try (FileInputStream fileInputStream = new FileInputStream(spellFile);
                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                        Spell spell = (Spell) objectInputStream.readObject();
                        spells.add(spell);
                    } catch (IOException | ClassNotFoundException e) {
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
