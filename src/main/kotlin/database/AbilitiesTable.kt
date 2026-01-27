package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object AbilitiesTable : IntIdTable() {
    val name = varchar("name", 255)
}