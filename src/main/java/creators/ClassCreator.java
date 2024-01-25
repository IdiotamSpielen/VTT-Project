package creators;

import classifications.CharacterClass;
import classifications.ClassFeature;

import java.util.List;

//Will that classname be problematic at any point?
public class ClassCreator {
    public static CharacterClass createClass(String name, String description, List<ClassFeature> classFeatures) {
        return new CharacterClass(name, description, classFeatures);
    }
}
