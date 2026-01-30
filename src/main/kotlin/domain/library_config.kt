package domain

import kotlinx.serialization.*

/**
 * Das Basis-Interface für ALLES, was in der LibraryTable landen kann.
 */
@Serializable
sealed interface LibraryConfig

// --- KLASSEN ---
@Serializable
sealed interface ClassConfig : LibraryConfig

@Serializable
@SerialName("dnd5e_class")
data class DnD5eClassConfig(
    val hitDie: String,
    val savingThrows: List<String>,
    val spellcastingAbility: String? = null
) : ClassConfig

// --- RASSEN ---
@Serializable
sealed interface RaceConfig : LibraryConfig

@Serializable
@SerialName("dnd5e_race")
data class DnD5eRaceConfig(
    val speed: Int,
    val size: String,
    val darkvision: Boolean
) : RaceConfig

// --- ZAUBER (Spells) ---
@Serializable
sealed interface SpellConfig : LibraryConfig

@Serializable
@SerialName("dnd5e_spell")
data class DnD5eSpellConfig(
    val level: Int,
    val school: String,
    val components: String,
    val castingTime: String,
    val range: String,
    val duration: String,
    val ingredients: String? = null
) : SpellConfig