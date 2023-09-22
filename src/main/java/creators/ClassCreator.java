package creators;

import classifications.DnDClass;
import classifications.Feature;

import java.util.List;

//Will that classname be problematic at any point?
public class ClassCreator {
    public static DnDClass createClass(String name, String description, List<Feature> classFeatures) {
        return new DnDClass(name, description, classFeatures);
    }
}
