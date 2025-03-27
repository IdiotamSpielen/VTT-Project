package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.models.Spell
import idiotamspielen.vttproject.services.FileHandler
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class SpellSearchController : Controller() {

    val searchInput = SimpleStringProperty()
    val spellName = SimpleStringProperty()
    val range = SimpleStringProperty()
    val castingTime = SimpleStringProperty()
    val components = SimpleStringProperty()
    val duration = SimpleStringProperty()
    val ingredients = SimpleStringProperty()
    val descArea = SimpleStringProperty()
    val level = SimpleStringProperty()
    val school = SimpleStringProperty()
    val ritual = SimpleStringProperty()
    val concentration = SimpleStringProperty()

    val spellList: ObservableList<String> = FXCollections.observableArrayList()
    private val spellMap: MutableMap<String, Spell> = mutableMapOf()
    private val fileHandler = FileHandler(Spell::class.java, "spells")

    fun handleSearch() {
        val query = searchInput.get() ?: ""
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
    }

    fun handleSpellSelection(selectedSpellName: String?) {
        val selectedSpell = spellMap[selectedSpellName]
        if (selectedSpell != null) {
            spellName.set(selectedSpell.name)
            range.set(selectedSpell.range)
            castingTime.set(selectedSpell.castingTime)
            components.set(selectedSpell.components)
            duration.set(selectedSpell.duration)
            ingredients.set(selectedSpell.ingredients)
            descArea.set(selectedSpell.description)
            level.set(selectedSpell.level.toString())
            school.set(selectedSpell.school)
            ritual.set(if (selectedSpell.ritual) "ritual" else "")
            concentration.set(if (selectedSpell.concentration) "concentration" else "")
        }
    }
}
