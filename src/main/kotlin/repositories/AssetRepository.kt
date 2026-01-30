package repositories

import database.ImageAssetEntity
import database.ImageAssetsTable
import domain.ElementType
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

class AssetRepository {

    fun updateLastAccessed(id: Int) {
        transaction {
            val asset = ImageAssetEntity.findById(id)
            asset?.lastAccessed = System.currentTimeMillis()
        }
    }

    // CREATE: Wir merken uns, dass wir dieses Bild besitzen
    fun addAsset(fileName: String, filePath: String, assetType: ElementType): Int {
        return transaction {
            ImageAssetEntity.new {
                name = fileName
                path = filePath
                type = assetType.name
            }.id.value
        }
    }

    // READ: Lade alle verfügbaren Bilder für eine Auswahl-Leiste (später)
    fun getAllAssets(): List<ImageAssetModel> { // Du brauchst ein kleines Model dafür
        return transaction {
            ImageAssetEntity.all().map {
                ImageAssetModel(it.id.value, it.name, it.path, ElementType.valueOf(it.type))
            }
        }
    }

    fun getRecentAssets(limit: Int = 10): List<ImageAssetModel> {
        return transaction {
            ImageAssetEntity.all()
                .orderBy(ImageAssetsTable.lastAccessed to SortOrder.DESC) // Neueste zuerst
                .limit(limit)
                .map {
                    ImageAssetModel(it.id.value, it.name, it.path, ElementType.valueOf(it.type))
                }
        }
    }

    // DELETE: Löscht Datenbank-Eintrag UND Datei!
    fun deleteAsset(id: Int) {
        transaction {
            val asset = ImageAssetEntity.findById(id)
            if (asset != null) {
                // 1. Datei von der Festplatte löschen
                val file = File(asset.path)
                if (file.exists()) {
                    file.delete()
                }
                // 2. Eintrag aus DB löschen
                asset.delete()
            }
        }
    }
}

// Kleines Hilfs-Model für den Transport (damit Entity nicht leakt)
data class ImageAssetModel(val id: Int, val name: String, val path: String, val type: ElementType){}