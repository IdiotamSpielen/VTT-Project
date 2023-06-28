package Handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import classifications.Spell;

public class SpellFileHandler {
    private Spell spell;
    
    public boolean saveSpellToFile(Spell spell){

        if (spell == null) {
            System.out.println("Failed to save Spell: Spell is null");
            return false;
        }

        File directory = new File("src/Library/data/spells");
        directory.mkdirs();

        String fileName = spell.getName() + ".dat";
        File file = new File(directory, fileName);
        try {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(spell);
        objectOutputStream.close();
        System.out.println("Spell saved as " + fileName);
        return true;
     } catch (FileNotFoundException e){
        System.out.println("Failed to save Spell: File not found");
        e.printStackTrace();
        return false;
     } catch (IOException e){
        System.out.println("Failed to save Spell: " + e.getMessage());
        e.printStackTrace();
        return false;
     }
    }

    public boolean saved(){
        return saveSpellToFile(spell);
    }

    public String getName(){
        if (spell != null) {
            return spell.getName();
        } else return null;
    }
}
