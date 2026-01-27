package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import exceptions.InvalidSpellException
import models.Spell
import repositories.SpellRepository
import services.FeedbackHandler
import services.FeedbackHandler.FeedbackType.*
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
    private val repository = SpellRepository()
    val feedbackHandler = FeedbackHandler()
    var uiState by mutableStateOf(SpellUiState())
    private set

    fun onNameChange(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    fun createSpell() {
        try {
            val currentState = uiState
            validateInput(currentState)

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

            repository.save(spell)

            feedbackHandler.displayFeedback(
                L.SUCCESS.t(mapOf("spellName" to currentState.name)), SUCCESS)
            clear()
        } catch (e: InvalidSpellException){
            feedbackHandler.displayFeedback(e.errorKey.t(), ERROR)
        } catch (e: Exception) {
            feedbackHandler.displayFeedback(L.ERR_GENERIC_INVALID.t(), ERROR)
            e.printStackTrace()
        }
    }

    private fun validateInput(state: SpellUiState) {
        val levelInt = state.level.toIntOrNull()

        val validationRules = listOf(
            state.name.isBlank() to L.ERR_NAME_EMPTY,
            state.castingTime.isBlank() to L.ERR_CASTING_EMPTY,
            state.duration.isBlank() to L.ERR_DURATION_EMPTY,
            state.range.isBlank() to L.ERR_RANGE_EMPTY,
            state.components.isBlank() to L.ERR_COMP_EMPTY,
            state.description.isBlank() to L.ERR_DESC_EMPTY,
            // Hier fangen wir ab, wenn Level gar keine Zahl ist oder falsch
            (levelInt == null || levelInt !in 0..9) to L.ERR_LEVEL_RANGE,
            state.school.isBlank() to L.ERR_SCHOOL_EMPTY,
        )

        validationRules.find { it.first }?.let {
            throw InvalidSpellException(it.second)
        }
    }

    fun updateState(newState: SpellUiState) {
        uiState = newState
    }

    fun clear(){
        uiState = SpellUiState()
    }
}
