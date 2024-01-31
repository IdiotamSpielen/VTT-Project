package handlers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import classifications.CharacterRace;

public class RaceFileHandler {
    private boolean isSaved = false;
    private Gson gson = new Gson();

    public void saveRaceToFile(CharacterRace race){

        if (race == null) {
            System.out.println("Failed to save Race: Race is null");
            return;
        }

        //save spell to library. If library doesn't exist yet, create one.
        File directory = new File("src/Library/data/CharacterRaces");
        directory.mkdirs();

        String fileName = race.getName() + ".json";
        File file = new File(directory, fileName);
        try (FileWriter writer = new FileWriter(file)) {
                // Serialize the Spell object to JSON
                String json = gson.toJson(spell);

                writer.write(json);
                writer.flush();
        if (!isSaved) {
            System.out.println("Race saved as " + fileName);
            isSaved = true;
        }
     } catch (FileNotFoundException e){
        System.out.println("Failed to save Race: File not found");
        e.printStackTrace();
        } catch (IOException e){
        System.out.println("Failed to save Race: " + e.getMessage());
        e.printStackTrace();
        }
    }

    public boolean isSaved(){
        return  isSaved;
    }

    public List<CharacterRace> getSavedRaceInformation() {
        List<CharacterRace> races = new ArrayList<>();

        new RaceFileHandler();
        File RaceDirectory = new File("src/Library/data/characterRaces");
        File[] raceFiles = raceDirectory.listFiles();

        if (raceFiles != null) {
            for (File raceFile : raceFiles) {
                if (raceFile.isFile() && raceFile.getName().endsWith(".json")) { // Check for JSON files
                    try (FileReader fileReader = new FileReader(raceFile)) {
                        // Deserialize the JSON into a CharacterRace object
                        CharacterRace race = gson.fromJson(fileReader, CharacterRace.class);
                        races.add(race);
                    } catch (IOException e) {
                        System.out.println("Failed to read race file: " + raceFile.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
        return races;
    }

    public List<CharacterRace> searchRace(String query) {
        List<CharacterRace> allRaces = getSavedRaceInformation();
        List<CharacterRace> matchingRaces = new ArrayList<>();

        // Search for races matching the query (case-insensitive)
        for (CharacterRace race : allRaces) {
            if (race.getName().toLowerCase().contains(query.toLowerCase())) {
                matchingRaces.add(race);
            }
        }
        return matchingRaces;
    }
}