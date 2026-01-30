package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object ClassesTable : IntIdTable() {
    val name = varchar("name", 255)
    val description = text("description")
    val systemId = varchar("system_id", 50) // "dnd5e", "coc", "shadowrun"

    val progression = text("progression")
    val specificData = text("specific_data")
}