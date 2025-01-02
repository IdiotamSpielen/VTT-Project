package idiotamspielen.vttproject.classifications

abstract class Item(
    var itemName: String, var description: String?, var weight: Double, var value: Int
) : Nameable {
    abstract val itemType: String?
    override fun getName(): String = itemName
}
