package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object RacesTable : IntIdTable() {
    val name = varchar("name", 255)
    val systemId = varchar("system_id", 50)

    // JSON: { "dex": 2, "int": 1 }
    val statModifiers = text("stat_modifiers")

    // JSON Array von IDs: [12, 45, 99] -> Verweist auf Features "Darkvision", "Keen Senses", etc.
    val featureIds = text("feature_ids")
}