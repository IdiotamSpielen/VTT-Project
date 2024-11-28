package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.classifications.Spell
import idiotamspielen.vttproject.creators.SpellCreator
import idiotamspielen.vttproject.handlers.FileHandler
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

    /**
     * Creates a new spell using the properties provided in the SpellCreationController and saves it to the file system.
     *
     * This method constructs a new instance of `SpellCreator` using a `FileHandler` for managing spell files.
     * It compiles all the spell properties from the controller into a `SpellData` object and utilizes the `SpellCreator`
     * to create and save the spell. The method ensures the spell is valid and successfully saved; otherwise, it throws
     * appropriate exceptions.
     *
     * @throws InvalidSpellException if the spell is determined to be invalid.
     * @throws SpellNotSavedException if the spell could not be saved to the file system.
     */
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

    /**
     * Returns the value of this SimpleStringProperty if it is non-null; otherwise, returns the specified default value.
     *
     * @param default The default value to return if the property's value is null.
     * @return The value of this SimpleStringProperty if it is non-null, otherwise the default value.
     */
    private fun SimpleStringProperty.getOrElse(default : String): String{
        return this.get() ?: default
    }
}

/**
 * Exception thrown to indicate an invalid spell during creation or validation.
 *
 * This exception is typically used in the context of spell creation processes
 * within the application, particularly when the provided spell data does not meet
 * the necessary validation criteria. It signifies that the spell being processed
 * is defective or non-conforming to expected standards.
 *
 * @param message Detailed message describing the reason for the exception.
 */
class InvalidSpellException(message: String) : Exception(message)

/**
 * Exception thrown when a spell could not be saved to the file system.
 *
 * This exception is used in scenarios where a save operation for a spell
 * fails, providing an appropriate error message for debugging or user notification.
 *
 * @param message Detailed message explaining the reason for the failure.
 */
class SpellNotSavedException(message: String) : Exception(message)