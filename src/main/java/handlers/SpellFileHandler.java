package handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

        //save spell to library. If it doesn't exist yet, create one.
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
}
