package repositories

import database.ImageAssetsTable
import models.ElementType
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.io.File

class AssetRepository : Repository<ImageAssetModel> {

    override fun save(item: ImageAssetModel) {
        transaction {
            val existing = ImageAssetsTable.selectAll().where { ImageAssetsTable.path eq item.path }.firstOrNull()

            if (existing != null) {
                ImageAssetsTable.update({ ImageAssetsTable.path eq item.path }) {
                    it[lastAccessed] = System.currentTimeMillis()
                }
            } else {
                ImageAssetsTable.insert {
                    it[name] = item.name
                    it[path] = item.path
                    it[type] = item.type.name
                    it[lastAccessed] = System.currentTimeMillis()
                }
            }
        }
    }

    override fun getAll(): List<ImageAssetModel> {
        return transaction {
            ImageAssetsTable.selectAll().map { rowToModel(it) }
        }
    }

    override fun getRecent(limit: Int): List<ImageAssetModel> {
        return transaction {
            ImageAssetsTable.selectAll()
                .orderBy(ImageAssetsTable.lastAccessed to SortOrder.DESC)
                .limit(limit)
                .map { rowToModel(it) }
        }
    }

    override fun search(query: String): List<ImageAssetModel> {
        return transaction {
            ImageAssetsTable.selectAll()
                .where { ImageAssetsTable.name.lowerCase() like "%${query.lowercase()}%" }
                .map { rowToModel(it) }
        }
    }

    override fun delete(item: ImageAssetModel) {
        transaction {
            val file = File(item.path)
            if (file.exists()) {
                file.delete()
            }
            ImageAssetsTable.deleteWhere { path eq item.path }
        }
    }

    fun updateLastAccessed(id: Int) {
        transaction {
            ImageAssetsTable.update({ ImageAssetsTable.id eq id }) {
                it[lastAccessed] = System.currentTimeMillis()
            }
        }
    }

    private fun rowToModel(row: ResultRow): ImageAssetModel {
        return ImageAssetModel(
            id = row[ImageAssetsTable.id].value,
            name = row[ImageAssetsTable.name],
            path = row[ImageAssetsTable.path],
            type = ElementType.valueOf(row[ImageAssetsTable.type])
        )
    }
}

// UI-friendly model to avoid leaking database entities
data class ImageAssetModel(val id: Int, val name: String, val path: String, val type: ElementType)