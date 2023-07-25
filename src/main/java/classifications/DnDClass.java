package classifications;

public class DnDClass extends EntryRef{
    private String name;
    private String description;
    private  String[] abilities;

    public DnDClass(String name, String description, String[] abilities){
        this.name = name;
        this.description = description;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }
}
