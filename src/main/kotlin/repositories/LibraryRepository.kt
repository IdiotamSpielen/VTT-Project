package repositories


import database.LibraryTable
import kotlinx.serialization.encodeToString
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlinx.serialization.json.Json
import domain.LibraryEntry
import domain.LibraryConfig
import database.LibraryEntity

class LibraryRepository {

    private val json = Json { ignoreUnknownKeys = true }

    fun save(entry: LibraryEntry) {
        transaction {
            val configRaw = json.encodeToString(entry.config)
            val existing = LibraryEntity.find {
                (LibraryTable.name eq entry.name) and (LibraryTable.systemId eq entry.systemId)
            }.firstOrNull()

            if (existing == null) {
                LibraryEntity.new {
                    name = entry.name
                    type = entry.type
                    systemId = entry.systemId
                    jsonConfig = configRaw
                }
            } else {
                existing.apply {
                    type = entry.type
                    systemId = entry.systemId
                    jsonConfig = configRaw
                }
            }
        }
    }

    fun search(query: String, systemId: String? = null): List<LibraryEntry> {
        return transaction {
            val conditions = mutableListOf<Op<Boolean>>()
            conditions.add(LibraryTable.name.lowerCase() like "%${query.lowercase()}%")

            if (systemId != null) {
                conditions.add(LibraryTable.systemId eq systemId)
            }

            val combinedCondition = conditions.reduce { acc, op -> acc and op }

            LibraryEntity.find { combinedCondition }
                .orderBy(LibraryTable.name to SortOrder.ASC)
                .map { rowToModel(it) }
        }
    }

    fun delete(id: Int) {
        transaction {
            LibraryTable.deleteWhere { LibraryTable.id eq id }
        }
    }

    private fun rowToModel(row: LibraryEntity): LibraryEntry {
        return LibraryEntry(
            id = row.id.value,
            name = row.name,
            type = row.type,
            systemId = row.systemId,
            // Hier passiert die Magie: Der Serializer erkennt anhand des "type" Feldes im JSON,
            // welche konkrete Klasse (SpellConfig, ItemConfig...) gebaut werden muss.
            config = json.decodeFromString<LibraryConfig>(row.jsonConfig)
        )
    }
}