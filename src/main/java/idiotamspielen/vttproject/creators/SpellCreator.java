package idiotamspielen.vttproject.creators;

import idiotamspielen.vttproject.classifications.Spell;
import idiotamspielen.vttproject.handlers.FileHandler;

public class SpellCreator {

    private Spell spell;
    private final FileHandler<Spell> fileHandler;

    public SpellCreator(FileHandler<Spell> fileHandler) {
        this.fileHandler = fileHandler;
    }
    public void create(String spellName, String range, String castingTime, String description, String ingredients, String school, String duration, boolean ritual, boolean concentration, String components, String levelString){
        Spell spell = new Spell();
         spell.setName(spellName);
         spell.setRange(range);
         spell.setTime(castingTime);
         spell.setDescription(description);
         if (ingredients != null && !ingredients.isEmpty()) {
         spell.setIngredients(ingredients);}
         else {
             spell.setIngredients("N/A");}
         spell.setRitual(ritual);
         spell.setConcentration(concentration);
         spell.setComponents(components);
         spell.setSchool(school);
         spell.setDuration(duration);


        if (validInputs(spellName, range, castingTime, description, levelString, components)) {
            int level = Integer.parseInt(levelString);
            spell.setLevel(level);
            this.spell = spell;
            fileHandler.saveToFile(getSpell());
        }
    }

    public boolean validInputs(String spellName,String range,String castingTime,String description,String levelString, String components) {
        if (spellName == null || spellName.isEmpty()) {
            System.out.println("Invalid spell name");
            return false;
        }

        if (range == null || range.isEmpty()) {
            System.out.println("Invalid spell range");
            return false;
        }

        if (castingTime == null || castingTime.isEmpty()) {
            System.out.println("Invalid casting time");
            return false;
        }

        if (description == null || description.isEmpty()) {
            System.out.println("Invalid spell description");
            return false;
        }

        if (components == null || components.isEmpty()) {
            System.out.println("Invalid components");
            return false;
        }

        if (levelString == null || levelString.isEmpty()) {
            System.out.println("Invalid level value");
            return false;
        }

        try {
            int level = Integer.parseInt(levelString);
            if (level < 0 || level > 9) {
                System.out.println("Invalid level value: " + levelString);
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid level value: " + levelString);
            return false;
        }
        return true;
    }

    public boolean isSpellCreated () {
            return spell != null;
    }

    public boolean isSpellSaved() {return fileHandler.isSaved();}

    public Spell getSpell() {
        return spell;
    }
}