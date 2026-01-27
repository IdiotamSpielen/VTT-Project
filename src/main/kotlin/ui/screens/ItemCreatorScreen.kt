package ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodels.ItemCreationViewmodel
import models.ItemType
import utils.L

@Composable
fun ItemCreatorView(viewModel: ItemCreationViewmodel, onClose: () -> Unit) {
    // Styling Konstanten
    val spacing = 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // --- NAME ---
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text(L.ITEM_NAME.t()) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // --- TYPE DROPDOWN ---
        // Compose hat kein einfaches "ComboBox" Widget, man baut es aus Box + DropdownMenu
        ItemTypeDropdown(
            selectedType = viewModel.selectedType,
            onTypeSelected = { viewModel.selectedType = it }
        )

        // --- CONDITIONAL FIELD: DAMAGE ---
        // Das ist der Ersatz für "visibleWhen". Einfach ein if!
        // Wird nur gerendert, wenn der Typ WEAPON ist.
        if (viewModel.selectedType == ItemType.WEAPON) {
            OutlinedTextField(
                value = viewModel.damage,
                onValueChange = { viewModel.damage = it },
                label = { Text(L.ITEM_DAMAGE.t()) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        // --- DESCRIPTION ---
        OutlinedTextField(
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
            label = { Text(L.ITEM_DESC.t()) },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            maxLines = 5
        )

        // --- BUTTONS ---
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
                    viewModel.createAndSaveItem(onSuccess = {
                        viewModel.clear()
                        onClose()
                    })
                },
                // Button ist disabled, wenn Name leer oder Typ fehlt
                enabled = viewModel.name.isNotBlank() && viewModel.selectedType != null
            ) {
                Text(L.BTN_SAVE.t())
            }
        }
    }
}

/**
 * Hilfs-Composable für das Dropdown, um den Hauptcode sauber zu halten
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
            readOnly = true, // Man kann nicht tippen, nur auswählen
            label = { Text(L.ITEM_TYPE.t()) },
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, "Select", Modifier.clickable { expanded = !expanded })
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Eine unsichtbare Box über dem Textfeld fängt Klicks ab, um das Menü zu öffnen
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f) // Breite relativ zur Box anpassen
        ) {
            ItemType.entries.forEach { type ->
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