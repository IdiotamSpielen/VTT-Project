package services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale
import java.util.ResourceBundle

object LocalizationService {

    // Der "State". Wenn sich das hier ändert, rendert Compose neu.
    var currentLocale: Locale by mutableStateOf(Locale.US)

    // Lädt das passende Bundle basierend auf dem State
    // "strings" ist der Name der Datei im resources-Ordner (ohne _de_DE etc.)
    private val bundle: ResourceBundle
        get() = ResourceBundle.getBundle("strings", currentLocale)

    /**
     * Fetches a string from a translation file.
     * @param key The key of the called string
     * @param args (Optional) Placeholder-Map
     */
    fun getString(key: String, args: Map<String, String> = emptyMap()): String {
        return try {
            var text = bundle.getString(key)
            // Ersetzt Platzhalter {key} durch value
            args.forEach { (placeholder, value) ->
                text = text.replace("{$placeholder}", value)
            }
            text
        } catch (e: Exception) {
            "MISSING: $key" // Fallback, falls Schlüssel fehlt
        }
    }
}