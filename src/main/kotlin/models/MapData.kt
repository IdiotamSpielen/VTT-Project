package models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
enum class LayerType {
    BACKGROUND, // Only for the main map image
    OBJECTS,    // Terrain features, furniture, etc.
    TOKENS,     // Character and enemy tokens
    FOG_OF_WAR, // For the fog layer
    GM_ONLY     // For notes or hidden elements
}

@Serializable
data class MapLayer(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: LayerType,
    val isVisible: Boolean = true,
    val elements: MutableList<TableTopElement> = mutableListOf()
)

@Serializable
data class MapFloor(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val layers: List<MapLayer> = listOf(
        MapLayer(name = "Background", type = LayerType.BACKGROUND),
        MapLayer(name = "Objects", type = LayerType.OBJECTS),
        MapLayer(name = "Tokens", type = LayerType.TOKENS),
        MapLayer(name = "GM Notes", type = LayerType.GM_ONLY)
    )
)

@Serializable
data class TableTopMap(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "New Map",
    val floors: MutableList<MapFloor> = mutableListOf(MapFloor(name = "Floor 1"))
)
