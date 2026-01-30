package models

import kotlinx.serialization.*

@Serializable
sealed interface RaceConfig

// D&D 5e braucht Speed und Size
@Serializable
@SerialName("dnd5e")
data class DnD5eRaceConfig(
    val speed: Int,      // 30
    val size: String,    // "Medium"
    val darkvision: Boolean
) : RaceConfig

// Shadowrun braucht Limits für Attribute
@Serializable
@SerialName("shadowrun")
data class ShadowrunMetatypeConfig(
    val bodyMax: Int,
    val strengthMax: Int,
    val essenceCost: Double
) : RaceConfig

// Ein System, das Rassen nur als "Label" und Feature-Container nutzt
@Serializable
@SerialName("simple_rpg")
object NoRaceConfig : RaceConfig