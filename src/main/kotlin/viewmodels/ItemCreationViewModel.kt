package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import domain.Item
import domain.ItemType
import services.FeedbackHandler
import services.FeedbackHandler.FeedbackType.*
import repositories.ItemRepository
import utils.L

class ItemCreationViewModel {

    // Abhängigkeiten
    private val repository = ItemRepository()
    val feedbackHandler = FeedbackHandler() // Public für die View

    // State (Klassischer Ansatz ohne UiState-Klasse, wie du es hattest)
    var name by mutableStateOf("")
    var selectedType by mutableStateOf<ItemType?>(null)
    var description by mutableStateOf("")
    var damage by mutableStateOf("")

    fun createAndSaveItem(onSuccess: () -> Unit) {
        try {
            // 1. Validierung
            if (name.isBlank()) {
                feedbackHandler.displayFeedback(L.ERR_NAME_EMPTY.t(), ERROR)
                return
            }
            if (selectedType == null) {
                feedbackHandler.displayFeedback("Please select a type", ERROR)
                return
            }

            // 2. Erstellung des Models
            val newItem = Item(
                name = name,
                type = selectedType!!,
                description = description,
                // Logik: Schaden wird nur gespeichert, wenn es eine Waffe ist
                damage = if (selectedType == ItemType.WEAPON) damage else null
            )

            // 3. Speichern in DB (statt FileHandler)
            repository.save(newItem)

            // 4. Feedback
            feedbackHandler.displayFeedback(L.SUCCESS.t(mapOf("name" to name)), SUCCESS)

            // Optional: Fenster schließen oder Formular leeren
            // onSuccess() // Wenn das Fenster zugehen soll
            clear() // Wenn man gleich das nächste Item anlegen will

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
        // Feedback muss man oft nicht löschen, damit der User "Success" noch sieht
    }
}