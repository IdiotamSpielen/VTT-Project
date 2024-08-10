package idiotamspielen.vttproject.classifications;

import java.util.List;

public class CharacterClass extends Things {
    private String name;
    private String description;
    private int hitPoints;
    private int armorClass;


    public CharacterClass(String name, String description, List classFeatures) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
