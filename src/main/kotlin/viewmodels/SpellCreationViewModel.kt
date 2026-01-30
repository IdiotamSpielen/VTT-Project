package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import domain.DnD5eSpellConfig
import domain.LibraryEntry
import exceptions.InvalidSpellException
import domain.Spell
import repositories.LibraryRepository
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
 * creating a spell using a [SpellRepository]. It also ensures
 * that the created spell is valid and saved properly.
 */
class SpellCreationViewmodel(
    private val repository: LibraryRepository = LibraryRepository(),
    val feedbackHandler : FeedbackHandler = FeedbackHandler()
) {
    var uiState by mutableStateOf(SpellUiState())
    private set

    fun onNameChange(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    fun createSpell() {
        try {
            val currentState = uiState
            validateInput(currentState)

            val spellConfig = DnD5eSpellConfig(
                level = currentState.level.toInt(),
                school = currentState.school,
                components = currentState.components,
                castingTime = currentState.castingTime,
                range = currentState.range,
                duration = currentState.duration,
                ingredients = currentState.ingredients.takeIf { it.isNotBlank() },
                description = currentState.description, // Falls description in Config gehört
                isRitual = currentState.isRitual,
                isConcentration = currentState.isConcentration
            )

            val entry = LibraryEntry(
                id = 0, // 0 für neue Einträge (Auto-Increment)
                name = currentState.name,
                type = "spell", // Der Diskriminator
                systemId = "dnd5e",
                config = spellConfig
            )

            repository.save(entry)
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
