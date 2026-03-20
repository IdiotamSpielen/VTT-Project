package utils

import services.LocalizationService

enum class L(val key: String) {
    // Generics
    CREATE("general.create"),
    SETTINGS("general.settings"),
    SPELL("general.spell"),
    ITEM("general.item"),
    SEARCH("general.search"),
    LANGUAGE("general.language"),
    LANG_EN("general.language.english"),
    LANG_DE("general.language.german"),
    LANG_ES("general.language.spanish"),
    TOKEN_SIZE("general.tokensize"),
    TOKEN_S("general.tokensize.small"),
    TOKEN_M("general.tokensize.medium"),
    TOKEN_L("general.tokensize.large"),
    TOKEN_XL("general.tokensize.xlarge"),
    CLEAR_DB("general.cleardb"),

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

    // Items
    ITEM_NAME("label.item.name"),
    ITEM_TYPE("label.item.type"),
    ITEM_DESC("label.item.description"),
    ITEM_DAMAGE("label.item.damage"),

    // Search specifics
    SEARCH_PLACEHOLDER("label.search.placeholder"),
    SEARCH_BUTTON("label.search.button"),
    SEARCH_BACK("label.search.back"),

    // Titles
    TITLE_SPELL_CREATE("title.spell.create"),
    TITLE_SPELL_SEARCH("title.spell.search"),
    TITLE_ITEM_CREATE("title.item.create"),

    // Actions
    BTN_SAVE("button.save"),
    BTN_SAVE_ITEM("button.save.item"),
    BTN_CANCEL("button.cancel"),

    // Checkboxes / Labels
    RITUAL("label.checkbox.ritual"),
    CONCENTRATION("label.checkbox.concentration"),
    YES("label.yes"),
    NO("label.no"),

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