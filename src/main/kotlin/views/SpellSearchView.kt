package views

//import controllers.SpellSearchController
//import javafx.geometry.Pos
//import javafx.scene.control.PopupControl.USE_COMPUTED_SIZE
//import javafx.scene.input.KeyCode
//import javafx.scene.input.MouseEvent
//import javafx.scene.layout.ColumnConstraints
//import javafx.scene.layout.Priority
//import tornadofx.*
//
//class SpellSearchView : View() {
//    private val controller: SpellSearchController by inject()
//
//    init {
//        importStylesheet("/styles/spell_search.css")
//    }
//
//    override val root = borderpane {
//        prefHeight = USE_COMPUTED_SIZE
//        prefWidth = USE_COMPUTED_SIZE
//
//        top {
//            textfield {
//                promptText = "Enter spell name to search"
//                bind(controller.searchInput)
//                setOnKeyPressed { event ->
//                    if (event.code == KeyCode.ENTER) {
//                        controller.handleSearch()
//                        event.consume()
//                    }
//                }
//            }
//        }
//        center {
//            stackpane {
//                listview(controller.spellList) {
//                    visibleWhen { controller.spellName.isEmpty }
//                    setOnMouseClicked { event: MouseEvent ->
//                        if (event.clickCount == 2) {
//                            val selectedSpellName = selectedItem
//                            controller.handleSpellSelection(selectedSpellName)
//                            event.consume()
//                        }
//                    }
//                }
//
//                gridpane {
//                    visibleWhen { controller.spellName.isNotEmpty }
//                    hgap = 10.0
//                    vgap = 10.0
//                    padding = insets(10.0)
//
//                    columnConstraints.addAll(
//                        ColumnConstraints().apply { percentWidth = 50.0 },
//                        ColumnConstraints().apply { percentWidth = 50.0 }
//                    )
//                    row {
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.spellName) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }.gridpaneConstraints { columnSpan = 2 }
//                    }
//                    row {
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.castingTime) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.range) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                    }
//                    row {
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.components) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.duration) {
//                                addClass("spell-detail-textd")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                    }
//                    row {
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.ingredients) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }.gridpaneConstraints { columnSpan = 2 }
//                    }
//                    row {
//                        scrollpane {
//                            vbox {
//                                textflow {
//                                    text(controller.descArea) {
//                                        addClass("spell-detail-text")
//                                    }
//                                }
//                            }
//                        }.gridpaneConstraints { columnSpan = 2 }
//                    }
//                    row {
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.level) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.school) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                    }
//                    row {
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.ritual) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                        hbox {
//                            alignment = Pos.CENTER
//                            label(controller.concentration) {
//                                addClass("spell-detail-text")
//                                hgrow = Priority.ALWAYS
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        bottom {
//            stackpane {
//                alignment = Pos.BOTTOM_CENTER
//                button("Display Spells") {
//                    action {controller.handleSearch()}
//                }
//                paddingAll = 10
//            }
//        }
//    }
//}