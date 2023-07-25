package classifications;

public class DnDRace extends EntryRef{

    private String name;
    private String description;
    private String[] abilities;
    private String[] bonuses;

    public DnDRace(String name, String description, String[] abilities, String[] bonuses) {
        this.name = name;
        this.description = description;
        this.abilities = abilities;
        this.bonuses = bonuses;
    }

    public String getName() {
        return name;
    }
}
