package classifications;

import java.util.List;

public class CharacterRace {
//The race gives the character an armor class, a list of racial traits, a base weapon, Weapon and/or spell proficiencies, and potentially a subrace
    protected String name;
    protected String description;
    protected int armorClass;


    public CharacterRace(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
