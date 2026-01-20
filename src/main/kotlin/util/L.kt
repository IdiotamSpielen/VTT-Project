package util

import services.LocalizationService

enum class L(val key: String) {
    // Generics
    CREATE("general.create"),
    SETTINGS("general.settings"),
    SPELL("general.spell"),

    // spell specifics
    SPELL_NAME("label.spell.name"),
    SPELL_CASTTIME("label.spell.castingTime"),
    SPELL_RANGE("label.spell.range"),
    SPELL_COMPONENTS("label.spell.components"),
    SPELL_DURATION("label.spell.duration"),
    SPELL_INGREDIENTS("label.spell.ingredients"),
    SPELL_LEVEL("label.spell.level"),
    SPELL_SCHOOL("label.spell.school"),
    SPELL_DESCRIPTION("label.spell.description"),

    // Checkboxes
    RITUAL("label.checkbox.ritual"),
    CONCENTRATION("label.checkbox.concentration"),

    SUCCESS("feedback.success"),

    // Error Messages
    ERR_NAME_EMPTY("error.name.empty"),
    ERR_LEVEL_RANGE("error.level.range"),
    ERR_CASTING_EMPTY("error.casting.empty"),
    ERR_DESC_EMPTY("error.desc.empty"),
    ERR_COMP_EMPTY("error.comp.empty"),
    ERR_RANGE_EMPTY("error.range.empty"),
    ERR_DURATION_EMPTY("error.duration.empty"),
    ERR_SCHOOL_EMPTY("error.school.empty"),
    ERR_GENERIC_INVALID("feedback.invalid"),
    ERR_SAVE_FAILED("error.save.failed"),
    ERR_UNEXPECTED("error.unexpected");

    //Helper function to minimize boilerplate
    fun t(args: Map<String, String> = emptyMap()): String {
        return LocalizationService.getString(this.key, args)
    }
}