package idiotamspielen.vttproject.models

/**
 * Represents a race that a character can belong to in the game.
 *
 * @property name The name of the main race (e.g., "Elf", "Dwarf").
 * @property subRace The name of the subrace, if applicable (e.g., "High Elf", "Mountain Dwarf").
 * @property attributeModifiers A map defining the attribute modifiers associated with the race.
 *                              The keys are of type [AttributeType] and the values are the respective modifiers.
 * @property description A textual description providing lore or background information about the race.
 * @property racialTraits A list of special traits or abilities specific to the race.
 * @constructor Initializes the CharacterRace with the specified properties.
 *
 * Implements the [Nameable] interface to provide a name representation of the race, which includes the
 * subRace name if present, or the general race name otherwise.
 */
open class CharacterRace(
    val baseName: String,
    val subRace: String?,
    val attributeModifiers: Map<AttributeType, Int>,
    val description: String,
    val racialTraits: List<String>,
) : Nameable{
    override val name: String
        get() = subRace?.let { "$it $baseName" } ?: baseName
}
