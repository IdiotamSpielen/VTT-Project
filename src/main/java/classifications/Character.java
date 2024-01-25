package classifications;

public class Character{
    private String name;
    private String backstory;
    private String bonds;
    private String flaws;
    private String alignment;
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

    private CharacterRace race;
    private CharacterClass characterClass;

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

    public void setStrength(int strength) {this.strength = strength;}

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getBackstory() {
        return backstory;
    }

    public void setBackstory(String backstory) {
        this.backstory = backstory;
    }

    public String getBonds() {
        return bonds;
    }

    public void setBonds(String bonds) {
        this.bonds = bonds;
    }

    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public CharacterRace getRace() {
        return race;
    }

    public void setRace(CharacterRace race) {
        this.race = race;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setDndClass(CharacterClass dndClass) {
        this.characterClass = dndClass;
    }

    public int getAbilityScore(int abilityIndex) {
        switch (abilityIndex) {
            case 0:
                return strength;
            case 1:
                return dexterity;
            case 2:
                return constitution;
            case 3:
                return intelligence;
            case 4:
                return wisdom;
            case 5:
                return charisma;
            default:
                throw new IllegalArgumentException("Invalid ability index: " + abilityIndex);
        }
    }

    public int getAbilityModifier(int abilityIndex) {
        return (getAbilityScore(abilityIndex) - 10) / 2;
    }
}
