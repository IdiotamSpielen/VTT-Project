package repositories

import database.ImageAssetEntity
import database.ImageAssetsTable
import models.ElementType
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

class AssetRepository {

    fun updateLastAccessed(id: Int) {
        transaction {
            val asset = ImageAssetEntity.findById(id)
            asset?.lastAccessed = System.currentTimeMillis()
        }
    }

    // Register image asset for tracking
    fun addAsset(fileName: String, filePath: String, assetType: ElementType): Int {
        return transaction {
            val existingAsset = ImageAssetEntity.find {
                ImageAssetsTable.path eq filePath
            }.firstOrNull()

            if (existingAsset != null) {
                existingAsset.lastAccessed = System.currentTimeMillis()
                existingAsset.id.value
            } else {
                ImageAssetEntity.new {
                    name = fileName
                    path = filePath
                    type = assetType.name
                }.id.value
            }
        }
    }

    fun getAllAssets(): List<ImageAssetModel> {
        return transaction {
            ImageAssetEntity.all().map {
                ImageAssetModel(it.id.value, it.name, it.path, ElementType.valueOf(it.type))
            }
        }
    }

    fun getRecentAssets(limit: Int = 10): List<ImageAssetModel> {
        return transaction {
            ImageAssetEntity.all()
                .orderBy(ImageAssetsTable.lastAccessed to SortOrder.DESC) // Display most recently used assets first
                .limit(limit)
                .map {
                    ImageAssetModel(it.id.value, it.name, it.path, ElementType.valueOf(it.type))
                }
        }
    }

    // Permanently remove database entry and its corresponding physical file
    fun deleteAsset(id: Int) {
        transaction {
            val asset = ImageAssetEntity.findById(id)
            if (asset != null) {
                val file = File(asset.path)
                if (file.exists()) {
                    file.delete()
                }
                asset.delete()
            }
        }
    }

    /**
     * Clears all assets from the database.
     */
    fun clearAll() {
        transaction {
            ImageAssetEntity.all().forEach { it.delete() }
        }
    }
}

// UI-friendly model to avoid leaking database entities
data class ImageAssetModel(val id: Int, val name: String, val path: String, val type: ElementType){}