package repositories

import database.SpellsTable
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.core.ResultRow

/**
 * The Repository responsible for linking the Spell Creation process to its appropriate Database Table.
 *
 * @param Spell the spell to be saved
 */
open class SpellRepository : Repository<Spell> {
    open override fun save(item: Spell) {
        transaction {
            val existingCount = SpellsTable.select(SpellsTable.id).where { SpellsTable.name eq item.name }.count()

            if (existingCount == 0L) {
                SpellsTable.insert {
                    it[name] = item.name
                    it[castingTime] = item.castingTime
                    it[duration] = item.duration
                    it[range] = item.range
                    it[components] = item.components
                    it[ingredients] = item.ingredients
                    it[description] = item.description
                    it[level] = item.level
                    it[school] = item.school
                    it[ritual] = item.ritual
                    it[concentration] = item.concentration
                    it[lastAccessed] = item.lastAccessed
                }
            } else {
                SpellsTable.update({ SpellsTable.name eq item.name }) {
                    it[castingTime] = item.castingTime
                    it[duration] = item.duration
                    it[range] = item.range
                    it[components] = item.components
                    it[ingredients] = item.ingredients
                    it[description] = item.description
                    it[level] = item.level
                    it[school] = item.school
                    it[ritual] = item.ritual
                    it[concentration] = item.concentration
                    it[lastAccessed] = item.lastAccessed
                }
            }
        }
    }

    override fun search(query: String): List<Spell> {
        return transaction {
            SpellsTable.selectAll().where { SpellsTable.name.lowerCase() like "%${query.lowercase()}%" }
                .map { rowToModel(it) }
        }
    }

    override fun getAll(): List<Spell> {
        return transaction {
            SpellsTable.selectAll()
                .orderBy(SpellsTable.name to SortOrder.ASC)
                .map { rowToModel(it) }
        }
    }

    override fun getRecent(limit: Int): List<Spell> {
        return transaction {
            SpellsTable.selectAll()
                .orderBy(SpellsTable.lastAccessed to SortOrder.DESC)
                .limit(limit)
                .map { rowToModel(it) }
        }
    }

    override fun delete(item: Spell) {
        transaction {
            SpellsTable.deleteWhere { SpellsTable.name.lowerCase() eq item.name.lowercase() }
        }
    }

    private fun rowToModel(row: ResultRow): Spell {
        return Spell(
            name = row[SpellsTable.name],
            description = row[SpellsTable.description],
            components = row[SpellsTable.components],
            castingTime = row[SpellsTable.castingTime],
            duration = row[SpellsTable.duration],
            level = row[SpellsTable.level],
            range = row[SpellsTable.range],
            ingredients = row[SpellsTable.ingredients],
            school = row[SpellsTable.school],
            ritual = row[SpellsTable.ritual],
            concentration = row[SpellsTable.concentration],
            lastAccessed = row[SpellsTable.lastAccessed]
        )
    }
}

data class Spell(
    val name: String,
    val duration: String,
    val components: String,
    val ingredients: String?,
    val description: String,
    val school: String,
    val level: Int,
    val range: String,
    val castingTime: String,
    val ritual: Boolean,
    val concentration: Boolean,
    val lastAccessed: Long
)