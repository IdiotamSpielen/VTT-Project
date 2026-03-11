package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.*

class MainViewmodel {
    enum class Screen {
        TABLETOP,
        SPELL_CREATOR,
        SPELL_SEARCH,
        ITEM_CREATOR
    }

    var currentScreen by mutableStateOf(Screen.TABLETOP)
        private set

    var currentLocale by mutableStateOf(Locale.of("de", "DE"))
        private set

    var strings by mutableStateOf(ResourceBundle.getBundle("strings", currentLocale))


    fun setLanguage(langCode: String) {
        val parts = langCode.split("_")

        currentLocale = if (parts.size >= 2) {
            Locale.of(parts[0], parts[1])
        } else {
            Locale.of(parts[0])
        }

        strings = ResourceBundle.getBundle("strings", currentLocale)

    }

    fun openSpellCreator() {
        currentScreen = Screen.SPELL_CREATOR
    }

    fun openSpellSearch() {
        currentScreen = Screen.SPELL_SEARCH
    }

    fun openItemCreator() {
        currentScreen = Screen.ITEM_CREATOR
    }

    fun closeOverlay() {
        currentScreen = Screen.TABLETOP
    }

    /**
     * Resets the application state to the default tabletop view.
     */
    fun clearAll() {
        currentScreen = Screen.TABLETOP
    }
}