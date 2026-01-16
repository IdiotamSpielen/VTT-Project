package services

import exceptions.InvalidSpellException
import exceptions.SpellNotSavedException
import models.Spell

class SpellHandler(private val fileHandler: FileHandler<Spell>){

    /**
     * Erstellt einen neuen Zauber und speichert ihn.
     *
     * @param spell Der zu speichernde Zauber.
     * @throws InvalidSpellException wenn der Zauber nicht gültig ist.
     * @throws SpellNotSavedException wenn der Zauber nicht gespeichert werden konnte.
     */
    fun createAndSaveSpell(spell: Spell) {
        validateSpell(spell)
        fileHandler.saveToFile(spell)
        if (!fileHandler.isSaved()) {
            throw SpellNotSavedException("The Spell was not saved.")
        }
    }

    /**
     * validate values passed for the Spell to be saved
     *
     * @param spell The [Spell] Object to be saved
     */
    private fun validateSpell(spell: Spell) {
        val validationRules = listOf(
            spell.name.isEmpty() to "Spell name cannot be empty.",
            spell.castingTime.isEmpty() to "Casting time cannot be empty.",
            spell.duration.isEmpty() to "Duration cannot be empty.",
            spell.range.isEmpty() to "Range cannot be empty.",
            spell.components.isEmpty() to "Components cannot be empty.",
            spell.description.isEmpty() to "Description cannot be empty.",
            (spell.level !in 0..9) to "Spell level must be between 0 and 9.",
            spell.school.isEmpty() to "School cannot be empty."
        )
        validationRules.find { it.first }?.let {
            throw InvalidSpellException(it.second)
        }
    }
}