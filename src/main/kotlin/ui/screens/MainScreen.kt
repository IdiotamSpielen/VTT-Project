package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import viewmodels.ItemCreationViewModel
import viewmodels.MainViewModel
import viewmodels.SpellCreationViewmodel
import viewmodels.SpellSearchViewModel
import viewmodels.TableTopViewModel
import ui.components.OverlayWindow
import ui.components.TableTopView

/**
 * The MainView
 */
@Composable
fun MainView(viewmodel: MainViewModel, tableTopViewmodel: TableTopViewModel) {
    val spellCreationViewmodel = remember { SpellCreationViewmodel() }
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
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellCreatorView(spellCreationViewmodel)
                }
            }
            MainViewModel.Screen.SPELL_SEARCH -> {
                OverlayWindow(
                    title = "Search Spells",
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellSearchView(spellSearchViewmodel)
                }
            }
            MainViewModel.Screen.TABLETOP -> {
                // Kein Overlay anzeigen
            }
            MainViewModel.Screen.ITEM_CREATOR -> {
                OverlayWindow(
                    title = "Create New Item", // Oder L.TITLE_CREATE_ITEM.t()
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    ItemCreatorView(itemCreationViewmodel, onClose = { viewmodel.closeOverlay() })
                }
            }
        }
    }
}