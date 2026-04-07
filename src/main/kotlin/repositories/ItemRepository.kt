package repositories

import database.ItemsTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

/**
 * The repository connecting the Item Creation process to the appropriate Database Table
 */
class ItemRepository : Repository<Item> {

    /**
     *
     * @param Item the
     */
    override fun save(item: Item): Int? {
        return transaction {
            val existing = ItemsTable.selectAll().where { ItemsTable.name eq item.name }.firstOrNull()

            fun ItemsTable.mapColumns(it: UpdateBuilder<*>) {
                it[type] = item.type
                it[description] = item.description
                it[damage] = item.damage
                it[lastAccessed] = System.currentTimeMillis()
            }

            if (existing != null) {
                val id = existing[ItemsTable.id].value
                ItemsTable.update({ ItemsTable.id eq id }) {
                    mapColumns(it)
                }
                id
            } else {
                ItemsTable.insertAndGetId {
                    it[name] = item.name
                    mapColumns(it)
                }.value
            }
        }
    }

    override fun getAll(): List<Item> {
        return transaction {
            ItemsTable.selectAll().map { entityToModel(it)}
        }
    }

    override fun getRecent(limit: Int): List<Item> {
            TODO("Not yet implemented")
    }

    override fun search(query: String): List<Item> {
        return transaction {
            ItemsTable.selectAll().where { ItemsTable.name.lowerCase() like "%${query.lowercase()}%" }
                .map { entityToModel(it) }
        }
    }

    override fun delete(item: Item) {
        return transaction {
            val itemToDelete = ItemsTable.selectAll().where { ItemsTable.name eq item.name }.firstOrNull()
            if (itemToDelete != null) {
                ItemsTable.deleteWhere { ItemsTable.id eq itemToDelete[ItemsTable.id] }
            }
        }
    }

    private fun entityToModel(e: ResultRow): Item {
        return Item(
            name = e[ItemsTable.name],
            type = e[ItemsTable.type],
            description = e[ItemsTable.description],
            weight = e[ItemsTable.weight],
            damage = e[ItemsTable.damage],
            lastAccessed = e[ItemsTable.lastAccessed]
        )
    }
}

enum class ItemType {
    WEAPON,
    ARMOR,
    CONSUMABLE,
    MATERIALS,
    OTHER;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

data class Item(
    val name: String,
    val type: ItemType,
    val description: String,
    val weight: Double = 0.0,
    val value: Long = 0,
    val damage: String? = null,
    val lastAccessed: Long = 0
)