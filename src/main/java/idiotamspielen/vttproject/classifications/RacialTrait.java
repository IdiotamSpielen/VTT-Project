package idiotamspielen.vttproject.classifications;

public class RacialTrait implements Feature {
    private String name;
    private String description;

    public RacialTrait(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RacialTrait{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
