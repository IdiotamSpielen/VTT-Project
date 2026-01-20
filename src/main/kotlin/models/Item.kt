package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(
    override val name: String,
    val type: ItemType,
    val description: String,
    // Diese Felder sind optional, je nach Typ
    val damage: String? = null,
    val weight: Double = 0.0,
    val value: Int = 0
) : Nameable
