package idiotamspielen.vttproject.models

class Weapon(
    weaponName: String, description: String?, weight: Double, value: Int,
    val weaponType: String?,
    val range: Int
) : Item(weaponName, description, weight, value)
