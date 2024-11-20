package idiotamspielen.vttproject.creators

import idiotamspielen.vttproject.classifications.SpellV2
import idiotamspielen.vttproject.handlers.FileHandlerV2

class SpellCreatorV2(private val fileHandler: FileHandlerV2<SpellV2>) {
    private var spell: SpellV2? = null

    fun create(spellName: String, range: String, castingTime: String, description: String, ingredients: String,
               school: String, duration: String, ritual: Boolean, concentration: Boolean, components: String, levelString: String) {
        if (validInputs(spellName, duration, description, levelString, components)) {
            val level = levelString.toInt()
            spell = SpellV2(spellName, school, duration, components, level, range, castingTime, description,
                ingredients, ritual, concentration)
            fileHandler.saveToFile(spell)
        }
    }

    fun validInputs(spellName: String, duration: String, description: String, levelString: String, components: String): Boolean {
        if (spellName.isEmpty()) {
            println("Invalid spell name")
            return false
        }
        if (duration.isEmpty()) {
            println("Invalid duration")
            return false
        }
        if (components.isEmpty()) {
            println("Invalid components")
            return false
        }
        if (description.isEmpty()) {
            println("Invalid description")
            return false
        }
        if (levelString.isEmpty() || levelString.toIntOrNull() == null || levelString.toInt() !in 0..9) { //toIntOrNull might not be necessary anymore. I'll keep it in just to be safe for now
            println("Invalid level value: $levelString")
            return false
        }
        return true
    }

    fun isSpellValid(): Boolean {
        return spell != null
    }

    fun isSpellSaved(): Boolean {
        return fileHandler.isSaved()
    }
}