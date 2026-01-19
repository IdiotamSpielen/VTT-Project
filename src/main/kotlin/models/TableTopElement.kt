package models

import androidx.compose.ui.geometry.Offset
import java.util.UUID

enum class ElementType {
    MAP, TOKEN
}

data class TableTopElement(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val absolutePath: String,
    val position: Offset,
    val type: ElementType
)