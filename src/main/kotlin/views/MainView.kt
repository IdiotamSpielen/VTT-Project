package views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import controllers.MainController
import controllers.SpellCreationController
import controllers.SpellSearchController
import controllers.TableTopController

/**
 * The MainView
 */
@Composable
fun MainView(controller: MainController, tableTopController: TableTopController) {
    val spellController = remember { SpellCreationController() }
    val searchController = remember { SpellSearchController() }

    // 1. Hintergrund abdunkeln (macht es visuell hochwertig)
    Box(modifier = Modifier.fillMaxSize()) {

        TableTopView(tableTopController)

        // Die Overlay-Logik
        when (controller.currentScreen) {
            MainController.Screen.SPELL_CREATOR -> {
                OverlayWindow(
                    title = "Create New Spell",
                    onClose = { controller.closeOverlay() }
                ) {
                    SpellCreatorView(spellController)
                }
            }
            MainController.Screen.SPELL_SEARCH -> {
                OverlayWindow(
                    title = "Search Spells",
                    onClose = { controller.closeOverlay() }
                ) {
                    SpellSearchView(searchController)
                }
            }
            MainController.Screen.TABLETOP -> {
                // Kein Overlay anzeigen
            }
        }
    }
}