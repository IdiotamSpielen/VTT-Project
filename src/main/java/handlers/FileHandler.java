package handlers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler<T extends Things> {
    private boolean isSaved;
    private ObjectMapper mapper = new ObjectMapper();
    private final Class<T> clazz;
    private final String directoryPath;

    public FileHandler(Class<T> clazz, String directoryPath){
        this.clazz = clazz;
        this.directoryPath = directoryPath;
    }

    public void saveToFile(T obj){

        if (obj == null) {
            System.out.println("Failed to save: Object is null");
            return;
        }

        //save object to library. If library doesn't exist yet, create one.
        File directory = new File(directoryPath);
        directory.mkdirs();

        String fileName = obj.getName() + ".json";
        File file = new File(directory, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            // Serialize the object to JSON
            String json = mapper.writeValueAsString(obj);

            writer.write(json);
            writer.flush();

            System.out.println(clazz.getSimpleName() + " Saved as " + fileName);
            isSaved = true;
        } catch (FileNotFoundException e){
        System.out.println("Failed to save: File not found");
        e.printStackTrace();
        } catch (IOException e){
            System.out.println("Failed to save: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isSaved(){
        return  isSaved;
    }

    public List<T> getSavedObjectInformation() {
        List<T> objects = new ArrayList<>();

        File objectDirectory = new File(directoryPath);
        File[] objectFiles = objectDirectory.listFiles();

        if (objectFiles != null) {
            for (File objectFile : objectFiles) {
                if (objectFile.isFile() && objectFile.getName().endsWith(".json")) { // Check for JSON files
                    try (FileReader fileReader = new FileReader(objectFile)) {
                        // Deserialize the JSON into an object
                        T obj = mapper.readValue(fileReader, clazz);
                        objects.add(obj);
                    } catch (IOException e) {
                        System.out.println("Failed to read file: " + objectFile.getName());
                        e.printStackTrace();
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