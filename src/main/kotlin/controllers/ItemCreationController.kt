package controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.Item
import models.ItemType
import services.FeedbackHandler
import services.FileHandler
import utils.L

//import models.Item
//import services.FileHandler
//
class ItemCreationController{
    var name by mutableStateOf("")
    var selectedType by mutableStateOf<ItemType?>(null)
    var description by mutableStateOf("")
    var damage by mutableStateOf("")
}
//    internal val itemName = SimpleStringProperty()
//    internal val selectedType = SimpleIntegerProperty()
//    internal val itemDescription = SimpleStringProperty()
//    internal val fileHandler = FileHandler(Item::class.java, "Items")
//
//    internal fun createNewItem() {
//        // Validierung und Erstellung des neuen Items
//        val name = itemName.value
//        val type = selectedType.value
//        val description = itemDescription.value.orEmpty()
//    }
//}