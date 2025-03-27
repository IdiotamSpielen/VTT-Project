package idiotamspielen.vttproject.models

abstract class Item(
    override val name: String,
    var description: String?,
    var weight: Double,
    var value: Int
) : Nameable
