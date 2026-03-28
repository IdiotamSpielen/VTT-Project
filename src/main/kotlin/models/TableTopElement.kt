package models

import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
enum class ElementType {
    MAP, TOKEN
}

@Serializable
data class TableTopElement(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val absolutePath: String,
    val x: Float,
    val y: Float,
    val type: ElementType
) {
    val position: Offset
        get() = Offset(x, y)
}