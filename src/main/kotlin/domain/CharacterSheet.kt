package domain

import kotlinx.serialization.Serializable

@Serializable
data class CharacterSheet(
    val id: String,
    val systemId: String,

    val rawStats: Map<String, Int>,

    val name: String
)
