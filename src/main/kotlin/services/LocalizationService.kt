package services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale
import java.util.ResourceBundle

object LocalizationService {

    // Triggers UI re-render when the locale is changed
    var currentLocale: Locale by mutableStateOf(Locale.US)

    // Retrieves the ResourceBundle corresponding to the current locale
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
            args.forEach { (placeholder, value) ->
                text = text.replace("{$placeholder}", value)
            }
            text
        } catch (e: Exception) {
            "MISSING: $key" // Return fallback string if key is not found
        }
    }
}