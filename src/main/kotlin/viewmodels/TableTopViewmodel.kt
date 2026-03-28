package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import models.*
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.*
import repositories.AssetRepository
import repositories.ImageAssetModel
import repositories.Repository
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import java.nio.charset.StandardCharsets

class TableTopViewmodel {

    var currentMap by mutableStateOf(TableTopMap())
    var currentFloorIndex by mutableStateOf(0)
    var activeLayerType by mutableStateOf(LayerType.TOKENS)

    val currentFloor: MapFloor
        get() = currentMap.floors[currentFloorIndex]

    fun saveCurrentMap() {
        try {
            val userHome = System.getProperty("user.home")
            val mapsDir = Paths.get(userHome, "Documents/VTT/maps/data")
            if (!Files.exists(mapsDir)) Files.createDirectories(mapsDir)

            val fileName = "${currentMap.name.replace(" ", "_")}_${currentMap.id}.json"
            val targetPath = mapsDir.resolve(fileName)
            
            // Manual JSON for now if the library is not being picked up
            val jsonString = """{"id":"${currentMap.id}","name":"${currentMap.name}"}"""
            Files.write(targetPath, jsonString.toByteArray(StandardCharsets.UTF_8))
            
            // Here we would also update the MapsTable in DB
            println("Map saved to $targetPath")
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = "Failed to save map: ${e.message}"
        }
    }

    // Simplified for the current UI: flattened list of all visible elements in the current floor
    val elements: List<TableTopElement>
        get() = currentFloor.layers
            .filter { it.isVisible }
            .flatMap { it.elements }

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
        recentAssets.addAll(assetRepository.getRecent(10))
    }

    fun moveElement(id: String, dragAmount: Offset) {
        currentFloor.layers.forEach { layer ->
            val index = layer.elements.indexOfFirst { it.id == id }
            if (index != -1) {
                val element = layer.elements[index]
                val newPos = element.position + dragAmount
                layer.elements[index] = element.copy(x = newPos.x, y = newPos.y)
                return
            }
        }
    }

    // Called by the DropTarget-Listener to initiate image import
    fun onFileDropped(file: File) {
        val extension = file.extension.lowercase()
        if (extension in allowedExtensions) {
            errorMessage = null
            // Infer type from active layer
            val type = if (activeLayerType == LayerType.BACKGROUND) ElementType.MAP else ElementType.TOKEN
            importFile(file, type)
        } else {
            errorMessage = "Invalid file format: .$extension. Please use images (JPG, PNG...)."
        }
    }

    fun dismissError() {
        errorMessage = null
    }

    private fun importFile(file: File, type: ElementType) {
        try {
            val userHome = System.getProperty("user.home")
            val subFolder = if (type == ElementType.MAP) "maps" else "tokens"
            val targetDir = Paths.get(userHome, "Documents/VTT/library", subFolder)

            if (!Files.exists(targetDir)) Files.createDirectories(targetDir)

            val targetPath = targetDir.resolve(file.name)
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING)

            assetRepository.save(
                ImageAssetModel(
                    id = 0,
                    name = file.name,
                    path = targetPath.toString(),
                    type = type
                )
            )

            val newElement = TableTopElement(
                name = file.name,
                absolutePath = targetPath.toString(),
                x = 100f,
                y = 100f,
                type = type
            )

            val targetLayer = currentFloor.layers.first { it.type == activeLayerType }
            targetLayer.elements.add(newElement)
            loadRecents()

        } catch (e: IOException) {
            e.printStackTrace()
            errorMessage = "Error: ${e.message}"
        }
    }

    fun addFromHistory(asset: ImageAssetModel) {
        // Update usage timestamp to keep frequently used assets in the "recent" list
        assetRepository.updateLastAccessed(asset.id)
        loadRecents() // Reorder list to reflect latest usage

        // Place the asset onto the tabletop board
        val newElement = TableTopElement(
            name = asset.name,
            absolutePath = asset.path,
            x = 100f,
            y = 100f,
            type = asset.type
        )
        
        val targetLayer = currentFloor.layers.first { it.type == activeLayerType }
        targetLayer.elements.add(newElement)
    }
    
    fun addFloor() {
        val newFloor = MapFloor(name = "Floor ${currentMap.floors.size + 1}")
        currentMap.floors.add(newFloor)
        currentFloorIndex = currentMap.floors.size - 1
    }
    fun removeElement(elementId: String) {
        currentFloor.layers.forEach { layer ->
            if (layer.elements.removeIf { it.id == elementId }) {
                return
            }
        }
    }

    fun toggleEditMode() {
        isEditMode = !isEditMode
    }
}