package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object MapsTable : IntIdTable() {
    val name = varchar("name", 255)
    val path = text("path") // Path to the map metadata file (e.g., .ora or .json)
    val lastModified = long("last_modified").default(0)
    val thumbnailPath = text("thumbnail_path").nullable()
}
