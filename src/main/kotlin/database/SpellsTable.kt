package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object SpellsTable : IntIdTable() {
    val name = varchar("name", 255).uniqueIndex()
    val duration = varchar("duration", 255)
    val components = varchar("components", 255)
    val ingredients = varchar("ingredients", 255)
    val description = varchar("description", 255)
    val school = varchar("school", 255)
    val level = integer("level", "BETWEEN 0 AND 9").default(0)
    val range = varchar("range", 255)
    val castingTime = varchar("castingTime", 255)
    val ritual = bool("ritual").default(false)
    val concentration = bool("concentration").default(false)
}