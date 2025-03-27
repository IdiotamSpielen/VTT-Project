package idiotamspielen.vttproject.models

open class CharacterClass(
    override val name: String,
    val description: String,
    val abilities: List<Ability>,
    val spells: List<Spell>,
    val levelFeatures: Map<Int, List<String>>
) : Nameable

