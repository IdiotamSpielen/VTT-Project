package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import repositories.Item
import repositories.ItemType
import services.FeedbackHandler
import services.FeedbackHandler.FeedbackType.*
import repositories.ItemRepository
import utils.L

class ItemCreationViewmodel {

    // Dependencies
    private val repository = ItemRepository()
    val feedbackHandler = FeedbackHandler() // Public for the view

    // State
    var name by mutableStateOf("")
    var selectedType by mutableStateOf<ItemType?>(null)
    var description by mutableStateOf("")
    var damage by mutableStateOf("")

    fun createAndSaveItem(onSuccess: () -> Unit) {
        try {
            // 1. Validation
            if (name.isBlank()) {
                feedbackHandler.displayFeedback(L.ERR_NAME_EMPTY.t(), ERROR)
                return
            }
            if (selectedType == null) {
                feedbackHandler.displayFeedback("Please select a type", ERROR)
                return
            }

            // 2. Model Creation
            val newItem = Item(
                name = name,
                type = selectedType!!,
                description = description,
                // Logic: Damage is only stored if it's a weapon
                damage = if (selectedType == ItemType.WEAPON) damage else null
            )

            // 3. Save to DB
            repository.save(newItem)

            // 4. Feedback
            feedbackHandler.displayFeedback(L.SUCCESS.t(mapOf("name" to name)), SUCCESS)

            onSuccess() // Close window or perform next action
            clear() // Clear form for next item

        } catch (e: Exception) {
            e.printStackTrace()
            feedbackHandler.displayFeedback(L.ERR_GENERIC_INVALID.t(), ERROR)
        }
    }

    fun clear() {
        name = ""
        selectedType = null
        description = ""
        damage = ""
    }
}