package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewModels.ItemCreationViewmodel
import models.ItemType
import utils.L

@Composable
fun ItemCreatorView(controller: ItemCreationViewmodel, onClose: () -> Unit) {
    val spacing = 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = controller.name,
            onValueChange = { controller.name = it },
            label = { Text(L.ITEM_NAME.t()) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Custom ComboBox implementation using Box and DropdownMenu
        ItemTypeDropdown(
            selectedType = controller.selectedType,
            onTypeSelected = { controller.selectedType = it }
        )

        // Only display damage field for weapons
        if (controller.selectedType == ItemType.WEAPON) {
            OutlinedTextField(
                value = controller.damage,
                onValueChange = { controller.damage = it },
                label = { Text(L.ITEM_DAMAGE.t()) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        OutlinedTextField(
            value = controller.description,
            onValueChange = { controller.description = it },
            label = { Text(L.ITEM_DESC.t()) },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            maxLines = 5
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = spacing),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onClose) {
                Text(L.BTN_CANCEL.t())
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    controller.createAndSaveItem(onSuccess = {
                        controller.clear()
                        onClose()
                    })
                },
                enabled = controller.name.isNotBlank() && controller.selectedType != null
            ) {
                Text(L.BTN_SAVE.t())
            }
        }
    }
}

/**
 * Composable for item type selection
 */
@Composable
fun ItemTypeDropdown(
    selectedType: ItemType?,
    onTypeSelected: (ItemType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedType?.toString() ?: "",
            onValueChange = {},
            readOnly = true, // Prevent direct text input
            label = { Text(L.ITEM_TYPE.t()) },
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, "Select", Modifier.clickable { expanded = !expanded })
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Overlay Box to capture clicks and toggle the menu
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f) // Keep menu width consistent with the anchor box
        ) {
            ItemType.values().forEach { type ->
                DropdownMenuItem(onClick = {
                    onTypeSelected(type)
                    expanded = false
                }) {
                    Text(text = type.toString())
                }
            }
        }
    }
}