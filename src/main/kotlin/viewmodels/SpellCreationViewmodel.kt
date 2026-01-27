package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import exceptions.InvalidSpellException
import exceptions.SpellNotSavedException
import models.Spell
import services.FeedbackHandler
import services.FeedbackHandler.FeedbackType.*
import services.SpellHandler
import utils.L

data class SpellUiState(
    val name: String = "",
    val castingTime: String = "",
    val range: String = "",
    val components: String = "",
    val duration: String = "",
    val ingredients: String = "",
    val description: String = "",
    val level: String = "", // String für Eingabe, Int erst beim Speichern
    val school: String = "",
    val isRitual: Boolean = false,
    val isConcentration: Boolean = false
)


/**
 * Viewmodel responsible for creating spells.
 *
 * This class provides functionality for setting spell properties and
 * creating a spell using the [SpellHandler] class. It also ensures
 * that the created spell is valid and saved properly.
 */
class SpellCreationViewmodel {
    private val spellHandler = SpellHandler()
    val feedbackHandler = FeedbackHandler()
    var uiState by mutableStateOf(SpellUiState())
    private set

    fun onNameChange(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    fun createSpell() {
        try {
            val currentState = uiState
            val spell = Spell(
                name = currentState.name,
                school = currentState.school,
                duration = currentState.duration,
                components = currentState.components,
                level = currentState.level.toIntOrNull() ?: -1,
                range = currentState.range,
                castingTime = currentState.castingTime,
                description = currentState.description,
                ingredients = currentState.ingredients,
                ritual = currentState.isRitual,
                concentration = currentState.isConcentration
            )

            spellHandler.createAndSaveSpell(spell)
            feedbackHandler.displayFeedback(
                L.SUCCESS.t(mapOf("spellName" to currentState.name)), SUCCESS)
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

    fun updateState(newState: SpellUiState) {
        uiState = newState
    }

    fun clear(){
        uiState = SpellUiState()
    }
}
