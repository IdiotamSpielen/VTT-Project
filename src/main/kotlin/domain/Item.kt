package domain

data class Item(
    val name: String,
    val type: ItemType,
    val description: String,
    val weight: Double = 0.0,
    val value: Long = 0,
    val damage: String? = null,
)
