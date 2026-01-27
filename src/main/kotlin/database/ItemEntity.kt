package database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class ItemEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemEntity>(ItemsTable)
    var name by ItemsTable.name
    var type by ItemsTable.type
    var value by ItemsTable.value
    var weight by ItemsTable.weight
    var description by ItemsTable.description
    var damage by ItemsTable.damage
}