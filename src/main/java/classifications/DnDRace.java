package classifications;

import java.util.List;

public class DnDRace{

    private String name;
    private String description;
    private List<Ability> abilities;
    private String[] bonuses;

    public DnDRace(String name, String description, List abilities, String[] bonuses) {
        this.name = name;
        this.description = description;
        this.abilities = abilities;
        this.bonuses = bonuses;
    }

    public String getName() {
        return name;
    }
}
