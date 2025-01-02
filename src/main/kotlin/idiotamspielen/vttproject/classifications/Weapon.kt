package idiotamspielen.vttproject.classifications

class Weapon(
    weaponName: String, description: String?, weight: Double, value: Int,
    var weaponType: String?, var range: Int
) : Item(weaponName, description, weight, value) {
    override val itemType: String? = "Weapon"
}
