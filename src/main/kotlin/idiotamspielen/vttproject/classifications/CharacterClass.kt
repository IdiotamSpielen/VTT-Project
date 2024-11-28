package idiotamspielen.vttproject.classifications

open class CharacterClass(name1: String?, description1: String?, abilities1: MutableList<*>?) : Nameable{
    private val name: String? = null
    private val description: String? = null
    private val abilities: List<Ability>? = null

    override fun getName(): String {
        return name!!
    }
}
