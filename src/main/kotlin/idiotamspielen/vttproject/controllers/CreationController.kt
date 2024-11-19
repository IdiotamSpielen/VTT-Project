package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.classifications.Spell
import idiotamspielen.vttproject.creators.SpellCreator
import idiotamspielen.vttproject.handlers.FileHandler
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import tornadofx.intProperty

class CreationController : Controller() {
    val spellName = SimpleStringProperty()
    val castingTime = SimpleStringProperty()
    val range = SimpleStringProperty()
    val component = SimpleStringProperty()
    val duration = SimpleStringProperty()
    val ingredients = SimpleStringProperty()
    val description = SimpleStringProperty()
    val level = SimpleIntegerProperty()
    val school = SimpleStringProperty()

    val isRitual = SimpleBooleanProperty(false)
    val isConcentration = SimpleBooleanProperty(false)

    fun createSpell() {
        val spellNameValue = spellName.get()
        val rangeValue = range.get()
        val castingTimeValue = castingTime.get()
        val descriptionValue = description.get()
        val ingredientsValue = ingredients.get()
        val schoolValue = school.get()
        val durationValue = duration.get()
        val componentsValue = component.get()
        val levelValue = level.get()
        val isRitualValue = isRitual.get()
        val isConcentrationValue = isConcentration.get()

        val userHome = System.getProperty("user.home")
        val documentsPath = "$userHome/Documents"
        val fileHandler = FileHandler(
            Spell::class.java, "$documentsPath/VTT/library/data/spells"
        )


    }
}