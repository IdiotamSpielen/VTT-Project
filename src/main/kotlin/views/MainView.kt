package views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import controllers.SpellCreationController
import controllers.MainController

/**
 * The MainView
 */
@Composable
fun MainView(controller: MainController) {
    val spellController = remember { SpellCreationController() }

    // 1. Hintergrund abdunkeln (macht es visuell hochwertig)
    Box(modifier = Modifier.fillMaxSize()) {
        //TableTop()

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
                    // SpellSearchView(searchController) // Kommt als nächstes!
                    Text("Hier wird bald gesucht...")
                }
            }
            MainController.Screen.TABLETOP -> {
                // Kein Overlay anzeigen
            }
        }
    }
}