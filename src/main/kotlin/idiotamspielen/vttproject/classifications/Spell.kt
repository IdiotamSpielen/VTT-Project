package idiotamspielen.vttproject.classifications

import com.fasterxml.jackson.annotation.JsonProperty

data class Spell(
    @JsonProperty("name") val spellName: String,
    @JsonProperty("school") val school: String,
    @JsonProperty("duration") val duration: String,
    @JsonProperty("components") val components: String,
    @JsonProperty("level") val level: Int,
    @JsonProperty("range") val range: String,
    @JsonProperty("castingTime") val castingTime: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("ingredients") val ingredients: String,
    @JsonProperty("ritual") val ritual: Boolean,
    @JsonProperty("concentration") val concentration: Boolean
) : Nameable {
    override fun getName(): String = spellName
}
