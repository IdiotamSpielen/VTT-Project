package controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.Item
import models.ItemType
import services.FeedbackHandler
import services.FileHandler
import utils.L

class ItemCreationController {
    // State für die UI-Felder
    var name by mutableStateOf("")
    var selectedType by mutableStateOf<ItemType?>(null) // Nullable, da am Anfang nichts gewählt ist
    var description by mutableStateOf("")
    var damage by mutableStateOf("")

    // Services
    private val fileHandler = FileHandler(Item::class.java, "items")
    private val feedbackHandler = FeedbackHandler() // Annahme: Du hast den als Service oder Singleton

    fun createAndSaveItem(onSuccess: () -> Unit) {
        try {
            // 1. Validierung
            if (name.isBlank()) throw IllegalArgumentException(L.ERR_NAME_EMPTY.t())
            if (selectedType == null) throw IllegalArgumentException("Type must be selected") // TODO: Add L.ERR_TYPE_EMPTY

            // 2. Erstellung
            val newItem = Item(
                name = name,
                type = selectedType!!,
                description = description,
                // Nur speichern, wenn es eine Waffe ist, sonst null
                damage = if (selectedType == ItemType.WEAPON) damage else null
            )

            // 3. Speichern
            fileHandler.saveToFile(newItem)

            // 4. Feedback & Schließen
            feedbackHandler.displayFeedback(L.SUCCESS.t(mapOf("name" to name)), FeedbackHandler.FeedbackType.SUCCESS)
            onSuccess() // Callback zum Schließen des Fensters

        } catch (e: Exception) {
            feedbackHandler.displayFeedback(e.message ?: L.ERR_UNEXPECTED.t(), FeedbackHandler.FeedbackType.ERROR)
        }
    }

    fun clear() {
        name = ""
        selectedType = null
        description = ""
        damage = ""
    }
}