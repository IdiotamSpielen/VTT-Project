package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object ItemsTable : IntIdTable() {
    val name = varchar("name", 255)
    val type = varchar("type", 50)
    val value = long("value").default(0)
    val weight = double("weight").default(0.0)
    val description = text("description")
    val damage = varchar("damage", 50).nullable()
}