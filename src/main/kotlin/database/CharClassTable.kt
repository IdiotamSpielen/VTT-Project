package database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object CharClassTable : IntIdTable(){
    val name = varchar("name", 255)
    val description = text("description")
    val hitDice = integer("hit_dice")
    val proficiencyBonus = integer("proficiency_bonus")
}