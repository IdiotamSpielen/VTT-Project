package viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import models.ElementType
import models.TableTopElement
import repositories.AssetRepository
import repositories.ImageAssetModel
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class TableTopViewmodel {

    val elements = mutableStateListOf<TableTopElement>()
    var isEditMode by mutableStateOf(false)
        private set
    var recentAssets = mutableStateListOf<ImageAssetModel>()
    var pendingImportFile by mutableStateOf<File?>(null)
        private set

    var tokenSize by mutableStateOf(100f) // Default token size in DP

    var errorMessage by mutableStateOf<String?>(null)
        private set
    private val assetRepository = AssetRepository()
    private val allowedExtensions = setOf("jpg", "jpeg", "png", "bmp", "webp", "svg")

    init {
        loadRecents()
    }

    private fun loadRecents() {
        recentAssets.clear()
        recentAssets.addAll(assetRepository.getRecentAssets())
    }

    fun moveElement(id: String, dragAmount: Offset) {
        val index = elements.indexOfFirst { it.id == id }
        if (index != -1) {
            val element = elements[index]
            val newPos = element.position + dragAmount
            val updated = element.copy(position = newPos)

            elements.removeAt(index)
            elements.add(updated)
        }
    }

    // Called by the DropTarget-Listener to initiate image import
    fun onFileDropped(file: File) {
        val extension = file.extension.lowercase()
        if (extension in allowedExtensions) {
            errorMessage = null
            pendingImportFile = file
        } else {
            errorMessage = "Invalid file format: .$extension. Please use images (JPG, PNG...)."
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

            //val dbId = assetRepository.addAsset(
            assetRepository.addAsset(
                fileName = file.name,
                filePath = targetPath.toString(),
                assetType = type
            )

            val newElement = TableTopElement(
                //id = dbId.toString(),
                name = file.name,
                absolutePath = targetPath.toString(),
                position = Offset(100f, 100f),
                type = type
            )

            if (type == ElementType.MAP) elements.add(0, newElement)
            else elements.add(newElement)

        } catch (e: IOException) {
            e.printStackTrace()
            errorMessage = "Error: ${e.message}"
        } finally {
            pendingImportFile = null
        }
    }

    fun addFromHistory(asset: ImageAssetModel) {
        // Update usage timestamp to keep frequently used assets in the "recent" list
        assetRepository.updateLastAccessed(asset.id)
        loadRecents() // Reorder list to reflect latest usage

        // Place the asset onto the tabletop board
        val newElement = TableTopElement(
            //id = asset.id.toString(),
            name = asset.name,
            absolutePath = asset.path,
            position = Offset(100f, 100f),
            type = asset.type
        )
        elements.add(newElement)
    }
    fun removeElement(elementId: String) {
        elements.removeIf { it.id == elementId }
    }

    fun toggleEditMode() {
        isEditMode = !isEditMode
    }
}