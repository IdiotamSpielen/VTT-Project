package idiotamspielen.vttproject.classifications

class Character : Nameable {
    var Charactername: String? = null
    override fun getName(): String {
        return Charactername!!
    }
}
