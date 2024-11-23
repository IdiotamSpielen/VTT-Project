package idiotamspielen.vttproject.handlers;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler<T extends Things> {
    private boolean isSaved;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> clazz;
    private final Path categoryPath;

    public FileHandler(Class<T> clazz, String category){
        this.clazz = clazz;
        // Base path without category
        Path baseDirectoryPath = Paths.get(System.getProperty("user.home"), "Documents", "VTT", "library", "data");
        //String baseDirectoryPath = System.getProperty("user.home") + "/Documents/VTT/library/data";
        this.categoryPath = baseDirectoryPath.resolve(category);
    }

    public void saveToFile(T obj){

        if (obj == null) {
            System.err.println("Failed to save: Object is null");
            return;
        }

        //save object to library. If library doesn't exist yet, create one.
        try {
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + categoryPath + " - " + e.getMessage());
        }

        Path filePath = categoryPath.resolve(obj.getName() + ".json");
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            // Serialize the object to JSON
            String json = mapper.writeValueAsString(obj);

            writer.write(json);
            writer.flush();

            System.out.println(clazz.getSimpleName() + " Saved as " + filePath.getFileName());
            isSaved = true;
        } catch (FileNotFoundException e){
        System.err.println("Failed to save: File not found");
        } catch (IOException e){
            System.err.println("Failed to save: " + e.getMessage());
        }
    }

    public boolean isSaved(){
        return  isSaved;
    }

    public List<T> getSavedObjectInformation() {
        List<T> objects = new ArrayList<>();

        File objectDirectory = categoryPath.toFile();
        File[] objectFiles = objectDirectory.listFiles();

        if (objectFiles != null) {
            for (File objectFile : objectFiles) {
                if (objectFile.isFile() && objectFile.getName().endsWith(".json")) { // Check for JSON files
                    try (FileReader fileReader = new FileReader(objectFile)) {
                        // Deserialize the JSON into an object
                        T obj = mapper.readValue(fileReader, clazz);
                        objects.add(obj);
                    } catch (IOException e) {
                        System.err.println("Failed to read file: " + objectFile.getName() +
                                            " due to " + e.getMessage());
                    }
                }
            }
        }
        return objects;
    }

    public List<T> search(String query) {
        List<T> allObjects = getSavedObjectInformation();
        List<T> matchingObjects = new ArrayList<>();

        // Search for objects matching the query (case-insensitive)
        for (T obj : allObjects) {
            if (obj.getName().toLowerCase().contains(query.toLowerCase())) {
                matchingObjects.add(obj);
            }
        }
        return matchingObjects;
    }
}