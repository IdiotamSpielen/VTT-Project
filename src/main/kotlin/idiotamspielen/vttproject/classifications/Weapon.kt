package idiotamspielen.vttproject.classifications

class Weapon(
    name: String?, description: String?, weight: Double, value: Int, //i.e. "Melee", "Ranged"
    var weaponType: String?, var range: Int
) : Item(name, description, weight, value) {
    val damageTypes: MutableMap<String?, Int?> = HashMap<String?, Int?>()

    fun addDamageType(type: String?, value: Int) {
        this.damageTypes.put(type, value)
    }

    fun getDamageValue(type: String?): Int {
        return this.damageTypes.getOrDefault(type, 0)!!
    }

    override val itemType: String?
        get() = "Weapon"
}
