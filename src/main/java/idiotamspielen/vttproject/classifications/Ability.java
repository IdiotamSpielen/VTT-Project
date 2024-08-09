package idiotamspielen.vttproject.classifications;

// Enum für Fähigkeiten, um Indices klarer darzustellen
public enum Ability {
    STRENGTH(0),
    DEXTERITY(1),
    CONSTITUTION(2),
    INTELLIGENCE(3),
    WISDOM(4),
    CHARISMA(5);

    private final int index;

    Ability(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
