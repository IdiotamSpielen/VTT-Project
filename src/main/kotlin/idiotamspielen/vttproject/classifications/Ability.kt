package idiotamspielen.vttproject.classifications

class Ability : Nameable {
    private val name: String? = null
    private val description: String? = null
    private val level: Int? = null
    private val type: String? = null
    private val cost: Int? = null
    private val cooldown: Int? = null
    private val duration: Int? = null
    private val range: Int? = null
    private val target: String? = null
    private val descriptionLong: String? = null
    private val descriptionShort: String? = null

    override fun getName(): String {
        return name!!
    }
}
