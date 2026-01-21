package Repositories

import database.SpellEntity
import database.SpellsTable
import models.Spell
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class SpellRepository: Repository<Spell> {
    override fun save (item: Spell) {
        transaction {
            // Check: Gibt es ihn schon?
            val existingSpell = SpellEntity.find { SpellsTable.name eq item.name }.firstOrNull()

            if (existingSpell == null) {
                // Neu erstellen
                SpellEntity.new {
                    assignValues(this, item)
                }
            } else {
                // Optional: Update durchführen, falls er schon existiert
                assignValues(existingSpell, item)
            }
        }
    }

    override fun search(query: String): List<Spell> {
        return transaction {
            SpellEntity.find { SpellsTable.name like "%${query}%" }
                .map { entity -> Spell(
                    name = entity.name,
                    description = entity.description,
                    components = entity.components,
                    castingTime = entity.castingTime,
                    duration = entity.duration,
                    level = entity.level,
                    range = entity.range,
                    ingredients = entity.ingredients,
                    school = entity.school,
                    ritual = entity.ritual,
                    concentration = entity.concentration
                ) }
        }
    }

    override fun getAll(): List<Spell> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Spell) {
        transaction {
            item
        }
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
    }
}