package handlers;

import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class MapFileHandler {

    public void copyFileToLibrary(File sourceFile) {
        Path sourcePath = sourceFile.toPath();
        Path targetDirectory = Paths.get("src/Library/maps/");
        Path targetPath = targetDirectory.resolve(sourceFile.getName());

        try {
            Files.createDirectories(targetDirectory); // Erstellt das Verzeichnis, wenn es nicht existiert
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
