package idiotamspielen.vttproject.classifications;

import java.util.Map;

public class Character extends Things{
    private final String name;
    private final String backstory;
    private final String bonds;
    private final String flaws;
    private final String alignment;
    private final int armorClass;
    private final int movementSpeed;
    private final int hitPoints;
    // EnumMap for abilities
    private final Map<Ability, Integer> abilityScores;

    // References to race and class
    private final String characterRace;
    private final String characterClass;

    //Constructor
    public Character(String name, String backstory, String bonds, String flaws, String alignment,
                     int armorClass, int movementSpeed, int hitPoints, String characterRace,
                     String characterClass, Map<Ability, Integer> abilityScores) {
        this.name = name;
        this.backstory = backstory;
        this.bonds = bonds;
        this.flaws = flaws;
        this.alignment = alignment;
        this.armorClass = armorClass;
        this.movementSpeed = movementSpeed;
        this.hitPoints = hitPoints;
        this.characterRace = characterRace;
        this.characterClass = characterClass;
        this.abilityScores = abilityScores;
    }

    // getting ability scores
    public int getAbilityScore(Ability ability) {
        return abilityScores.getOrDefault(ability, 0);
    }

    // calculating modifiers
    public int getAbilityModifier(Ability ability) {
        return (getAbilityScore(ability) - 10) / 2;
    }

    public String getName() {
            return name != null ? name : "Unknown character";
    }

    public int getInitiative() {
        return getAbilityModifier(Ability.DEXTERITY);
    }

    public int getArmorClass() {
        return armorClass;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public String getBackstory() {
        return backstory;
    }

    public String getBonds() {
        return bonds;
    }

    public String getFlaws() {
        return flaws;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getRace() {
        return characterRace;
    }

    public String getCharacterClass() {
        return characterClass;
    }
}
