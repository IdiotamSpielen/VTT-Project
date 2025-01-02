package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.classifications.Item
import idiotamspielen.vttproject.handlers.FileHandler
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import tornadofx.information
import tornadofx.value

class ItemCreationController : Controller(){
    internal val itemName = SimpleStringProperty()
    internal val selectedType = SimpleIntegerProperty()
    internal val itemDescription = SimpleStringProperty()
    internal val fileHandler = FileHandler(Item::class.java, "Items")

    internal fun createNewItem() {
        // Validierung und Erstellung des neuen Items
        val name = itemName.value
        val type = selectedType.value
        val description = itemDescription.value.orEmpty()
    }
}