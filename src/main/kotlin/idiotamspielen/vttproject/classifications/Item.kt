package idiotamspielen.vttproject.classifications

abstract class Item(
    var name: String?, var description: String?, var weight: Double, var value: Int
) {
    abstract val itemType: String?
}
