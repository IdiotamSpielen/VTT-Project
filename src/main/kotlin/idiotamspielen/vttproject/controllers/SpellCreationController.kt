package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.classifications.Spell
import idiotamspielen.vttproject.creators.SpellCreator
import idiotamspielen.vttproject.handlers.FileHandler
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class SpellCreationController : Controller() {
    val spellName = SimpleStringProperty()
    val castingTime = SimpleStringProperty()
    val range = SimpleStringProperty()
    val component = SimpleStringProperty()
    val duration = SimpleStringProperty()
    val ingredients = SimpleStringProperty()
    val description = SimpleStringProperty()
    val level = SimpleStringProperty()
    val school = SimpleStringProperty()

    val isRitual = SimpleBooleanProperty(false)
    val isConcentration = SimpleBooleanProperty(false)

    fun createSpell(){
        val newSpellCreator = SpellCreator(FileHandler(Spell::class.java, "spells"))

        newSpellCreator.create(
            spellName = spellName.getOrElse(""),
            castingTime = castingTime.getOrElse("instantaneous"),
            range = range.getOrElse("touch"),
            components = component.getOrElse(""),
            duration = duration.getOrElse(""),
            ingredients = ingredients.getOrElse("N/A"),
            description = description.getOrElse(""),
            levelString = level.getOrElse(""),
            school = school.getOrElse(""),
            ritual = isRitual.get(),
            concentration = isConcentration.get()
        )

        if (!newSpellCreator.isSpellValid()) {
            throw InvalidSpellException("The Spell is invalid. Check your input.")
        } else if (!newSpellCreator.isSpellSaved()) {
            throw SpellNotSavedException("The Spell was not saved. Check the logs for more information.")
        }
    }

    private fun SimpleStringProperty.getOrElse(default : String): String{
        return this.get() ?: default
    }
}

class InvalidSpellException(message: String) : Exception(message)
class SpellNotSavedException(message: String) : Exception(message)