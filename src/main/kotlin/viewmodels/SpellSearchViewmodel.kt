package viewmodels

import repositories.SpellRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import repositories.Spell

class SpellSearchViewmodel {
    private val repository = SpellRepository()
    var searchInput by mutableStateOf("")
    var searchResults = mutableStateListOf<Spell>()

    var selectedSpell by mutableStateOf<Spell?>(null)

    fun handleSearch() {
        CoroutineScope(Dispatchers.IO).launch {
            val results = if (searchInput.isBlank()) {
                repository.getAll()
            } else {
                repository.search(searchInput)
            }

            withContext(Dispatchers.Main) {
                searchResults.clear()
                searchResults.addAll(results)
                selectedSpell = null
            }
        }
    }

    fun selectSpell(spell: Spell) {
        selectedSpell = spell
    }

    fun clearSelection() {
        selectedSpell = null
    }

    fun clearAll(){
        searchInput = ""
        searchResults.clear()
    }
}
