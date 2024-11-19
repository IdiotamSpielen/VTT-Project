package idiotamspielen.vttproject.classifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import idiotamspielen.vttproject.handlers.Things;

public class Spell extends Things {

    private final String spellName;
    private final String school;
    private final String duration;
    private final String components;
    private final int level;
    private final String range;
    private final String castingTime;
    private final String description;
    private final String ingredients;
    private final boolean ritual;
    private final boolean concentration;

    public Spell(@JsonProperty("name") String spellName, @JsonProperty("school") String school,
                 @JsonProperty("duration") String duration, @JsonProperty("components") String components,
                 @JsonProperty("level") int level, @JsonProperty("range") String range, @JsonProperty("time") String castingTime,
                 @JsonProperty("description") String description, @JsonProperty("ingredients") String ingredients,
                 @JsonProperty("ritual") boolean ritual, @JsonProperty("concentration") boolean concentration) {
        this.spellName = spellName;
        this.school = school;
        this.duration = duration;
        this.components = components;
        this.level = level;
        this.range = range;
        this.castingTime = castingTime;
        this.description = description;
        this.ingredients = ingredients;
        this.ritual = ritual;
        this.concentration = concentration;
    }

    //Getters
    public String getName() {
        return spellName;
    }

    public String getRange() {
        return range;
    }

    public String getTime(){
        return castingTime;
    }
    
    public String getDescription(){
        return description;
    }

    public Boolean isRitual(){
        return ritual;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getSchool() {
        return school;
    }

     public String getDuration() {
        return duration;
    }

    public String getComponents() {
        return components;
    }

    public int getLevel() {
        return level;
    }
    
    public boolean isConcentration() {
        return concentration;
    }
}
