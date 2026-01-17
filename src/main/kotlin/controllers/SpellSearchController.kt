package controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.Spell
import services.FileHandler

class SpellSearchController {
    var searchInput by mutableStateOf("")
    var spellList = mutableStateListOf<String>()
    
    var spellName by mutableStateOf("")
    var range by mutableStateOf("")
    var castingTime by mutableStateOf("")
    var components by mutableStateOf("")
    var duration by mutableStateOf("")
    var ingredients by mutableStateOf("")
    var descArea by mutableStateOf("")
    var level by mutableStateOf("")
    var school by mutableStateOf("")
    var ritual by mutableStateOf("")
    var concentration by mutableStateOf("")

    private val spellMap: MutableMap<String, Spell> = mutableMapOf()
    private val fileHandler = FileHandler(Spell::class.java, "spells")

    fun handleSearch() {
        val query = searchInput
        println("Searching for $query in spells")
        val spells = fileHandler.search(query)
        if (spells.isEmpty()) {
            println("No spells found for query: $query")
        } else {
            println("Found ${spells.size} spells for query: $query")
        }
        displaySpells(spells)
    }

    private fun displaySpells(spells: List<Spell>) {
        spellList.clear()
        spellMap.clear()
        for (spell in spells) {
            spellList.add(spell.name)
            spellMap[spell.name] = spell
        }
        // Clear selection when searching
        spellName = ""
    }

    fun handleSpellSelection(selectedSpellName: String?) {
        val selectedSpell = spellMap[selectedSpellName]
        if (selectedSpell != null) {
            spellName = selectedSpell.name
            range = selectedSpell.range
            castingTime = selectedSpell.castingTime
            components = selectedSpell.components
            duration = selectedSpell.duration
            ingredients = selectedSpell.ingredients
            descArea = selectedSpell.description
            level = selectedSpell.level.toString()
            school = selectedSpell.school
            ritual = if (selectedSpell.ritual) "ritual" else ""
            concentration = if (selectedSpell.concentration) "concentration" else ""
        }
    }
}
