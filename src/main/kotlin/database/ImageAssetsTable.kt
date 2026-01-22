package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object ImageAssetsTable : IntIdTable() {
    // Der Dateipfad ist das Wichtigste
    val path = text("path")
    // Der Name (z.B. "Goblin Warrior")
    val name = varchar("name", 255)
    // Map oder Token?
    val type = varchar("type", 50)
    val lastAccessed = long("last_accessed").default(0)
}
