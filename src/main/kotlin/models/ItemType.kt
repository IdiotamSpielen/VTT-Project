package models

enum class ItemType {
    WEAPON,
    ARMOR,
    CONSUMABLE,
    MATERIALS,
    OTHER;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}