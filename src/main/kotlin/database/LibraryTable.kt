package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object LibraryTable : IntIdTable() {

    // 2. Metadaten (für die Suche)
    val name = varchar("name", 255).index()
    val type = varchar("type", 50).index()
    val systemId = varchar("system_id", 50)

    val jsonConfig = text("config")
}