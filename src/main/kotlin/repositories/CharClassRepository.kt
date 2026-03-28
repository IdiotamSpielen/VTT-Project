package repositories

import models.Ability

class CharClassRepository : Repository<CharClassModel> {

    override fun save(item: CharClassModel) {
        requireNotNull(item) { "Character class cannot be null" }
        require(item.name.isNotBlank()) { "Character class name cannot be blank" }
        require(item.description.isNotBlank()) { "Character class description cannot be blank" }
        require(item.abilities.isNotEmpty()) { "Character class must have at least one ability" }
        require(item.spells.isNotEmpty()) { "Character class must have at least one spell" }
        require(item.levelFeatures.isNotEmpty()) { "Character class must have at least one level feature" }
    }

    override fun search(query: String): List<CharClassModel> {
        return emptyList()
    }

    override fun getAll(): List<CharClassModel> {
        return emptyList()
    }

    override fun getRecent(limit: Int): List<CharClassModel> {
        return emptyList()
    }

    override fun delete(item: CharClassModel) {

    }
}

data class CharClassModel(
    val name: String,
    val description: String,
    val abilities: List<Ability>,
    val spells: List<Spell>,
    val levelFeatures: Map<Int, List<String>>
) {
    init {
        require(name.isNotBlank()) { "Character class name cannot be blank" }
        require(description.isNotBlank()) { "Character class description cannot be blank" }
        require(abilities.isNotEmpty()) { "Character class must have at least one ability" }
        require(spells.isNotEmpty()) { "Character class must have at least one spell" }
        require(levelFeatures.isNotEmpty()) { "Character class must have at least one level feature" }
    }
}