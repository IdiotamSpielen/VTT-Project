package idiotamspielen.vttproject.classifications
/*The race gives the character an armor class, a list of racial traits, a base weapon,
 * Weapon and/or spell proficiencies, and potentially a subrace
 */
open class CharacterRace(
    var name: String?,
    protected var description: String?
) {
    protected var armorClass: Int = 0
}
