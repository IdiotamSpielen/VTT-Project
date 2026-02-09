package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodels.ItemCreationViewModel
import viewmodels.MainViewModel
import viewmodels.SpellCreationViewModel
import viewmodels.SpellSearchViewModel
import viewmodels.TableTopViewModel
import ui.components.OverlayWindow
import ui.components.TableTopView

/**
 * The MainView
 */
@Composable
fun MainView(viewmodel: MainViewModel, tableTopViewmodel: TableTopViewModel) {
    val spellCreationViewmodel = remember { SpellCreationViewModel() }
    val itemCreationViewmodel = remember { ItemCreationViewModel() }
    val spellSearchViewmodel = remember { SpellSearchViewModel() }

    // 1. Hintergrund abdunkeln (macht es visuell hochwertig)
    Box(modifier = Modifier.fillMaxSize()) {

        TableTopView(tableTopViewmodel)

        // Die Overlay-Logik
        when (viewmodel.currentScreen) {
            MainViewModel.Screen.SPELL_CREATOR -> {
                OverlayWindow(
                    title = "Create New Spell",
                    onClose = { viewmodel.closeOverlay() },
                    actions = {
                        // This is a RowScope, so you can use weight, etc.
                        Button(
                            onClick = { spellCreationViewmodel.createSpell() },
                            modifier = Modifier.weight(1f)
                        ) { Text("Save") }

                        Spacer(Modifier.width(8.dp))

                        Button(
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        ) { Text("Reset") }
                    }
                ) {
                    SpellCreatorScreen(spellCreationViewmodel)
                }
            }
            MainViewModel.Screen.SPELL_SEARCH -> {
                OverlayWindow(
                    title = "Search Spells",
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellSearchScreen(spellSearchViewmodel)
                }
            }
            MainViewModel.Screen.TABLETOP -> {
                // Kein Overlay anzeigen
            }
            MainViewModel.Screen.ITEM_CREATOR -> {
                OverlayWindow(
                    title = "Create New Item", // Oder L.TITLE_CREATE_ITEM.t()
                    onClose = { viewmodel.closeOverlay() },
                ) {
                    ItemCreatorView(itemCreationViewmodel)
                }
            }
        }
    }
}