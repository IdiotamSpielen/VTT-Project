package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object ImageAssetsTable : IntIdTable() {
    val path = text("path")
    val name = varchar("name", 255)
    val type = varchar("type", 50)
    val lastAccessed = long("last_accessed").default(0)
}
