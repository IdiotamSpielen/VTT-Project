package idiotamspielen.vttproject.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import idiotamspielen.vttproject.classifications.Nameable
import java.io.*
import java.nio.file.*

class FileHandler<T : Nameable>(private val clazz: Class<T>, category: String) {
    private var isSaved: Boolean = false
    private val mapper = ObjectMapper()
    private val categoryPath: Path

    init {
        val baseDirectoryPath = Paths.get(System.getProperty("user.home"), "Documents", "VTT", "library", "data")
        this.categoryPath = baseDirectoryPath.resolve(category)
    }

    fun saveToFile(obj: T?) {
        if (obj == null) {
            System.err.println("Failed to save: Object is null")
            return
        }

        try {
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath)
            }
        } catch (e: IOException) {
            System.err.println("Failed to create directory: $categoryPath - ${e.message}")
        }

        val filePath = categoryPath.resolve("${obj.getName()}.json")
        try {
            FileWriter(filePath.toFile()).use { writer ->
                val json = mapper.writeValueAsString(obj)
                writer.write(json)
                writer.flush()
                println("${clazz.simpleName} Saved as ${filePath.fileName}")
                isSaved = true
            }
        } catch (e: FileNotFoundException) {
            System.err.println("Failed to save: File not found")
        } catch (e: IOException) {
            System.err.println("Failed to save: ${e.message}")
        }
    }

    fun isSaved(): Boolean {
        return isSaved
    }

    fun getSavedObjectInformation(): List<T> {
        val objects = mutableListOf<T>()
        val objectDirectory = categoryPath.toFile()
        val objectFiles = objectDirectory.listFiles()

        if (objectFiles != null) {
            for (objectFile in objectFiles) {
                if (objectFile.isFile && objectFile.name.endsWith(".json")) {
                    try {
                        FileReader(objectFile).use { fileReader ->
                            val obj = mapper.readValue(fileReader, clazz)
                            objects.add(obj)
                        }
                    } catch (e: IOException) {
                        System.err.println("Failed to read file: ${objectFile.name} due to ${e.message}")
                    }
                }
            }
        }
        return objects
    }

    fun search(query: String): List<T> {
        val allObjects = getSavedObjectInformation()
        val matchingObjects = mutableListOf<T>()
        for (obj in allObjects) {
            if (obj.getName().contains(query, ignoreCase = true)) {
                matchingObjects.add(obj)
            }
        }
        return matchingObjects
    }
}