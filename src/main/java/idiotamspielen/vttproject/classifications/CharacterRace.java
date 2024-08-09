package idiotamspielen.vttproject.classifications;

import java.util.ArrayList;
import java.util.List;

public class CharacterRace {
//The race gives the character an armor class, a list of racial traits, a base weapon, Weapon and/or spell proficiencies, and potentially a subrace
    protected String name;
    protected String description;
    protected int armorClass;
    protected List<RacialTrait> racialTraits;


    public CharacterRace(String name, String description) {
        this.name = name;
        this.description = description;
        this.racialTraits = new ArrayList<>(); // Initialize the list of racial traits
    }

    // Method to add a racial trait
    public void addRacialTrait(RacialTrait trait) {
        racialTraits.add(trait);
    }

    // Method to remove a racial trait
    public void removeRacialTrait(RacialTrait trait) {
        racialTraits.remove(trait);
    }

    // Getter for the list of racial traits
    public List<RacialTrait> getRacialTraits() {
        return racialTraits;
    }

    // Setter for the list of racial traits
    public void setRacialTraits(List<RacialTrait> racialTraits) {
        this.racialTraits = racialTraits;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for armor class
    public int getArmorClass() {
        return armorClass;
    }

    // Setter for armor class
    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    // Override toString for better representation of CharacterRace
    @Override
    public String toString() {
        return "CharacterRace{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", armorClass=" + armorClass +
                ", racialTraits=" + racialTraits +
                '}';
    }
}
