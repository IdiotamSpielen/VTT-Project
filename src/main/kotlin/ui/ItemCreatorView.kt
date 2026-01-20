package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import controllers.ItemCreationController

@Composable
fun ItemCreatorView(controller: ItemCreationController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create New Item", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = controller.name,
            onValueChange = { controller.name = it },
            label = {Text("Name of the item")},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )


    }
}

//import controllers.ItemCreationController
//import tornadofx.*
//
//class ItemCreatorView : View("Create New Item") {
//    private val controller: ItemCreationController by inject()
//
//    override val root  = form {
//        fieldset("Item Creation") {
//            field("Name") {
//                textfield(controller.itemName).required() // Name ist verpflichtend
//            }
//
//            field("Type") {
//                //FIXME - Figure out why this doesn't like ENUMS?
//                combobox(controller.selectedType){ //ItemType.values().toList())
//                    promptText = "Select an Item Type"
//                }.validator {
//                    if (it == null) error("You must select an item type.") else null
//                }
//            }
//
//            field("Description") {
//                textarea(controller.itemDescription) {
//                    promptText = "Write the item description here"
//                }
//            }
//
//            field("Weapon Damage") {
//                textfield() {
//                    //visibleWhen { controller.selectedType.isEqualTo(ItemType.WEAPON) } FIXME - figure this out
//                    required()
//                }
//            }
//
//            // Buttons
//            hbox(10) {
//                button("Save") {
//                    //FIXME - Do we need an isNotNull import?
//                    //enableWhen {
//                        //controller.itemName.isNotEmpty.and(controller.selectedType.isNotNull) Aktiv nur bei gültigen Eingaben
//                    //}
//                    action {
//                        controller.createNewItem()
//                        close()
//                    }
//                }
//                button("Cancel") {
//                    action {
//                        close()
//                    }
//                }
//            }
//        }
//    }
//}