package idiotamspielen.vttproject.services

import com.fasterxml.jackson.databind.ObjectMapper
import idiotamspielen.vttproject.models.Nameable
import org.slf4j.LoggerFactory
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * A generic file handler for managing objects of type [T] which implements the [Nameable] interface.
 * Provides functionality to save objects to files, retrieve stored objects, and search by name.
 *
 * @param T the type of objects which this handler will manage. Must implement [Nameable].
 * @param clazz The [Class] type of the objects to be processed.
 * @param category The category used to organize files in directories.
 */
class FileHandler<T : Nameable>(private val clazz: Class<T>, category: String) {
    private val logger = LoggerFactory.getLogger(FileHandler::class.java)
    private val mapper = ObjectMapper()
    private val categoryPath: Path = Paths.get(
        System.getProperty("user.home"),
        "Documents",
        "VTT",
        "library",
        "data",
        category
    )
    @Volatile private var isSaved: Boolean = false

    /**
     * Saves an object to a file in the [category] directory. The file is named after the object's name.
     *
     * @param obj The object to be saved. If the object is null, the method logs an error and returns.
     */
    fun saveToFile(obj: T?) {
        if (obj == null) {
            logger.error("Failed to save: Object is null")
            return
        }

        try {
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath)
                logger.info("Created directory at {}", categoryPath)
            }

            val filePath = categoryPath.resolve("${obj.name}.json")
            FileWriter(filePath.toFile()).use { writer ->
                val json = mapper.writeValueAsString(obj)
                writer.write(json)
                writer.flush()
                logger.info("{} saved as {}", clazz.simpleName, filePath.fileName)
                isSaved = true
            }
        } catch (e: IOException) {
            logger.error("Failed to save object '{}': {}", obj.name, e.message, e)
        }
    }

    /**
     * Checks whether the last save operation was successful.
     *
     * @return `true` if the object is saved, `false` otherwise.
     */
    fun isSaved(): Boolean = isSaved

    /**
     * Retrieves a list of saved objects by reading JSON files from the specified directory.
     *
     * @return A list of objects of type [T].
     * Returns an empty list if the directory does not exist or contains no valid files.
     */
    fun getSavedObjectInformation(): List<T> {
        val objects = mutableListOf<T>()
        val objectDirectory = categoryPath.toFile()

        if (!objectDirectory.exists() || !objectDirectory.isDirectory) {
            logger.warn("Directory {} does not exist or is not a directory", categoryPath)
            return objects
        }

        val objectFiles = objectDirectory.listFiles { _, name -> name.endsWith(".json") } ?: emptyArray()

        for (objectFile in objectFiles) {
            try {
                FileReader(objectFile).use { fileReader ->
                    val obj = mapper.readValue(fileReader, clazz)
                    objects.add(obj)
                }
            } catch (e: IOException) {
                logger.error("Failed to read file '{}': {}", objectFile.name, e.message, e)
            }
        }
        return objects
    }

    /**
     * Searches for objects whose names contain the specified query string, ignoring case.
     *
     * @param query The search string used to filter object names.
     * @return A list of objects of type [T] whose names contain the query string.
     */
    fun search(query: String): List<T> {
        val allObjects = getSavedObjectInformation()
        return allObjects.filter { it.name.contains(query, ignoreCase = true) }
    }
}