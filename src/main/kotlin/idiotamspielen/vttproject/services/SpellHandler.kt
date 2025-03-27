package idiotamspielen.vttproject.services

import idiotamspielen.vttproject.exceptions.InvalidSpellException
import idiotamspielen.vttproject.exceptions.SpellNotSavedException
import idiotamspielen.vttproject.models.Spell

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
     * Validiert die Eingaben für den Zauber.
     *
     * @param spell Der zu validierende Zauber.
     * @throws InvalidSpellException wenn die Eingaben ungültig sind.
     */
    private fun validateSpell(spell: Spell) {
        if (spell.name.isEmpty()) throw InvalidSpellException("Spell name cannot be empty.")
        if (spell.duration.isEmpty()) throw InvalidSpellException("Duration cannot be empty.")
        if (spell.components.isEmpty()) throw InvalidSpellException("Components cannot be empty.")
        if (spell.level !in 0..9) throw InvalidSpellException("Spell level must be between 0 and 9.")
        if (spell.description.isEmpty()) throw InvalidSpellException("Description cannot be empty.")
    }
}