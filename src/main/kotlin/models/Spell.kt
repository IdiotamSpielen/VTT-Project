package models

data class Spell(
    val name: String,
    val duration: String,
    val components: String,
    val ingredients: String?,
    val description: String,
    val school: String,
    val level: Int,
    val range: String,
    val castingTime: String,
    val ritual: Boolean,
    val concentration: Boolean
)