package classifications;

public class Character extends EntryRef{
    private String name;
    private int initiative;
    private int armorClass;
    private int movementSpeed;
    private int hitPoints;
    private String backstory;
    private String bonds;
    private String flaws;
    private String alignment;
    private Race race;
    private DnDClass dndClass;

    public Character(String name, Race race, DnDClass dndClass) {
        this.name = name;
        this.race = race;
        this.dndClass = dndClass;
    }
}
