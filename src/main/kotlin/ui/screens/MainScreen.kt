package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ui.TableTopView
import ui.components.OverlayWindow
import viewmodels.*

/**
 * The MainScreen
 */
@Composable
fun MainScreen(viewmodel: MainViewmodel, tableTopViewmodel: TableTopViewmodel) {
    val spellCreationViewmodel = remember { SpellCreationViewmodel() }
    val itemCreationViewmodel = remember { ItemCreationViewmodel() }
    val spellSearchViewmodel = remember { SpellSearchViewmodel() }

    // 1. Hintergrund abdunkeln (macht es visuell hochwertig)
    Box(modifier = Modifier.fillMaxSize()) {

        TableTopView(tableTopViewmodel)

        // Die Overlay-Logik
        when (viewmodel.currentScreen) {
            MainViewmodel.Screen.SPELL_CREATOR -> {
                OverlayWindow(
                    title = "Create New Spell",
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellCreatorView(spellCreationViewmodel)
                }
            }
            MainViewmodel.Screen.SPELL_SEARCH -> {
                OverlayWindow(
                    title = "Search Spells",
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellSearchView(spellSearchViewmodel)
                }
            }
            MainViewmodel.Screen.TABLETOP -> {
                // Kein Overlay anzeigen
            }
            MainViewmodel.Screen.ITEM_CREATOR -> {
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