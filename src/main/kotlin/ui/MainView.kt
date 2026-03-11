package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import viewModels.ItemCreationViewmodel
import viewModels.MainViewmodel
import viewModels.SpellCreationViewmodel
import viewModels.SpellSearchViewmodel
import viewModels.TableTopViewmodel

/**
 * The MainView
 */
@Composable
fun MainView(controller: MainViewmodel, tableTopViewmodel: TableTopViewmodel) {
    val spellController = remember { SpellCreationViewmodel() }
    val itemController = remember { ItemCreationViewmodel() }
    val searchController = remember { SpellSearchViewmodel() }

    // 1. Hintergrund abdunkeln (macht es visuell hochwertig)
    Box(modifier = Modifier.fillMaxSize()) {

        TableTopView(tableTopViewmodel)

        // Die Overlay-Logik
        when (controller.currentScreen) {
            MainViewmodel.Screen.SPELL_CREATOR -> {
                OverlayWindow(
                    title = "Create New Spell",
                    onClose = { controller.closeOverlay() }
                ) {
                    SpellCreatorView(spellController)
                }
            }
            MainViewmodel.Screen.SPELL_SEARCH -> {
                OverlayWindow(
                    title = "Search Spells",
                    onClose = { controller.closeOverlay() }
                ) {
                    SpellSearchView(searchController)
                }
            }
            MainViewmodel.Screen.TABLETOP -> {
                // Kein Overlay anzeigen
            }
            MainViewmodel.Screen.ITEM_CREATOR -> {
                OverlayWindow(
                    title = "Create New Item", // Oder L.TITLE_CREATE_ITEM.t()
                    onClose = { controller.closeOverlay() }
                ) {
                    ItemCreatorView(itemController, onClose = { controller.closeOverlay() })
                }
            }
        }
    }
}