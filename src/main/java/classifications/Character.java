package classifications;

public class Character extends EntryRef{
    private String name;
    private int initiative;
    private int armorClass;
    private int movementSpeed;
    private int hitPoints;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String backstory;
    private String bonds;
    private String flaws;
    private String alignment;
    private Race race;
    private DnDClass dndClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    public int getStrength() {
        return strength;
    }

    public int getStrengthModifier() {
        return (strength - 10) / 2;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getDexModifier() {
        return (dexterity - 10) / 2;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getConModifier() {
        return (constitution - 10) / 2;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getIntModifier(){return (intelligence - 10) / 2;}

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getWisModifier(){return (wisdom - 10) / 2;}

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getChaModifier(){return  (charisma - 10) / 2;}

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }
}
