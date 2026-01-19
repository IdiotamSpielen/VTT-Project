package controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import models.ElementType
import models.TableTopElement
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class TableTopController {

    val elements = mutableStateListOf<TableTopElement>()
    var pendingImportFile by mutableStateOf<File?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val allowedExtensions = setOf("jpg", "jpeg", "png", "bmp", "gif", "webp")

    fun moveElement(id: String, dragAmount: Offset) {
        val index = elements.indexOfFirst { it.id == id }
        if (index != -1) {
            val element = elements[index]
            val newPos = element.position + dragAmount
            elements[index] = element.copy(position = newPos)
        }
    }

    // Diese Funktion wird vom DropTarget-Listener aufgerufen
    fun onFileDropped(file: File) {
        val extension = file.extension.lowercase()
        if (extension in allowedExtensions) {
            errorMessage = null // Alte Fehler löschen
            pendingImportFile = file
        } else {
            // Fehler setzen, Datei ignorieren
            errorMessage = "Ungültiges Dateiformat: .$extension. Bitte nutze Bilder (JPG, PNG...)."
            pendingImportFile = null
        }
    }

    fun dismissError() {
        errorMessage = null
    }

    fun cancelImport() {
        pendingImportFile = null
    }

    fun confirmImport(type: ElementType) {
        val file = pendingImportFile ?: return
        try {
            val userHome = System.getProperty("user.home")
            val subFolder = if (type == ElementType.MAP) "maps" else "tokens"
            val targetDir = Paths.get(userHome, "Documents/VTT/library", subFolder)

            if (!Files.exists(targetDir)) Files.createDirectories(targetDir)

            val targetPath = targetDir.resolve(file.name)
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING)

            val newElement = TableTopElement(
                name = file.name,
                absolutePath = targetPath.toString(),
                position = Offset(100f, 100f),
                type = type
            )

            if (type == ElementType.MAP) elements.add(0, newElement)
            else elements.add(newElement)

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            pendingImportFile = null
        }
    }
}