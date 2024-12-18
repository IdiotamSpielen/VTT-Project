package idiotamspielen.vttproject.views

import idiotamspielen.vttproject.classifications.Item
import idiotamspielen.vttproject.classifications.ItemType
import idiotamspielen.vttproject.controllers.ItemCreationController
import idiotamspielen.vttproject.handlers.FileHandler
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class ItemCreatorView : View("Create New Item") {
    private val controller: ItemCreationController by inject()

    override val root  = form {
        fieldset("Item Creation") {
            field("Name") {
                textfield(controller.itemName).required() // Name ist verpflichtend
            }

            field("Type") {
                combobox(controller.selectedType, ItemType.values().toList()) {
                    promptText = "Select an Item Type"
                }.validator {
                    if (it == null) error("You must select an item type.") else null
                }
            }

            field("Description") {
                textarea(controller.itemDescription) {
                    promptText = "Write the item description here"
                }
            }

            field("Weapon Damage") {
                textfield() {
                    visibleWhen { controller.selectedType.isEqualTo(ItemType.WEAPON) }
                    required()
                }
            }

            // Buttons
            hbox(10) {
                button("Save") {
                    enableWhen {
                        controller.itemName.isNotEmpty.and(controller.selectedType.isNotNull) // Aktiv nur bei gültigen Eingaben
                    }
                    action {
                        controller.createNewItem()
                        close()
                    }
                }
                button("Cancel") {
                    action {
                        close()
                    }
                }
            }
        }
    }
}