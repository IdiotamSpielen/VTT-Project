package database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class LibraryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LibraryEntity>(LibraryTable)

    var name by LibraryTable.name
    var type by LibraryTable.type
    var systemId by LibraryTable.systemId
    var jsonConfig by LibraryTable.jsonConfig
}