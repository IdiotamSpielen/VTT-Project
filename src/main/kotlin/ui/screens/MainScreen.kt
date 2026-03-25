package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ui.TableTopView
import ui.components.OverlayWindow
import viewmodels.*
import utils.L

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
                    title = L.TITLE_SPELL_CREATE.t(),
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellCreatorView(spellCreationViewmodel)
                }
            }
            MainViewmodel.Screen.SPELL_SEARCH -> {
                OverlayWindow(
                    title = L.TITLE_SPELL_SEARCH.t(),
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    SpellSearchView(spellSearchViewmodel)
                }
            }
            MainViewmodel.Screen.TABLETOP -> {
            }
            MainViewmodel.Screen.ITEM_CREATOR -> {
                OverlayWindow(
                    title = L.TITLE_ITEM_CREATE.t(),
                    onClose = { viewmodel.closeOverlay() }
                ) {
                    ItemCreatorView(itemCreationViewmodel, onClose = { viewmodel.closeOverlay() })
                }
            }
        }
    }
}