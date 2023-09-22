package classifications;

import java.util.List;

public class DnDClass{
    private String name;
    private String description;
    private List<Feature> classFeatures;

    public DnDClass(String name, String description, List classFeatures){
        this.name = name;
        this.description = description;
        this.classFeatures = classFeatures;
    }

    public String getName() {
        return name;
    }
}
