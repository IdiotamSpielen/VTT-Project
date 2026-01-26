package database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class SpellEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SpellEntity>(SpellsTable)

    var name by SpellsTable.name
    var duration by SpellsTable.duration
    var components by SpellsTable.components
    var ingredients by SpellsTable.ingredients
    var description by SpellsTable.description
    var school by SpellsTable.school
    var level by SpellsTable.level
    var range by SpellsTable.range
    var castingTime by SpellsTable.castingTime
    var ritual by SpellsTable.ritual
    var concentration by SpellsTable.concentration
}