package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object CharacterTable : IntIdTable(){
    val name = varchar("name", 255)
    val level = integer("level")
    val experience = long("experience")
    val hitPoints = integer("hit_points")
    val maxHitPoints = integer("max_hit_points")
    val armorClass = integer("armor_class")
    val initiative = integer("initiative")
    val speed = integer("speed")
    val charClassId = integer("char_class_id").references(CharClassTable.id)
}