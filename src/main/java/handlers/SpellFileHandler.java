package handlers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import classifications.Spell;

public class SpellFileHandler {
    private boolean isSaved = false;
    private Gson gson = new Gson();

    public void saveSpellToFile(Spell spell){

        if (spell == null) {
            System.out.println("Failed to save Spell: Spell is null");
            return;
        }

        //save spell to library. If library doesn't exist yet, create one.
        File directory = new File("src/Library/data/spells");
        directory.mkdirs();

        String fileName = spell.getName() + ".json";
        File file = new File(directory, fileName);
        try (FileWriter writer = new FileWriter(file)) {
                // Serialize the Spell object to JSON
                String json = gson.toJson(spell);

                writer.write(json);
                writer.flush();
        if (!isSaved) {
            System.out.println("Spell saved as " + fileName);
            isSaved = true;
        }
     } catch (FileNotFoundException e){
        System.out.println("Failed to save Spell: File not found");
        e.printStackTrace();
        } catch (IOException e){
        System.out.println("Failed to save Spell: " + e.getMessage());
        e.printStackTrace();
        }
    }

    public boolean isSaved(){
        return  isSaved;
    }

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
