package idiotamspielen.vttproject.classifications

enum class ItemType {
    WEAPON,
    ARMOR,
    CONSUMABLE,
    MATERIALS,
    OTHER;

    override fun toString(): String {
        return name.replaceFirstChar { it.uppercase() }.lowercase().replace('_', ' ')
    }
}