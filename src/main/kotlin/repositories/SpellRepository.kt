package repositories

import database.SpellEntity
import database.SpellsTable
import models.Spell
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

/**
 * The Repository responsible for linking the Spell Creation process to its appropriate Database Table.
 *
 * @param Spell the spell to be saved
 */
open class SpellRepository: Repository<Spell> {
    open override fun save (item: Spell) {
        transaction {
            val existingSpell = SpellEntity.find { SpellsTable.name eq item.name }.firstOrNull()

            if (existingSpell == null) {
                SpellEntity.new {
                    assignValues(this, item)
                }
            } else {
                // Update existing record if found
                assignValues(existingSpell, item)
            }
        }
    }

    override fun search(query: String): List<Spell> {
        return transaction {
            SpellEntity.find { SpellsTable.name.lowerCase() like "%${query.lowercase()}%" }
                .map { entityToModel(it) }
        }
    }

    override fun getAll(): List<Spell> {
        return transaction {
            SpellEntity.all()
                .orderBy(SpellsTable.name to SortOrder.ASC)
                .map { entityToModel(it) }
        }
    }

    override fun getRecent(limit: Int): List<Spell> {
        return transaction {
            SpellEntity.all()
                .orderBy(SpellsTable.lastAccessed to SortOrder.DESC)
                .map { entityToModel(it) }
        }
    }

    override fun delete(item: Spell) {
        transaction {
            val spell = SpellEntity.find { SpellsTable.name.lowerCase() eq item.name.lowercase() }.firstOrNull()
            spell?.delete()
        }
    }

    private fun entityToModel(e: SpellEntity): Spell {
        return Spell(
            name = e.name,
            description = e.description,
            components = e.components,
            castingTime = e.castingTime,
            duration = e.duration,
            level = e.level,
            range = e.range,
            ingredients = e.ingredients,
            school = e.school,
            ritual = e.ritual,
            concentration = e.concentration,
            lastAccessed = e.lastAccessed
        )
    }

    private fun assignValues(entity: SpellEntity, spell: Spell) {
        entity.name = spell.name
        entity.castingTime = spell.castingTime
        entity.duration = spell.duration
        entity.range = spell.range
        entity.components = spell.components
        entity.ingredients = spell.ingredients
        entity.description = spell.description
        entity.level = spell.level
        entity.school = spell.school
        entity.ritual = spell.ritual
        entity.concentration = spell.concentration
        entity.lastAccessed = spell.lastAccessed
    }
}