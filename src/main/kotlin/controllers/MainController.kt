package controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainController {
    enum class Screen {
        TABLETOP,
        SPELL_CREATOR,
        SPELL_SEARCH
    }

    var currentScreen by mutableStateOf(Screen.TABLETOP)
        private set

    fun openSpellCreator() {
        currentScreen = Screen.SPELL_CREATOR
    }

    fun openSpellSearch() {
        currentScreen = Screen.SPELL_SEARCH
    }

    fun closeOverlay() {
        currentScreen = Screen.TABLETOP
    }
}