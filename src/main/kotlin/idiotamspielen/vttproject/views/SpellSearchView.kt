package idiotamspielen.vttproject.views

import idiotamspielen.vttproject.controllers.SpellSearchController
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.Priority
import tornadofx.*

class SpellSearchView : View() {
    private val controller: SpellSearchController by inject()

    override val root = borderpane {
        prefHeight = 450.0
        prefWidth = 450.0
        maxWidth = 500.0
        maxHeight = 500.0
        minWidth = 500.0
        minHeight = 500.0

        top {
            textfield {
                promptText = "Enter spell name to search"
                bind(controller.searchInput)
                setOnKeyPressed { event ->
                    if (event.code == KeyCode.ENTER) {
                        controller.handleSearch()
                        event.consume()
                    }
                }
            }
        }
        center {
            stackpane {
                listview(controller.spellList) {
                    visibleWhen { controller.spellName.isEmpty }
                    setOnMouseClicked { event: MouseEvent ->
                        if (event.clickCount == 2) {
                            val selectedSpellName = selectedItem
                            controller.handleSpellSelection(selectedSpellName)
                            event.consume()
                        }
                    }
                }

                gridpane {
                    visibleWhen { controller.spellName.isNotEmpty }
                    hgap = 10.0
                    vgap = 10.0
                    padding = insets(10.0)

                    columnConstraints.addAll(
                        ColumnConstraints().apply { percentWidth = 50.0 },
                        ColumnConstraints().apply { percentWidth = 50.0 }
                    )
                    row {
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.spellName) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }.gridpaneConstraints { columnSpan = 2 }
                    }
                    row {
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.castingTime) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.range) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                    }
                    row {
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.components) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.duration) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                    }
                    row {
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.ingredients) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }.gridpaneConstraints { columnSpan = 2 }
                    }
                    row {
                        scrollpane {
                            vbox {
                                textflow {
                                    text(controller.descArea) {
                                        addClass("text-field")
                                        style { fontSize = 17.px }
                                    }
                                }
                            }
                        }.gridpaneConstraints { columnSpan = 2 }
                    }
                    row {
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.level) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.school) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                    }
                    row {
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.ritual) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                        hbox {
                            alignment = Pos.CENTER
                            label(controller.concentration) {
                                addClass("text-field")
                                style { fontSize = 17.px }
                                hgrow = Priority.ALWAYS
                            }
                        }
                    }
                }
            }
        }
        bottom {
            stackpane {
                alignment = Pos.BOTTOM_CENTER
                button("Display Spells") {
                    action {controller.handleSearch()}
                }
                paddingAll = 10
            }
        }
    }
}