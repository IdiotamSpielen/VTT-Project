package models

import kotlinx.serialization.*

// 1. Der Container für die spezifischen Daten
@Serializable
sealed interface ClassConfig

@Serializable
@SerialName("dnd5e")
data class DnD5eClassConfig(
    val hitDie: String,
    val savingThrows: List<String>,
    val spellcastingAbility: String? = null
) : ClassConfig
@Serializable
@SerialName("coc")
data class CoCJobConfig(
    val creditRatingMin: Int,
    val creditRatingMax: Int,
    val occupationalSkills: List<String>
) : ClassConfig

@Serializable
@SerialName("my_system")
object NoConfig : ClassConfig