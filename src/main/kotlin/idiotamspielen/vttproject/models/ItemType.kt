package idiotamspielen.vttproject.models

enum class ItemType {
    WEAPON,
    ARMOR,
    CONSUMABLE,
    MATERIALS,
    OTHER;

    override fun toString(): String {
        return name
            .replace('_', ' ')
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }
}