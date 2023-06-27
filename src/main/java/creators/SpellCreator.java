package main.java.creators;

import main.java.Handlers.FeedbackHandler;
import main.java.Handlers.SpellFileHandler;
import main.java.classifications.Spell;

public class SpellCreator {

    private Spell spell;
    private SpellFileHandler spellFileHandler;
    
    public SpellCreator(FeedbackHandler feedbackHandler, SpellFileHandler spellFileHandler) {
        spellFileHandler = new SpellFileHandler();
        this.spellFileHandler = spellFileHandler;
    }
    public void create(String spellname, String range, String castingTime, String description, String ingredients, boolean ritual, boolean concentration, String components, String levelString){
        spell = new Spell();
         spell.setName(spellname);
         spell.setRange(range);
         spell.setTime(castingTime);
         spell.setDescription(description);
         if (ingredients != null && !ingredients.isEmpty()) {
         spell.setIngredients(ingredients);}
         else {spell.setIngredients("N/A");}
         spell.setRitual(ritual);
         spell.setConcentration(concentration);
         spell.setComponents(components);
        try {int level = Integer.parseInt(levelString);
        spell.setLevel(level);
        } catch (NumberFormatException e) {
        System.out.println("Invalid level value: " + levelString);}

        spellFileHandler.saveSpellToFile(spell);
    }
}