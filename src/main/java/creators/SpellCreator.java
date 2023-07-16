package creators;

import classifications.Spell;
import handlers.FeedbackHandler;
import handlers.SpellFileHandler;

public class SpellCreator {

    private SpellFileHandler spellFileHandler;
    private Spell spell;
    
    public SpellCreator(FeedbackHandler feedbackHandler, SpellFileHandler spellFileHandler) {
        spellFileHandler = new SpellFileHandler();
        this.spellFileHandler = spellFileHandler;
    }
    public void create(String spellName, String range, String castingTime, String description, String ingredients, boolean ritual, boolean concentration, String components, String levelString){
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


        if (validInputs(spellName, range, castingTime, description, levelString, components)) {
            int level = Integer.parseInt(levelString);
            spell.setLevel(level);
            this.spell = spell;
            spellFileHandler.saveSpellToFile(getSpell());
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

    public Spell getSpell() {
        return spell;
    }
}