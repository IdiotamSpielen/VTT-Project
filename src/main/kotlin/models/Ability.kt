package models

class Ability(
    val name: String,
    val description: String,
    val linked_skills: List<Skill>,
)
