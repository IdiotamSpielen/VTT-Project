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
    // UI state for item properties
    var name by mutableStateOf("")
    var selectedType by mutableStateOf<ItemType?>(null) // No initial selection required
    var description by mutableStateOf("")
    var damage by mutableStateOf("")

    // Services
    private val fileHandler = FileHandler(Item::class.java, "items")
    private val feedbackHandler = FeedbackHandler() // Annahme: Du hast den als Service oder Singleton

    fun createAndSaveItem(onSuccess: () -> Unit) {
        try {
            if (name.isBlank()) throw IllegalArgumentException(L.ERR_NAME_EMPTY.t())
            if (selectedType == null) throw IllegalArgumentException("Type must be selected") // TODO: Add L.ERR_TYPE_EMPTY
            val newItem = Item(
                name = name,
                type = selectedType!!,
                description = description,
                damage = if (selectedType == ItemType.WEAPON) damage else null // Damage is only applicable to weapons
            )

            fileHandler.saveToFile(newItem)

            feedbackHandler.displayFeedback(L.SUCCESS.t(mapOf("name" to name)), FeedbackHandler.FeedbackType.SUCCESS)
            onSuccess() // Notify UI to close the creation window

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