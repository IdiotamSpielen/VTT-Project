package services

import repositories.SpellRepository
import exceptions.InvalidSpellException
import exceptions.SpellNotSavedException
import models.Spell
import utils.L

class SpellHandler(private val repository: SpellRepository = SpellRepository()) {

    /**
     * Erstellt einen neuen Zauber und speichert ihn.
     *
     * @param spell Der zu speichernde Zauber.
     * @throws InvalidSpellException wenn der Zauber nicht gültig ist.
     * @throws SpellNotSavedException wenn der Zauber nicht gespeichert werden konnte.
     */
    fun createAndSaveSpell(spell: Spell) {
        validateSpell(spell)
        try {
            repository.save(spell)
        } catch (e: Exception) {
            e.printStackTrace()
            throw SpellNotSavedException(L.ERR_SAVE_FAILED)
        }
    }

    /**
     * validate values passed for the Spell to be saved
     *
     * @param spell The [Spell] Object to be saved
     */
    private fun validateSpell(spell: Spell) {
        val validationRules = listOf(
            spell.name.isEmpty() to L.ERR_NAME_EMPTY,
            spell.castingTime.isEmpty() to L.ERR_CASTING_EMPTY,
            spell.duration.isEmpty() to L.ERR_DURATION_EMPTY,
            spell.range.isEmpty() to L.ERR_RANGE_EMPTY,
            spell.components.isEmpty() to L.ERR_COMP_EMPTY,
            spell.description.isEmpty() to L.ERR_DESC_EMPTY,
            (spell.level !in 0..9) to L.ERR_LEVEL_RANGE,
            spell.school.isEmpty() to L.ERR_SCHOOL_EMPTY,
        )
        validationRules.find { it.first }?.let {
            throw InvalidSpellException(it.second)
        }
    }
}