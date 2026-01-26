package database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class ImageAssetEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ImageAssetEntity>(ImageAssetsTable)
    var path by ImageAssetsTable.path
    var name by ImageAssetsTable.name
    var type by ImageAssetsTable.type
    var lastAccessed by ImageAssetsTable.lastAccessed
}