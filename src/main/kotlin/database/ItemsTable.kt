package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import repositories.ItemType

object ItemsTable : IntIdTable() {
    val name = varchar("name", 255)
    val type = enumerationByName("type", 50, ItemType::class)
    val value = long("value").default(0)
    val weight = double("weight").default(0.0)
    val description = text("description")
    val damage = varchar("damage", 50).nullable()
    val lastAccessed = long("last_accessed").default(0)
}