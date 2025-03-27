package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.models.Spell
import idiotamspielen.vttproject.services.FileHandler
import idiotamspielen.vttproject.services.SpellHandler
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

/**
 * Controller class responsible for creating spells.
 *
 * This class provides functionality for setting spell properties and
 * creating a spell using the `SpellCreator` class. It also ensures
 * that the created spell is valid and saved properly.
 */
class SpellCreationController : Controller() {
    private val fileHandler = FileHandler(Spell::class.java, "spells")
    private val spellHandler = SpellHandler(fileHandler)

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

    fun createSpell() {
        val spell = Spell(
            spellName.get(),
            school.get(),
            duration.get(),
            component.get(),
            level.get().toInt(),
            range.get(),
            castingTime.get(),
            description.get(),
            ingredients.get(),
            isRitual.get(),
            isConcentration.get()
        )

        spellHandler.createAndSaveSpell(spell)
    }
}

