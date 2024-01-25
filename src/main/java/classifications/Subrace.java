package classifications;

public class Subrace extends CharacterRace {
    protected String subRaceName;

    public Subrace(String raceName, String description, String subRaceName) {
        super(raceName, description);
        this.subRaceName = subRaceName;
    }
}
