package idiotamspielen.vttproject.classifications

class Character : Nameable {
    var characterName: String? = null
    override fun getName(): String {
        return characterName!!
    }
}
