package repositories

import database.ItemEntity
import database.ItemsTable
import models.Item
import models.ItemType
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class ItemRepository : Repository<Item> {
    override fun save(item: Item) {
        transaction {
            ItemEntity.new {
                name = item.name
                type = item.type // Enum zu String
                description = item.description
                damage = item.damage
            }
        }
    }

    override fun getAll(): List<Item> {
        return transaction {
            ItemEntity.all().map { entityToModel(it)
            }
        }
    }

    override fun search(query: String): List<Item> {
        return transaction {
            ItemEntity.find { ItemsTable.name.lowerCase() like "%${query.lowercase()}%" }
                .map { entityToModel(it)}
        }
    }

    override fun delete(item: Item) {
        transaction {
            val itemToDelete = ItemEntity.find { ItemsTable.name.lowerCase() eq item.name.lowercase() }.firstOrNull()
            itemToDelete?.delete()
        }
    }

    private fun entityToModel(e: ItemEntity): Item {
        return Item(
            name = e.name,
            type = e.type,
            description = e.description,
            weight = e.weight,
            value = e.value,
            damage = e.damage
        )
    }
}