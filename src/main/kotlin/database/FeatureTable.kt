package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object FeatureTable : IntIdTable() {
    val name = varchar("name", 255)
    val description = text("description")
    val systemId = varchar("system_id", 50) // "dnd5e"

    val type = varchar("type", 50).nullable()
}