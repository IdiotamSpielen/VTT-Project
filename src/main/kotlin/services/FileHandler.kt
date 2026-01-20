package services

import com.fasterxml.jackson.databind.ObjectMapper
import models.Nameable
import org.slf4j.LoggerFactory
import kotlin.jvm.Throws
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name

/**
 * A generic file handler for managing objects of type [T] which implements the [Nameable] interface.
 * Provides functionality to save objects to files, retrieve stored objects, and search by name.
 *
 * @param T the type of objects which this handler will manage. Must implement [Nameable].
 * @param clazz The [Class] type of the objects to be processed.
 * @param category The category used to organize files in directories.
 */

class FileHandler<T : Nameable>(
    private val clazz: Class<T>,
    category: String,
    basePath: Path = Paths.get(System.getProperty("user.home"), "Documents", "VTT", "library", "data")
) {
    private val logger = LoggerFactory.getLogger(FileHandler::class.java)
    private val mapper = ObjectMapper()
    private val categoryPath: Path = basePath.resolve(category)

    /**
     * Saves an object to a file in the [category] directory. The file is named after the object's name.
     *
     * @param obj The object to be saved. If the object is null, the method logs an error and returns.
     */
    @Throws(IOException::class)
    fun saveToFile(obj: T?) {
        if (obj == null) {
            throw IllegalArgumentException("Object cannot be Null")
        }

        try {
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath)
                logger.info("Created directory at {}", categoryPath)
            }

            val filePath = categoryPath.resolve("${obj.name}.json")
            Files.newBufferedWriter(filePath).use { writer ->
                val json = mapper.writeValueAsString(obj)
                writer.write(json)
                logger.info("{} '{}' saved successfully", clazz.simpleName, obj.name)
            }
        } catch (e: IOException) {
            logger.error("IO Error during save of {}: {}", obj.name, e.message)
            throw e
        }
    }

    /**
     * Retrieves a list of saved objects by reading JSON files from the specified directory.
     *
     * @return A list of objects of type [T].
     * Returns an empty list if the directory does not exist or contains no valid files.
     */
    fun getSavedObjectInformation(): List<T> {
        val objects = mutableListOf<T>()

        if (!Files.exists(categoryPath) || !Files.isDirectory(categoryPath)) {
            logger.warn("Directory {} does ot exist", categoryPath)
            return objects
        }

        try {
            Files.newDirectoryStream(categoryPath, "*.json").use { stream ->
                for (path in stream) {
                    try {
                        Files.newBufferedReader(path).use { reader ->
                            val obj = mapper.readValue(reader, clazz)
                            objects.add(obj)
                        }
                    } catch (e: IOException) {
                        logger.error("Failed to read file '{}': {}", path.name, e.message)
                    }
                }
            }
        } catch (e: IOException) {
            logger.error("Error accessing directory {}: {}", categoryPath, e.message)
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
        return getSavedObjectInformation().filter { it.name.contains(query, ignoreCase = true) }
    }
}