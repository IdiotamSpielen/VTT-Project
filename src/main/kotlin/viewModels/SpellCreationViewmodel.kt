package viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import exceptions.InvalidSpellException
import exceptions.SpellNotSavedException
import models.Spell
import services.FeedbackHandler
import services.FeedbackHandler.FeedbackType.ERROR
import services.FeedbackHandler.FeedbackType.SUCCESS
import services.SpellHandler
import utils.L

//
///**
// * Controller class responsible for creating spells.
// *
// * This class provides functionality for setting spell properties and
// * creating a spell using the `SpellHandler` class. It also ensures
// * that the created spell is valid and saved properly.
// */
class SpellCreationViewmodel {
    private val spellHandler = SpellHandler()
    val feedbackHandler = FeedbackHandler()

    var spellName by mutableStateOf("")
    var castingTime by mutableStateOf("")
    var range by mutableStateOf("")
    var component by mutableStateOf("")
    var duration by mutableStateOf("")
    var ingredients by mutableStateOf("")
    var description by mutableStateOf("")
    var level by mutableStateOf("")
    var school by mutableStateOf("")

    var isRitual by mutableStateOf(false)
    var isConcentration by mutableStateOf(false)

    fun createSpell() {
        try {
            val spell = Spell(
                name = spellName,
                school = school,
                duration = duration,
                components = component,
                level = level.toIntOrNull() ?: -1,
                range = range,
                castingTime = castingTime,
                description = description,
                ingredients = ingredients,
                ritual = isRitual,
                concentration = isConcentration
            )

            spellHandler.createAndSaveSpell(spell)
            feedbackHandler.displayFeedback(
                L.SUCCESS.t(mapOf("spellName" to spellName)), SUCCESS)
            clear()
        }catch (e: InvalidSpellException){
            feedbackHandler.displayFeedback(e.errorKey.t(), ERROR)
        } catch (e: SpellNotSavedException) {
            feedbackHandler.displayFeedback(e.errorKey.t(), ERROR)
        } catch (e: Exception) {
            feedbackHandler.displayFeedback(L.ERR_GENERIC_INVALID.t(), ERROR)
            e.printStackTrace()
        }
    }

    fun clear(){
        spellName = ""
        castingTime = ""
        range = ""
        component = ""
        duration = ""
        ingredients = ""
        description = ""
        level = ""
        school = ""
        isRitual = false
        isConcentration = false
    }
}
