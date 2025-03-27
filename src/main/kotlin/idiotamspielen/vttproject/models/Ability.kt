package idiotamspielen.vttproject.models

class Ability(
    override val name: String,
    private val level: Int?,
    private val type: String?,
    private val cost: Int?,
    private val cooldown: Int?,
    private val duration: Int?,
    private val range: Int?,
    private val target: String?,
    private val descriptionLong: String?,
    private val descriptionShort: String?,
) : Nameable
