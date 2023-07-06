package java.classifications;

public class Spell extends EntryRef{
    
    private String spellname;
    private String school;
    private String duration;
    private String components;
    private int level;
    private String range;
    private String castingTime;
    private String description;
    private String ingredients;
    private boolean ritual;
    private boolean concentration;

    public String getName() {
        return spellname;
    }

    public void setName(String spellname) {
        this.spellname = spellname;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTime(){
        return castingTime;
    }

    public void setTime(String castingTime){
        this.castingTime = castingTime;
    }
    
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Boolean isRitual(){
        return ritual;
    }

    public void setRitual(Boolean ritual){
        this.ritual = ritual;
    }

    public String getIngredients(){
        return ingredients;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

       public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

     public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setConcentration(boolean concentration) {
        this.concentration = concentration;
    }
    
    public boolean getConcentration() {
        return concentration;
    }
}
