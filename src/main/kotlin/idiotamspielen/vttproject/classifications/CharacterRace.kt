package idiotamspielen.vttproject.classifications
/*The race gives the character an armor class, a list of racial traits, a base weapon,
 * Weapon and/or spell proficiencies, and potentially a subrace
 */
open class CharacterRace(
    protected var description: String?
) : Nameable {
    protected var armorClass: Int = 0
    override fun getName(): String {
        TODO("Not yet implemented")
    }
}
